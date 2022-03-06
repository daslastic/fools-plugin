package me.daslastic.fool.commands.subcommands.ranks;

import org.bukkit.command.CommandSender;

import me.daslastic.fool.Fool;
import me.daslastic.fool.commands.SubCommand;
import me.daslastic.fool.player.ranks.RankManager;
import net.md_5.bungee.api.ChatColor;

public class DeleteRank extends SubCommand {

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getDescription() {
        return "delete a rank";
    }

    @Override
    public String getSyntax() {
        return "<id>";
    }

    @Override
    public void execute(Fool plugin, CommandSender player, String[] args) {
        
        RankManager rMan = plugin.getPlayerManager().getRanks();

        if(rMan.isRank(args[1])) {
            rMan.deleteRank(rMan.getRank(args[1]));
            player.sendMessage(ChatColor.GREEN + args[1] + " rank deleted!");
        } else {
            player.sendMessage(ChatColor.RED + args[1] + " rank not found.");
        }
        

    }
    
}
