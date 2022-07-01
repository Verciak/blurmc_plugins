package pl.pieszku.sectors.impl;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {

    public CommandInfo getCommandInfo(){
        return this.getClass().isAnnotationPresent(CommandInfo.class) ? this.getClass().getDeclaredAnnotation(CommandInfo.class) : null;
    }
    public abstract void execute(CommandSender commandSender, String[] args);

    public List<String> tabComplete(CommandSender sender,  String alias,String[] args){
        return new ArrayList<>();
    }
}
