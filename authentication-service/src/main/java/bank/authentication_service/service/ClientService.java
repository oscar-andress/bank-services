package bank.authentication_service.service;

import bank.authentication_service.dto.request.ClientCreateRequest;
import bank.authentication_service.dto.response.ClientCreateResponse;

public interface ClientService {
    ClientCreateResponse createClient(ClientCreateRequest request);
}
