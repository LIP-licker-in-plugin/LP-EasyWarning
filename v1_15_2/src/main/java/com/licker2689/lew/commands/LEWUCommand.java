package com.licker2689.lew.commands;

import com.licker2689.lew.EasyWarning;
import com.licker2689.lew.functions.LEWFunction;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public class LEWUCommand implements CommandExecutor {
    private final EasyWarning plugin = EasyWarning.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(plugin.prefix + "인게임에서만 사용할 수 있습니다.");
            return false;
        }
        Player p = (Player) sender;
        if (args.length == 0) {
            LEWFunction.checkWarn(p);
            return false;
        }
        return false;
    }
}
