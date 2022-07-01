package org.pieszku.bot.impl;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;

public abstract class Command {


    public CommandInfo getCommandInfo(){
        return this.getClass().isAnnotationPresent(CommandInfo.class) ? this.getClass().getDeclaredAnnotation(CommandInfo.class) : null;
    }
    public void runDefault(MessageReceivedEvent event, String... arguments){

    }
    public void runSlash(SlashCommandEvent event, MessageChannel messageChannel){

    }
    public abstract OptionData[] optionData();
    public abstract SelectionMenu[] selectionMenus();
    public abstract Button[] buttons();
}
