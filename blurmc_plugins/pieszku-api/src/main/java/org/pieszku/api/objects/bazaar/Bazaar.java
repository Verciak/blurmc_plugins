package org.pieszku.api.objects.bazaar;

import org.pieszku.api.impl.Identifiable;
import org.pieszku.api.type.TimeType;

import java.io.Serializable;

public class Bazaar implements Identifiable<Integer>, Serializable {

    private final int id;
    private final String nickName;
    private final int countSell;
    private final long delayTime;
    private final String serializedItem;


    public Bazaar(int id, String nickName, int countSell, String serializedItem){
        this.id = id;
        this.nickName = nickName;
        this.countSell = countSell;
        this.delayTime = System.currentTimeMillis() + TimeType.HOUR.getTime(24);
        this.serializedItem = serializedItem;
    }
    public boolean hasBazaarClient(){
        return this.delayTime <= System.currentTimeMillis();
    }


    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer integer) {
    }

    public long getDelayTime() {
        return delayTime;
    }


    public String getSerializedItem() {
        return serializedItem;
    }

    public int getCountSell() {
        return countSell;
    }

    public String getNickName() {
        return nickName;
    }
}
