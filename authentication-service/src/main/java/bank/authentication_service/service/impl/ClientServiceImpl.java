package bank.authentication_service.service.impl;

import org.springframework.stereotype.Service;

import bank.authentication_service.dto.request.ClientCreateRequest;
import bank.authentication_service.dto.response.ClientCreateResponse;
import bank.authentication_service.exception.client.ClientNotCreatedException;
import bank.authentication_service.keycloak.facade.KeycloakFacade;
import bank.authentication_service.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService{

    private final KeycloakFacade keycloakFacade;

    ClientServiceImpl(KeycloakFacade keycloakFacade) {
        this.keycloakFacade = keycloakFacade;
    }

    @Override
    public ClientCreateResponse createClient(ClientCreateRequest request) {

        String userId = null;

        try {
            userId = keycloakFacade.createUser(request);
            keycloakFacade.assignCredentials(userId, request.password());
            keycloakFacade.assignRole(userId);
            return new ClientCreateResponse(userId);
        } catch (Exception e) {
            if(userId != null) keycloakFacade.deleteUser(userId);
            throw new ClientNotCreatedException(e.getMessage());
        }
        
    }
    
}
