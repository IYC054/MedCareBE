package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.DTOs.APIResponse;
import fpt.aptech.pjs4.DTOs.AuthLoginToken;
import fpt.aptech.pjs4.DTOs.Introspect;
import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @PostMapping
    public APIResponse<Account> createAccount(@RequestBody @Valid Account account) {
        try {
            APIResponse<Account> apiResponse = new APIResponse<>();
            apiResponse.setResult(accountService.createAccount(account));
            apiResponse.setMessage("Account created successfully!");
            return apiResponse;
        } catch (Exception e) {
            throw new RuntimeException(e);
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
    public ResponseEntity<Account> updateAccount(@PathVariable int id, @RequestBody Account account) {
        Account updatedAccount = accountService.updateAccount(id, account);
        return ResponseEntity.ok(updatedAccount);
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

    @PostMapping("/login")
    public APIResponse<AuthLoginToken> login(@RequestBody Account loginRequest) {
        APIResponse<AuthLoginToken> apiResponse = new APIResponse<>();
        AuthLoginToken acc = accountService.AuthLogin(loginRequest.getEmail(), loginRequest.getPassword());
        apiResponse.setResult(acc);
        apiResponse.setMessage("Login successfully");

        return apiResponse;
    }
    @PostMapping("/login/token")
    public APIResponse<Introspect> login(@RequestBody Introspect loginRequest) {
        APIResponse<Introspect> apiRespone = new APIResponse<>();

        Introspect acc = accountService.introspect(loginRequest);

        if (acc.isAuthenticated()) {
            apiRespone.setResult(acc);
            apiRespone.setMessage("Login successfully with token");
        } else {
            apiRespone.setResult(null);
            apiRespone.setMessage("Invalid token");
            apiRespone.setCode(401);
        }

        return apiRespone;
    }
    @GetMapping("/detail/token")
    public Map<String, Object> getTokenClaims(@RequestHeader("Authorization") String token) {
        // Loại bỏ tiền tố "Bearer " nếu có
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // Gọi service để lấy toàn bộ claims từ token
        return accountService.getClaimsFromToken(token);
    }
}
