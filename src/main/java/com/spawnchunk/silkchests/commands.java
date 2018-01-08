package com.spawnchunk.silkchests;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.spawnchunk.silkchests.SilkChests.*;
import static com.spawnchunk.silkchests.config.reloadConfig;
import static com.spawnchunk.silkchests.global.sectionSymbol;

/**
 * Copyright © 2017 spawnchunk.com
 * Created by klugemonkey on 5/15/17.
 */
class commands {

    // silkchests command
    public static boolean silkChests(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!perms.playerHas(player, "silkchests.admin")) {
                sender.sendMessage(sectionSymbol("&fUnknown command. Type \"/help\" for help."));
                return true;
            }
        }
        // reload
        if (args.length != 1) return false;
        if (!args[0].equalsIgnoreCase("reload")) return false;
        reloadConfig();
        sender.sendMessage(sectionSymbol("&fSilkChests reloaded"));
        return true;
    }
}
