package bank.api_gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Configuration
public class RateLimiterConfig {
    
    @Bean
    public KeyResolver tokenKeyResolver(){
        return exchange -> 
            exchange.getPrincipal()
                    .cast(JwtAuthenticationToken.class)
                    .map(auth -> auth.getToken().getSubject());
    }
}
