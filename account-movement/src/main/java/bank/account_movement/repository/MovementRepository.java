package bank.account_movement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import bank.account_movement.entity.Movement;

public interface MovementRepository extends JpaRepository<Movement, Long> {
    Page<Movement> findByAccountIdOrderByMovementDateDescMovementHourDesc(Long accountId, Pageable pageable);
}
