package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/account_web_socket")
public class AccountWebSocketController {

    @Autowired
    private AccountService accountService;

    @MessageMapping("/user.addUser")
    @SendTo("/user/public")
    public Account notifyNewAccount(@Payload Account account) {
        accountService.addConnectedAccount(account); // Chỉ đánh dấu là account đang online
        return account;
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public Account disconnect(@Payload Account account) {
        accountService.disconnect(account);
        return account; // Trả về thông tin tài khoản mới để gửi qua WebSocket
    }

    @GetMapping("/users")
    public ResponseEntity<List<Account>> findConnectedAccounts() {
        return ResponseEntity.ok(accountService.findConnectedAccount());
    }
}
