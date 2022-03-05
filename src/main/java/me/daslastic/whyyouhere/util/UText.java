package me.daslastic.whyyouhere.util;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class UText {
    
    public static String color(String s) {
        if(s == null) return s;
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String cmdInvalidArgs(String arg) {
        return color(arg + "&c is an invalid argument!");
    }

    public static String cmdNoArgs(String syntax) {
        if(syntax.charAt(0) == '/') {
            syntax = syntax.replaceFirst("/","");
        }
        return color("&cInvalid command do &f/" + syntax);
    }

    public static String capitalize(String string) {
        return string.toUpperCase().charAt(0) + string.substring(1);
    }
    
    public static void sendTitle(Player player, String messege, TitleType titleType, int fandeIn, int duration, int fadeOut) {
        messege = color(messege);
        switch (titleType) {
            case TITLE:
                player.sendTitle(messege, null, fandeIn, duration, fadeOut);
                break;
            case SUBTITLE:
                player.sendTitle(null, messege, fandeIn, duration, fadeOut);
                break;
            case ACTIONBAR:
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(messege));
                break;
        }
    }
}
