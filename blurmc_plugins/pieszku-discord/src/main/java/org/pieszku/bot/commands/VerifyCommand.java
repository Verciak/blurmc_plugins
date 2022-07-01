package org.pieszku.bot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;
import org.pieszku.bot.impl.Command;
import org.pieszku.bot.impl.CommandInfo;

@CommandInfo(name = "weryfikacja",
description = "weryfikacja konta z minecraft",
slashCommand = true)

public class VerifyCommand extends Command {

    @Override
    public void runSlash(SlashCommandEvent event, MessageChannel messageChannel) {
        String nickName = event.getOptions().get(0).getAsString();
        event.reply("Twój kod weryfikacjyny: **test1234**").setEphemeral(true).queue();
    }

    @Override
    public OptionData[] optionData() {
        return new OptionData[]{
                new OptionData(OptionType.STRING, "nick", "twój nick z gry minecraft", true)
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
