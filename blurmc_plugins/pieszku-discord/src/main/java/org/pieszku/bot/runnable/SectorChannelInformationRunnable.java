package org.pieszku.bot.runnable;

import net.dv8tion.jda.api.entities.VoiceChannel;
import org.pieszku.api.sector.Sector;
import org.pieszku.api.sector.SectorService;
import org.pieszku.bot.BotMain;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SectorChannelInformationRunnable implements Runnable{


    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private final SectorService sectorService = BotMain.getInstance().getSectorService();

    public void start() {
        this.scheduledExecutorService.scheduleAtFixedRate(this, 5,1, TimeUnit.MINUTES);
    }

    @Override
    public void run() {
        VoiceChannel voiceChannel = BotMain.getInstance().getJda().getVoiceChannelById("942524602410758185");
        if(voiceChannel == null)return;


        int countPlayers = 0;
        for(Sector sector : this.sectorService.getSectorList()) {
            for(String players : sector.getPlayers()){
                countPlayers++;
            }
        }

        voiceChannel.getManager().setName("\uD83C\uDF1A﹞sektory: " + countPlayers).complete();
    }
}
