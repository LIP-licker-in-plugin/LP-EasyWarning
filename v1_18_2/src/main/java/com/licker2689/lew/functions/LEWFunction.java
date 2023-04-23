package com.licker2689.lew.functions;

import com.licker2689.lew.EasyWarning;
import com.licker2689.lpc.utils.ColorUtils;
import com.licker2689.lpc.utils.ConfigUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("all")
public class LEWFunction {
    private static final EasyWarning plugin = EasyWarning.getInstance();

    public static void giveWarn(CommandSender sender, String player, String[] content) {
        Player p = Bukkit.getPlayer(player);
        if (p == null) {
            sender.sendMessage(plugin.prefix + "해당 플레이어는 존재하지 않습니다.");
            return;
        }
        String text = getText(content, 2);
        String context = ColorUtils.applyColor(plugin.config.getString("Settings.giveContext").replace("<player>", p.getName()).replace("<reason>", text));
        p.sendMessage(context);
        increaseWarn(p, context);
    }

    public static void takeWarn(CommandSender sender, String player, String[] content) {
        Player p = Bukkit.getPlayer(player);
        if (p == null) {
            sender.sendMessage(plugin.prefix + "해당 플레이어는 존재하지 않습니다.");
            return;
        }
        String text = getText(content, 2);
        String context = ColorUtils.applyColor(plugin.config.getString("Settings.takeContext").replace("<player>", p.getName()).replace("<reason>", text));
        p.sendMessage(context);
        decreaseWarn(p, context);
    }

    public static void increaseWarn(Player p, String context) {
        int maxWarn = plugin.config.getInt("Settings.maxWarn");
        boolean isIPBan = plugin.config.getBoolean("Settings.isIPBan");
        int currentWarn = plugin.udata.get(p.getUniqueId()).getInt("Warn.currentWarn");
        currentWarn++;
        plugin.udata.get(p.getUniqueId()).set("Warn.currentWarn", currentWarn);
        List<String> reasons = plugin.udata.get(p.getUniqueId()).getStringList("Warn.reasons");
        reasons.add(context);
        plugin.udata.get(p.getUniqueId()).set("Warn.reasons", reasons);
        if (currentWarn >= maxWarn) {
            if (isIPBan) {
                Bukkit.getBanList(BanList.Type.IP).addBan(p.getAddress().getHostString(), plugin.config.getString("Settings.banContext").replace("<player>", p.getName()), null, null);
            } else {
                Bukkit.getBanList(BanList.Type.NAME).addBan(p.getUniqueId().toString(), plugin.config.getString("Settings.banContext").replace("<player>", p.getName()), null, null);
            }
            p.kickPlayer(plugin.config.getString("Settings.banContext").replace("<player>", p.getName()));
        }
    }

    public static void decreaseWarn(Player p, String context) {
        int currentWarn = plugin.udata.get(p.getUniqueId()).getInt("Warn.currentWarn");
        List<String> reasons = plugin.udata.get(p.getUniqueId()).getStringList("Warn.reasons");
        reasons.add(context);
        plugin.udata.get(p.getUniqueId()).set("Warn.reasons", reasons);
        if (currentWarn <= 0) {
            return;
        } else {
            plugin.udata.get(p.getUniqueId()).set("Warn.currentWarn", currentWarn - 1);
        }
    }

    public static void setWarnContext(CommandSender sender, String[] content) {
        String text = getText(content, 1);
        plugin.config.set("Settings.giveContext", text);
        sender.sendMessage(plugin.prefix + "설정이 완료되었습니다.");
        saveConfig();
    }

    public static void setTakeContext(CommandSender sender, String[] content) {
        String text = getText(content, 1);
        plugin.config.set("Settings.takeContext", text);
        sender.sendMessage(plugin.prefix + "설정이 완료되었습니다.");
        saveConfig();
    }

    public static void setBanContext(CommandSender sender, String[] content) {
        String text = getText(content, 1);
        plugin.config.set("Settings.banContext", text);
        sender.sendMessage(plugin.prefix + "설정이 완료되었습니다.");
        saveConfig();
    }

    public static void setMaxWarn(CommandSender sender, String sint) {
        try {
            plugin.config.set("Settings.maxWarn", Integer.parseInt(sint));
            sender.sendMessage(plugin.prefix + "설정이 완료되었습니다.");
        } catch (Exception e) {
            sender.sendMessage(plugin.prefix + "옳바르지 않은 숫자입니다.");
        }
        saveConfig();
    }

    public static void setIPBan(CommandSender sender, String sBool) {
        try {
            plugin.config.set("Settings.isIPBan", Boolean.parseBoolean(sBool));
            sender.sendMessage(plugin.prefix + "설정이 완료되었습니다.");
        } catch (Exception e) {
            sender.sendMessage(plugin.prefix + "옳바르지 않은 형식입니다.");
        }
        saveConfig();
    }

    public static String getText(String[] args, int line) {
        StringBuilder s = new StringBuilder();
        args = Arrays.copyOfRange(args, line, args.length);
        Iterator<String> i = Arrays.stream(args).iterator();
        while (i.hasNext()) {
            s.append(i.next()).append(" ");
        }
        // delete last space
        if (s.charAt(s.length() - 1) == ' ') {
            s.deleteCharAt(s.length() - 1);
        }
        return s.toString();
    }

    public static void checkWarn(CommandSender sender, String splayer) {
        Player p = Bukkit.getPlayer(splayer);
        if (p == null) {
            sender.sendMessage(plugin.prefix + "해당 플레이어는 존재하지 않습니다.");
            return;
        }
        sender.sendMessage(plugin.prefix + "플레이어 " + p.getName() + "의 현재 경고는 " + plugin.udata.get(p.getUniqueId()).getInt("Warn.currentWarn") + "입니다.");
        sender.sendMessage(plugin.prefix + "플레이어 " + p.getName() + "의 경고 사유는 다음과 같습니다.");
        List<String> reasons = plugin.udata.get(p.getUniqueId()).getStringList("Warn.reasons");
        for (String reason : reasons) {
            sender.sendMessage(plugin.prefix + reason);
        }
    }

    public static void checkWarn(Player p) {
        p.sendMessage(plugin.prefix + "당신의 현재 경고는 " + plugin.udata.get(p.getUniqueId()).getInt("Warn.currentWarn") + "회 입니다.");
        p.sendMessage(plugin.prefix + "당신의 경고 사유는 다음과 같습니다.");
        List<String> reasons = plugin.udata.get(p.getUniqueId()).getStringList("Warn.reasons");
        for (String reason : reasons) {
            p.sendMessage(plugin.prefix + reason);
        }
    }

    public static void initUserData(Player p) {
        YamlConfiguration data = ConfigUtils.initUserData(plugin, p.getUniqueId().toString());
        plugin.udata.put(p.getUniqueId(), data);
    }

    public static void saveAndLeave(Player p) {
        ConfigUtils.saveCustomData(plugin, plugin.udata.get(p.getUniqueId()), p.getUniqueId().toString());
        plugin.udata.remove(p.getUniqueId());
    }

    public static void reloadConfig() {
        plugin.config = ConfigUtils.reloadPluginConfig(plugin, plugin.config);
        plugin.prefix = ColorUtils.applyColor(plugin.config.getString("Settings.prefix"));
    }

    public static void saveConfig() {
        ConfigUtils.savePluginConfig(plugin, plugin.config);
    }
}
