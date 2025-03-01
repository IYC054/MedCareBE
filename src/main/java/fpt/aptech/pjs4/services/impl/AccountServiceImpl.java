package fpt.aptech.pjs4.services.impl;


import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import fpt.aptech.pjs4.DTOs.request.AuthencicationRequest;
import fpt.aptech.pjs4.DTOs.response.AuthencicationResponse;
import fpt.aptech.pjs4.configs.FirebaseConfig;
import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.enums.Status;
import fpt.aptech.pjs4.enums.Role;
import fpt.aptech.pjs4.exceptions.AppException;
import fpt.aptech.pjs4.exceptions.ErrorCode;
import fpt.aptech.pjs4.repositories.AccountRepository;
import fpt.aptech.pjs4.services.AccountService;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @NonFinal
    protected static final String SIGNER_KEY = "5e3b6f9e67e9f1e3b6ad775d9a1c9078c9078b72ad34d3e4e745fb6b64367861";

    private final FirebaseConfig firebaseConfig;
    public AccountServiceImpl(FirebaseConfig firebaseConfig) {
        this.firebaseConfig = firebaseConfig;
    }
    public String getUserToken(String userId) {
        Firestore db = firebaseConfig.getFirestore();
        DocumentReference docRef = db.collection("user_data").document(userId);

        try {
            DocumentSnapshot document = docRef.get().get();
            if (document.exists() && document.contains("token")) {
                return document.getString("token");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AuthencicationResponse authenticate(AuthencicationRequest request) {
        // tim tai khoang voi ten dang nhap
        var username = accountRepository.findAccountByEmail(request.getEmail()).orElseThrow(() -> new AppException(ErrorCode.USER_EXITED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // check pass có khớp  ko
        boolean authencatite = passwordEncoder.matches(request.getPassword(), username.getPassword());
        if (!authencatite) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(username);
        AuthencicationResponse authencicationResponse = new AuthencicationResponse();
        authencicationResponse.setToken(token);
        authencicationResponse.setAuthenticated(true);
        authencicationResponse.setUser(username);
        return authencicationResponse;

    }

    private String generateToken(Account account) {
        // tao header jwt chon thuat toan
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        // tap payload Data trong body
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                // dai dien cho usser dang nhap
                .subject(account.getEmail())
                // xac nhan issure tu ai
                .issuer("nghimathit.com")
                // thoi diem hien tai
                .issueTime(new Date())
                //  thời hạn token
                .claim("scope", buildScope(account))
                .claim("id", account.getId())
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

    private String buildScope(Account account) {
        // được sử dụng để kết nối các chuỗi lại với nhau (join strings)
        // một cách dễ dàng, đặc biệt khi bạn cần kết hợp các phần tử của
        // một tập hợp hoặc danh sách mà không cần phải lo lắng về dấu phân
        // cách hay việc xử lý dấu phẩy ở cuối chuỗi
        StringJoiner stringJoiner = new StringJoiner(" ");
       if (!CollectionUtils.isEmpty(account.getRole())) {
            account.getRole().forEach(role->{
                stringJoiner.add(role.getName());
                if(!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
                }
            });
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
        roles.add(Role.PATIENTS.name());
        account.setStatus(Status.ONLINE);
        //account.setRole(roles);
        return accountRepository.save(account);
    }

    @Override
    public boolean getAccountExists(String email) {
        return accountRepository.existsAccountByEmail(email);
    }

    @Override
    // chỉ User Id nào mới truy cập id đó được thôi và Role Admin
    // email trong entity so sanh voi email trong token
    @PostAuthorize("returnObject.email == authentication.name or hasRole('ADMIN')")
    public Account getAccountById(int id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Ko tìm thấy user"));
        return account;
    }

    @Override
    // chỉ có Role Admin mới truy cập đc vào method này
    @PreAuthorize("hasRole('ADMIN')")
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }


//    @Override
//    @PostAuthorize("returnObject.email == authentication.name")
//    public Account updateAccount(int id, Account account) {
//        Account account1 = getAccountById(id);
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        account.setPassword(passwordEncoder.encode(account.getPassword()));
//        if (accountRepository.existsById(id)) {
//            account.setId(id);

//    @Override
//    @PostAuthorize("returnObject.email == authentication.name")
//    public Account updateAccount(int id, Account account) {
//        Account account1 = getAccountById(id);
//        if (accountRepository.existsById(id)) {
//            account.setId(id);
//
////            if (account1.getPassword().equals(account.getPassword())) {
////                throw new AppException(ErrorCode.CHECK_UPDATEPASS);
////            }
//
//            return accountRepository.save(account);
//        }
//        return null;
//    }
@Override
//@PostAuthorize("returnObject.email == authentication.name")
@PostAuthorize("returnObject.email == authentication.name or hasRole('ADMIN')")
public Account updateAccount(int id, Account account) {
    Account existingAccount = getAccountById(id);


    if (existingAccount == null) {
        throw new AppException(ErrorCode.USER_EXITED);
    }

    // Giữ nguyên ID của tài khoản
    account.setId(id);

    // Kiểm tra nếu mật khẩu mới được cung cấp, mã hóa trước khi lưu
    if (account.getPassword() != null && !account.getPassword().isEmpty()) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Nếu mật khẩu mới khác với mật khẩu cũ thì mới cập nhật
        if (!passwordEncoder.matches(account.getPassword(), existingAccount.getPassword())) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
        } else {
            throw new AppException(ErrorCode.CHECK_UPDATEPASS);
        }
    } else {
        // Nếu không nhập mật khẩu mới, giữ nguyên mật khẩu cũ
        account.setPassword(existingAccount.getPassword());
    }

    // Giữ nguyên avatar nếu không cập nhật mới
    if (account.getAvatar() == null || account.getAvatar().isEmpty()) {
        account.setAvatar(existingAccount.getAvatar());
    }

    // Giữ nguyên các thông tin không thay đổi
    account.setRole(existingAccount.getRole());

    return accountRepository.save(account);
}


    @Override
    public void deleteAccount(int id) {
        accountRepository.deleteById(id);
    }


    @Override
    // lấy thông tin infor của chủ token
    public Account getMyInfor(){
        var context = SecurityContextHolder.getContext(); // thong tin infor cua token
        String email = context.getAuthentication().getName();
        Account byUserEmail= accountRepository.findAccountByEmail(email).orElseThrow(()->new AppException(ErrorCode.USER_EXITED));
        Account userResponse = new Account();
        userResponse.setEmail(byUserEmail.getEmail());
        userResponse.setName(byUserEmail.getName());
        userResponse.setPhone(byUserEmail.getPhone());
        userResponse.setGender(byUserEmail.getGender());
        userResponse.setBirthdate(byUserEmail.getBirthdate());
        userResponse.setAvatar(byUserEmail.getAvatar());
        return userResponse;
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

    //for chat
    @Override
    public void addConnectedAccount(Account account) {
        var storedAccount = accountRepository.findAccountByEmail(account.getEmail()).orElse(null);
        if (storedAccount != null) {
            storedAccount.setStatus(Status.ONLINE);
            accountRepository.save(storedAccount);
        }
    }
    @Override
    public void disconnect(Account account) {
        var storedAccount = accountRepository.findAccountByEmail(account.getEmail()).orElse(null);
        if (storedAccount != null) {
            storedAccount.setStatus(Status.OFFLINE);
            accountRepository.save(storedAccount);
        }
    }

    @Override
    public List<Account> findConnectedAccount() {
        return accountRepository.findAllByStatus(Status.ONLINE);
    }
}
