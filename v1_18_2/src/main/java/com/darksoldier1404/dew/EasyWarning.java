package com.darksoldier1404.dew;

import com.darksoldier1404.dew.commands.DEWCommand;
import com.darksoldier1404.dew.commands.DEWUCommand;
import com.darksoldier1404.dew.events.DEWEvent;
import com.darksoldier1404.dew.functions.DEWFunction;
import com.darksoldier1404.dppc.utils.ColorUtils;
import com.darksoldier1404.dppc.utils.ConfigUtils;
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
        plugin.getServer().getPluginManager().registerEvents(new DEWEvent(), plugin);
        getCommand("경고").setExecutor(new DEWCommand());
        getCommand("경고확인").setExecutor(new DEWUCommand());
    }

    @Override
    public void onDisable() {
        DEWFunction.saveConfig();
    }
}
