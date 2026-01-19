package bank.client_person.service.impl;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import bank.client_person.dto.request.ClientDeleteRequest;
import bank.client_person.dto.request.ClientUpdateRequest;
import bank.client_person.dto.request.CreateClientRequest;
import bank.client_person.dto.response.ClientResponse;
import bank.client_person.dto.response.ClientUpdateResponse;
import bank.client_person.dto.response.CreateClientResponse;
import bank.client_person.dto.response.PageResponse;
import bank.client_person.entity.Client;
import bank.client_person.kafka.producer.ClientEventProducer;
import bank.client_person.mapper.ClientMapper;
import bank.client_person.repository.ClientRepository;
import bank.client_person.service.ClientService;


@Service
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder;
    private final ClientEventProducer clientEventProducer;

    ClientServiceImpl(ClientRepository clientRepository,
                       ClientMapper clientMapper,
                       PasswordEncoder passwordEncoder,
                       ClientEventProducer clientEventProducer
    ){
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.passwordEncoder = passwordEncoder;
        this.clientEventProducer = clientEventProducer;
    }

    @Override
    @Transactional
    public CreateClientResponse registerClient(CreateClientRequest request) {

        // Find existen client
        clientRepository
            .findByIdentification(request.getIdentification())
            .ifPresent( existentClient -> {
                    throw new ResponseStatusException(
                                    HttpStatus.CONFLICT, 
                                    "Client "+ request.getIdentification() + " already registered");
                    }); 
        
        // Map request to entity
        Client client = clientMapper.toEntity(request);

        // Encode password
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        client.setPassword(encodedPassword);
        
        // Register client
        Client savedClient = clientRepository.save(client);

        // Compute age
        savedClient.setAge(computeAge(savedClient.getBirthDate()));
        
        // Compute clientID
        savedClient.setClientId(generateClientId(savedClient.getPersonId()));

        // Update client
        clientRepository.save(savedClient);

        // Event publisher
        clientEventProducer.sendMessage(clientMapper.toClientEvent(savedClient, request.getAccountType().toString()));

        // Map client to response
        return clientMapper.toCreateClienteResponse(savedClient);
    }

    private String computeAge(LocalDate birthDate) {
        return String.valueOf(Period.between(birthDate, LocalDate.now()).getYears());
    }

    private String generateClientId(Long id) {
        return String.format("CLI-%06d", id);
    }

    @Override
    public PageResponse<ClientResponse> getClients(int pageNumber, int pageSize) {

        // Paged clients
        Page<Client> pageClient = clientRepository.findAll(PageRequest.of(pageNumber, pageSize));
        
        // Map to page response
        return new PageResponse<>(
            pageClient.getContent()
                .stream()
                .map(clientMapper::toClientResponse)
                .toList(),
            pageClient.getNumber(),
            pageClient.getSize(),
            pageClient.getTotalElements(),
            pageClient.getTotalPages()
        );
    }

    @Override
    public ClientUpdateResponse updateClient(ClientUpdateRequest request) {

        // Find client
        Client client = findClientOrThrow(request.getPersonId());
        // Update data allowed
        client.setAddress(request.getAddress());
        client.setCellPhone(request.getCellPhone());

        // Save changes
        Client savedClient = clientRepository.save(client);

        // Map to response
        return clientMapper.toClientUpdateResponse(savedClient);
    }

    @Override
    public void deleteClient(ClientDeleteRequest request) {

        // Find client
        findClientOrThrow(request.getPersonId());

        // Delete client
        clientRepository.deleteById(request.getPersonId());
    }
    
    private Client findClientOrThrow(Long personId) {
        return clientRepository.findById(personId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
    }
}
