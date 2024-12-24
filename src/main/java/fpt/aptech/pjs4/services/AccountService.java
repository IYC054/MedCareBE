package fpt.aptech.pjs4.services;

import com.nimbusds.jose.JOSEException;
import fpt.aptech.pjs4.DTOs.AuthLoginToken;
import fpt.aptech.pjs4.DTOs.Introspect;
import fpt.aptech.pjs4.DTOs.request.AuthencicationRequest;
import fpt.aptech.pjs4.DTOs.request.IntrospecRequest;
import fpt.aptech.pjs4.DTOs.response.AuthencicationResponse;
import fpt.aptech.pjs4.DTOs.response.IntrospecResponse;
import fpt.aptech.pjs4.entities.Account;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface AccountService {
    Account createAccount(Account account);

    Account getAccountById(int id);

    List<Account> getAllAccounts();

    Account updateAccount(int id, Account account);

    void deleteAccount(int id);

    public AuthencicationResponse authenticate(AuthencicationRequest request);

    public IntrospecResponse introspec (IntrospecRequest request) throws JOSEException, ParseException;
}
