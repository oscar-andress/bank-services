package bank.client_person.entity;

import bank.client_person.model.Person;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Client extends Person{

    @Column(name = "client_id", unique = true, length = 10)
    private String clientId;

    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String password;

    @Column(name = "status", nullable = false)
    private Boolean status = true;
}
