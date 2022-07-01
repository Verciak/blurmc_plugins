package org.pieszku.bot.service;

import org.pieszku.bot.impl.Giveaway;

import java.util.ArrayList;
import java.util.List;

public class GiveawayService {

    private final List<Giveaway> giveawayList = new ArrayList<>();

    public Giveaway create(Giveaway giveaway){
        this.giveawayList.add(giveaway);
        return giveaway;
    }


    public List<Giveaway> getGiveawayList() {
        return giveawayList;
    }
}
