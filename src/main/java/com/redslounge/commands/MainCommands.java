package com.redslounge.commands;

import com.redslounge.Plugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommands implements CommandExecutor
{
    private Plugin plugin;

    public MainCommands(Plugin plugin)
    {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
    {
        if(!(commandSender instanceof Player))
        {
            commandSender.sendMessage("You must be in game to use this command.");
            return true;
        }

        Player player = (Player) commandSender;

        if(command.getName().equalsIgnoreCase("fcf"))
        {
            player.sendMessage(plugin.settings.getChatPrefix() + " &aTODO ADD COMMANDS LOL");
        }

        return false;
    }
}
