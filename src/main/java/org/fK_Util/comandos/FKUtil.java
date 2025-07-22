package org.fK_Util.comandos;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.fK_Util.FK_Util;
import org.fK_Util.PlayerCustom;

import java.util.Objects;

public class FKUtil implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (!sender.hasPermission("FK_UTIL.Fkutil")) {
            sender.sendMessage(ChatColor.RED + "Você não possue permissão!");
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            boolean hasPermission = false;

            if (sender instanceof Player) {
                PlayerCustom p = new PlayerCustom((Player) sender);
                hasPermission = p.getPlayer().hasPermission("FK_UTIL.Reload");
            } else if (sender == Bukkit.getConsoleSender()) {
                hasPermission = true;
            }

            if (hasPermission) {
                FK_Util.reloadConfigs();
                sendMessage(sender, "Configurações recarregadas com sucesso!", true);
            } else {
                sendMessage(sender, "Você não possue &cpermissão&f.", true);
            }
        } else {
            sendMessage(sender, "Opções disponíveis:", true);
            sendMessage(sender, "Reload - Recarrega todas as configurações", false);
        }
        return false;
    }

    public void sendMessage(CommandSender sender, String str, Boolean prefix) {
        FileConfiguration config = FK_Util.getConfig("config");
        if (sender instanceof Player) {
            PlayerCustom p = new PlayerCustom((Player) sender);
            if (prefix) {
                p.sendColouredMessage(FK_Util.getPrefix() + " " + str);
            } else {
                String ultimaCor = getUltimoCodigoDeCor(Objects.requireNonNull(FK_Util.getPrefix()));
                p.sendColouredMessage(ultimaCor.replaceAll("&", "§") + str);
            }
        } else {
            if (prefix) {
                Bukkit.getConsoleSender().sendMessage(FK_Util.getPrefix().replaceAll("&", "§") + " " + str);
            } else {
                String ultimaCor = getUltimoCodigoDeCor(Objects.requireNonNull(FK_Util.getPrefix()));
                Bukkit.getConsoleSender().sendMessage(ultimaCor.replaceAll("&", "§") + str);
            }
        }
    }

    private String getUltimoCodigoDeCor(String texto) {
        String ultimaCor = "&f";
        for (int i = 0; i < texto.length() - 1; i++) {
            if (texto.charAt(i) == '&' && i + 1 < texto.length()) {
                char code = texto.charAt(i + 1);
                if ("0123456789abcdefklmnor".indexOf(Character.toLowerCase(code)) != -1) {
                    ultimaCor = "&" + code;
                }
            }
        }
        return ultimaCor;
    }
}
