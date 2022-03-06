package me.daslastic.fool.player.ranks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import me.daslastic.fool.Fool;
import me.daslastic.fool.player.PlayerData;
import me.daslastic.fool.player.PlayerManager;
import me.daslastic.fool.player.PlayerRunnable;
import me.daslastic.fool.util.UConfig;
import me.daslastic.fool.util.UText;

public class RankManager {

    private final Fool plugin;
    private final PlayerManager pManager;
    private Map<String, Rank> ranks = new ConcurrentHashMap<>();
    private static final String RANK_KEY = new String("ranks");
    private final UConfig rankConfigManager = new UConfig(Fool.getInstance().getName(), "ranks");
    
    public RankManager(Fool plugin, PlayerManager pManager) {
        this.plugin = plugin;
        this.pManager = pManager;

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

            makeRank(rankSection, rankName, isSuffix);

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
                        drawRank(playerData.getPlayer(), rank);
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
        ranks.put(id, new Rank(id, name, isSuffix));
        save();
    }

    public void save() {
        rankConfigManager.save();
    }

    public void assignRank(Player player, Rank rank) {
        PlayerData playerData = pManager.getPlayerData(player);
        
        List<String> ranks = playerData.config().getStringList(RANK_KEY);
        ranks.add(rank.getId());
        playerData.config().set(RANK_KEY, ranks);
        
        updateRanks(player);
    }

    public void revokeRank(Player player, Rank rank) {
        PlayerData playerData = pManager.getPlayerData(player);

        List<String> ranks = playerData.config().getStringList(RANK_KEY);
        ranks.remove(rank.getId());
        playerData.config().set(RANK_KEY, ranks);

        updateRanks(player);
    }

    public void deleteRank(Rank rank) {
        rankConfigManager.get().set(rank.getId(), null);
        ranks.remove(rank.getId());

        pManager.getPlayerDataMap().values().forEach(playerData -> {
            updateRanks(playerData.getPlayer());
        });
    }

    public void updateRanks(Player player) {
        PlayerData playerData = pManager.getPlayerData(player);
        Team team = playerData.team();

        team.setSuffix("");
        team.setPrefix("");
        player.setPlayerListName(player.getName());

        for(String rankID : playerData.config().getStringList(RANK_KEY)) {
            if(isRank(rankID)) {
                Rank rank = getRank(rankID);
                drawRank(player, rank);
            }
        }
    }

    public void drawRank(Player player, Rank rank) {
        PlayerData playerData = pManager.getPlayerData(player);
        Team team = playerData.team();
        if(rank.isSuffix()) {
            team.setSuffix(UText.color(String.format("%s &f%s", team.getSuffix(), rank.getName())));
        } else {
            team.setPrefix(UText.color(String.format("%s &f%s", rank.getName(), team.getPrefix())));
        }

        player.setPlayerListName(String.format("%s%s%s", team.getPrefix(), player.getName(), team.getSuffix()));
    }

    public boolean isRank(String name) {
        return this.ranks.containsKey(name);
    }

    public Rank getRank(String name) {
        return this.ranks.get(name);
    }

    public Map<String, Rank> rankMap() {
        return this.ranks;
    }

}
