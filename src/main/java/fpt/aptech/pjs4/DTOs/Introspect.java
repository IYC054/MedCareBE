package fpt.aptech.pjs4.DTOs;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
public class Introspect {
    String token;
    boolean authenticated;

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
}