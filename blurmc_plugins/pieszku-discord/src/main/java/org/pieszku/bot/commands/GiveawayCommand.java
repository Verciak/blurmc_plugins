package org.pieszku.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;
import org.pieszku.api.utilities.DataUtilities;
import org.pieszku.bot.BotMain;
import org.pieszku.bot.impl.Command;
import org.pieszku.bot.impl.CommandInfo;
import org.pieszku.bot.impl.Giveaway;
import org.pieszku.bot.service.GiveawayService;

@CommandInfo(name = "konkurs",
permission = {Permission.ADMINISTRATOR},
slashCommand = true,
description = "giveaway command")
public class GiveawayCommand extends Command {


    private final GiveawayService giveawayService = BotMain.getInstance().getGiveawayService();

    @Override
    public void runSlash(SlashCommandEvent event, MessageChannel messageChannel) {


        long time = DataUtilities.parseDateDiff(event.getOption("time").getAsString(), true);
        int amount = (int) event.getOption("amount").getAsDouble();
        String reward = event.getOption("reward").getAsString();
        boolean minecraft = event.getOption("minecraft").getAsBoolean();


        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(" ");
        embedBuilder.addField("KONKURS",
                "Nagroda: **" + reward + "**\n" +
                "Pozostały czas: **" + DataUtilities.getTimeToString(time) + "**\n" +
                        "Wygranych: **" + amount + "**\n" +
        (minecraft ? "**Konkurs dotyczy graczy z powiązanym kontem do minecraft**" : " "), false);
        embedBuilder.setImage("https://i.imgur.com/8y2OLnX.png");


        Message message = event.getTextChannel().sendMessage(embedBuilder.build()).complete();
        message.addReaction("\uD83C\uDF89").queue();
        this.giveawayService.create(new Giveaway(this.giveawayService.getGiveawayList().size() + 1, reward, minecraft, amount, message , time));

    }

    @Override
    public OptionData[] optionData() {
        return new OptionData[]{
                new OptionData(OptionType.INTEGER, "amount", "ilość wygranych"),
                new OptionData(OptionType.BOOLEAN, "minecraft", "czy ma być tylko dla zweryfikowanych z minecraft"),
                new OptionData(OptionType.STRING, "reward", "nagroda"),
                new OptionData(OptionType.STRING, "time", "czas trwania"),
        };
    }

    @Override
    public SelectionMenu[] selectionMenus() {
        return new SelectionMenu[0];
    }

    @Override
    public Button[] buttons() {
        return new Button[0];
    }
}
