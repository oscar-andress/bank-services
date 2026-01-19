package bank.client_person.dto.request;

import java.time.LocalDate;

import bank.client_person.enumeration.AccountType;
import bank.client_person.enumeration.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CreateClientRequest {
    
    @NotBlank(message = "Name is required")
    @Size(max = 200, message = "Name must not exceed 200 characters")
    private String name;

    @NotBlank(message = "Gender is required")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;

    @NotBlank(message = "Identification is required")
    @Size(min = 10, max = 10, message = "Identification must have 10 digits")
    private String identification;

    @NotBlank(message = "Address is required")
    @Size(max = 200, message = "Address must not exced 200 characters")
    private String address;

    @NotBlank(message = "Password is required")
    @Size(min = 10, max = 10, message = "CellPhone must have 10 digits")
    private String cellPhone;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password at least have 6 digits")
    private String password;

    @NotBlank(message = "Account type is required")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
}
