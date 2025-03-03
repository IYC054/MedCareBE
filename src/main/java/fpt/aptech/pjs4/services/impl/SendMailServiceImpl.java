package fpt.aptech.pjs4.services.impl;


import fpt.aptech.pjs4.entities.SendMail;
import fpt.aptech.pjs4.repositories.SendMailReponsitory;
import fpt.aptech.pjs4.services.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendMailServiceImpl implements SendMailService {

    @Autowired
    private SendMailReponsitory sendRepository;

    final JavaMailSender mailSender;

    public SendMailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public SendMail sendMail(SendMail sendMail) {
        SendMail lde = sendRepository.save(sendMail);
        sendThankYouEmail(sendMail.getSender_email(), sendMail.getMessage());
        return lde;
    }

    @Override
    public SendMail userSendMail(SendMail sendMail) {
        SendMail lde = sendRepository.save(sendMail);
        return lde;
    }

    @Override
    public void DeleteMail(Long id) {
        sendRepository.deleteById(id);
    }

    @Override
    public List<SendMail> getAll() {
        return sendRepository.findAll();
    }

    @Override
    public SendMail getOne(Long id) {
        return sendRepository.findById(id).orElseThrow();
    }

    private void sendThankYouEmail(String to, String feedbackMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Thank You for Your Feedback");
        message.setText("Dear " + "User" + ",\n\nThank you for your feedback: \"" + feedbackMessage + "\".\n\nWe appreciate your input and will strive to improve.\n\nBest regards,\nMedCare Team");

        mailSender.send(message);
    }
}
