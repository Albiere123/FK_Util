package org.fK_Util.eventos;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.fK_Util.comandos.Back;

public class BackEvent implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Back.setBack(e.getPlayer(), null);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Back.setBack(e.getEntity(), e.getEntity().getLocation());
    }
}
