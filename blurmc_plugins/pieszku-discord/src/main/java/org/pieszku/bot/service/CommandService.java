package org.pieszku.bot.service;

import org.pieszku.bot.impl.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CommandService {

    private final List<Command> commands = new ArrayList<>();

    public void register(Command command){
        commands.add(command);
    }

    public Optional<Command> getCommand(String name){
        return this.commands
                .stream()
                .filter(command -> command.getCommandInfo() != null
                        && command.getCommandInfo().name().equalsIgnoreCase(name)
                        || Arrays.asList(command.getCommandInfo().aliases()).contains(name.toLowerCase()))
                .findFirst();
    }

    public List<Command> getCommands() {
        return commands;
    }
}
