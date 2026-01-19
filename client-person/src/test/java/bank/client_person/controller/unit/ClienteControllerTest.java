package bank.client_person.controller.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bank.client_person.controller.ClienteController;
import bank.client_person.data.TestData;
import bank.client_person.dto.request.CreateClientRequest;
import bank.client_person.dto.response.CreateClientResponse;
import bank.client_person.service.ClientService;


@WebMvcTest(ClienteController.class)
@AutoConfigureMockMvc(addFilters = false)

class ClienteControllerTest {
   
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ClientService clientService;

    private CreateClientResponse response;
    private CreateClientRequest request;
    private String stringJsonRequest;

    @BeforeEach
    void setUp() throws JsonProcessingException{
        // GIVEN
        // Parse data to String JSON
        request = TestData.generateRequestData();
        stringJsonRequest = objectMapper.writeValueAsString(request);
        response = TestData.generateResponseData();
    }

    @Test
    void registerClient_Success_WhenClientNotExists() throws Exception{

        // WHEN
        when(clientService.registerClient(any()))
            .thenReturn(response);
        
        // ACT - THEN
        mockMvc.perform(
            post("/bank/api/v1/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringJsonRequest)
        )
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.clientId").value(response.getClientId()));
        
        // Verify
        verify(clientService).registerClient(any(CreateClientRequest.class));
    }

    @Test
    void registerClient_Success_WhenClientExists() throws Exception{

        // WHEN
        doThrow(new ResponseStatusException(
            HttpStatus.CONFLICT,
            "Client "+ request.getIdentification()+ " already registered"
        )).when(clientService).registerClient(any(CreateClientRequest.class));
        
        // ACT - THEN
        mockMvc.perform(
            post("/bank/api/v1/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringJsonRequest)
        )
        .andDo(print())
        .andExpect(status().isConflict())
        .andExpect(status().reason(containsString("Client "+ request.getIdentification()+" already registered")));
        
        // Verify
        verify(clientService).registerClient(any(CreateClientRequest.class));
    }

}
