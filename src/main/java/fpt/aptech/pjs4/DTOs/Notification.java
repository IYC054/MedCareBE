package fpt.aptech.pjs4.DTOs;

import com.google.cloud.Timestamp;

public class Notification {
    private String title;
    private String body;
    private Timestamp timestamp;
    private String userId;

    // Constructors
    public Notification() {}

    public Notification(String title, String body, Timestamp timestamp, String userId) {
        this.title = title;
        this.body = body;
        this.timestamp = timestamp;
        this.userId = userId;
    }

    // Getters & Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}
