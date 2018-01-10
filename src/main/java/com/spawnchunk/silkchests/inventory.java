package com.spawnchunk.silkchests;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static com.spawnchunk.silkchests.SilkChests.*;
import static com.spawnchunk.silkchests.global.sectionSymbol;

/**
 * Copyright © 2017 spawnchunk.com
 * Created by klugemonkey on 12/30/17.
 */
public class inventory {

    private static Map<Integer, ItemStack> getItemMap(Inventory inventory) {
        Map<Integer, ItemStack> items = new HashMap<>();
        ItemStack contents[] = inventory.getContents();
        for(int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if(item != null && item.getType() != Material.AIR) {
                items.put(i, item);
            }
        }
        return items;
    }

    static Inventory setInventory(Inventory inventory, ItemStack itemStack) {
        Map<Integer, ItemStack> itemMap = nms.getItemsList(itemStack);
        inventory.clear();
        int size = inventory.getSize();
        ItemStack[] items = new ItemStack[size];
        for(int slot: itemMap.keySet()) {
            items[slot] = itemMap.get(slot);
        }
        inventory.setContents(items);
        return inventory;
    }

    static String toString(Inventory inventory, Boolean large, String customName) {
        Map<Integer, ItemStack> itemMap = getItemMap(inventory);
        StringBuilder string = new StringBuilder("{");
        string.append(String.format("Large:%d", large?1:0));
        if(!customName.isEmpty()) {
            string.append(String.format(",display:{Name:\"%s\"}",customName));
        }
        if(!itemMap.isEmpty()) {
            string.append(",Items:[");
            for (Integer slot : itemMap.keySet()) {
                ItemStack item = itemMap.get(slot);
                string.append(itemstack.toString(item, slot));
                string.append(",");
            }
            string.setLength(string.length()-1);
            string.append("],");
        }
        string.append("}");
        if(debug) logger.info(sectionSymbol(String.format("%s nbt = %s ", pluginPrefix, string.toString())));
        return string.toString();
    }

}
