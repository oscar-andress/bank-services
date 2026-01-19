package bank.client_person.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ClientDeleteRequest {
    @NotNull(message = "Person id is required")
    @Positive(message = "Person id must be positive")
    private Long personId;
}
