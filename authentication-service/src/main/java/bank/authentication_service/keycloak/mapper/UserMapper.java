package bank.authentication_service.keycloak.mapper;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import bank.authentication_service.dto.request.ClientCreateRequest;
import bank.authentication_service.dto.response.ClientCreateResponse;

@Component
public class UserMapper {

    public UserRepresentation toUserRepresentation(ClientCreateRequest request) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.username());
        user.setEnabled(true);
        user.setEmailVerified(false);
        return user;
    }

    public CredentialRepresentation toCredentialRepresentation(ClientCreateRequest request) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.password());
        credential.setTemporary(false);
        return credential;
    }

    public ClientCreateResponse toClientCreateResponse(String userId) {
        return new ClientCreateResponse(userId);
    }
}
