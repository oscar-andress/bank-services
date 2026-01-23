package bank.account_movement.kafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "spring.kafka.topics")
@Getter
@Setter
@NoArgsConstructor

public class KafkaTopicsProperties {
    private String createAccount;
    private String createMovement;
    private String deleteMovement;
}
