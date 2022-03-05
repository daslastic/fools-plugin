package me.daslastic.whyyouhere.player.ranks;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Rank {
    
    private String name;
    private List<UUID> assigned = new ArrayList<>();

    public Rank(String name) {
        this.name = name;
        this.assigned = new ArrayList<>();
    }

}
