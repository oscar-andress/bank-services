package bank.authentication_service.keycloak.facade;

import java.util.List;

import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import bank.authentication_service.dto.request.ClientCreateRequest;
import bank.authentication_service.keycloak.config.KeycloackProperties;
import jakarta.ws.rs.core.Response;

@Component
public class KeycloakFacade {

    private final Keycloak keycloak;
    private final KeycloackProperties keyCloackProperties;

    KeycloakFacade(Keycloak keycloak,
                      KeycloackProperties keycloackProperties
    ){
        this.keycloak = keycloak;
        this.keyCloackProperties = keycloackProperties;
    }

    public String createUser(ClientCreateRequest request) {

        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.username());
        user.setEnabled(true);
        user.setEmailVerified(false);

        Response response = getRealm()
                                .users()
                                .create(user);

        return CreatedResponseUtil.getCreatedId(response);
    }

    public void assignCredentials(String userId, String password){
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);

        getRealm()
                .users()
                .get(userId)
                .resetPassword(credential);
    }

    public void assignRole(String userId) {

        RoleRepresentation role = keycloak
                                    .realm(keyCloackProperties.getRealm())
                                    .roles()
                                    .get("CLIENT")
                                    .toRepresentation();

        getRealm()
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(List.of(role));
    }

    private RealmResource getRealm() {
        return keycloak.realm(keyCloackProperties.getRealm());
    }

    public void deleteUser(String userId){
        getRealm()                
                .users()
                .get(userId)
                .remove();
    }

}
