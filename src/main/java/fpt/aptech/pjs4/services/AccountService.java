package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.DTOs.AuthLoginToken;
import fpt.aptech.pjs4.DTOs.Introspect;
import fpt.aptech.pjs4.entities.Account;

import java.util.List;
import java.util.Map;

public interface AccountService {
    Account createAccount(Account account);

    Account getAccountById(int id);

    List<Account> getAllAccounts();

    Account updateAccount(int id, Account account);

    void deleteAccount(int id);

    AuthLoginToken AuthLogin(String email, String password);

    Introspect introspect(Introspect request);

    Map<String, Object> getClaimsFromToken(String token);
}
