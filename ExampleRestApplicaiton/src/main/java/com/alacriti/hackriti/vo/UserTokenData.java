package com.alacriti.hackriti.vo;

public class UserTokenData {
    private String name;
    private String userName;
    private String clientKey;
    private String emailId;
    private String role;

    public UserTokenData() {
    }

    public UserTokenData(String clientKey, String emailId, String role) {
        this.clientKey = clientKey;
        this.emailId = emailId;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
