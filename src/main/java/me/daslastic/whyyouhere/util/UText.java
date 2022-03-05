package me.daslastic.whyyouhere.util;

import net.md_5.bungee.api.ChatColor;

public class UText {
    
    public static String color(String s) {
        if(s == null) return s;
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    
}
