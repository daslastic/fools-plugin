package me.daslastic.fool.player.ranks;

import java.util.ArrayList;
import java.util.List;

import me.daslastic.fool.player.PlayerData;
import me.daslastic.fool.util.UText;

public class Rank {
    
    private String id;

    private String name;
    private boolean suffix;
    
    public Rank(String id, String name, boolean suffix) {
        this.id = id;
        this.name = UText.color(name);
        this.suffix = suffix;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSuffix() {
        return this.suffix;
    }

    public void setSuffix(boolean suffix) {
        this.suffix = suffix;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
