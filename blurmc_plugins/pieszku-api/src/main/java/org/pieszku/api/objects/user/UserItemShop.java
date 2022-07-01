package org.pieszku.api.objects.user;

public class UserItemShop {

    private final String name;
    private final String data;
    private final String command;
    private  boolean picked;

    public UserItemShop(String name, String data, String command){
        this.name = name;
        this.data = data;
        this.command = command;
        this.picked = false;
    }

    public String getData() {
        return data;
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }

    public boolean isPicked() {
        return picked;
    }

    public void setPicked(boolean picked) {
        this.picked = picked;
    }
}
