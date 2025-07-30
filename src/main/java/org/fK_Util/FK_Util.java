package org.fK_Util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.fK_Util.comandos.*;
import org.fK_Util.comandos.TPA.Manager;
import org.fK_Util.eventos.BackEvent;
import org.fK_Util.eventos.GodEvent;
import org.fK_Util.eventos.ResetFake;
import java.util.List;
import java.util.Objects;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.jar.JarFile;
import java.util.Enumeration;
import java.util.jar.JarEntry;

public final class FK_Util extends JavaPlugin {

    private static Plugin pls;
    private static Config configM;
    private static FileConfiguration config;
    private static FileConfiguration warps;
    private static String prefix;

    private List<String> commands = List.of(
            "ping", "fkutil", "warp", "setwarp",
            "warps", "delwarp", "fake", "tpa", "tpaaccept", "tpdeny",
            "lobby", "setlobby", "dellobby", "god"
    );
    @Override
    public void onEnable() {
        pls = this;
        configM = new Config(this);

        reloadConfigs();
        if (!config.contains("options.prefix")) {
            config.set("options.prefix", "&e[ &fFK_Util &e] &f");
            configM.saveConfig("config");
        }
        prefix = config.getString("options.prefix");

        carregarEventos();
        carregarComandos(commands);

        Bukkit.getConsoleSender().sendMessage(Objects.requireNonNull(getPrefix()).replaceAll("&", "§") + " §fSistema Iniciado!!");

    }

    public static FileConfiguration getConfig(String str) {
        if (str.equalsIgnoreCase("warps")) {
            return warps;
        } else {
            return config;
        }
    }

    public void carregarEventos() {
        getServer().getPluginManager().registerEvents(new ResetFake(), this);
        getServer().getPluginManager().registerEvents(new GodEvent(), this);
        getServer().getPluginManager().registerEvents(new BackEvent(), this);
    }
    public void carregarComandos(List<String> comandos) {

        try {
            String packageName = "org.fK_Util.comandos";
            String packagePath = packageName.replace('.', '/');

            String jarPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            String decodedJarPath = URLDecoder.decode(jarPath, StandardCharsets.UTF_8.name());

            JarFile jarFile = new JarFile(decodedJarPath);

            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (name.startsWith(packagePath) && name.endsWith(".class") && !entry.isDirectory()) {
                    String className = name.replace('/', '.').replace(".class", "");
                    Class<?> clazz = Class.forName(className);
                    if (CommandExecutor.class.isAssignableFrom(clazz)) {
                        Manager tpaManager = new Manager();
                        String nome = clazz.getSimpleName().toLowerCase();
                        CommandExecutor executor = nome.equalsIgnoreCase("tpa") || nome.equalsIgnoreCase("tpaccept") || nome.equalsIgnoreCase("tpdeny") ? (CommandExecutor)clazz.getDeclaredConstructor(Manager.class).newInstance(tpaManager) : (CommandExecutor) clazz.getDeclaredConstructor().newInstance();
                        Objects.requireNonNull(getCommand(nome)).setExecutor(executor);
                        getLogger().info("Registrado comando: " + nome);
                    }
                }
            }
            jarFile.close();
        } catch (Exception e) {
            System.out.println("Deu erro aqui oh");
            e.printStackTrace();
        }

    }
    public static String getPrefix() {
        return prefix;
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
        Fake.resetAllFakes();
    }

    public static Plugin getpl() {
        return pls;
    }
}
