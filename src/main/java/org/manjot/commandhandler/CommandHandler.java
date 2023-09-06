package org.manjot.commandhandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommandHandler {
    public static Map<String, Command> commandMap = new LinkedHashMap<>();

    public static void addCommand(Command command) {
        commandMap.put(command.getName(), command);
    }

    public static void removeCommand(Command command) {
        commandMap.remove(command.getName());
    }

    public static void removeCommand(String commandName) {
        commandName = commandName.toLowerCase();
        if (commandMap.containsKey(commandName)) {
            commandMap.remove(commandName);
            return;
        }
        for (Command command : commandMap.values()) {
            if (command.getAliases().contains(commandName))
                commandMap.remove(command.getName());
        }
    }

    public static List<Command> getCommands() {
        return commandMap.values().stream().toList();
    }

    public static Command getCommandByName(String commandName) {
        commandName = commandName.toLowerCase();
        if (commandMap.containsKey(commandName)) {
            return commandMap.get(commandName);
        }
        for (Command command : commandMap.values()) {
            if (command.getAliases().contains(commandName))
                return command;
        }
        return null;
    }

    public static List<Command> getCommandsByType(CommandType commandType) {
        return commandMap.values().stream()
                .filter(c -> c.getType() == commandType)
                .toList();
    }

    public static List<String> getCommandNames() {
        return commandMap.keySet().stream()
                .toList();
    }

    public static List<String> getCommandNamesByType(CommandType commandType) {
        return commandMap.values().stream()
                .filter(c -> c.getType() == commandType)
                .map(Command::getName)
                .toList();
    }
}