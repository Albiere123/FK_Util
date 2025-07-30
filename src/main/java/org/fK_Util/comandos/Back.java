package org.fK_Util.comandos;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.fK_Util.FK_Util;
import org.fK_Util.PlayerCustom;

import java.util.HashMap;
import java.util.Map;

public class Back implements CommandExecutor {

    private static Map<Player, Location> backL = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String prefix = FK_Util.getPrefix();

        if (!(sender instanceof Player player)) {
            System.out.println(prefix + " &fComando disponivel apenas para jogadores.");
            return true;
        }

        PlayerCustom p = new PlayerCustom(player);

        if (!player.hasPermission("FK_UTIL.Back")) {
            (new PlayerCustom(player)).sendColouredMessage(prefix + " &cVocê não possue permissão!");
            return true;
        }

        if (getBack(player) == null) {
            p.sendColouredMessage(prefix + " &cVocê não possue um local anterior para retornar.");
            return true;
        }

        Location back = player.getLocation();

        player.teleport(getBack(player));
        p.sendColouredMessage(prefix + " &aTeleportado com sucesso para seu ultimo local.");
        setBack(player, back);
        return true;
    }

    public static Location getBack(Player player) {
        return backL.getOrDefault(player, null);
    }

    public static void setBack(Player p, Location loc) {
        backL.put(p, loc);
    }

}
