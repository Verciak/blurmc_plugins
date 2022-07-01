package org.pieszku.bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.pieszku.api.API;
import org.pieszku.api.redis.RedisConnector;
import org.pieszku.api.redis.packet.sector.request.SectorConfigurationDiscordRequestPacket;
import org.pieszku.api.sector.SectorService;
import org.pieszku.bot.handler.CommandListener;
import org.pieszku.bot.impl.Command;
import org.pieszku.bot.redis.SectorConfigurationHandler;
import org.pieszku.bot.redis.SectorInformationDiscordSynchronizeHandler;
import org.pieszku.bot.runnable.GiveawayInformationRunnable;
import org.pieszku.bot.runnable.SectorChannelInformationRunnable;
import org.pieszku.bot.runnable.SectorInformationMessageRunnable;
import org.pieszku.bot.service.CommandService;
import org.pieszku.bot.service.GiveawayService;
import org.reflections.Reflections;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BotMain {


    private static BotMain instance;
    private final JDA jda;
    private RedisConnector redisConnector;
    private API api;
    private final CommandService commandService;
    private final SectorService sectorService;
    private final GiveawayService giveawayService = new GiveawayService();

    public BotMain() throws LoginException, InterruptedException {
        instance = this;
        this.sectorService = new SectorService();
        this.commandService = new CommandService();
        this.jda = JDABuilder.createDefault("OTM0NTM4MTU1NTQ0MTYyMzY0.Yexiew.YLltK-6nuoQlkSRhavep8XPHnkY").build();
        this.jda.addEventListener(new CommandListener());
        this.jda.awaitReady();
        this.registerConfiguration();
        this.registerCommand();
    }

    private void registerConfiguration() {
        this.api = new API(true);
        this.redisConnector = this.api.getRedisConnector();
        this.redisConnector.connect();
        this.redisConnector.getRedisService().subscribe("DISCORD", new SectorInformationDiscordSynchronizeHandler());
        this.redisConnector.getRedisService().subscribe("DISCORD", new SectorConfigurationHandler());
        new SectorConfigurationDiscordRequestPacket("DISCORD").sendToChannel("MASTER");
    }

    private void registerCommand() {
        List<CommandData> cmds = new ArrayList<>();

        new Reflections("org.pieszku.bot.commands").getSubTypesOf(Command.class).forEach(aClass -> {
            try {
                this.commandService.register(aClass.newInstance());

                if (aClass.newInstance().getCommandInfo().slashCommand()) {
                    cmds.add(new CommandData(aClass.newInstance().getCommandInfo().name(), aClass.newInstance().getCommandInfo().description())
                            .addOptions(aClass.newInstance().optionData()));
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        this.commandService.getCommands().forEach(command -> {
            if (command.getCommandInfo().slashCommand()) {
                this.jda.upsertCommand(new CommandData(command.getCommandInfo().name(), command.getCommandInfo().description())
                        .addOptions(command.optionData())).complete();
            }
        });
        jda.getGuildById("934537306877075456").updateCommands().addCommands(cmds).queue();

        new GiveawayInformationRunnable().start();

        Guild guild = this.jda.getGuildById("934537306877075456");
        TextChannel textChannel = guild.getTextChannelById("942524770505883720");
        if(textChannel == null)return;
        textChannel.deleteMessageById(textChannel.getLatestMessageId()).complete();

        Date date = new Date(System.currentTimeMillis());
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("BLURMC.PL | SECTORS");
        embedBuilder.setFooter("BLURMC.PL * " + date.toLocaleString(), guild.getIconUrl());
        embedBuilder.setThumbnail(guild.getIconUrl());
        embedBuilder.setDescription("Tutaj znajdują się podstawowe informacje na temat twoich sektorów");
        embedBuilder.setColor(new Color(2, 108, 236, 255));
        Message message = textChannel.sendMessage(embedBuilder.build()).complete();
        new SectorInformationMessageRunnable(message).start();
        new SectorChannelInformationRunnable().start();
    }

    public static void main(String[] args) throws LoginException, InterruptedException {
        new BotMain();
    }

    public GiveawayService getGiveawayService() {
        return giveawayService;
    }

    public static BotMain getInstance() {
        return instance;
    }

    public API getApi() {
        return api;
    }

    public RedisConnector getRedisConnector() {
        return redisConnector;
    }

    public CommandService getCommandService() {
        return commandService;
    }

    public JDA getJda() {
        return jda;
    }

    public SectorService getSectorService() {
        return sectorService;
    }
}