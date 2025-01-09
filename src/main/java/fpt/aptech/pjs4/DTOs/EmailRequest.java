package fpt.aptech.pjs4.DTOs;

public class EmailRequest {
        private String to;
        private String subject;
        private String htmlContent;

        // Getters và Setters
        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getHtmlContent() {
            return htmlContent;
        }

        public void setHtmlContent(String htmlContent) {
            this.htmlContent = htmlContent;
        }
    }