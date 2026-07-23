package com.slmas.Sl.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;

public class LoginRequestDto {
    String userName;
    String password;
    @JsonAlias({"rememberMe", "keepLoggedIn", "mantenerIniciado"})
    Boolean rememberMe;

    public LoginRequestDto() {
    }

    public LoginRequestDto(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
    public Boolean getRememberMe() {
        return rememberMe;
    }

    public boolean isRememberMe() {
        return Boolean.TRUE.equals(rememberMe);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    public String toString() {
        return "LoginRequestDto{" +
                "userName='" + userName + '\'' +
                ", rememberMe=" + rememberMe +
                '}';
    }
}
