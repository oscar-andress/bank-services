package bank.client_person.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ClientUpdateResponse {
    private Long personId;
    private String address;
    private String cellPhone;
}
