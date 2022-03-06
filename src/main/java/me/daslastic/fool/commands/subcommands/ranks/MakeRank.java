package me.daslastic.fool.commands.subcommands.ranks;

import org.bukkit.command.CommandSender;

import me.daslastic.fool.Fool;
import me.daslastic.fool.commands.SubCommand;
import me.daslastic.fool.player.ranks.RankManager;
import net.md_5.bungee.api.ChatColor;

public class MakeRank extends SubCommand {

    @Override
    public String getName() {
        return "make";
    }

    @Override
    public String getDescription() {
        return "make a custom rank";
    }

    @Override
    public String getSyntax() {
        return "<id> <name> <suffix=true/false>";
    }

    @Override
    public void execute(Fool plugin, CommandSender player, String[] args) {
        
        RankManager rMan = plugin.getPlayerManager().getRanks();
        if(rMan.isRank(args[1])) {
            rMan.deleteRank(rMan.getRank(args[1]));
        }
        rMan.makeRank(args[1], args[2], Boolean.parseBoolean(args[3]));
        player.sendMessage(ChatColor.GREEN + args[1] + " rank created!");

    }
    
}
