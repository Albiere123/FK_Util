package org.fK_Util.eventos;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.fK_Util.comandos.God;

public class GodEvent implements Listener {

    @EventHandler
    public static void noDamage(EntityDamageEvent e) {

        if (e.getEntity() instanceof  Player player) {
            if (God.isGod(player)) e.setCancelled(true);
        }

    }
}
