package bank.client_person.mapper;

import org.springframework.stereotype.Component;

import bank.client_person.dto.message.event.ClientEvent;
import bank.client_person.dto.request.CreateClientRequest;
import bank.client_person.dto.response.ClientResponse;
import bank.client_person.dto.response.ClientUpdateResponse;
import bank.client_person.dto.response.CreateClientResponse;
import bank.client_person.entity.Client;



@Component
public class ClientMapper {
    public Client toEntity(CreateClientRequest request){
        Client client = new Client();
        client.setBirthDate(request.getBirthDate());
        client.setCellPhone(request.getCellPhone());
        client.setAddress(request.getAddress());
        client.setGender(request.getGender());
        client.setIdentification(request.getIdentification());
        client.setName(request.getName());
        return client;
    }

    public CreateClientResponse toCreateClienteResponse(Client client){
        CreateClientResponse response = new CreateClientResponse();
        response.setClientId(client.getClientId());
        return response;
    }

    public ClientResponse toClientResponse(Client client){
        ClientResponse response = new ClientResponse();
        response.setClientId(client.getClientId());
        response.setPersonId(client.getPersonId());
        response.setName(client.getName());
        response.setGender(client.getGender());
        response.setBirthDate(client.getBirthDate());
        response.setAge(client.getAge());
        response.setIdentification(client.getIdentification());
        response.setAddress(client.getAddress());
        response.setCellPhone(client.getCellPhone());
        response.setStatus(client.getStatus());
        return response;
    }

    public ClientUpdateResponse toClientUpdateResponse(Client client){
        ClientUpdateResponse response = new ClientUpdateResponse();
        response.setAddress(client.getAddress());
        response.setCellPhone(client.getCellPhone());
        response.setPersonId(client.getPersonId());
        return response;
    }

    public ClientEvent toClientEvent(Client client, String accountType){
        ClientEvent event = new ClientEvent();
        event.setClientId(client.getClientId());
        event.setAccountType(accountType);
        return event;
    }
}
