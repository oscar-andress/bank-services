package bank.client_person.service;

import bank.client_person.dto.request.ClientDeleteRequest;
import bank.client_person.dto.request.ClientUpdateRequest;
import bank.client_person.dto.request.CreateClientRequest;
import bank.client_person.dto.response.ClientResponse;
import bank.client_person.dto.response.ClientUpdateResponse;
import bank.client_person.dto.response.CreateClientResponse;
import bank.client_person.dto.response.PageResponse;

public interface ClientService {
    CreateClientResponse registerClient(CreateClientRequest client);
    PageResponse<ClientResponse> getClients(int pageNumber, int pageSize);
    ClientUpdateResponse updateClient(ClientUpdateRequest request);
    void deleteClient(ClientDeleteRequest request);
}
