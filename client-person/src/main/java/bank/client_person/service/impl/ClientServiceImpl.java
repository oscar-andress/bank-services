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

        findClientIfPresentThrowExistent(request.getIdentification());

        Client client = clientMapper.toEntity(request);

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        client.setPassword(encodedPassword);
        
        Client savedClient = clientRepository.save(client);

        savedClient.setAge(computeAge(savedClient.getBirthDate()));
        
        savedClient.setClientId(generateClientId(savedClient.getPersonId()));

        clientRepository.save(savedClient);

        clientEventProducer.produceClientEvent(clientMapper.toClientEvent(savedClient, request.getAccountType()));

        return clientMapper.toCreateClienteResponse(savedClient);
    }

    private void findClientIfPresentThrowExistent(String identification){
        clientRepository
            .findByIdentification(identification)
            .ifPresent( existentClient -> {
                    throw new ResponseStatusException(
                                    HttpStatus.CONFLICT, 
                                    "Client "+ identification+ " already registered");
                    }); 
    }

    private String computeAge(LocalDate birthDate) {
        return String.valueOf(Period.between(birthDate, LocalDate.now()).getYears());
    }

    private String generateClientId(Long id) {
        return String.format("CLI-%06d", id);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ClientResponse> getClients(int pageNumber, int pageSize) {

        Page<Client> pageClient = clientRepository.findAll(PageRequest.of(pageNumber, pageSize));
        
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
    @Transactional
    public ClientUpdateResponse updateClient(ClientUpdateRequest request) {

        Client client = findClientOrElseThrowNotFound(request.getPersonId());
        client.setAddress(request.getAddress());
        client.setCellPhone(request.getCellPhone());

        Client savedClient = clientRepository.save(client);

        return clientMapper.toClientUpdateResponse(savedClient);
    }

    @Override
    @Transactional
    public void deleteClient(ClientDeleteRequest request) {

        findClientOrElseThrowNotFound(request.getPersonId());

        clientRepository.deleteById(request.getPersonId());
    }
    
    private Client findClientOrElseThrowNotFound(Long personId) {
        return clientRepository.findById(personId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
    }
}
