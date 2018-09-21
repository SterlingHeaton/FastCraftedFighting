package com.redslounge.objects;

import org.bukkit.Material;

public class HeavyMaterial implements Comparable<HeavyMaterial>
{
    private Material material;
    private int weight;

    public HeavyMaterial(Material material, int weight)
    {
        this.material = material;
        this.weight = weight;
    }

    public Material getMaterial()
    {
        return material;
    }

    public int getWeight()
    {
        return weight;
    }

    @Override
    public int compareTo(HeavyMaterial otherHeavyMaterial)
    {
        return (this.weight - otherHeavyMaterial.weight);
    }

    @Override
    public String toString()
    {
        return ("Material: " + material.name() + ", Weight: " + weight);
    }
}
