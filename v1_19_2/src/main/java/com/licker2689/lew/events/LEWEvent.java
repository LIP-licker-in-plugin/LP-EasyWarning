package com.licker2689.lew.events;

import com.licker2689.lew.functions.LEWFunction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LEWEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        LEWFunction.initUserData(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        LEWFunction.saveAndLeave(e.getPlayer());
    }
}
