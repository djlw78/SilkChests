package com.spawnchunk.silkchests;

import org.bukkit.Material;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static com.spawnchunk.silkchests.SilkChests.*;
import static com.spawnchunk.silkchests.global.sectionSymbol;

/**
 * Copyright © 2017 spawnchunk.com
 * Created by klugemonkey on 5/17/17.
 */
class config {

    static void reloadConfig() {
        if(debug) logger.info(sectionSymbol(String.format("%s Reloading configuration", pluginPrefix)));
        instance.reloadConfig();
        fc = instance.getConfig();
        tools.clear();
        parseConfig();
    }

    static void parseConfig() {

        // parse the configuration file
        if(fc.contains("debug")) debug = fc.getBoolean("debug");
        if(fc.contains("silk.perms")) {
            silkPerms = fc.getBoolean("silk.perms");
            if(debug && silkPerms) logger.info(sectionSymbol(String.format("%s Silk only with permission", pluginPrefix)));
        }
        if(fc.contains("silk.creative")) {
            silkCreative = fc.getBoolean("silk.creative");
            if(debug && silkCreative) logger.info(sectionSymbol(String.format("%s Allow silk in Creative mode", pluginPrefix)));
        }
        if(fc.contains("silk.level")) {
            silkLevel = fc.getInt("silk.level");
            if(debug) logger.info(sectionSymbol(String.format("%s Require silk level %d", pluginPrefix, silkLevel)));
        }
        if(fc.contains("silk.tools")) {
            List<String> keys = fc.getStringList("silk.tools");
            if(keys != null) {
                for(String k: keys) {
                    String key = k.contains("minecraft:") ? k.toLowerCase(): "minecraft:" + k.toLowerCase();
                    Material material = nms.getMaterialFromKey(key);
                    if(material != null) {
                        tools.add(material);
                        if(debug) logger.info(sectionSymbol(String.format("%s Allow silk with %s", pluginPrefix, key)));
                    } else {
                        if (debug) logger.info(sectionSymbol(String.format("%s Material %s unknown", pluginPrefix, key)));
                    }
                }
            }
        }
        if(fc.contains("silk.instantly")) {
            silkInstantly = fc.getBoolean("silk.instantly");
            if(debug && silkInstantly) logger.info(sectionSymbol(String.format("%s Silk instantly", pluginPrefix)));
        }
    }

    static String getProperty(File f, String property)
    {
        Properties pr = new Properties();
        try
        {
            FileInputStream in = new FileInputStream(f);
            pr.load(in);
            return pr.getProperty(property);
        }
        catch (IOException ignored) { }
        return "";
    }
}
