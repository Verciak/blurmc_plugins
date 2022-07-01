package org.pieszku.bot.impl;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;

public class Giveaway {

    private final int id;
    private final String reward;
    private final boolean minecraft;
    private final int amount;
    private final long time;
    private final Message message;
    private final List<User> reactionUsers;

    public Giveaway(int id, String reward, boolean minecraft, int amount, Message message, long time){
        this.id = id;
        this.reward = reward;
        this.minecraft = minecraft;
        this.amount = amount;
        this.message = message;
        this.reactionUsers = new ArrayList<>();
        this.time = time;
    }

    public long getTime() {
        return time;
    }
    public boolean isActive(){
        return this.time <= System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public List<User> getReactionUsers() {
        return reactionUsers;
    }

    public Message getMessage() {
        return message;
    }

    public String getReward() {
        return reward;
    }

    public boolean isMinecraft() {
        return minecraft;
    }
}
