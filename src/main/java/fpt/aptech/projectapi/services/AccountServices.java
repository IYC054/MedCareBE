package fpt.aptech.projectapi.services;

import fpt.aptech.projectapi.DTOs.Introspect;
import fpt.aptech.projectapi.entities.Account;
import fpt.aptech.projectapi.DTOs.AuthLoginToken;

import java.util.List;
import java.util.Map;

public interface AccountServices {
    Account createAccount(Account account);
    Account getAccountById(int id);
    List<Account> getAllAccounts();
    Account updateAccount(int id, Account account);
    void deleteAccount(int id);
    AuthLoginToken AuthLogin(String email, String password);
    Introspect introspect(Introspect request);
    Map<String, Object> getClaimsFromToken(String token);
}
