package me.daslastic.fool.player.ranks;

import java.util.ArrayList;
import java.util.List;

import me.daslastic.fool.player.PlayerData;
import me.daslastic.fool.util.UText;

public class Rank {
    
    private String name;
    private boolean suffix;
    private List<PlayerData> assinged = new ArrayList<>();
    
    public Rank(String name, boolean suffix) {
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

    public List<PlayerData> getAssinged() {
        return this.assinged;
    }

    public void setAssinged(List<PlayerData> assinged) {
        this.assinged = assinged;
    }

}
