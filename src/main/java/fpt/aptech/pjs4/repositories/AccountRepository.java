package fpt.aptech.pjs4.repositories;

import fpt.aptech.pjs4.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findAccountByEmail(String email);
    boolean existsAccountByEmail(String email);
    boolean existsAccountByPhone(String phone);
    Account findAccountByPassword(String password);
}
