package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.entities.SendMail;

import java.util.List;

public interface SendMailService {
    SendMail sendMail( SendMail sendMail);
    SendMail userSendMail( SendMail sendMail);
    void DeleteMail(Long id);
    List<SendMail> getAll();
    SendMail getOne(Long id);
}
