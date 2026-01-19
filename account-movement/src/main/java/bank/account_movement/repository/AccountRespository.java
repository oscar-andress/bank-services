package bank.account_movement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import bank.account_movement.entity.Account;

public interface AccountRespository extends JpaRepository<Account, Long>{
    List<Account> findByClientIdOrderByAccountNumber(String clientId);

    @Query(value = """
                SELECT account_number
                FROM tbl_account
                WHERE client_id = :clientId
                AND account_type = :accountType
            """
            , nativeQuery = true)
    Optional<String> queryFindAccountNumber(@Param("clientId") String clientId,
                                     @Param("accountType") String accountType);

    @Query(value = """
                SELECT LPAD(
                    CASE
                        WHEN :accountType = 'SAVING' THEN nextval('account_number_saving_seq')
                        WHEN :accountType = 'CHECKING'  THEN nextval('account_number_checking_seq')
                    END::text,
                    6,
                    '0'
                )
            """
            , nativeQuery = true)
    String queryFindNextAccountNumber(@Param("accountType") String accountType);
}
