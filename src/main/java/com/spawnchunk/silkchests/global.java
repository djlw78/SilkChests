package com.spawnchunk.silkchests;

import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;

import static com.spawnchunk.silkchests.SilkChests.*;

/**
 * Copyright © 2017 spawnchunk.com
 * Created by klugemonkey on 8/17/17.
 */
class global {

    static String sectionSymbol(String message) {
        return message.replace("&","\u00a7");
    }

    static String envars(Player player, String m) {
        String world = player.getWorld().toString();
        String group = perms.getPrimaryGroup(player);
        String prefix = chat.getGroupPrefix(world, group);
        String playerName = player.getDisplayName();
        String message = m.replace("%server%", servername);
        message = message.replace("%level%", levelname);
        message = message.replace("%plugin_prefix%", pluginPrefix);
        message = message.replace("%world%", world);
        message = message.replace("%group%", group);
        message = message.replace("%prefix%", prefix);
        message = message.replace("%player%", playerName);
        return message;
    }

    static boolean isLarge(Chest chest) {
        return (chest.getInventory().getHolder() instanceof DoubleChest);
    }

    private static final BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
    private static final BlockFace[] radial = { BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST };

    static BlockFace yawToFace(float yaw, boolean useSubCardinalDirections) {
        if (useSubCardinalDirections) return radial[Math.round(yaw / 45f) & 0x7].getOppositeFace();
        return axis[Math.round(yaw / 90f) & 0x3].getOppositeFace();
    }
}
