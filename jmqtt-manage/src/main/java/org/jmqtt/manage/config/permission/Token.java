package org.jmqtt.manage.config.permission;

import java.util.Arrays;

public class Token {
    private String tokenStr;
    private String userId;
    private String username;
    private String[] roleIds;
    private Long exp;

    public String getTokenStr() {
        return tokenStr;
    }

    public void setTokenStr(String tokenStr) {
        this.tokenStr = tokenStr;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenStr='" + tokenStr + '\'' +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", roleIds=" + Arrays.toString(roleIds) +
                ", exp=" + exp +
                '}';
    }
}