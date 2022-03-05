package me.daslastic.whyyouhere.commands;

import java.util.List;

public class CommandData {

    private final String permission;
    private final List<SubCommand> subCommands;
    private final String description;
    private final boolean instancePlayer;
    private final FilterRunnable filter;

    public CommandData(String permission, String description, boolean instancePlayer, List<SubCommand> subCommands) {
        this.permission = permission;
        this.description = description;
        this.subCommands = subCommands;
        this.instancePlayer = instancePlayer;
        filter = null;
    }

    public CommandData(String permission, String description, boolean instancePlayer, List<SubCommand> subCommands, FilterRunnable filter) {
        this.permission = permission;
        this.description = description;
        this.subCommands = subCommands;
        this.filter = filter;
        this.instancePlayer = instancePlayer;
    }

    public String getPermission() {
        return permission;
    }

    public String getDescription() {
        return description;
    }

    public List<SubCommand> getSubCommands() {
        return subCommands;
    }

    public List<String> filter(String arg) {
        if(filter == null) return null;
        return filter.run(arg);
    }

    public boolean requirePlayer() {
        return instancePlayer;
    }
    
}
