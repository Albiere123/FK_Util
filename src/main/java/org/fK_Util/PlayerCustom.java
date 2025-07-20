package org.fK_Util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerCustom {

    private UUID playerUUID;

    public PlayerCustom(Player p) {
        this.playerUUID = p.getUniqueId();
    }


    public void setPlayerUUID(UUID newuuid) {
        this.playerUUID = newuuid;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }


    public Player getPlayer() {
        return Bukkit.getPlayer(this.playerUUID);
    }

    public void sendColouredMessage(String str) {
        this.getPlayer().sendMessage(str.replaceAll("&", "§"));
    }

    public int getPing() {
        return this.getPlayer().getPing();
    }
}