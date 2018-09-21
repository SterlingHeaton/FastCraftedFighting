package com.redslounge;

import com.redslounge.commands.HarvestCommands;
import com.redslounge.commands.MainCommands;
import com.redslounge.commands.LooterCommands;
import com.redslounge.commands.MineCommands;
import com.redslounge.services.Config;
import com.redslounge.services.Settings;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin
{
    public MineCommands mineCommands = new MineCommands(this);
    public LooterCommands looterCommands = new LooterCommands(this);
//    public Looters looters = new Looters(this);
    public Settings settings;
    public Config config;

    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(mineCommands, this);
//        getServer().getPluginManager().registerEvents(looterCommands, this);
        getServer().getPluginManager().registerEvents(new HarvestCommands(this), this);

        getCommand("fcf").setExecutor(new MainCommands(this));
        getCommand("mine").setExecutor(mineCommands);
//        getCommand("loot").setExecutor(looterCommands);
        getCommand("harvest").setExecutor(new HarvestCommands(this));

        settings = new Settings();
        config = new Config(this);
    }

    @Override
    public void onDisable()
    {
        config.saveSettings();
    }

    @Override
    public void onLoad()
    {

    }
}
