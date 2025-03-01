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
    void addConnectedAccount(Account account);
    List<Account> findConnectedAccount();
    void disconnect(Account account);
    String getUserToken(String userId);
    Account createAccount(Account account);

    //    protected static final String SIGNER_KEY = "jxNGPYNsP81q9q4AnUtVIkA6oKsjP8657q4PfkXz2e+tfqofPtrJTLW9dtgOJrUc";
    //    @Override
    //    public Introspect introspect(Introspect request) {
    //        var token = request.getToken();
    //
    //        try {
    //            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
    //            SignedJWT signedJWT = SignedJWT.parse(token);
    //            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
    //            var verify = signedJWT.verify(verifier);
    //            if (verify && expiryTime.after(new Date())) {
    //                request.setAuthenticated(true);
    //
    //            } else {
    //                request.setAuthenticated(false);
    //            }
    //        } catch (JOSEException | ParseException e) {
    //            throw new RuntimeException(e);
    //        }
    //        return request;
    //
    //    }

    boolean getAccountExists(String email);

    Account getAccountById(int id);

    List<Account> getAllAccounts();

    Account updateAccount(int id, Account account);

    void deleteAccount(int id);

    public Account getMyInfor();

    public AuthencicationResponse authenticate(AuthencicationRequest request);

//    public IntrospecResponse introspec (IntrospecRequest request) throws JOSEException, ParseException;
    public Map<String, Object> getClaimsFromToken(String token);
}

