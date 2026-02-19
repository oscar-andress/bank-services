package bank.authentication_service.keycloak.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "keycloak")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class KeycloackProperties {
    private String realm;
    private String authServerUrl;
    private String sslRequired;
    private String clientId;
    private String clientSecret;
}
