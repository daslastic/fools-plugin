package me.daslastic.fool.commands.subcommands.ranks;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.daslastic.fool.Fool;
import me.daslastic.fool.commands.SubCommand;
import me.daslastic.fool.player.ranks.RankManager;
import net.md_5.bungee.api.ChatColor;

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
        return "<id> <player>";
    }

    @Override
    public void execute(Fool plugin, CommandSender player, String[] args) {
        
        RankManager rMan = plugin.getPlayerManager().getRanks();
        Player playerAssign = plugin.getServer().getPlayer(args[2]);
        if(playerAssign == null) {
            player.sendMessage(ChatColor.RED + args[2] + " player not found.");
            return;
        }

        if(rMan.isRank(args[1])) {
            rMan.assignRank(playerAssign, rMan.getRank(args[1]));
            player.sendMessage(ChatColor.GREEN + args[1] + " rank assigned to " + args[2]);
        } else {
            player.sendMessage(ChatColor.RED + args[1] + " rank not found.");
        }

    }
    
}
