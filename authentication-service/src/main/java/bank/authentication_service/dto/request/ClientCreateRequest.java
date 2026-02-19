package bank.authentication_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClientCreateRequest(
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 10, message = "Name must not exceed 10 characters")
    String username,

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password at least have 6 digits")
    String password

) { }
