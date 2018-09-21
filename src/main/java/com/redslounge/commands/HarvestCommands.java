package com.redslounge.commands;

import com.redslounge.Plugin;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Crops;

public class HarvestCommands implements CommandExecutor, Listener
{
    private Plugin plugin;

    public HarvestCommands(Plugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args)
    {
        if(command.getName().equalsIgnoreCase("harvest") && (commandSender instanceof Player))
        {
            Player player = (Player) commandSender;

            player.sendMessage("giveing wand of harvest");

            ItemStack item = new ItemStack(Material.GOLDEN_HOE);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("Harvest Wand");
            meta.setUnbreakable(true);
            item.setItemMeta(meta);

            player.getInventory().addItem(item);
//            if(args.length == 1)
//            {
//
//            }
        }

        return false;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if(!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("harvest wand"))
        {
            return;
        }

        if(event.getAction() != Action.RIGHT_CLICK_BLOCK)
        {
            return;
        }

        Ageable ageable = (Ageable) event.getClickedBlock().getBlockData();
        ageable.setAge(ageable.getMaximumAge());
        event.getClickedBlock().setBlockData(ageable);
        event.getPlayer().sendMessage(event.getClickedBlock().getState().getBlockData().toString());
    }
}
