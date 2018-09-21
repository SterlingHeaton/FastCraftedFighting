package com.redslounge.services;

import com.redslounge.Plugin;
import com.redslounge.objects.HeavyMaterial;
import com.redslounge.objects.Mine;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class Config
{
    private Plugin plugin;
    private File fileConfigBlockset, fileConfigMines;
    private FileConfiguration configBlockset, configMines;

    public Config(Plugin plugin)
    {
        this.plugin = plugin;

        createFiles();
    }

    private void createFiles()
    {
        if(!plugin.getDataFolder().exists())
        {
            plugin.getDataFolder().mkdirs();
        }

        fileConfigBlockset = new File(plugin.getDataFolder(), "Mine Blocksets.yml");
        if(!fileConfigBlockset.exists())
        {
            createFile(fileConfigBlockset);
        }
        configBlockset = YamlConfiguration.loadConfiguration(fileConfigBlockset);

        fileConfigMines = new File(plugin.getDataFolder(), "Mine Saves.yml");
        if(!fileConfigMines.exists())
        {
            createFile(fileConfigMines);
        }
        configMines = YamlConfiguration.loadConfiguration(fileConfigMines);

        loadSettings();
    }

    private void loadSettings()
    {
        // LOAD MINE BLOCKSETS
        ConfigurationSection blockSets = getConfigBlockset().getConfigurationSection("");
        for(String blockSet : blockSets.getKeys(false))
        {
            ArrayList<HeavyMaterial> heavyMaterials = new ArrayList<>();

            ConfigurationSection stringHeavyMaterials = getConfigBlockset().getConfigurationSection(blockSet);
            for(String stringHeavyMaterial : stringHeavyMaterials.getKeys(false))
            {
                String blockTypes = (blockSet + "." + stringHeavyMaterial);
                HeavyMaterial heavyMaterial = new HeavyMaterial(Material.valueOf(getConfigBlockset().getString(blockTypes + ".type")), getConfigBlockset().getInt(blockTypes + ".weight"));

                heavyMaterials.add(heavyMaterial);
            }
            plugin.settings.getMines().getMineMaterials().put(blockSet, heavyMaterials);
            Collections.sort(plugin.settings.getMines().getMineMaterials().get(blockSet));
        }

        // LOAD MINE SETTINGS
        ConfigurationSection mines = getConfigMines().getConfigurationSection("");
        for(String mine : mines.getKeys(false))
        {
            Location locationA = new Location(plugin.getServer().getWorld(getConfigMines().getString(mine + ".locations.a.world")),
                    getConfigMines().getInt(mine + ".locations.a.x"),
                    getConfigMines().getInt(mine + ".locations.a.y"),
                    getConfigMines().getInt(mine + ".locations.a.z"));
            Location locationB = new Location(plugin.getServer().getWorld(getConfigMines().getString(mine + ".locations.b.world")),
                    getConfigMines().getInt(mine + ".locations.b.x"),
                    getConfigMines().getInt(mine + ".locations.b.y"),
                    getConfigMines().getInt(mine + ".locations.b.z"));
            ArrayList<HeavyMaterial> blockSet = plugin.settings.getMines().getMineMaterials().get(getConfigMines().getString(mine + ".blockSet"));
            Mine mineObject = new Mine(mine, locationA, locationB, blockSet, getConfigMines().getString(mine + ".blockSet"));

            plugin.settings.getMines().getMines().put(mineObject.getName(), mineObject);
        }
    }

    public void saveSettings()
    {
        // SAVE MINE SETTINGS
        for(Mine mine : plugin.settings.getMines().getMines().values())
        {
            Location locationA = mine.getLocationStart();
            Location locationB = mine.getLocationEnd();

            getConfigMines().set(mine.getName() + ".locations.a.world", locationA.getWorld().getName());
            getConfigMines().set(mine.getName() + ".locations.a.x", locationA.getBlockX());
            getConfigMines().set(mine.getName() + ".locations.a.y", locationA.getBlockY());
            getConfigMines().set(mine.getName() + ".locations.a.z", locationA.getBlockZ());

            getConfigMines().set(mine.getName() + ".locations.b.world", locationB.getWorld().getName());
            getConfigMines().set(mine.getName() + ".locations.b.x", locationB.getBlockX());
            getConfigMines().set(mine.getName() + ".locations.b.y", locationB.getBlockY());
            getConfigMines().set(mine.getName() + ".locations.b.z", locationB.getBlockZ());

            getConfigMines().set(mine.getName() + ".blockSet", mine.getBlockSet());
        }

        try
        {
            getConfigMines().save(fileConfigMines);
        }
        catch(Exception e)
        {

        }
    }

    private void createFile(File file)
    {
        try
        {
            file.createNewFile();
        } catch (Exception e)
        {
            System.out.println("&cFailed to create the file: " + file.getName());
        }
    }

    public FileConfiguration getConfigBlockset()
    {
        return configBlockset;
    }

    public FileConfiguration getConfigMines()
    {
        return configMines;
    }

}
