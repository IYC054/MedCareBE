package fpt.aptech.pjs4.services;

import fpt.aptech.pjs4.entities.Feedback;

import java.util.List;

public interface FeedbackService {
    Feedback saveFeedback(Integer accountId, Feedback feedback);
    List<Feedback> getFeedbacksByAccountId(Integer accountId);
    List<Feedback> getAllFeedbacks();
    Feedback getOneFeed(int feedbackId);
}
