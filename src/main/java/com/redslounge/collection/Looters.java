package com.redslounge.collection;

import com.redslounge.Plugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Looters implements Listener
{
    private Plugin plugin;
    private int washerId = -1;
    ArmorStand armorStand;

    public Looters(Plugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event)
    {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
        {
            return;
        }

        if (!(event.getClickedBlock().getState() instanceof Chest))
        {
            return;
        }

        Chest looter = (Chest) event.getClickedBlock().getState();
        Player player = event.getPlayer();

        if(armorStand == null)
        {
            Location location = event.getClickedBlock().getLocation();
            location.setX(location.getX() + .5);
            location.setY(location.getY() - .9);
            location.setZ(location.getZ() + .5);
            armorStand = location.getWorld().spawn(location, ArmorStand.class);
            armorStand.setCustomName("Test");
            armorStand.setCustomNameVisible(true);
            armorStand.setVisible(false);
            armorStand.setGravity(false);
        }


    }

    private boolean isEmpty(Chest chest)
    {
        for(ItemStack item : chest.getBlockInventory().getContents())
        {
            if(item != null)
            {
                return false;
            }
        }
        return true;
    }
}
