package fpt.aptech.projectapi.controller;

import fpt.aptech.projectapi.DTOs.Introspect;
import fpt.aptech.projectapi.entities.Account;
import fpt.aptech.projectapi.DTOs.ApiRespone;
import fpt.aptech.projectapi.DTOs.AuthLoginToken;
import fpt.aptech.projectapi.services.AccountServices;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/account")
@AllArgsConstructor

public class AccountController {
    private final AccountServices accountServices;

    @PostMapping
    public ApiRespone<Account> createAccount(@RequestBody @Valid Account account) {
        try {
            ApiRespone<Account> apiRespone = new ApiRespone<>();
            apiRespone.setResult(accountServices.createAccount(account));
            apiRespone.setMessage("Account created Successfully");
            return apiRespone;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable int id) {
        Account account = accountServices.getAccountById(id);
        return ResponseEntity.ok(account);
    }

    @GetMapping
    public ApiRespone<List<Account>> getAllAccounts() {
        ApiRespone<List<Account>> apiResponse = new ApiRespone<>();
        List<Account> accounts = accountServices.getAllAccounts();
        if (accounts != null) {
            apiResponse.setResult(accounts);
            apiResponse.setMessage("Success");
        } else {
            apiResponse.setCode(404);
            apiResponse.setMessage("No accounts found");
        }
        return apiResponse;
    }


    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable int id, @RequestBody Account account) {
        Account updatedAccount = accountServices.updateAccount(id, account);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Account> deleteAccount(@PathVariable int id) {
        Account acc = accountServices.getAccountById(id);
        if(acc != null) {
            accountServices.deleteAccount(id);
            return ResponseEntity.ok(acc);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(acc);
        }

    }

    @PostMapping("/login")
    public ApiRespone<AuthLoginToken> login(@RequestBody Account loginRequest) {
        ApiRespone<AuthLoginToken> apiRespone = new ApiRespone<>();
            AuthLoginToken acc = accountServices.AuthLogin(loginRequest.getEmail(), loginRequest.getPassword());
            apiRespone.setResult(acc);
            apiRespone.setMessage("Login successfully");

        return apiRespone;
    }
    @PostMapping("/login/token")
    public ApiRespone<Introspect> login(@RequestBody Introspect loginRequest) {
        ApiRespone<Introspect> apiRespone = new ApiRespone<>();

        Introspect acc = accountServices.introspect(loginRequest);

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
        return accountServices.getClaimsFromToken(token);
    }

}
