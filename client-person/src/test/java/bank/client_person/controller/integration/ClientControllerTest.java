package bank.client_person.controller.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bank.client_person.data.TestData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

class ClientControllerTest {

    @Container
    static KafkaContainer kafka = new KafkaContainer(
        DockerImageName.parse("confluentinc/cp-kafka:7.6.0")
    )
    .withEnv("KAFKA_AUTO_CREATE_TOPICS_ENABLE", "true")
    .withExposedPorts(9093);
    
    @DynamicPropertySource
    static void configureKafka(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${spring.kafka.create-client-topic.name}")
    private String createClientTopic;

    @PersistenceContext
    private EntityManager entityManager;

    private String clientEvent;
    private String request;
    private Properties consumerProps;

    @BeforeEach
    void setUp() throws JsonProcessingException{
        // GIVEN
        
        request = objectMapper.writeValueAsString(TestData.generateRequestData());
        clientEvent = objectMapper.writeValueAsString(TestData.generateClientEventData());
        
        consumerProps = TestData.generateProperties(kafka.getBootstrapServers());
    }

    @Test
    void registerClient_Success_WhenClientNotExists() throws Exception{
        

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Collections.singletonList(createClientTopic)); 

        // ACT - WHEN - THEN
        mockMvc.perform(
            post("/bank/api/v1/client")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request)
        )
        .andDo(print())
        .andExpect(status().isCreated());

        // VERIFY - WAIT AND CONSUME EVENT
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
        
        assertFalse(records.isEmpty(), "At least one event");
        
        records.forEach(record -> {
            assertNotNull(record.value());
            assertEquals(record.value(), clientEvent);
        });
        
        consumer.close();
    }

    @Test
    void registerClient_Success_WhenClientExists() throws Exception{

        // ACT - WHEN
        // Cliente register
        mockMvc.perform(
            post("/bank/api/v1/client")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request)
        )
        .andDo(print())
        .andExpect(status().isCreated());

        // THEN

        // ACT - WHEN - THEN
        // Client exist
        mockMvc.perform(
            post("/bank/api/v1/client")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request)
        )
        .andDo(print())
        .andExpect(status().isConflict());
    }
}
