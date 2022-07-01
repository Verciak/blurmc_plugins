package org.pieszku.api.objects.discord;

import org.pieszku.api.impl.Identifiable;

import java.io.Serializable;

public class AccountDiscord implements Serializable, Identifiable<String> {


    private final String discordId;
    private String captcha;
    private String nickNameMinecraft;
    private boolean verifyMinecraft;
    private boolean minecraftPicked;
    private boolean discordRewardPicked;

    public AccountDiscord(String discordId){
        this.discordId = discordId;
        this.captcha = "null";
        this.nickNameMinecraft = "null";
        this.minecraftPicked = false;
        this.verifyMinecraft = false;
        this.discordRewardPicked = false;
    }

    public boolean isVerifyMinecraft() {
        return verifyMinecraft;
    }

    public void setVerifyMinecraft(boolean verifyMinecraft) {
        this.verifyMinecraft = verifyMinecraft;
    }

    public String getCaptcha() {
        return captcha;
    }

    public String getDiscordId() {
        return discordId;
    }

    public String getNickNameMinecraft() {
        return nickNameMinecraft;
    }

    public boolean isDiscordRewardPicked() {
        return discordRewardPicked;
    }

    public boolean isMinecraftPicked() {
        return minecraftPicked;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public void setMinecraftPicked(boolean minecraftPicked) {
        this.minecraftPicked = minecraftPicked;
    }

    public void setDiscordRewardPicked(boolean discordRewardPicked) {
        this.discordRewardPicked = discordRewardPicked;
    }

    public void setNickNameMinecraft(String nickNameMinecraft) {
        this.nickNameMinecraft = nickNameMinecraft;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void setId(String s) {

    }
}
