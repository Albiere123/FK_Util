package org.fK_Util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.fK_Util.comandos.*;
import org.fK_Util.eventos.ResetFake;

import java.util.Objects;

public final class FK_Util extends JavaPlugin {

    private static Plugin pls;
    private static Config configM;
    private static FileConfiguration config;
    private static FileConfiguration warps;



    @Override
    public void onEnable() {
        pls = this;
        configM = new Config(this);
        reloadConfigs();
        if(!config.contains("options.prefix")) {
            config.set("options.prefix", "&e[ &fFK_Util &e] &f");
            configM.saveConfig("config");
        }
        getServer().getPluginManager().registerEvents(new ResetFake(), this);
        Objects.requireNonNull(getCommand("ping")).setExecutor(new Ping());
        Objects.requireNonNull(getCommand("fkutil")).setExecutor(new FKUtil());
        Objects.requireNonNull(getCommand("warp")).setExecutor(new Warp());
        Objects.requireNonNull(getCommand("setwarp")).setExecutor(new SetWarp());
        Objects.requireNonNull(getCommand("warps")).setExecutor(new Warps());
        Objects.requireNonNull(getCommand("delwarp")).setExecutor(new DelWarp());
        Objects.requireNonNull(getCommand("fake")).setExecutor(new Fake());
        Objects.requireNonNull(getCommand("lobby")).setExecutor(new Lobby());
        Objects.requireNonNull(getCommand("setlobby")).setExecutor(new SetLobby());
        Objects.requireNonNull(getCommand("dellobby")).setExecutor(new DelLobby());
        Bukkit.getConsoleSender().sendMessage(Objects.requireNonNull(config.getString("options.prefix")).replaceAll("&", "§")+" Sistema Iniciado!!");
    }

    public static FileConfiguration getConfig(String str) {
        if (str.equalsIgnoreCase("warps")){
            return warps;
        } else {
            return config;
        }
    }

    public static void saveWarp() {
        configM.saveConfig("warps");
    }
    public static void reloadConfigs() {
        configM.loadConfig("config");
        configM.loadConfig("warps");
        config = configM.getConfig("config");
        warps = configM.getConfig("warps");

    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Plugin getpl() {
        return pls;
    }
 }
