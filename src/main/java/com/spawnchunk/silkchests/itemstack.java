package com.spawnchunk.silkchests;

import org.bukkit.inventory.ItemStack;

import static com.spawnchunk.silkchests.SilkChests.*;

/**
 * Copyright © 2017 spawnchunk.com
 * Created by klugemonkey on 12/30/17.
 */
public class itemstack {

    static String toString(ItemStack item, int slot) {
        StringBuilder nbt = new StringBuilder("{");
        nbt.append(String.format("id:\"%s\",", nms.getMinecraftId(item)));
        String tag = nms.getTag(item);
        if(tag != null) nbt.append(String.format("tag:%s,", nms.getTag(item)));
        nbt.append(String.format("Count:%d,", item.getAmount()));
        nbt.append(String.format("Damage:%d,", item.getDurability()));
        nbt.append(String.format("Slot:%d", slot));
        nbt.append("}");
        return nbt.toString();
    }
}
