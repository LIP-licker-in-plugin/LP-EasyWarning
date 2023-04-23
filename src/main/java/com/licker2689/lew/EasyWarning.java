package com.licker2689.lew;

import com.licker2689.lew.commands.LEWCommand;
import com.licker2689.lew.commands.LEWUCommand;
import com.licker2689.lew.events.LEWEvent;
import com.licker2689.lew.functions.LEWFunction;
import com.licker2689.lpc.utils.ColorUtils;
import com.licker2689.lpc.utils.ConfigUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("all")
public class EasyWarning extends JavaPlugin {
    private static EasyWarning plugin;
    public static YamlConfiguration config;
    public static String prefix;
    public static Map<UUID, YamlConfiguration> udata = new HashMap<>();

    public static EasyWarning getInstance() {
        return plugin;
    }

    public void onEnable() {
        plugin = this;
        config = ConfigUtils.loadDefaultPluginConfig(plugin);
        prefix = ColorUtils.applyColor(config.getString("Settings.prefix"));
        plugin.getServer().getPluginManager().registerEvents(new LEWEvent(), plugin);
        getCommand("경고").setExecutor(new LEWCommand());
        getCommand("경고확인").setExecutor(new LEWUCommand());
    }

    @Override
    public void onDisable() {
        LEWFunction.saveConfig();
    }
}
