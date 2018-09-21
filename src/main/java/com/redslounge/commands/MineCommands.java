package com.redslounge.commands;

import com.redslounge.objects.Mine;
import com.redslounge.Plugin;
import com.redslounge.services.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MineCommands implements CommandExecutor, Listener
{
    private Plugin plugin;
    private Location locationRight;
    private Location locationLeft;

    public MineCommands(Plugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args)
    {
        if(command.getName().equalsIgnoreCase("mine"))
        {
            if(!(commandSender instanceof Player))
            {
                commandSender.sendMessage(Utils.color("&cMust be in game to use this command."));
                return false;
            }

            Player player = (Player) commandSender;

            if(args.length < 1)
            {
                switch (args[0])
                {
                    case "wand": giveWand(player); break;
                    case "set": setMine(player, args); break;
                    case "info": getInfo(player, args); break;
                    case "list": Utils.sendMessage(player, "&aListing all mines: &7" + plugin.settings.getMines().getMines().keySet().toString(), true); break;
                    case "generate": generateMines(player, args); break;
                    case "remove": removeMine(player, args); break;
                    default: Utils.sendMessage(player, "&cInvalid sub command for mines.", true); break;
                }
            }
        }

        return false;
    }

    public void executeCommands(Player player, String[] args)
    {
        if(args.length < 2)
        {
            Utils.sendMessage(player, "&cMust spcify action for mine.", true);
            return;
        }

        if(args[1].equalsIgnoreCase("wand"))
        {
            giveWand(player);
        }
        else if(args[1].equalsIgnoreCase("set"))
        {
            setMine(player, args);
        }
        else if(args[1].equalsIgnoreCase("info"))
        {
            getInfo(player, args);
        }
        else if(args[1].equalsIgnoreCase("list"))
        {
            Utils.sendMessage(player, "&aListing all mines: &7" + plugin.settings.getMines().getMines().keySet().toString(), true);
        }
        else if(args[1].equalsIgnoreCase("generate"))
        {
            generateMines(player, args);
        }
        else if(args[1].equalsIgnoreCase("remove"))
        {
            removeMine(player, args);
        }
    }

    private void giveWand(Player player)
    {
        ItemStack item = new ItemStack(Material.GOLDEN_PICKAXE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("Mine Wand");
        itemMeta.setUnbreakable(true);
        item.setItemMeta(itemMeta);

        player.getInventory().setItemInMainHand(item);
    }

    private void setMine(Player player, String[] args)
    {
        if(locationLeft == null || locationRight == null)
        {
            Utils.sendMessage(player, "&cMine doesn't have a selected area.", true);
            return;
        }

        if(locationRight.getWorld() != locationLeft.getWorld())
        {
            Utils.sendMessage(player, "&cMine points A and B aren't in the same world", true);
        }

        if(args.length < 2)
        {
            Utils.sendMessage(player, "&cMine has to have a name.", true);
            return;
        }

        if(args.length < 3)
        {
            Utils.sendMessage(player,"&cMine doesn't have a BlockSet. &7" + plugin.settings.getMines().getMineMaterials().keySet().toString(), true);
            return;
        }

        if(!plugin.settings.getMines().getMineMaterials().containsKey(args[2]))
        {
            Utils.sendMessage(player, "&cBlockSet for mine doesnt exist. &7 " + plugin.settings.getMines().getMineMaterials().keySet().toString(), true);
            return;
        }

        Location[] locations = calculateLocations();
        Mine mine = new Mine(args[1], locations[0], locations[1], plugin.settings.getMines().getMineMaterials().get(args[2]), args[2]);

        plugin.settings.getMines().addMine(args[1], mine);

        Utils.sendMessage(player, "&aMine &6" + mine.getName() + "&a created.", true);
    }

    private void getInfo(Player player, String[] args)
    {
        if(args.length < 2)
        {
            Utils.sendMessage(player,"&cMust speicify mine. &7" + plugin.settings.getMines().getMines().keySet().toString(), true);
        }

        if(plugin.settings.getMines().getMines().containsKey(args[1]))
        {
            Utils.sendMessage(player, "&cMine doesnt exist. &7" + plugin.settings.getMines().getMines().keySet().toString(), true);
            return;
        }

        Mine mine = plugin.settings.getMines().getMine(args[1]);

        Utils.sendMessage(player, "&aInformation for mine: &6" + mine.toString(), true);
        Utils.sendMessage(player, "&aInformation for mine blockset: &6" + plugin.settings.getMines().getMineMaterials().get(mine.getBlockSet()).toString(), true);
    }

    private void generateMines(Player player, String[] args)
    {
        if(args.length < 2)
        {
            Utils.sendMessage(player, "&aGenerating all mines.", true);
            plugin.settings.getMines().generateMines();
            return;
        }

        if(!plugin.settings.getMines().getMines().containsKey(args[1]))
        {
            Utils.sendMessage(player, "&cMine doesnt exist. &7" + plugin.settings.getMines().getMines().keySet().toString(), true);
            return;
        }

        Utils.sendMessage(player, "&aGenerating mine &6" + plugin.settings.getMines().getMine(args[1]).getName() + "&a.", true);
        plugin.settings.getMines().getMine(args[1]).generate();
    }

    public void removeMine(Player player, String[] args)
    {
        if(args.length < 2)
        {
            Utils.sendMessage(player, "&cMust specify a mine to remove.", true);
            return;
        }

        if(!plugin.settings.getMines().getMines().containsKey(args[1]))
        {
            Utils.sendMessage(player, "&cMine doesn't exist! &7" + plugin.settings.getMines().getMines().keySet().toString(), true);
            return;
        }

        Utils.sendMessage(player, "&aRemoved mine: &6" + plugin.settings.getMines().getMine(args[1]).getName(), true);
        String mine = plugin.settings.getMines().getMines().get(args[1]).getName();
        plugin.config.getConfigMines().set(mine, null);
        plugin.settings.getMines().getMines().remove(args[1]);
    }

    @EventHandler
    private void onInteration(PlayerInteractEvent event)
    {
        if(event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.AIR))
        {
            return;
        }

        if(!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("mine wand"))
        {
            return;
        }

        if(event.getAction() == null)
        {
            return;
        }

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            if(locationRight == null || !locationRight.equals(event.getClickedBlock().getLocation()))
            {
                locationRight = event.getClickedBlock().getLocation();
                Utils.sendMessage(event.getPlayer(), "&aLocationA Set: &7" + buildLocationText(locationRight), true);
            }
        }

        if(event.getAction() == Action.LEFT_CLICK_BLOCK)
        {
            if(locationLeft == null || !locationLeft.equals(event.getClickedBlock().getLocation()))
            {
                locationLeft = event.getClickedBlock().getLocation();
                Utils.sendMessage(event.getPlayer(), "&aLocationB Set: &7" +buildLocationText(locationLeft), true);
            }
        }
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event)
    {
        if(event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.AIR))
        {
            return;
        }

        if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("mine wand"))
        {
            event.setCancelled(true);
        }
    }

    private String buildLocationText(Location location)
    {
        return ("x" + location.getBlockX() + " y" + location.getBlockY() + " z" + location.getBlockZ());
    }

    private Location[] calculateLocations()
    {
        Location[] locations = new Location[2];

        int locStartX = locationRight.getBlockX();
        int locStartY = locationRight.getBlockY();
        int locStartZ = locationRight.getBlockZ();
        int locEndX = locationLeft.getBlockX();
        int locEndY = locationLeft.getBlockY();
        int locEndZ = locationLeft.getBlockZ();
        int temp;

        if(locStartX > locEndX)
        {
            temp = locStartX;
            locStartX = locEndX;
            locEndX = temp;
        }

        if(locStartY > locEndY)
        {
            temp = locStartY;
            locStartY = locEndY;
            locEndY = temp;
        }

        if(locStartZ > locEndZ)
        {
            temp = locStartZ;
            locStartZ = locEndZ;
            locEndZ = temp;
        }

        locations[0] = new Location(locationRight.getWorld(), locStartX, locStartY, locStartZ);
        locations[1] = new Location(locationLeft.getWorld(), locEndX, locEndY, locEndZ);

        return locations;
    }
}
