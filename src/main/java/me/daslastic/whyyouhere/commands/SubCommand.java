package me.daslastic.whyyouhere.commands;

import org.bukkit.command.CommandSender;

import me.daslastic.whyyouhere.SMP;

public abstract class SubCommand {

    public abstract String getName();
    public abstract String getDescription();
    public abstract String getSyntax();

    public abstract void execute(SMP plugin, CommandSender player, String args[]);

}
