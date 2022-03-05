package me.daslastic.whyyouhere.player.ranks;

import java.util.ArrayList;
import java.util.HashMap;
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

        pManager.addDefaultValue(rankKey, new ArrayList<String>());

        // rank init
        FileConfiguration rankConfig = rankConfigManager.get();
        // read each rank ID
        rankConfig.getKeys(false).forEach(rankSection -> {
            
        });

        // rank assingment
        pManager.getTasks().addJoinTask(new PlayerRunnable() {
            @Override
            public void run(PlayerData playerData) {
                List<String> ranks = playerData.config().getStringList(rankKey);
                List<String> toRemove = new ArrayList<>();
                for (String rank : ranks) {
                    if(isRank(rank)) {

                    } else {
                        toRemove.add(rank);
                    }
                }
                ranks.removeAll(toRemove);
                playerData.config().set(rankKey, ranks);
            }
        });

    }

    public boolean isRank(String name) {
        return this.ranks.containsKey(name);
    }

    public Rank getRank(String name) {
        return this.ranks.get(name);
    }

}
