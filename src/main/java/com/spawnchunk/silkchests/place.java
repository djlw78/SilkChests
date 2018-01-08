package com.spawnchunk.silkchests;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static com.spawnchunk.silkchests.SilkChests.*;
import static com.spawnchunk.silkchests.global.yawToFace;
import static com.spawnchunk.silkchests.inventory.setInventory;

/**
 * Copyright © 2017 spawnchunk.com
 * Created by klugemonkey on 12/30/17.
 */
public class place {
    static void onBlockPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        Material material = item.getType();
        if(material != Material.CHEST && material != Material.TRAPPED_CHEST) return;
        if(!nms.isSilked(item)) return;
        if(!canPlace(event)) {
            event.setCancelled(true);
            return;
        }
        onChestPlace(event);
    }

    private static void onChestPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();
        ItemStack item = event.getItemInHand();
        Boolean large = nms.isLarge(item);
        if(large) {
            Block adjoining = getBlockAdjoining(player, block);
            adjoining.setType(item.getType());
        }
        BlockState bs = block.getState();
        Chest ch = (Chest) bs;
        Inventory inventory = large ? ch.getInventory().getHolder().getInventory() : ch.getInventory();
        setInventory(inventory, item);
    }

    private static Block getBlockAdjoining(Player player, Block block) {
        BlockFace facing = getFacingDirection(player);
        if(facing.equals(BlockFace.NORTH)) return block.getRelative(BlockFace.EAST);
        if(facing.equals(BlockFace.EAST)) return block.getRelative(BlockFace.SOUTH);
        if(facing.equals(BlockFace.SOUTH)) return block.getRelative(BlockFace.WEST);
        if(facing.equals(BlockFace.WEST)) return block.getRelative(BlockFace.NORTH);
        return block.getRelative(BlockFace.NORTH);
    }

    private static BlockFace getFacingDirection(Player player) {
        Location location = player.getLocation();
        Float yaw = location.getYaw();
        return yawToFace(yaw, false);
    }

    private static boolean canPlace(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        ItemStack item = event.getItemInHand();
        Material type = item.getType();
        Boolean large = nms.isLarge(item);
        if(!large) return true;
        Block adjoining = getBlockAdjoining(event.getPlayer(), block);
        if(adjoining.getType() != Material.AIR) return false;
        Block north = adjoining.getRelative(BlockFace.NORTH);
        Block east = adjoining.getRelative(BlockFace.EAST);
        Block south = adjoining.getRelative(BlockFace.SOUTH);
        Block west = adjoining.getRelative(BlockFace.WEST);
        if(north.getType().equals(type) && !block.equals(north)) return false;
        if(east.getType().equals(type) && !block.equals(east)) return false;
        if(south.getType().equals(type) && !block.equals(south)) return false;
        if(west.getType().equals(type) && !block.equals(west)) return false;
        return true;
    }
}
