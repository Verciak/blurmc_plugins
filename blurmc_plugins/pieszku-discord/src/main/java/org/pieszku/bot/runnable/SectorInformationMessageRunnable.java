package org.pieszku.bot.runnable;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.pieszku.api.sector.Sector;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.utilities.DataUtilities;
import org.pieszku.bot.BotMain;

import java.awt.*;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SectorInformationMessageRunnable implements Runnable{

    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private final Message message;
    private final SectorService sectorService = BotMain.getInstance().getSectorService();

    public SectorInformationMessageRunnable(Message message) {
        this.message = message;
    }

    public void start() {
        this.scheduledExecutorService.scheduleAtFixedRate(this, 1,1, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        Date date = new Date(System.currentTimeMillis());


        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("ㅤ");
        embedBuilder.setFooter("PCODE * " + date.toLocaleString(),BotMain.getInstance().getJda().getGuildById("934537306877075456").getIconUrl());
        embedBuilder.setThumbnail(BotMain.getInstance().getJda().getGuildById("934537306877075456").getIconUrl());
        embedBuilder.setDescription(
                "ㅤㅤㅤㅤㅤㅤㅤ**STATUSY SEKTORÓW**ㅤㅤㅤㅤㅤ\n" +
                "ㅤㅤㅤㅤㅤㅤㅤOgólna ilość sektorów(**" + this.sectorService.getSectorList().size() + "**)ㅤㅤㅤㅤㅤ\n" +
                "ㅤㅤㅤㅤㅤㅤㅤㅤSektorów online(**" + this.sectorService.getCountOnlineSectors() + "**)ㅤㅤㅤㅤㅤ\nㅤ");
        for(Sector sector : this.sectorService.getSectorList()) {
            embedBuilder.addField(
                      "ㅤ",
                      "Sektor(" + sector.getName() + ")\n" +
                      "Typ(**" + sector.getSectorType().name() + "**)ㅤ\n" +
                      "Status(" + (sector.isOnline() ? "**ONLINE**" : "**OFFLINE**") + ")\n" +
                            "Online(**" + sector.getPlayers().size() + "**)\n" +
                            "LastPacket(**" + DataUtilities.getTimeToString(sector.getLatestInformation()) + "**)", true);


        }
        embedBuilder.setColor(new Color(2, 108, 236, 255));
        message.editMessage(embedBuilder.build()).queue();


    }
}
