package org.pieszku.api.proxy.user;

import org.pieszku.api.impl.Identifiable;

import java.io.Serializable;

public class ProxyUser implements Identifiable<String>, Serializable {

    private final String nickName;
    private boolean premium;
    private boolean login;
    private boolean register;
    private String captcha;
    private String password;

    public ProxyUser(String nickName) {
        this.nickName = nickName;
        this.premium = false;
        this.login = false;
        this.register = false;
        this.captcha = null;
        this.password = null;
    }

    public String getNickName() {
        return nickName;
    }

    @Override
    public String getId() {
        return this.nickName;
    }

    @Override
    public void setId(String s) {

    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
