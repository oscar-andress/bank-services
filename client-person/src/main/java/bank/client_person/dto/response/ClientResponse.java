package bank.client_person.dto.response;

import java.time.LocalDate;

import bank.client_person.enumeration.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ClientResponse {
    private String clientId;
    private Long personId;
    private String name;
    private Gender gender;
    private LocalDate birthDate;
    private String age;
    private String identification;
    private String address;
    private String cellPhone;
    private Boolean status;
}
