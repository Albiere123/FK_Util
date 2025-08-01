package org.fK_Util.comandos;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.fK_Util.FK_Util;
import org.fK_Util.PlayerCustom;

import java.util.Objects;

public class Warp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (!(sender instanceof Player player)) {
            Bukkit.getConsoleSender().sendMessage(FK_Util.getPrefix() + " Apenas jogadores podem executar o comando!");
            return true;
        }
        FileConfiguration warps = FK_Util.getConfig("warps");

        if (args.length > 0) {
            String warpGet = args[0].toLowerCase();
            PlayerCustom p = new PlayerCustom(player);
            if (!warps.contains(warpGet)) {
                p.sendColouredMessage(FK_Util.getPrefix() + " &cNão encontrei esta warp.");
                return true;

            }

            if (!player.hasPermission("FK_UTIL.Warp.warps." + warpGet)) {
                (new PlayerCustom(player)).sendColouredMessage(FK_Util.getPrefix() + " &cVocê não possue permissão para visitar a warp &f" + warpGet +"&c.");
                return true;
            }

            if (args.length > 1 && player.hasPermission("FK_UTIL.Warp.admin")) {
                Player playerForcado = Bukkit.getPlayer(args[1]);
                if (playerForcado != null && playerForcado != player) {
                    World w = Bukkit.getWorld(Objects.requireNonNull(warps.getString(warpGet + ".world")));
                    Location loc = new Location(w, warps.getDouble(warpGet + ".x"), warps.getDouble(warpGet + ".y"), warps.getDouble(warpGet + ".z"), Float.valueOf(warps.getString(warpGet + ".yaw")), Float.valueOf(warps.getString(warpGet + ".pitch")));
                    playerForcado.teleport(loc);
                    (new PlayerCustom(playerForcado)).sendColouredMessage(FK_Util.getPrefix() + " &aO staff &f" + player.getDisplayName() + " &ateleportou você para a warp " + warpGet);
                    (new PlayerCustom(player)).sendColouredMessage(FK_Util.getPrefix() + " &aTeleportei o jogador &f" + playerForcado.getDisplayName() + " &afoi teleportado com sucesso!");
                    return true;
                }

            }

            World w = Bukkit.getWorld(Objects.requireNonNull(warps.getString(warpGet + ".world")));
            Location loc = new Location(w, warps.getDouble(warpGet + ".x"), warps.getDouble(warpGet + ".y"), warps.getDouble(warpGet + ".z"), Float.valueOf(warps.getString(warpGet + ".yaw")), Float.valueOf(warps.getString(warpGet + ".pitch")));
            try {

                p.getPlayer().sendTitle("§eTeleportando...", "3");
                Thread.sleep(1000);
                p.getPlayer().sendTitle("§eTeleportando...", "2");
                Thread.sleep(1000);
                p.getPlayer().sendTitle("§eTeleportando...", "1");
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Back.setBack(player, player.getLocation());
            p.getPlayer().teleport(loc);
            p.getPlayer().sendTitle("§eTeleportado com §lSucesso!", " ");

            return true;
        } else {
            PlayerCustom p = new PlayerCustom(player);
            p.sendColouredMessage(FK_Util.getPrefix() + " &fInsira o nome da warp.");
        }
        return true;
    }
}
