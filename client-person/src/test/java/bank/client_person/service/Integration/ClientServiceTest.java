package bank.client_person.service.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.server.ResponseStatusException;

import bank.client_person.data.TestData;
import bank.client_person.dto.request.CreateClientRequest;
import bank.client_person.dto.response.CreateClientResponse;
import bank.client_person.entity.Client;
import bank.client_person.kafka.producer.ClientEventProducer;
import bank.client_person.repository.ClientRepository;
import bank.client_person.service.ClientService;
import bank.common_lib.event.dto.client.ClientCreateEvent;
import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional

class ClientServiceTest {
    private final ClientService clientService;
    private final ClientRepository clientRepository;
    
    @MockitoBean
    private ClientEventProducer clientEventProducer;
    
    private Client client;
    private CreateClientRequest request;
    private ArgumentCaptor<ClientCreateEvent> eventCaptor; 

    @Autowired
    public ClientServiceTest(ClientService clientService,
                             ClientRepository clientRepository
    ){
        this.clientService = clientService;
        this.clientRepository = clientRepository;
    }

    @BeforeEach
    void setUp(){
        // GIVEN
        clientRepository.deleteAll();
        
        client = TestData.generateClientData();
        clientRepository.save(client);

        request = TestData.generateRequestData();
    }

    @Test
    void registerClient_Success_WhenClientNotExist(){
        
        clientRepository.deleteAll();

        // ACT
        CreateClientResponse result = clientService.registerClient(request);

        // THEN
        assertNotNull(result);
        assertNotNull(result.getClientId());

        // VERIFY DB
        Client savedClient = clientRepository.findByIdentification(request.getIdentification())
            .orElseThrow();

        assertNotNull(savedClient.getPersonId());

        // VERIFY KAFKA EVENT
        eventCaptor = ArgumentCaptor.forClass(ClientCreateEvent.class);
        verify(clientEventProducer).produceClientEvent(eventCaptor.capture());
        
        ClientCreateEvent capturedEvent = eventCaptor.getValue();
        assertEquals(savedClient.getClientId(), capturedEvent.getClientId());
        assertEquals(request.getAccountType(), capturedEvent.getAccountType());
    }

    @Test
    void registerClient_Success_WhenClientExist(){

        // ACT
        ResponseStatusException result = assertThrows(
            ResponseStatusException.class, 
            () -> clientService.registerClient(request));
        
        // THEN
        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertTrue(result.getReason().contains("Client "+ request.getIdentification() +" already registered"));
    
    }


}
