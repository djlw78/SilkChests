package com.spawnchunk.silkchests;

import com.spawnchunk.silkchests.nms.*;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.permission.Permission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.spawnchunk.silkchests.commands.silkChests;
import static com.spawnchunk.silkchests.config.getProperty;
import static com.spawnchunk.silkchests.config.parseConfig;

/**
 * Copyright © 2017 spawnchunk.com
 * Created by klugemonkey on 5/15/17.
 */
public final class SilkChests extends JavaPlugin implements Listener {

    static SilkChests instance;
    static FileConfiguration fc;

    public static NMS nms;

    static PluginManager pm;
    static Permission perms = null;
    static Chat chat = null;
    static Boolean oldSound = false;

    static Boolean debug = false;

    static String pluginPrefix = "[SilkChests]";
    static String levelname;
    static String servername;

    static Boolean silkPerms = false;
    static Boolean silkCreative = false;
    static int silkLevel;
    static List<Material> tools = new ArrayList<>();
    static Boolean silkInstantly = false;

    static Logger logger = Bukkit.getLogger();

    public static void main(final String[] args) {
        // main entry point
    }

    @Override
    public void onEnable() {
        // logic to be performed when the plugin is enabled
        instance = this;

        File f = new File("server.properties");
        levelname = getProperty(f, "level-name");
        servername = getProperty(f, "server-name");

        // load the NMS handler
        String packageName = instance.getServer().getClass().getPackage().getName();
        String version = packageName.substring(packageName.lastIndexOf('.') + 1);
        logger.info(String.format("%s Using NMS version %s", pluginPrefix, version));
        if(version.equals("v1_12_R1")) {
            nms = new v1_12_R1();
        }
        if(version.equals("v1_11_R1")) {
            nms = new v1_11_R1();
        }
        if(version.equals("v1_10_R1")) {
            nms = new v1_10_R1();
        }
        if(version.equals("v1_9_R2")) {
            nms = new v1_9_R2();
        }
        if(version.equals("v1_9_R1")) {
            nms = new v1_9_R1();
        }
        if(version.equals("v1_8_R3")) {
            nms = new v1_8_R3();
            oldSound = true;
        }
        if(version.equals("v1_8_R2")) {
            nms = new v1_8_R2();
            oldSound = true;
        }
        if(version.equals("v1_8_R1")) {
            nms = new v1_8_R1();
            oldSound = true;
        }

        // load the permissions manager
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            setupPermissions();
        } else {
            logger.log(Level.SEVERE, String.format("%s%sDisabled due to Vault dependency failure!", pluginPrefix, pluginPrefix.isEmpty() ? "" : " "));
            getServer().getPluginManager().disablePlugin(instance);
            return;
        }

        // load the chat manager
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            setupChat();
        } else {
            logger.log(Level.SEVERE, String.format("%s%sDisabled due to Vault dependency failure!", pluginPrefix, pluginPrefix.isEmpty() ? "" : " "));
            getServer().getPluginManager().disablePlugin(instance);
            return;
        }

        // load the configuration
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        reloadConfig();
        fc = instance.getConfig();
        parseConfig();

        // register events
        pm = getServer().getPluginManager();
        pm.registerEvents(instance, instance);
    }

    private void setupPermissions() {
        RegisteredServiceProvider<Permission> rsp_perms = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp_perms.getProvider();
    }

    private void setupChat() {
        RegisteredServiceProvider<Chat> rsp_chat = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp_chat.getProvider();
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        if((event != null) && !event.isCancelled()) place.onBlockPlace(event);
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        if((event != null) && !event.isCancelled()) silk.onBlockBreak(event);
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockDamage(BlockDamageEvent event) {
        if((event != null) && !event.isCancelled()) silk.onBlockDamage(event);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        // handle dummy command
        if (cmd.getName().equalsIgnoreCase("silkchests")) return silkChests(sender, cmd, label, args);
        // otherwise
        return false;
    }
}
