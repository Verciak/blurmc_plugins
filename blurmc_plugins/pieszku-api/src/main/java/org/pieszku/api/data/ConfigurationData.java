package org.pieszku.api.data;

import org.pieszku.api.data.drop.CaseDropJson;
import org.pieszku.api.data.drop.StoneDropJson;
import org.pieszku.api.data.guild.GuildPermissionJson;
import org.pieszku.api.data.shop.ShopBuyJson;
import org.pieszku.api.data.shop.ShopSellJson;
import org.pieszku.api.data.shop.ShopVillagerJson;

import java.io.Serializable;

public class ConfigurationData implements Serializable {

    public CaseDropJson caseDropJson;
    public StoneDropJson stoneDropJson;
    public ShopSellJson shopSellJson;
    public ShopBuyJson shopBuyJson;
    public ShopVillagerJson shopVillagerJson;
    public GuildPermissionJson guildPermissionJson;

    public void setGuildPermissionJson(GuildPermissionJson guildPermissionJson) {
        this.guildPermissionJson = guildPermissionJson;
    }

    public GuildPermissionJson getGuildPermissionJson() {
        return guildPermissionJson;
    }

    public ShopVillagerJson getShopVillagerJson() {
        return shopVillagerJson;
    }

    public void setShopVillagerJson(ShopVillagerJson shopVillagerJson) {
        this.shopVillagerJson = shopVillagerJson;
    }

    public CaseDropJson getCaseDropJson() {
        return caseDropJson;
    }

    public StoneDropJson getStoneDropJson() {
        return stoneDropJson;
    }

    public void setStoneDropJson(StoneDropJson stoneDropJson) {
        this.stoneDropJson = stoneDropJson;
    }

    public void setCaseDropJson(CaseDropJson caseDropJson) {
        this.caseDropJson = caseDropJson;
    }


    public ShopBuyJson getShopBuyJson() {
        return shopBuyJson;
    }

    public ShopSellJson getShopSellJson() {
        return shopSellJson;
    }

    public void setShopBuyJson(ShopBuyJson shopBuyJson) {
        this.shopBuyJson = shopBuyJson;
    }

    public void setShopSellJson(ShopSellJson shopSellJson) {
        this.shopSellJson = shopSellJson;
    }

    @Override
    public String toString() {
        return "ConfigurationData{" +
                "caseDropJson=" + caseDropJson +
                ", stoneDropJson=" + stoneDropJson +
                ", shopSellJson=" + shopSellJson +
                ", shopBuyJson=" + shopBuyJson +
                ", shopVillagerJson=" + shopVillagerJson +
                ", guildPermissionJson=" + guildPermissionJson +
                '}';
    }
}
