package org.fK_Util.comandos;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.fK_Util.FK_Util;
import org.fK_Util.PlayerCustom;

public class SetWarp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (!(sender instanceof Player p)) {
            Bukkit.getConsoleSender().sendMessage(FK_Util.getPrefix() + " Apenas jogadores podem executar o comando!");
            return true;
        }

        if (!sender.hasPermission("FK_UTIL.Setwarp")) {
            (new PlayerCustom(p)).sendColouredMessage(FK_Util.getPrefix() + " &cVocê não possue permissão!");
            return true;
        }

        FileConfiguration warps = FK_Util.getConfig("warps");
        if (args.length > 0) {
            if (warps.contains(args[0].toLowerCase())) {
                (new PlayerCustom((Player) sender)).sendColouredMessage(FK_Util.getPrefix() + " Já existe uma warp com esse nome!");
                return true;
            }
            String warpGet = args[0].toLowerCase();
            World w = p.getWorld();
            warps.set(warpGet + ".world", w.getName());
            warps.set(warpGet + ".x", p.getLocation().getX());
            warps.set(warpGet + ".y", p.getLocation().getY());
            warps.set(warpGet + ".z", p.getLocation().getZ());
            warps.set(warpGet + ".yaw", p.getLocation().getYaw());
            warps.set(warpGet + ".pitch", p.getLocation().getPitch());

            try {
                FK_Util.saveWarp();
                (new PlayerCustom((Player) sender)).sendColouredMessage(FK_Util.getPrefix() + " &aWarp setada com sucesso!");
            } catch (Exception e) {
                (new PlayerCustom((Player) sender)).sendColouredMessage(FK_Util.getPrefix() + " &cFalha ao setar a warp!");
            }
        } else {
            (new PlayerCustom((Player) sender)).sendColouredMessage(FK_Util.getPrefix() + " &fDefina um nome para a warp");
        }
        return true;
    }
}
