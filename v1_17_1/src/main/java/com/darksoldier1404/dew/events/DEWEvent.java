package com.darksoldier1404.dew.events;

import com.darksoldier1404.dew.functions.DEWFunction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DEWEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        DEWFunction.initUserData(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        DEWFunction.saveAndLeave(e.getPlayer());
    }
}
