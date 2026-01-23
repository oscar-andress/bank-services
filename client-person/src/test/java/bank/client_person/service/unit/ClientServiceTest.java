package bank.client_person.service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import bank.client_person.data.TestData;
import bank.client_person.dto.request.CreateClientRequest;
import bank.client_person.dto.response.CreateClientResponse;
import bank.client_person.entity.Client;
import bank.client_person.kafka.producer.ClientEventProducer;
import bank.client_person.mapper.ClientMapper;
import bank.client_person.repository.ClientRepository;
import bank.client_person.service.impl.ClientServiceImpl;
import bank.common_lib.event.dto.client.ClientCreateEvent;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ClientEventProducer clientEventProducer;

    @InjectMocks
    private ClientServiceImpl clientServiceImpl;

    @Captor
    private ArgumentCaptor<Client> clientCaptor;

    private CreateClientRequest request;
    private CreateClientResponse response;
    private Client client;
    private ClientCreateEvent clientEvent;
    

    @BeforeEach
    void setUp(){
        // GIVEN

        request = TestData.generateRequestData();

        client = TestData.generateClientData();
        
        response = TestData.generateResponseData();

        clientEvent = TestData.generateClientEventData();
    }

    @Test
    void registerClient_Success_WhenClientNotExists(){

        // WHEN
        when(clientRepository.findByIdentification(request.getIdentification()))
            .thenReturn(Optional.empty());
    
        when(passwordEncoder.encode(request.getPassword()))
            .thenReturn(client.getPassword());

        when(clientMapper.toEntity(request))
            .thenReturn(client);
        
        when(clientRepository.save(any(Client.class)))
            .thenReturn(client);
        
        when(clientMapper.toClientEvent(any(Client.class), eq(request.getAccountType())))
            .thenReturn(clientEvent);

        when(clientMapper.toCreateClienteResponse(any(Client.class)))
            .thenReturn(response);

        // ACT
        CreateClientResponse result = clientServiceImpl.registerClient(request);

        // THEN
        assertNotNull(result);
        assertEquals(response.getClientId(), result.getClientId());

        // VERIFY DB
        verify(clientRepository, times(2))
            .save(clientCaptor.capture());

        // VERIFY KAFKA
        verify(clientEventProducer, times(1))
            .produceClientEvent(any(ClientCreateEvent.class));
        
        Client savedClient = clientCaptor.getValue();
        assertEquals(client.getAge(), savedClient.getAge());
        assertEquals(client.getPassword(), savedClient.getPassword());
        assertEquals(clientEvent.getClientId(), result.getClientId());
    }

    @Test
    void registerClient_Fails_WhenClientExists(){

        // WHEN
        when(clientRepository.findByIdentification(request.getIdentification()))
            .thenReturn(Optional.of(client));

        // ACT - THEN
        ResponseStatusException result = assertThrows(ResponseStatusException.class, 
                     () -> clientServiceImpl.registerClient(request));

        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertTrue(result.getMessage().contains("Client "+ request.getIdentification() + " already registered"));
        
        verifyNoInteractions(passwordEncoder);
        verifyNoInteractions(clientMapper);
        verify(clientRepository, never()).save(any(Client.class));
    }
}
