package com.spawnchunk.silkchests;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static com.spawnchunk.silkchests.SilkChests.*;
import static com.spawnchunk.silkchests.global.isLarge;
import static com.spawnchunk.silkchests.global.sectionSymbol;

/**
 * Copyright © 2017 spawnchunk.com
 * Created by klugemonkey on 12/30/17.
 */
public class silk {

    static void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        BlockState bs = block.getState();
        if(!(bs instanceof Chest)) return;
        onChestBreak(event);
    }

    private static void onChestBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(notSilking(player)) return;
        if(silkPerms && !perms.playerHas(player,"chests.silk")) return;
        // event.setDropItems(false);
        event.setExpToDrop(0);
        Block block = event.getBlock();
        silkChest(player, block);
        BlockState bs = block.getState();
        Chest ch = (Chest) bs;
        Boolean large = isLarge(ch);
        if(large) {
            DoubleChest dc = (DoubleChest) ch.getInventory().getHolder();
            dc.getInventory().clear();
            Chest leftSide = (Chest) dc.getLeftSide();
            Chest rightSide = (Chest) dc.getRightSide();
            leftSide.getBlock().setType(Material.AIR);
            rightSide.getBlock().setType(Material.AIR);
        } else {
            ch.getInventory().clear();
            block.setType(Material.AIR);
        }
        event.setCancelled(true);
        block.getWorld().playSound(block.getLocation(), Sound.valueOf(oldSound ? "DIG_WOOD": "BLOCK_WOOD_BREAK"), 1.0F, 1.0F);
    }

    static void onBlockDamage(BlockDamageEvent event) {
        Block block = event.getBlock();
        BlockState bs = block.getState();
        if(!(bs instanceof Chest)) return;
        onChestDamage(event);
    }

    private static void onChestDamage(BlockDamageEvent event) {
        Player player = event.getPlayer();
        if(notSilking(player)) return;
        if(silkPerms && !perms.playerHas(player,"chests.silk")) return;
        // force instabreak
        event.setInstaBreak(true);
    }

    private static boolean notSilking(Player player) {
        GameMode gm = player.getGameMode();
        if(gm.equals(GameMode.SPECTATOR) || gm.equals(GameMode.ADVENTURE)) return true;
        if(gm.equals(GameMode.CREATIVE) && !silkCreative) return true;
        ItemStack item = player.getInventory().getItemInHand();
        if(item == null) return true;
        Material tool = item.getType();
        if(tools.isEmpty()) return true;
        if(!tools.contains(tool)) return true;
        if(!item.containsEnchantment(Enchantment.SILK_TOUCH)) return true;
        return item.getEnchantmentLevel(Enchantment.SILK_TOUCH) < silkLevel;
    }

    private static void silkChest(Player player, Block block) {
        BlockState bs = block.getState();
        Material type = block.getType();
        if(!(bs instanceof Chest)) return;
        Chest ch = (Chest) bs;
        Boolean large = isLarge(ch);
        Inventory inventory = large ? ch.getInventory().getHolder().getInventory() : ch.getInventory();
        ItemStack item = new ItemStack(type, 1);
        String tag = com.spawnchunk.silkchests.inventory.toString(inventory, large);
        item = nms.setTag(item, tag);
        player.getWorld().dropItem(block.getLocation(), item);
    }

}
