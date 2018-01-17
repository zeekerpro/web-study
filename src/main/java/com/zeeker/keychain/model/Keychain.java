/**
 * @fileName :     Keychain
 * @author :       zeeker
 * @date :         11/01/2018 11:55
 * @description :
 */

package com.zeeker.keychain.model;

public class Keychain extends BaseObject{
    private static final long serialVersionUID = -916589067730480576L;

    private String name;

    private String nickname;

    private String account;

    private String loginPasswd;

    private String accessPasswd;

    private String email;

    private String phone;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLoginPasswd() {
        return loginPasswd;
    }

    public void setLoginPasswd(String loginPasswd) {
        this.loginPasswd = loginPasswd;
    }

    public String getAccessPasswd() {
        return accessPasswd;
    }

    public void setAccessPasswd(String accessPasswd) {
        this.accessPasswd = accessPasswd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
