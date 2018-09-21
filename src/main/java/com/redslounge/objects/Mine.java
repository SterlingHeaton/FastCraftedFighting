package com.redslounge.objects;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Random;

public class Mine
{
    private Location locationStart;
    private Location locationEnd;
    private ArrayList<HeavyMaterial> blocks;
    private String name;
    private String blockSet;
    private int totalWeight;

    public Mine(String name, Location locationStart, Location locationEnd, ArrayList<HeavyMaterial> blocks, String blockSet)
    {
        this.name = name;
        this.locationStart = locationStart;
        this.locationEnd = locationEnd;
        this.blocks = blocks;
        this.blockSet = blockSet;

        totalWeight = getTotalWeight();
    }

    public void generate()
    {
        for(int countX = locationStart.getBlockX(); countX <= locationEnd.getBlockX(); countX++)
        {
            for(int countY = locationStart.getBlockY(); countY <= locationEnd.getBlockY(); countY++)
            {
                for(int countZ = locationStart.getBlockZ(); countZ <= locationEnd.getBlockZ(); countZ++)
                {
                    locationStart.getWorld().getBlockAt(countX, countY, countZ).setType(chooseBlock());
                }
            }
        }
    }

    private Material chooseBlock()
    {
        Random random = new Random();
        int weight = random.nextInt(totalWeight);

        for(int count = 0; count < blocks.size(); count++)
        {
            if(weight <= blocks.get(count).getWeight())
            {
                return blocks.get(count).getMaterial();
            }

            if(count+1 == blocks.size())
            {
                return blocks.get(count).getMaterial();
            }
        }
        return Material.AIR;
    }

    private int getTotalWeight()
    {
        int total = 0;

        for (HeavyMaterial block : blocks)
        {
            total += block.getWeight();
        }

        return total;
    }

    @Override
    public String toString()
    {
        return ("Name:" + name + ";" +
                "LocationStart:" + locationStart.getWorld().getName() + "," + locationStart.getBlockX() + "," + locationStart.getBlockY() + "," + locationStart.getBlockZ() + ";" +
                "LocationEnd:" + locationEnd.getWorld().getName() + "," + locationEnd.getBlockX() + "," + locationEnd.getBlockY() + "," + locationEnd.getBlockZ() + ";" +
                "BlockSet:" + blockSet);
    }

    public String getBlockSet()
    {
        return blockSet;
    }

    public String getName()
    {
        return name;
    }

    public Location getLocationStart()
    {
        return locationStart;
    }

    public Location getLocationEnd()
    {
        return locationEnd;
    }
}
