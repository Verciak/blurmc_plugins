package org.pieszku.api.itemshop;

import org.pieszku.api.impl.Identifiable;

import java.io.Serializable;

public class ItemShop implements Identifiable<Integer>, Serializable {

    private final int id;
    private final String commandLine;

    public ItemShop(int id, String commandLine){
        this.id = id;
        this.commandLine = commandLine;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) {

    }

    public String getCommandLine() {
        return commandLine;
    }
}
