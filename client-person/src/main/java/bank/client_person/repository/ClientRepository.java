package bank.client_person.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import bank.client_person.entity.Client;


public interface ClientRepository extends JpaRepository<Client, Long>{
    Optional<Client> findByIdentification(String identification);
    Page<Client> findAll(Pageable pageable);
}
