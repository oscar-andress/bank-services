package bank.authentication_service.service.impl;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import bank.authentication_service.dto.request.ClientCreateRequest;
import bank.authentication_service.dto.response.ClientCreateResponse;
import bank.authentication_service.exception.client.ClientNotCreatedException;
import bank.authentication_service.keycloak.facade.KeycloakFacade;
import bank.authentication_service.keycloak.mapper.UserMapper;
import bank.authentication_service.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService{

    private final KeycloakFacade keycloakFacade;
    private final UserMapper userMapper;

    ClientServiceImpl(KeycloakFacade keycloakFacade,
                      UserMapper userMapper
    ) {
        this.keycloakFacade = keycloakFacade;
        this.userMapper = userMapper;
    }

    @Override
    public ClientCreateResponse createClient(ClientCreateRequest request) {

        String userId = null;

        try {

            UserRepresentation user = userMapper.toUserRepresentation(request);
            userId = keycloakFacade.createUser(user);

            CredentialRepresentation credential = userMapper.toCredentialRepresentation(request);
            keycloakFacade.assignCredentials(userId, credential);

            keycloakFacade.assignRole(userId);
            
            return userMapper.toClientCreateResponse(userId);
            
        } catch (Exception e) {
            if(userId != null) keycloakFacade.deleteUser(userId);
            throw new ClientNotCreatedException(e.getMessage());
        }
        
    }
    
}
