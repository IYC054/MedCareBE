package fpt.aptech.pjs4.services.impl;

import fpt.aptech.pjs4.entities.Account;
import fpt.aptech.pjs4.entities.Feedback;
import fpt.aptech.pjs4.repositories.AccountRepository;
import fpt.aptech.pjs4.repositories.FeedbackRepository;
import fpt.aptech.pjs4.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private AccountRepository accountRepository;

    final JavaMailSender mailSender;
    public FeedbackServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public Feedback saveFeedback(Integer accountId, Feedback feedback) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        // Kiểm tra thời gian gửi feedback gần nhất
//        if (account.getLastFeedbackTime() != null &&
//                account.getLastFeedbackTime().isAfter(LocalDateTime.now().minusMinutes(5))) {
//            throw new IllegalArgumentException("You can only send feedback every 5 minutes.");
//        }
        // Kiểm tra nội dung trùng lặp
        if (feedbackRepository.existsByAccountIdAndMessage(accountId, feedback.getMessage())) {
            throw new IllegalArgumentException("Duplicate feedback is not allowed.");
        }
        // Cập nhật thời gian gửi feedback
        account.setLastFeedbackTime(LocalDateTime.now());
        accountRepository.save(account);
        feedback.setAccount(account);
        // Lưu feedback và gửi email
        Feedback savedFeedback = feedbackRepository.save(feedback);
        sendThankYouEmail(account.getEmail(), feedback.getMessage());
        return savedFeedback;
    }

    @Override
    public List<Feedback> getFeedbacksByAccountId(Integer accountId) {
        return feedbackRepository.findByAccountId(accountId);
    }


    @Override
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback getOneFeed(int feedbackId) {
        return feedbackRepository.findById(feedbackId).orElseThrow();
    }

    @Override
    public void deleteFeedback(int feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }

    private void sendThankYouEmail(String to, String feedbackMessage) {
        Account account = accountRepository.findAccountByEmail(to).orElseThrow(() -> new RuntimeException("Account not found"));
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Thank You for Your Feedback");
        message.setText("Dear "+ account.getName() + ",\n\nThank you for your feedback: \"" + feedbackMessage + "\".\n\nWe appreciate your input and will strive to improve.\n\nBest regards,\nMedCare Team");

        mailSender.send(message);
    }
}
