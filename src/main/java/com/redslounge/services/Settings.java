package com.redslounge.services;

import com.redslounge.collection.Mines;

public class Settings
{
    private Mines mines = new Mines();
    public static String chatPrefix = "&8[&6F&aC&6F&8]";

    public Mines getMines()
    {
        return mines;
    }

    public String getChatPrefix()
    {
        return chatPrefix;
    }
}
