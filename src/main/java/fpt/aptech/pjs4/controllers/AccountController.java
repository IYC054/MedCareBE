package fpt.aptech.pjs4.controllers;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import fpt.aptech.pjs4.DTOs.APIResponse;
import fpt.aptech.pjs4.DTOs.AccountDTO;
import fpt.aptech.pjs4.DTOs.Introspect;
import fpt.aptech.pjs4.DTOs.request.AuthencicationRequest;
import fpt.aptech.pjs4.DTOs.response.AuthencicationResponse;
import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.entities.Role;
import fpt.aptech.pjs4.repositories.RoleRepository;
import fpt.aptech.pjs4.services.AccountService;
import fpt.aptech.pjs4.services.PatientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/account")
public class AccountController {
    @Value("src/main/resources/static/image/")
    private String fileUpload;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PatientService patientService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate; // Để gửi message qua WebSocket

    @GetMapping("/find")
    public ResponseEntity<Boolean> findByEmail(@RequestParam String email) {
        boolean exists = accountService.getAccountExists(email); // ✅ Lấy kết quả thực tế
        return ResponseEntity.ok(exists);
    }


//    @MessageMapping("/user.addUser")
//    @SendTo("/user/topic")
    @PostMapping("/register")
    public APIResponse<Account> createAccount2(@ModelAttribute AccountDTO accountDTO,
                                               @RequestParam List<String> role,
                                               @RequestPart(value = "avatar", required = false) MultipartFile image) { // Chú ý: required = false
        try {
            // Lưu hình ảnh
            String imageUrl = null;
            if (image != null && !image.isEmpty()) {
                Path uploadDir = Paths.get("src/main/resources/static/image");
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }

                String uniqueFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
                Path destinationPath = uploadDir.resolve(uniqueFileName);
                Files.write(destinationPath, image.getBytes());

                imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/image/")
                        .path(uniqueFileName)
                        .toUriString();
            }

            // Tạo account
            Account account = new Account();
            account.setEmail(accountDTO.getEmail());
            account.setName(accountDTO.getName());
            account.setPassword(accountDTO.getPassword());
            account.setPhone(accountDTO.getPhone());
            account.setGender(accountDTO.getGender());
            account.setBirthdate(LocalDate.parse(accountDTO.getBirthdate()));


            // Lấy danh sách vai trò
            List<Role> roles = roleRepository.findAllById(role);
            account.setRole(roles);

            // Thiết lập avatar (nếu có)
            account.setAvatar(imageUrl);

            // Lưu account
            APIResponse<Account> apiResponse = new APIResponse<>();
            Account account1 = accountService.createAccount(account);

            // Gửi thông báo WebSocket
//            messagingTemplate.convertAndSend("/user/public", account1);

            apiResponse.setResult(account1);
            apiResponse.setMessage("Account created successfully!");
            if (role.contains("PATIENTS")) {
                patientService.createPatient(account1.getId());
            }

            return apiResponse;

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload avatar", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping()
    public APIResponse<Account> createAccount(@ModelAttribute AccountDTO accountDTO,
                                              @RequestParam List<String> role,
                                              @RequestPart(value = "avatar", required = false) MultipartFile image) { // Chú ý: required = false
        try {
            // Lưu hình ảnh
            String imageUrl = null;
            if (image != null && !image.isEmpty()) {
                Path uploadDir = Paths.get("src/main/resources/static/image");
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }

                String uniqueFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
                Path destinationPath = uploadDir.resolve(uniqueFileName);
                Files.write(destinationPath, image.getBytes());

                imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/image/")
                        .path(uniqueFileName)
                        .toUriString();
            }

            // Tạo account
            Account account = new Account();
            account.setEmail(accountDTO.getEmail());
            account.setName(accountDTO.getName());
            account.setPassword(accountDTO.getPassword());
            account.setPhone(accountDTO.getPhone());
            account.setGender(accountDTO.getGender());
            account.setBirthdate(LocalDate.parse(accountDTO.getBirthdate()));

            // Lấy danh sách vai trò
            List<Role> roles = roleRepository.findAllById(role);
            account.setRole(roles);

            // Thiết lập avatar (nếu có)
            account.setAvatar(imageUrl);

            // Lưu account
            APIResponse<Account> apiResponse = new APIResponse<>();
            Account account1 = accountService.createAccount(account);

            // Gửi thông báo WebSocket
//            messagingTemplate.convertAndSend("/user/public", account1);

            apiResponse.setResult(account1);
            apiResponse.setMessage("Account created successfully!");
            if (role.contains("PATIENTS")) {
                patientService.createPatient(account1.getId());
            }

            return apiResponse;

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload avatar", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference users = db.collection("user_data");

        // 🔍 Tìm kiếm email trong Firestore
        Query query = users.whereEqualTo("email", email);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        try {
            List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
            boolean exists = !documents.isEmpty(); // Nếu có document => email tồn tại
            System.out.println("📌 Kiểm tra email: " + email + " - Tồn tại: " + exists);
            return ResponseEntity.ok(exists);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(false); // Lỗi server
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable int id) {
        Account account = accountService.getAccountById(id);
        return ResponseEntity.ok(account);
    }

    @GetMapping
    public APIResponse<List<Account>> getAllAccounts() {
        APIResponse<List<Account>> apiResponse = new APIResponse<>();
        List<Account> accounts = accountService.getAllAccounts();
        if (accounts != null) {
            apiResponse.setResult(accounts);
            apiResponse.setMessage("Success");
        } else {
            apiResponse.setCode(404);
            apiResponse.setMessage("Account not found");
        }
        return apiResponse;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(
            @PathVariable int id,
            @RequestParam(required = false) List<String> role,
            @ModelAttribute AccountDTO accountDTO,
            @RequestPart(value = "avatar", required = false) MultipartFile image) throws IOException {

        Account existingAccount = accountService.getAccountById(id);
        if (existingAccount == null) {
            throw new EntityNotFoundException("Account not found");
        }

        // Xử lý avatar (chỉ thay đổi nếu có ảnh mới)
        String imageUrl = existingAccount.getAvatar();
        if (image != null && !image.isEmpty()) {
            Path uploadDir = Paths.get("src/main/resources/static/image");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String uniqueFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path destinationPath = uploadDir.resolve(uniqueFileName);
            Files.write(destinationPath, image.getBytes());

            imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/image/")
                    .path(uniqueFileName)
                    .toUriString();
        }

        // Cập nhật thông tin tài khoản nếu có giá trị mới
        if (accountDTO.getEmail() != null && !accountDTO.getEmail().isEmpty()) {
            existingAccount.setEmail(accountDTO.getEmail());
        }
        if (accountDTO.getName() != null && !accountDTO.getName().isEmpty()) {
            existingAccount.setName(accountDTO.getName());
        }
        if (accountDTO.getPassword() != null && !accountDTO.getPassword().isEmpty()) {
            existingAccount.setPassword(accountDTO.getPassword());
        }
        if (accountDTO.getPhone() != null && !accountDTO.getPhone().isEmpty()) {
            existingAccount.setPhone(accountDTO.getPhone());
        }
        if (accountDTO.getGender() != null) {
            existingAccount.setGender(accountDTO.getGender());
        }
        if (accountDTO.getBirthdate() != null) {
            existingAccount.setBirthdate(LocalDate.parse(accountDTO.getBirthdate()));

        }

        // Cập nhật vai trò nếu có thay đổi
        if (role != null && !role.isEmpty()) {
            existingAccount.setRole(roleRepository.findAllById(role));
        }

        // Cập nhật avatar mới nếu có
        existingAccount.setAvatar(imageUrl);

        // Lưu thay đổi vào database
        Account updatedAccount = accountService.updateAccount(id, existingAccount);

        return ResponseEntity.ok(Map.of(
                "message", "Account updated successfully!",
                "account", updatedAccount
        ));
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<Account> deleteAccount(@PathVariable int id) {
        Account acc = accountService.getAccountById(id);
        if(acc != null) {
            accountService.deleteAccount(id);
            return ResponseEntity.ok(acc);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(acc);
        }
    }
     // lấy thông tin của chủ token
    @GetMapping("/myinfor")
    public APIResponse<Account>  getMyInfor() {
        APIResponse<Account> apiResponse = new APIResponse<>();
        apiResponse.setResult(accountService.getMyInfor());
        return apiResponse;
    }


//    // login
//    @PostMapping("/login")
//    public APIResponse<AuthLoginToken> login(@RequestBody Account loginRequest) {
//        APIResponse<AuthLoginToken> apiResponse = new APIResponse<>();
//        AuthLoginToken acc = accountService.AuthLogin(loginRequest.getEmail(), loginRequest.getPassword());
//        apiResponse.setResult(acc);
//        apiResponse.setMessage("Login successfully");
//
//        return apiResponse;
//    }
    // login để lấy token
    @PostMapping("/token")
    public APIResponse<AuthencicationResponse> authencication(@RequestBody AuthencicationRequest request) {
        var result = accountService.authenticate(request);
        APIResponse<AuthencicationResponse> apiResponse = new APIResponse<>();
        apiResponse.setResult(result);
        return apiResponse;
    }
//    @PostMapping("/login/token")
//    public APIResponse<Introspect> login(@RequestBody Introspect loginRequest) {
//        APIResponse<Introspect> apiRespone = new APIResponse<>();
//
//        Introspect acc = accountService.introspect(loginRequest);
//
//        if (acc.isAuthenticated()) {
//            apiRespone.setResult(acc);
//            apiRespone.setMessage("Login successfully with token");
//        } else {
//            apiRespone.setResult(null);
//            apiRespone.setMessage("Invalid token");
//            apiRespone.setCode(401);
//        }
//
//        return apiRespone;
//    }
// post token kiểm tra còn sống mình tạo ra ko
@PostMapping("/introspect")
public Map<String, Object> authentication(@RequestBody Introspect introspect) {
    String token = introspect.getToken();

    // Trả về claims từ token
    return accountService.getClaimsFromToken(token);
}
//    @GetMapping("/detail/token")
//    public Map<String, Object> getTokenClaims(@RequestHeader("Authorization") String token) {
//        // Loại bỏ tiền tố "Bearer " nếu có
//        if (token.startsWith("Bearer ")) {
//            token = token.substring(7);
//        }
//
//        // Gọi service để lấy toàn bộ claims từ token
//        return accountService.getClaimsFromToken(token);
//    }
}
