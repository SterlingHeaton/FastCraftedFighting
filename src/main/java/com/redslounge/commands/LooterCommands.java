package com.redslounge.commands;

import com.redslounge.Plugin;
import com.redslounge.services.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class LooterCommands implements CommandExecutor, Listener
{
    private Plugin plugin;
    private ArmorStand armorStand;
    private int washerId = -1;
    private Chest looter;
    private Inventory inventory;

    public LooterCommands(Plugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {
        return false;
    }

    public void executeCommands(Player player, String[] args)
    {
        Block block = player.getTargetBlock(null, 5);

        if(!(block.getState() instanceof Chest))
        {
            return;
        }

        Chest chest = (Chest) block.getState();

        switch(args[1])
        {
            case "add": addLooteer(player, args, chest); break;
            case "setText": setLooterText(player, args); break;
        }
    }

    private void addLooteer(Player plyaer, String[] args, Chest chest)
    {
        if(looter != null)
        {
            return;
        }

        looter = chest;
        Location location = looter.getLocation();
        location.setX(location.getX() + .5);
        location.setY(location.getY() - .9);
        location.setZ(location.getZ() + .5);
        armorStand = location.getWorld().spawn(location, ArmorStand.class);
        armorStand.setCustomName("Looter Default Title");
        armorStand.setCustomNameVisible(true);
        armorStand.setVisible(false);
        armorStand.setGravity(false);

        Utils.bugTest("Test set for custom names");
        inventory = Bukkit.createInventory(null, 27, "&8[&6Looter&8] &aAvailable");
    }

    private void setLooterText(Player player, String[] args)
    {
        if(looter == null)
        {
            return;
        }

        armorStand.setCustomName(Utils.color(Utils.buildMessage(args, 2)));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
        {
            return;
        }

        if(!event.getClickedBlock().getLocation().equals(looter.getLocation()))
        {
            return;
        }

        event.setCancelled(true);
        event.getPlayer().openInventory(inventory);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        Utils.bugTest("Testing looter remove.");
        Utils.bugTest("event: " + event.getBlock().getLocation().toString() + "\nlooter: " + looter.getLocation().toString());
        if(event.getBlock().getLocation().equals(looter.getLocation()))
        {
            Utils.bugTest("Removing looter/armorstand");
            looter = null;
            armorStand.remove();
            armorStand = null;
        }
    }
}
