package com.redslounge.collection;

import com.redslounge.objects.HeavyMaterial;
import com.redslounge.objects.Mine;

import java.util.ArrayList;
import java.util.TreeMap;

public class Mines
{
    private TreeMap<String, Mine> mines = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private TreeMap<String, ArrayList<HeavyMaterial>> mineTypes = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public Mine getMine(String mineName)
    {
        return mines.get(mineName);
    }

    public void addMine(String mineName, Mine mine)
    {
        mines.put(mineName, mine);
    }

    public TreeMap<String, Mine> getMines()
    {
        return mines;
    }

    public TreeMap<String, ArrayList<HeavyMaterial>> getMineMaterials()
    {
        return mineTypes;
    }

    public void generateMines()
    {
        for(Mine mine : mines.values())
        {
            mine.generate();
        }
    }
}
