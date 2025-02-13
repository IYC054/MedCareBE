package fpt.aptech.pjs4.controllers;

import fpt.aptech.pjs4.entities.SendMail;
import fpt.aptech.pjs4.services.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sendmail")
public class SendMailController {

    @Autowired
    private SendMailService sendMailService;

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMail(@PathVariable Long id) {
        sendMailService.DeleteMail(id);
        return ResponseEntity.ok("Mail deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<SendMail> getMail(@PathVariable Long id) {
        SendMail ok = sendMailService.getOne(id);
        return ResponseEntity.ok(ok);
    }

    @PostMapping("/other")
    public ResponseEntity<SendMail> sendMailother(@RequestBody SendMail sendMail) {
        SendMail savedMail = sendMailService.userSendMail(sendMail);
        return ResponseEntity.ok(savedMail);
    }

    @PostMapping
    public ResponseEntity<SendMail> sendMail(@RequestBody SendMail sendMail) {
        SendMail savedMail = sendMailService.sendMail(sendMail);
        return ResponseEntity.ok(savedMail);
    }

    @GetMapping
    public ResponseEntity<List<SendMail>> getAllMails() {
        return ResponseEntity.ok(sendMailService.getAll());
    }

}
