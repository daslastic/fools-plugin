package me.daslastic.whyyouhere.util;

import net.md_5.bungee.api.ChatColor;

public class UText {
    
    public static String translateColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    
}
