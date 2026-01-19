package bank.client_person.model;

import java.io.Serializable;
import java.time.LocalDate;

import bank.client_person.enumeration.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass

public class Person implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq")
    @SequenceGenerator(name = "client_seq", sequenceName = "client_seq", allocationSize = 1)
    @Column(name = "person_id", nullable = false, updatable = false)
    private Long personId;

    @Size (max = 200,  message = "Name must not exceed 200 characters")
    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 20, nullable = false)
    private Gender gender;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "age", length = 3)
    private String age;

    @Size(min = 10,  message = "Identification must have 10 digits")
    @Column(name = "identification", nullable = false, length = 10)
    private String identification;

    @Size(max = 200,  message = "Address must not exceed 200 characters")
    @Column(name = "address", nullable = false, length = 200)
    private String address;

    @Size(min = 10,  message = "CellPhone must have 10 digits")
    @Column(name = "cell_phone", length = 10)
    private String cellPhone;
}
