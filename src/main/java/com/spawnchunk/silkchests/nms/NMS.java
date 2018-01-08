package com.spawnchunk.silkchests.nms;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * Copyright © 2017 spawnchunk.com
 * Created by klugemonkey on 10/26/17.
 */
public interface NMS {
    String getMinecraftId(ItemStack itemStack);
    String getTag(ItemStack itemStack);
    Boolean isSilked(ItemStack itemStack);
    Boolean isLarge(ItemStack itemStack);
    Map<Integer, ItemStack> getItemsList(ItemStack itemStack);
    ItemStack setTag(ItemStack itemStack, String nbt);
    Material getMaterialFromKey(String key);
}
