package fpt.aptech.pjs4.DTOs.response;

import fpt.aptech.pjs4.entities.Account;

public class AuthencicationResponse {
    boolean authenticated;
    private String token;
    private Account user;
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }
}
