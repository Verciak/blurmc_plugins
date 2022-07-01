package org.pieszku.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.ButtonStyle;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;
import org.pieszku.bot.impl.Command;
import org.pieszku.bot.impl.CommandInfo;

import java.awt.*;
import java.util.Date;

@CommandInfo(
        name = "configuration",
        description = "Configuration pieszku-sectors",
        slashCommand = true,
        permission = {Permission.ADMINISTRATOR}
)
public class SectorConfigurationCommand extends Command {


    @Override
    public void runSlash(SlashCommandEvent event, MessageChannel messageChannel) {
        Date date = new Date(System.currentTimeMillis());
        EmbedBuilder embedBuilder = new EmbedBuilder();
  /*      embedBuilder.setTitle("BLURMC.PL | NAGRODA");
        embedBuilder.setDescription("Na naszym serwerze wprowadziliśmy darmową nagrodę za dołączenie na nasz serwer discord!");
        embedBuilder.addField("Jak odebrać nagrodę?", "Wystarczy że na tym kanale klikniesz przycisk **Odbierz nagrodę**", false);
        embedBuilder.addField("Kiedy otrzymam moją nagrode?", "Nagroda zostanie automatycznie nadana w ciągu **1** minuty\nNastępnie na serwerze wpisz: /nagroda", false);
        embedBuilder.setFooter("BLURMC.PL • " + date.toLocaleString(), event.getGuild().getIconUrl());
        embedBuilder.setColor(new Color(2, 108, 236, 255));
        embedBuilder.setThumbnail(event.getGuild().getIconUrl());

        event.getTextChannel().sendMessage(embedBuilder.build()).setActionRow(new Button[]{
                Button.danger("picked-reward", "Odbierz nagrodę")
        }).queue();
*/
        embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(new Color(2, 108, 236, 255));
        embedBuilder.setTitle("BLURMC.PL | KONTO");
        embedBuilder.setDescription("Na serwerze wprowadziliśmy powiązanie konta discord z minecraft");
        embedBuilder.addField("Jak powiązać konto z minecraft?"
                ,"Jest to bardzo proste napisz tutaj\n" +
                        "komende:```/weryfikacja <nick>```\nnastępnie wejdż na serwer\n" +
                        "wpisz **/weryfikacja <kod>** który otrzymasz w wiadomości\n" +
                        "a twoje konto zostanie powiązane z naszym serwerem discord\n" +
                        "dzięki temu będziesz mógł zarządżać swoją gildią\n" +
                        "oraz brać udział w konkursach i wiele więcej.", false);
        embedBuilder.addField("\n",
                "Ip Serwera: **BLURMC.PL**\n" +
                        "Wersja Serwera: **(1.16-1.18)**", false);
        embedBuilder.setFooter("BLURMC.PL • " + date.toLocaleString(), event.getGuild().getIconUrl());
        embedBuilder.setThumbnail(event.getGuild().getIconUrl());
        event.getTextChannel().sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public OptionData[] optionData() {
        return new OptionData[]{
                new OptionData(OptionType.INTEGER, "start", "Starting configuration pieszku-sectors")
        };
    }

    @Override
    public SelectionMenu[] selectionMenus() {
        return new SelectionMenu[]{
                SelectionMenu.create("sector:configuration")
                        .setPlaceholder("Wybierz ilość sektorów")
                        .setRequiredRange(1, 1)
                        .addOption("4", "4")
                        .addOption("8", "8")
                        .addOption("16", "16")
                        .addOption("32", "32")
                        .build(),
        };
    }

    @Override
    public Button[] buttons() {
        return new Button[]{
                Button.of(ButtonStyle.SUCCESS, "sectors:payments", "Złóż zamówienie"),
                Button.of(ButtonStyle.SECONDARY, "sectors:contact", "Napisz do nas"),
                Button.of(ButtonStyle.DANGER, "sectors:problem", "Potrzebuje pomocy")
        };
    }
}
