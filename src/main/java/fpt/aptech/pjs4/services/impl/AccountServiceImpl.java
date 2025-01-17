package fpt.aptech.pjs4.services.impl;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import fpt.aptech.pjs4.DTOs.request.AuthencicationRequest;
import fpt.aptech.pjs4.DTOs.request.IntrospecRequest;
import fpt.aptech.pjs4.DTOs.response.AuthencicationResponse;
import fpt.aptech.pjs4.DTOs.response.IntrospecResponse;
import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.enums.Role;
import fpt.aptech.pjs4.exceptions.AppException;
import fpt.aptech.pjs4.exceptions.ErrorCode;
import fpt.aptech.pjs4.repositories.AccountRepository;
import fpt.aptech.pjs4.services.AccountService;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.StringJoiner;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @NonFinal
    protected static final String SIGNER_KEY = "5e3b6f9e67e9f1e3b6ad775d9a1c9078c9078b72ad34d3e4e745fb6b64367861";

    @Override
    public AuthencicationResponse authenticate(AuthencicationRequest request) {
        // tim tai khoang voi ten dang nhap
        var username = accountRepository.findAccountByEmail(request.getEmail()).orElseThrow(() -> new AppException(ErrorCode.USER_EXITED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // check pass có khớp  ko
        boolean authencatite = passwordEncoder.matches(request.getPassword(), username.getPassword());
        if (!authencatite) {
            throw new AppException(ErrorCode.CHECK_LOGIN);
        }
        var token = generateToken(username);
        AuthencicationResponse authencicationResponse = new AuthencicationResponse();
        authencicationResponse.setToken(token);
        authencicationResponse.setAuthenticated(true);
        return authencicationResponse;

    }

    private String generateToken(Account account) {
        // tao header jwt chon thuat toan
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        // tap payload Data trong body
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                // dai dien cho usser dang nhap
                .subject(account.getName())
                // xac nhan issure tu ai
                .issuer("nghimathit.com")
                // thoi diem hien tai
                .issueTime(new Date())
                //  thời hạn token
                .expirationTime(new Date(Instant.now().plus(10, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", buildScope(account))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject()); // chỉ nhận Json
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        // ký token
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
//            log.error(" ko the tao token",e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public IntrospecResponse introspec(IntrospecRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        // check token het han chua
        Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verifide = signedJWT.verify(verifier);
        IntrospecResponse introspecResponse = new IntrospecResponse();
        introspecResponse.setValid(verifide && expityTime.after(new Date()));
        return introspecResponse;
    }

    private String buildScope(Account account) {
        // được sử dụng để kết nối các chuỗi lại với nhau (join strings)
        // một cách dễ dàng, đặc biệt khi bạn cần kết hợp các phần tử của
        // một tập hợp hoặc danh sách mà không cần phải lo lắng về dấu phân
        // cách hay việc xử lý dấu phẩy ở cuối chuỗi
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(account.getRole())) {
            account.getRole().forEach(item -> stringJoiner.add(item));
        }
        return stringJoiner.toString();
    }

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
    @Override
    public Account createAccount(Account account) {
        if (account.getPassword() == null || account.getPassword().isEmpty()) {
            throw new RuntimeException("Password cannot be empty");
        }
        if (accountRepository.existsAccountByEmail(account.getEmail())) {
            throw new AppException(ErrorCode.CHECK_EMAIL);
        }
        if (accountRepository.existsAccountByPhone(account.getPhone())) {
            throw new AppException(ErrorCode.CHECK_PHONE);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        account.setRole(roles);
        return accountRepository.save(account);
    }

    @Override
    public Account getAccountById(int id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Ko tìm thấy user"));
        return account;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account updateAccount(int id, Account account) {
        Account account1 = getAccountById(id);
        if (accountRepository.existsById(id)) {
            account.setId(id);
            if (account1.getPassword().equals(account.getPassword())) {
                throw new AppException(ErrorCode.CHECK_UPDATEPASS);
            }

            return accountRepository.save(account);
        }
        return null;
    }

    @Override
    public void deleteAccount(int id) {
        accountRepository.deleteById(id);
    }

    //@Override
//    public AuthLoginToken AuthLogin(String email, String password) {
//        var user = accountRepository.findAccountByEmail(email).orElseThrow(() -> new RuntimeException("Email not found"));
//
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
//        if(passwordEncoder.matches(password, user.getPassword())) {
//            var token = generateToken(email);
//            return new AuthLoginToken(user.getId(), user.getEmail(), token, user.getRole(), user.getGender());
//        }else{
//            throw new RuntimeException("Wrong password");
//        }
//    }

//    public Map<String, Object> getClaimsFromToken(String token) {
//        try {
//            // Giải mã token
//            JWSObject jwsObject = JWSObject.parse(token);
//
//            // Xác minh chữ ký của token
//            if (!jwsObject.verify(new MACVerifier(SIGNER_KEY.getBytes()))) {
//                throw new RuntimeException("Token không hợp lệ");
//            }
//
//            // Lấy toàn bộ claims trong token
//            JWTClaimsSet claimsSet = JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
//            return claimsSet.getClaims();
//
//        } catch (Exception e) {
//            throw new RuntimeException("Lỗi khi xử lý token", e);
//        }
//    }

//    public String generateToken(String email){
//        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
//        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
//                .subject(email) // (thông tin về người dùng)
//                .issuer("localhost") //(người phát hành token)
//                .issueTime(new Date()) //(thời điểm token được phát hành)
//                .expirationTime(new Date( //(thời gian hết hạn)
//                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli() //hết hạn sau 1 giờ
//                ))
//                .claim("customClaim", "Custom")
//                .build();
//        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
//        JWSObject jwsObject = new JWSObject(header, payload);
//        //private key và secret key
//        //private key dùng để ký
//        //secret key dùng để verify token
//        try {
//            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
//            return  jwsObject.serialize();
//        } catch (JOSEException e) {
//            log.error("Cannot create token");
//            throw new RuntimeException(e);
//        }
//    }
}
