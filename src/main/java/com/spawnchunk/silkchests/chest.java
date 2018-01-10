package com.spawnchunk.silkchests;

import org.bukkit.Nameable;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;

/**
 * Copyright © 2018 spawnchunk.com
 * Created by klugemonkey on 1/9/18.
 */
public class chest {

    static String getChestName(Block block) {
        BlockState blockState = block.getState();
        if(blockState instanceof Chest) {
            Chest chest = (Chest) blockState;
            if(chest instanceof Nameable) {
                Nameable nameable = (Nameable) chest;
                String name = nameable.getCustomName();
                if(name != null) return name;
            }
        }
        return null;
    }

    static void setChestName(Block block, String name) {
        BlockState blockState = block.getState();
        if(blockState instanceof Chest) {
            Chest chest = (Chest) blockState;
            if(chest instanceof Nameable) {
                Nameable nameable = (Nameable) chest;
                nameable.setCustomName(name);
                chest.update(true);
            }
        }
    }

}
