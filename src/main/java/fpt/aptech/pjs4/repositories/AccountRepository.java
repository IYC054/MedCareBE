package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findAccountByEmail(String email);
    boolean existsAccountByEmail(String email);
    boolean existsAccountByPhone(String phone);
    Account findAccountByPassword(String password);

    @Query("SELECT a FROM Account a WHERE a.email = :email")
    Account findByEmail(@Param("email") String email);

}
