package bank.client_person.data;

import java.time.LocalDate;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import bank.client_person.dto.message.event.ClientEvent;
import bank.client_person.dto.request.CreateClientRequest;
import bank.client_person.dto.response.CreateClientResponse;
import bank.client_person.entity.Client;
import bank.client_person.enumeration.AccountType;
import bank.client_person.enumeration.Gender;


public final class TestData {

    private static final String IDENTIFICATION = "1001122334";
    private static final LocalDate BIRTH_DATE = LocalDate.of(1996, 03, 17);
    private static final String PASSWORD = "mySecretP@ssword";
    private static final String ENCODED_PASSWORD = "encodedPassword";
    private static final Gender GENDER = Gender.MALE;
    private static final String CELL_PHONE = "0987654321";
    private static final String NAME = "Oscar Andres Vega Arellano";
    private static final String ADDRESS = "Muy lejos";
    private static final String AGE = "29";
    private static final String CLIENT_ID = "CLI-000001";
    private static final AccountType ACCOUNT_TYPE = AccountType.SAVING;

    public static CreateClientRequest generateRequestData(){
        CreateClientRequest request = new CreateClientRequest();
        request.setIdentification(IDENTIFICATION);
        request.setBirthDate(BIRTH_DATE);
        request.setPassword(PASSWORD);
        request.setGender(GENDER);
        request.setCellPhone(CELL_PHONE);
        request.setName(NAME);
        request.setAddress(ADDRESS);
        request.setAccountType(ACCOUNT_TYPE);
        return request;
    }

    public static Client generateClientData(){
        Client client = new Client();
        client.setAddress(ADDRESS);
        client.setAge(AGE);
        client.setBirthDate(BIRTH_DATE);
        client.setCellPhone(CELL_PHONE);
        client.setClientId(CLIENT_ID);
        client.setGender(GENDER);
        client.setIdentification(IDENTIFICATION);
        client.setName(NAME);
        client.setPassword(ENCODED_PASSWORD);
        return client;
    }

    public static CreateClientResponse generateResponseData(){
        CreateClientResponse response = new CreateClientResponse();
        response.setClientId(CLIENT_ID);
        return response;
    }

    public static ClientEvent generateClientEventData(){
        ClientEvent clientEvent = new ClientEvent();
        clientEvent.setAccountType(ACCOUNT_TYPE.toString());
        clientEvent.setClientId(CLIENT_ID);
        return clientEvent;
    }

    public static Properties generateProperties(String kafkaBosstraperServer){
        // Properties for events
        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBosstraperServer);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return consumerProps;
    }
}
