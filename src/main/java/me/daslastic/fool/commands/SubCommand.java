package me.daslastic.fool.commands;

import org.bukkit.command.CommandSender;

import me.daslastic.fool.Fool;

public abstract class SubCommand {

    public abstract String getName();
    public abstract String getDescription();
    public abstract String getSyntax();

    public abstract void execute(Fool plugin, CommandSender player, String args[]);

}
