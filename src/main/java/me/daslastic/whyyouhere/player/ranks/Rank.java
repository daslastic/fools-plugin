package me.daslastic.whyyouhere.player.ranks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import me.daslastic.whyyouhere.player.PlayerData;
import me.daslastic.whyyouhere.util.UText;

public class Rank {
    
    private String name;
    private Boolean isSuffix;
    private List<PlayerData> assigned = new ArrayList<>();

    public Rank(String name, Boolean isSuffix) {
        this.name = UText.color(name);
        this.isSuffix = isSuffix;
        this.assigned = new ArrayList<>();
    }

    public void assignRank(PlayerData playerData) {
        Team team = playerData.team();
        if(isSuffix) {
            team.setSuffix(UText.color(String.format("%s &f%s", team.getSuffix(), name)));
        } else {
            team.setPrefix(UText.color(String.format("%s &f%s", name, team.getPrefix())));
        }
        assigned.add(playerData);
        Player player = playerData.getPlayer();
        player.getPlayer().setPlayerListName(String.format("%s%s%s", team.getPrefix(), player.getName(), team.getSuffix()));
    }

}
