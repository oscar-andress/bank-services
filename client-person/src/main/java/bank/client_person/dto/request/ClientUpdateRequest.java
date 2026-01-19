package bank.client_person.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ClientUpdateRequest {

    @NotNull(message = "Person id is required")
    @Positive(message = "Person id must be positive")
    private Long personId;

    @NotBlank(message = "Address is required")
    @Size(max = 200, message = "Address must not exced 200 characters")
    private String address;

    @NotBlank(message = "Cell phone is required")
    @Size(min = 10, max = 10, message = "CellPhone must have 10 digits")
    private String cellPhone;
}
