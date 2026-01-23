package bank.client_person.kafka.config;

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
    private String createClient;
}
