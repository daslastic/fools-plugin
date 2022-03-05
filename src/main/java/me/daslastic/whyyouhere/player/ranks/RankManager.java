package me.daslastic.whyyouhere.player.ranks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import me.daslastic.whyyouhere.SMP;
import me.daslastic.whyyouhere.player.PlayerData;
import me.daslastic.whyyouhere.player.PlayerManager;
import me.daslastic.whyyouhere.player.PlayerRunnable;
import me.daslastic.whyyouhere.util.UConfig;
import me.daslastic.whyyouhere.util.UText;

public class RankManager {

    private Map<String, Rank> ranks = new ConcurrentHashMap<>();
    private static final String RANK_KEY = new String("ranks");
    private final UConfig rankConfigManager = new UConfig(SMP.getInstance().getName(), "ranks");
    
    public RankManager(SMP plugin, PlayerManager pManager) {

        // set default ranks
        pManager.addDefaultValue(RANK_KEY, List.of("default"));

        // rank init
        FileConfiguration rankConfig = rankConfigManager.get();
        // add default rank
        if(!rankConfig.getKeys(false).contains("default")) {
            makeRank("default", "&a&lMember", false);
        }

        // read each rank ID
        rankConfig.getKeys(false).forEach(rankSection -> {
            
            String rankName = rankConfig.getString(String.format("%s.name", rankSection));
            boolean isSuffix = rankConfig.getBoolean(String.format("%s.suffix", rankSection));

            ranks.put(rankSection, new Rank(rankName, isSuffix));

        });

        // rank assingment
        pManager.getTasks().addJoinTask(new PlayerRunnable() {
            @Override
            public void run(PlayerData playerData) {
                List<String> ranks = playerData.config().getStringList(RANK_KEY);
                List<String> toRemove = new ArrayList<>();
                for (String rankID : ranks) {
                    if(isRank(rankID)) {
                        Rank rank = getRank(rankID);
                        assignRank(playerData.getPlayer(), playerData, rank);
                    } else {
                        toRemove.add(rankID);
                    }
                }
                ranks.removeAll(toRemove);
                playerData.config().set(RANK_KEY, ranks);
            }
        });

    }

    public void makeRank(String id, String name, Boolean isSuffix) {
        rankConfigManager.get().set(String.format("%s.%s", id, "name"), name);
        rankConfigManager.get().set(String.format("%s.%s", id, "suffix"), isSuffix);
        save();
    }

    public void save() {
        rankConfigManager.save();
    }

    public void assignRank(Player player, PlayerData playerData, Rank rank) {
        Team team = playerData.team();
        if(rank.isSuffix()) {
            team.setSuffix(UText.color(String.format("%s &f%s", team.getSuffix(), rank.getName())));
        } else {
            team.setPrefix(UText.color(String.format("%s &f%s", rank.getName(), team.getPrefix())));
        }

        player.setPlayerListName(String.format("%s%s%s", team.getPrefix(), player.getName(), team.getSuffix()));
        rank.getAssinged().add(playerData);
    }

    public void revokeRank(Player player, PlayerData playerData, Rank rank) {
        List<String> newRanks = playerData.config().getStringList(RankManager.RANK_KEY);
        newRanks.remove(rank.getName());
        playerData.config().set(RankManager.RANK_KEY, newRanks);

        updateRanks(player, playerData);

        rank.getAssinged().remove(playerData);
    }

    public void updateRanks(Player player, PlayerData playerData) {
        Team team = playerData.team();

        team.setSuffix("");
        team.setPrefix("");
        player.setPlayerListName(player.getName());
        
        for (String rankID : playerData.config().getStringList(RANK_KEY)) {
            Rank newRank = getRank(rankID);
            assignRank(player, playerData, newRank);
        }
    }

    public boolean isRank(String name) {
        return this.ranks.containsKey(name);
    }

    public Rank getRank(String name) {
        return this.ranks.get(name);
    }

}
