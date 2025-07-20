package org.fK_Util.eventos;

import com.mojang.authlib.GameProfile;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.fK_Util.comandos.Fake;

import java.lang.reflect.Field;

public class ResetFake implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        CraftPlayer cp = (CraftPlayer) player;
        Fake.ORIGINAL_NAMES.remove(player);
        try {
            GameProfile profile = cp.getProfile();

            Field nameField = GameProfile.class.getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(profile, player.getName());
            player.setDisplayName(player.getName());
            player.setPlayerListName(player.getName());

            profile.getProperties().removeAll("textures");


            for (Player online : Bukkit.getOnlinePlayers()) {
                online.hidePlayer(player);
                online.showPlayer(player);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

