package org.pieszku.bot.runnable;

import net.dv8tion.jda.api.EmbedBuilder;
import org.pieszku.api.utilities.DataUtilities;
import org.pieszku.bot.BotMain;
import org.pieszku.bot.impl.Giveaway;
import org.pieszku.bot.service.GiveawayService;

import java.awt.*;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GiveawayInformationRunnable implements Runnable{

    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private final GiveawayService giveawayService = BotMain.getInstance().getGiveawayService();

    public void start() {
        this.scheduledExecutorService.scheduleAtFixedRate(this, 1,1, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        for(Giveaway giveaway  : this.giveawayService.getGiveawayList()){

            Date date = new Date(System.currentTimeMillis());
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("ㅤ");
            embedBuilder.addField("ㅤㅤㅤㅤㅤㅤㅤㅤ\uD83C\uDF89 KONKURS \uD83C\uDF89",
                    "ㅤㅤㅤㅤㅤㅤㅤNagroda: **" + giveaway.getReward() + "**\n" +
                            "ㅤㅤㅤㅤㅤㅤPozostały czas: **" + DataUtilities.getTimeToString(giveaway.getTime()) + "**\n" +
                            "ㅤㅤㅤㅤㅤㅤㅤㅤWygranych: **" + giveaway.getAmount() + "**\n" +
                            (giveaway.isMinecraft() ? "ㅤㅤ\n**Konkurs dotyczy graczy z powiązanym kontem do minecraft**" : " " + "\n" +
                            "ㅤㅤ"), false);
            embedBuilder.setImage("https://i.imgur.com/8y2OLnX.png");
            embedBuilder.setColor(new Color(2, 108, 236, 255));
            embedBuilder.setFooter("PCODE * " + date.toLocaleString(),BotMain.getInstance().getJda().getGuildById("934537306877075456").getIconUrl());
            embedBuilder.setThumbnail(BotMain.getInstance().getJda().getGuildById("934537306877075456").getIconUrl());
            giveaway.getMessage().editMessage(embedBuilder.build()).queue();
        }
    }
}
