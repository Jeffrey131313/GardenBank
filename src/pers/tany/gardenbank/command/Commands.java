package pers.tany.gardenbank.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import pers.tany.gardenbank.Main;
import pers.tany.gardenbank.gui.BankInterface;
import pers.tany.yukinoaapi.interfacepart.configuration.IConfig;
import pers.tany.yukinoaapi.interfacepart.inventory.IInventory;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.isOp()) {
                    sender.sendMessage("§c您没有权限执行此命令");
                    return true;
                }
                Main.config = IConfig.loadConfig(Main.plugin, "", "config");
                Main.data = IConfig.loadConfig(Main.plugin, "", "data");
                Main.message = IConfig.loadConfig(Main.plugin, "", "message");
                sender.sendMessage("§a重载成功");
                return true;
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("§6/gb reload  §e重载插件");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("bank")) {
                IInventory.openInventory(new BankInterface(player), player);
                return true;
            }
        }
        if (player.isOp()) {
            sender.sendMessage("§6/gb bank  §e打开银行界面");
            sender.sendMessage("§6/gb reload  §e重载插件");
        } else {
            sender.sendMessage("§6/gb bank  §e打开银行界面");
        }
        return true;
    }
}
