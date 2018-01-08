package com.spawnchunk.silkchests.nms;

import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © 2017 spawnchunk.com
 * Created by klugemonkey on 10/26/17.
 */
public class v1_12_R1 implements NMS {

    public String getMinecraftId(ItemStack itemStack) {
        net.minecraft.server.v1_12_R1.ItemStack item = CraftItemStack.asNMSCopy(itemStack);
        final int id = Item.getId(item.getItem());
        final MinecraftKey key = Item.REGISTRY.keySet()
                .stream()
                .filter(minecraftKey -> Item.getId(Item.REGISTRY.get(minecraftKey)) == id)
                .findFirst()
                .orElse(null);
        return (key != null) ? key.toString() : null;
    }

    public String getTag(ItemStack itemStack) {
        NBTTagCompound tag = CraftItemStack.asNMSCopy(itemStack).getTag();
        return tag != null ? tag.toString() : null;
    }

    public Boolean isSilked(ItemStack itemStack) {
        NBTTagCompound nbt = CraftItemStack.asNMSCopy(itemStack).getTag();
        return nbt != null && (nbt.hasKey("Large") || nbt.hasKey("Items"));
    }

    public Boolean isLarge(ItemStack itemStack) {
        NBTTagCompound nbt = CraftItemStack.asNMSCopy(itemStack).getTag();
        return (nbt != null && nbt.hasKey("Large") && nbt.getInt("Large") == 1);
    }

    public Map<Integer, ItemStack> getItemsList(ItemStack itemStack) {
        NBTTagCompound nbt = CraftItemStack.asNMSCopy(itemStack).getTag();
        NBTTagList itemsNBT = getItemsNBT(nbt);
        Map<Integer, ItemStack> items = new HashMap<>();
        if(itemsNBT != null && !itemsNBT.isEmpty()) {
            for(int i = 0; i < itemsNBT.size(); i++) {
                NBTTagCompound slotItem = itemsNBT.get(i);
                if(slotItem.hasKey("id")) {
                    String id = slotItem.getString("id");
                    if(!id.isEmpty()) {
                        Material material = getMaterialFromKey(id);
                        if(material != null && material != Material.AIR) {
                            if (slotItem.hasKey("Slot")) {
                                int slot = slotItem.getInt("Slot");
                                slotItem.remove("Slot");
                                int amount = slotItem.getInt("Count");
                                short durability = slotItem.getShort("Damage");
                                ItemStack item = new ItemStack(material, amount, durability);
                                net.minecraft.server.v1_12_R1.ItemStack is = CraftItemStack.asNMSCopy(item);
                                items.put(slot, CraftItemStack.asBukkitCopy(is));
                            }
                        }
                    }
                }
            }
        }
        return items;
    }

    private NBTTagList getItemsNBT(NBTTagCompound chestNBT) {
        NBTTagList nbt = new NBTTagList();
        if(chestNBT != null) {
            if(chestNBT.hasKeyOfType("Items", 9)) {
                nbt = chestNBT.getList("Items", 10);
            }
        }
        return nbt;
    }

    public ItemStack setTag(ItemStack itemStack, String nbt) {
        net.minecraft.server.v1_12_R1.ItemStack item = CraftItemStack.asNMSCopy(itemStack);
        try {
            NBTTagCompound tag = MojangsonParser.parse(nbt);
            item.setTag(tag);
            return CraftItemStack.asBukkitCopy(item);
        } catch (MojangsonParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Material getMaterialFromKey(String key) {
        MinecraftKey minecraftKey = new MinecraftKey(key);
        ItemStack item = CraftItemStack.asNewCraftStack(Item.REGISTRY.get(minecraftKey));
        return item.getType();
    }

}
