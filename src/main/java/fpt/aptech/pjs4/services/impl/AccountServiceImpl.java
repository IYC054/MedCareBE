package fpt.aptech.pjs4.services.impl;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import fpt.aptech.pjs4.DTOs.AuthLoginToken;
import fpt.aptech.pjs4.DTOs.Introspect;
import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.repositories.AccountRepository;
import fpt.aptech.pjs4.services.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    protected static final String SIGNER_KEY = "jxNGPYNsP81q9q4AnUtVIkA6oKsjP8657q4PfkXz2e+tfqofPtrJTLW9dtgOJrUc";
    @Override
    public Introspect introspect(Introspect request) {
        var token = request.getToken();

        try {
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            var verify = signedJWT.verify(verifier);
            if (verify && expiryTime.after(new Date())) {
                request.setAuthenticated(true);

            } else {
                request.setAuthenticated(false);
            }
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException(e);
        }
        return request;

    }
    @Override
    public Account createAccount(Account account) {
        if (account.getPassword() == null || account.getPassword().isEmpty()) {
            throw new RuntimeException("Password cannot be empty");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public Account getAccountById(int id) {
        Account account = accountRepository.findById(id).orElseThrow( () -> new RuntimeException("Ko tìm thấy user"));
        return account;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account updateAccount(int id, Account account) {
        if (accountRepository.existsById(id)) {
            account.setId(id);
            return accountRepository.save(account);
        }
        return null;
    }

    @Override
    public void deleteAccount(int id) {
        accountRepository.deleteById(id);
    }

    @Override
    public AuthLoginToken AuthLogin(String email, String password) {
        var user = accountRepository.findAccountByEmail(email).orElseThrow(() -> new RuntimeException("Email not found"));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if(passwordEncoder.matches(password, user.getPassword())) {
            var token = generateToken(email);
            return new AuthLoginToken(user.getId(), user.getEmail(), token, user.getRole(), user.getGender());
        }else{
            throw new RuntimeException("Wrong password");
        }
    }

    public Map<String, Object> getClaimsFromToken(String token) {
        try {
            // Giải mã token
            JWSObject jwsObject = JWSObject.parse(token);

            // Xác minh chữ ký của token
            if (!jwsObject.verify(new MACVerifier(SIGNER_KEY.getBytes()))) {
                throw new RuntimeException("Token không hợp lệ");
            }

            // Lấy toàn bộ claims trong token
            JWTClaimsSet claimsSet = JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
            return claimsSet.getClaims();

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi xử lý token", e);
        }
    }

    public String generateToken(String email){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(email) // (thông tin về người dùng)
                .issuer("localhost") //(người phát hành token)
                .issueTime(new Date()) //(thời điểm token được phát hành)
                .expirationTime(new Date( //(thời gian hết hạn)
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli() //hết hạn sau 1 giờ
                ))
                .claim("customClaim", "Custom")
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        //private key và secret key
        //private key dùng để ký
        //secret key dùng để verify token
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return  jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token");
            throw new RuntimeException(e);
        }
    }
}
