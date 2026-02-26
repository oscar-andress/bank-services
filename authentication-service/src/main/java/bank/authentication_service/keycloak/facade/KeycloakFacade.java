package bank.authentication_service.keycloak.facade;

import java.util.List;

import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bank.authentication_service.exception.client.ClientNotCreatedException;
import bank.authentication_service.keycloak.config.KeycloackProperties;
import bank.authentication_service.keycloak.dto.response.UserCreateErrorResponse;
import jakarta.ws.rs.core.Response;

@Component
public class KeycloakFacade {

    private final Keycloak keycloak;
    private final KeycloackProperties keyCloackProperties;
    private final ObjectMapper objectMapper;

    KeycloakFacade(Keycloak keycloak,
                   KeycloackProperties keycloackProperties,
                   ObjectMapper objectMapper

    ){
        this.keycloak = keycloak;
        this.keyCloackProperties = keycloackProperties;
        this.objectMapper = objectMapper;
    }

    public String createUser(UserRepresentation user) {

        Response response = getRealm().users().create(user);
        validateResponse(response);
        return CreatedResponseUtil.getCreatedId(response);
    }

    public void assignCredentials(String userId, CredentialRepresentation credential){ 
        getRealm().users().get(userId).resetPassword(credential);
    }

    public void assignRole(String userId) {
        
        RoleRepresentation role = keycloak
                                    .realm(keyCloackProperties.getRealm())
                                    .roles()
                                    .get("CLIENT")
                                    .toRepresentation();

        getRealm().users().get(userId).roles().realmLevel().add(List.of(role));
    }

    private RealmResource getRealm() {
        return keycloak.realm(keyCloackProperties.getRealm());
    }

    public void deleteUser(String userId){
        getRealm().users().get(userId).remove();
    }

    private void validateResponse(Response response){

        if(response.getStatus() != HttpStatus.CREATED.value()){
            String rawBody = response.readEntity(String.class);
            String message = parseErrorMessage(rawBody);
            throw new ClientNotCreatedException(message);
        }
    }

    private String parseErrorMessage(String rawBody) {
        
        try {
            UserCreateErrorResponse error = objectMapper.readValue(rawBody, UserCreateErrorResponse.class);
            return error.errorMessage() != null ? error.errorMessage() : rawBody;
        } catch (JsonProcessingException e) {
            return rawBody;
        }
    }

}
