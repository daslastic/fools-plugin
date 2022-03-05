package me.daslastic.fool.commands.subcommands.ranks;

import org.bukkit.command.CommandSender;

import me.daslastic.fool.Fool;
import me.daslastic.fool.commands.SubCommand;

public class AssignRank extends SubCommand {

    @Override
    public String getName() {
        return "assign";
    }

    @Override
    public String getDescription() {
        return "asssign a rank to a player";
    }

    @Override
    public String getSyntax() {
        return "<player> <id>";
    }

    @Override
    public void execute(Fool plugin, CommandSender player, String[] args) {
        
    }
    
}
