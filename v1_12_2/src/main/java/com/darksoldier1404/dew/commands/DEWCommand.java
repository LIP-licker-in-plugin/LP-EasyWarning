package com.darksoldier1404.dew.commands;

import com.darksoldier1404.dew.EasyWarning;
import com.darksoldier1404.dew.functions.DEWFunction;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class DEWCommand implements CommandExecutor, TabCompleter {
    private final EasyWarning plugin = EasyWarning.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(plugin.prefix + "권한이 없습니다.");
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage(plugin.prefix + "/경고 추가 <player> <reason> - 해당 플레이어를 경고합니다.");
            sender.sendMessage(plugin.prefix + "/경고 차감 <player> <reason> - 해당 플레이어의 경고를 차감합니다.");
            sender.sendMessage(plugin.prefix + "/경고 확인 <player> - 해당 플레이어의 경고를 확인합니다.");
            sender.sendMessage(plugin.prefix + "/경고 차단 <number> - 해당 경고수에 도달하면 해당되는 플레이어를 추방합니다.");
            sender.sendMessage(plugin.prefix + "/경고 추가사유 <reason> - 경고 추가 사유를 설정합니다.");
            sender.sendMessage(plugin.prefix + "/경고 차감사유 <reason> - 경고 차감 사유를 설정합니다.");
            sender.sendMessage(plugin.prefix + "/경고 추방사유 <reason> - 추방 사유를 설정합니다.");
            sender.sendMessage(plugin.prefix + "/경고 아이피벤 <true/false> - 추방시 아이피벤 처리 여부를 설정합니다.");
            sender.sendMessage(plugin.prefix + "/경고 리로드 - 콘피그 설정 파일을 리로드 합니다.");
            return false;
        }
        if (args[0].equals("추가")) {
            if (args.length == 1) {
                sender.sendMessage(plugin.prefix + "플레이어를 입력해주세요.");
                return false;
            }
            if (args.length == 2) {
                sender.sendMessage(plugin.prefix + "사유를 입력해주세요.");
                return false;
            }
            DEWFunction.giveWarn(sender, args[1], args);
            return false;
        }
        if (args[0].equals("차감")) {
            if (args.length == 1) {
                sender.sendMessage(plugin.prefix + "플레이어를 입력해주세요.");
                return false;
            }
            if (args.length == 2) {
                sender.sendMessage(plugin.prefix + "사유를 입력해주세요.");
                return false;
            }
            DEWFunction.takeWarn(sender, args[1], args);
            return false;
        }
        if (args[0].equals("확인")) {
            if (args.length == 1) {
                sender.sendMessage(plugin.prefix + "플레이어를 입력해주세요.");
                return false;
            }
            if (args.length == 2) {
                DEWFunction.checkWarn(sender, args[1]);
                return false;
            }
        }
        if (args[0].equals("차단")) {
            if (args.length == 1) {
                sender.sendMessage(plugin.prefix + "경고수를 입력해주세요.");
                return false;
            }
            if (args.length == 2) {
                DEWFunction.setMaxWarn(sender, args[1]);
                return false;
            }
        }
        if (args[0].equals("추가사유")) {
            if (args.length == 1) {
                sender.sendMessage(plugin.prefix + "사유를 입력해주세요.");
                return false;
            }
            DEWFunction.setWarnContext(sender, args);
            return false;
        }
        if (args[0].equals("차감사유")) {
            if (args.length == 1) {
                sender.sendMessage(plugin.prefix + "사유를 입력해주세요.");
                return false;
            }
            DEWFunction.setTakeContext(sender, args);
            return false;
        }
        if (args[0].equals("추방사유")) {
            if (args.length == 1) {
                sender.sendMessage(plugin.prefix + "사유를 입력해주세요.");
                return false;
            }
            DEWFunction.setBanContext(sender, args);
            return false;
        }
        if (args[0].equals("아이피벤")) {
            if (args.length == 1) {
                sender.sendMessage(plugin.prefix + "true 또는 false 를 입력해주세요.");
                return false;
            }
            if (args.length == 2) {
                DEWFunction.setIPBan(sender, args[1]);
                return false;
            }
        }
        if (args[0].equals("리로드")) {
            DEWFunction.reloadConfig();
            sender.sendMessage(plugin.prefix + "콘피그 설정을 리로드 하였습니다.");
            return false;
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender.isOp()) {
            if (args.length == 1) {
                return Arrays.asList("추가", "차감", "차단", "추가사유", "차감사유", "추방사유", "확인", "아이피벤", "리로드");
            }
            if (args[0].equals("추가") || args[0].equals("차감") || args[0].equals("확인")) {
                if (args.length == 2) {
                    return Bukkit.getOnlinePlayers().stream().collect(Collectors.toList()).stream().map(player -> player.getName()).collect(Collectors.toList());
                }
            }
            if (args[0].equals("아이피벤")) {
                if (args.length == 2) {
                    return Arrays.asList("TRUE", "FALSE");
                }
            }
        }
        return null;
    }
}
