package org.pieszku.bot.handler;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.pieszku.bot.BotMain;
import org.pieszku.bot.impl.Command;
import org.pieszku.bot.impl.CommandInfo;
import org.pieszku.bot.service.CommandService;

import java.util.Arrays;

public class CommandListener extends ListenerAdapter {

    private final CommandService commandService = BotMain.getInstance().getCommandService();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot())return;

        Member member = event.getMember();
        if (member == null) {
            return;
        }
        String[] arguments = event.getMessage().getContentDisplay().split(" ");


        if (!arguments[0].startsWith("/")) return;


        this.commandService.getCommand(arguments[0].split("/")[1])
                .ifPresent(command -> {

                    CommandInfo info = command.getCommandInfo();

                    Permission[] permissions = info.permission();
                    String requiredPermissions = Arrays.toString(permissions).replace("[", "").replace("]", "");
                    if (!member.getPermissions().isEmpty() && !member.getPermissions().containsAll(Arrays.asList(permissions))) {
                        String message = "%member_name% Nie posiadasz permisji do: %permissions%"
                                .replace("%member_name%", member.getEffectiveName())
                                .replace("%member_with_tag%", member.getUser().getAsTag())
                                .replace("%member_id%", member.getId())
                                .replace("%guild_name%", event.getGuild().getName())
                                .replace("%guild_id%", event.getGuild().getId())
                                .replace("%permissions%", requiredPermissions);
                        event.getTextChannel().sendMessage(message).complete();
                        return;
                    }
                    command.runDefault(event, arguments);
                });
    }
    @Override
    public void onSlashCommand(SlashCommandEvent event){
        Command command = this.commandService.getCommand(event.getName()).orElse(null);
        if(command == null)return;

        if (!event.getName().equals(command.getCommandInfo().name())) return;

        Member member = event.getMember();

        if(member == null)return;

        Permission[] permissions = command.getCommandInfo().permission();
        String requiredPermissions = Arrays.toString(permissions).replace("[", "").replace("]", "");
        if (!member.getPermissions().isEmpty() && !member.getPermissions().containsAll(Arrays.asList(permissions))) {
            String message = "%member_name% Nie posiadasz permisji do: **%permissions%**"
                    .replace("%member_name%", member.getEffectiveName())
                    .replace("%member_with_tag%", member.getUser().getAsTag())
                    .replace("%member_id%", member.getId())
                    .replace("%guild_name%", event.getGuild().getName())
                    .replace("%guild_id%", event.getGuild().getId())
                    .replace("%permissions%", requiredPermissions);
            event.getTextChannel().sendMessage(message).complete();
            return;
        }

        command.runSlash(event, event.getChannel());
    }

}
