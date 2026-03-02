package bank.api_gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import reactor.core.publisher.Mono;

@Configuration
public class RateLimiterConfig {
    
    @Primary
    @Bean(name = "tokenKeyResolver")
    public KeyResolver tokenKeyResolver(){
        return exchange -> 
            exchange.getPrincipal()
                    .cast(JwtAuthenticationToken.class)
                    .map(auth -> auth.getToken().getSubject());
    }

    @Bean(name = "ipKeyResolver")
    public KeyResolver ipKeyResolver() {
        return exchange -> {

            String ip = exchange.getRequest()
                    .getHeaders()
                    .getFirst("X-Forwarded-For");

            if (ip == null) {
                ip = exchange.getRequest()
                        .getRemoteAddress()
                        .getAddress()
                        .getHostAddress();
            }

            return Mono.just(ip);
        };
    }

}
