package bank.account_movement.dto.request;

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

public class AccountUpdateRequest {
    
    @NotNull(message = "Account id is required")
    @Positive(message = "Account id must be positive")
    private Long accountId;

    @NotNull(message = "Status is required")
    private Boolean status;
}
