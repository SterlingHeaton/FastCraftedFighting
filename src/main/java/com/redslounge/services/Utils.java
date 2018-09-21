package com.redslounge.services;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils
{
    public static void sendMessage(Player player, String message, boolean prefix)
    {
        if(prefix)
        {
            player.sendMessage(color( Settings.chatPrefix + " " + message));
        }
        else
        {
            player.sendMessage(color(message));
        }
    }

    public static void broadcastMessage(String message)
    {
        Bukkit.broadcastMessage(color(message));
    }

    public static String color(String color)
    {
        return ChatColor.translateAlternateColorCodes('&', color);
    }

    public static void bugTest(String message)
    {
        Bukkit.broadcastMessage(Utils.color("&8[&4BugTest&8]" + "&7 " + message));
    }

    public static void bugTest(int message)
    {
        Bukkit.broadcastMessage(Utils.color("&8[&4BugTest&8]" + "&7 " + message));
    }

    public static String buildMessage(String[] parts, int start)
    {
        StringBuilder note = new StringBuilder();
        for(int count = start; count < parts.length; count++)
        {
            note.append(parts[count] + " ");
        }
        return note.toString();
    }
}
