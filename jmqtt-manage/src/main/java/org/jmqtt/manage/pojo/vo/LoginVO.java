package org.jmqtt.manage.pojo.vo;

public class LoginVO {

    private String token;

    private String username;

    public LoginVO() {

    }

    public LoginVO(String token, String username) {
        this.token = token;
        this.username = username;
    }
}