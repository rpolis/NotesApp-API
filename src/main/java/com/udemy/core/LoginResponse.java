package com.udemy.core;

public class LoginResponse {
    int code;
    User user;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LoginResponse(int code, User user) {
        this.code = code;
        this.user = user;
    }
}
