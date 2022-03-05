package me.daslastic.whyyouhere.player.ranks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.configuration.file.FileConfiguration;

import me.daslastic.whyyouhere.SMP;
import me.daslastic.whyyouhere.player.PlayerData;
import me.daslastic.whyyouhere.player.PlayerManager;
import me.daslastic.whyyouhere.player.PlayerRunnable;
import me.daslastic.whyyouhere.util.UConfig;

public class RankManager {

    private Map<String, Rank> ranks = new ConcurrentHashMap<>();
    private final String rankKey = new String("ranks");
    private final UConfig rankConfigManager = new UConfig(SMP.getInstance().getName(), "ranks");
    
    public RankManager(SMP plugin, PlayerManager pManager) {

        // set default ranks
        pManager.addDefaultValue(rankKey, List.of("default"));

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
                List<String> ranks = playerData.config().getStringList(rankKey);
                List<String> toRemove = new ArrayList<>();
                for (String rankID : ranks) {
                    if(isRank(rankID)) {
                        Rank rank = getRank(rankID);
                        rank.assignRank(playerData);
                    } else {
                        toRemove.add(rankID);
                    }
                }
                ranks.removeAll(toRemove);
                playerData.config().set(rankKey, ranks);
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

    public boolean isRank(String name) {
        return this.ranks.containsKey(name);
    }

    public Rank getRank(String name) {
        return this.ranks.get(name);
    }

}
