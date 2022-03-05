package me.daslastic.fool.commands.subcommands.ranks;

import org.bukkit.command.CommandSender;

import me.daslastic.fool.Fool;
import me.daslastic.fool.commands.SubCommand;

public class RevokeRank extends SubCommand {

    @Override
    public String getName() {
        return "revoke";
    }

    @Override
    public String getDescription() {
        return "remove a rank to a player";
    }

    @Override
    public String getSyntax() {
        return "<player> <id>";
    }

    @Override
    public void execute(Fool plugin, CommandSender player, String[] args) {
        
    }
    
}
