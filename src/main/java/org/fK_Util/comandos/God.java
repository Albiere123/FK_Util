package org.fK_Util.comandos;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.fK_Util.FK_Util;
import org.fK_Util.PlayerCustom;

import java.util.HashMap;
import java.util.Map;

public class God implements CommandExecutor {

    private static Map<Player, Boolean> god = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String prefix = FK_Util.getPrefix();
        if (!(sender instanceof Player player)) {
            sender.sendMessage(prefix + ChatColor.RED + " Apenas para jogadores!");
            return true;
        }
        PlayerCustom p = new PlayerCustom(player);
        if (!player.hasPermission("FK_UTIL.God")) {
            p.sendColouredMessage(prefix + " &cVocê não possue permissão!");
            return true;
        }

        if (args.length > 0) try {
            player = Bukkit.getPlayer(args[0]);
        } catch (Exception e) {
            player = ((Player) sender).getPlayer();
        }
        setGod(player, !isGod(player));
        (new PlayerCustom(player)).sendColouredMessage(prefix + " &aGod " + (isGod(player) ? "&fativado" : "&fdesativado") + (player != p.getPlayer() ? " &apelo staff &f" + p.getPlayer().getDisplayName() : "."));
        if (player != p.getPlayer())
            p.sendColouredMessage(prefix + " &aGod do usuário: &f" + player.getDisplayName() + " &afoi " + (isGod(player) ? "&fativado." : "&fdesativado."));
        return true;
    }

    public static boolean isGod(Player p) {
        return god.getOrDefault(p, false);
    }

    public static void setGod(Player p, boolean gods) {
        god.put(p, gods);
    }
}
