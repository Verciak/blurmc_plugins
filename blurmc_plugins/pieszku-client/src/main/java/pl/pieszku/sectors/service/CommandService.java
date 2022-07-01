package pl.pieszku.sectors.service;

import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;
import org.reflections.Reflections;
import pl.pieszku.sectors.helper.ReflectionHelper;
import pl.pieszku.sectors.impl.BukkitCommand;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandService {

    private final List<Command> commandList = new ArrayList<>();


    public Command findCommand(String name){
        return this.commandList
                .stream()
                .filter(command -> command.getCommandInfo().name().equalsIgnoreCase(name)
                        || Arrays.asList(command.getCommandInfo().aliases()).contains(name.toLowerCase()))
                .findFirst()
                .orElse(null);
    }
    public void load(){
        for (Class<? extends Command> aClass : new Reflections("pl.pieszku.sectors.commands").getSubTypesOf(Command.class)) {
            accept(aClass);
        }
    }
    public void loadGuilds(org.bukkit.command.Command command){
        try {
            SimpleCommandMap simpleCommandMap = (SimpleCommandMap) ReflectionHelper.getField(SimplePluginManager.class, "commandMap", SimpleCommandMap.class).get(Bukkit.getPluginManager());
            simpleCommandMap.register(command.getName(), command);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    private void accept(Class<? extends Command> command) {
        try {
            if(command.newInstance().getCommandInfo()== null)return;
            CommandInfo commandInfo = command.newInstance().getCommandInfo();
            this.commandList.add(command.newInstance());

            SimpleCommandMap simpleCommandMap = (SimpleCommandMap) ReflectionHelper.getField(SimplePluginManager.class, "commandMap", SimpleCommandMap.class).get(Bukkit.getPluginManager());
            simpleCommandMap.register(commandInfo.name(), new BukkitCommand(command.newInstance()));

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
