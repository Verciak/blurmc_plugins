package org.pieszku.api.proxy.global;

import java.util.LinkedList;
import java.util.Optional;

public class WhitelistServerService {

    private LinkedList<WhitelistServer> whitelistServersList = new LinkedList<>();

    public Optional<WhitelistServer> findWhitelistByServerName(String serverName){
        return this.whitelistServersList
                .stream()
                .filter(whitelistServer -> whitelistServer.getChannelName().equalsIgnoreCase(serverName))
                .findFirst();
    }
    public WhitelistServer findOrCreate(String serverName, WhitelistServerType whitelistServerType){
        for (WhitelistServer whitelistServer : this.whitelistServersList) {
            if (whitelistServer.getChannelName().equalsIgnoreCase(serverName)) {
                return whitelistServer;
            }
        }
        return create(serverName, whitelistServerType);
    }
    public WhitelistServer create(String serverName, WhitelistServerType whitelistServerType){
        WhitelistServer whitelistServer = new WhitelistServer(serverName, whitelistServerType);
        this.whitelistServersList.add(whitelistServer);
        return whitelistServer;
    }


    public void setWhitelistServersList(LinkedList<WhitelistServer> whitelistServersList) {
        this.whitelistServersList = whitelistServersList;
    }

    public LinkedList<WhitelistServer> getWhitelistServersList() {
        return whitelistServersList;
    }
}
