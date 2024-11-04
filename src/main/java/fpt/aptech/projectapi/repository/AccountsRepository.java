package fpt.aptech.projectapi.repository;

import fpt.aptech.projectapi.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findAccountByEmail(String email);
    Account findAccountByPassword(String password);
}
