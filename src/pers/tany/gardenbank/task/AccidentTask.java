package pers.tany.gardenbank.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pers.tany.gardenbank.Main;
import pers.tany.yukinoaapi.interfacepart.configuration.IConfig;
import pers.tany.yukinoaapi.interfacepart.other.IDouble;
import pers.tany.yukinoaapi.interfacepart.other.IList;
import pers.tany.yukinoaapi.interfacepart.other.IRandom;
import pers.tany.yukinoaapi.interfacepart.other.IString;

import java.util.ArrayList;
import java.util.List;

public class AccidentTask extends BukkitRunnable {
    private final String bank;

    public AccidentTask(String bank) {
        this.bank = bank;
    }

    @Override
    public void run() {
        new AccidentTask(bank).runTaskLater(Main.plugin, IRandom.randomNumber(Main.config.getInt(bank + ".MinMinute"), Main.config.getInt(bank + ".MaxMinute")) * 1200);
        List<String> list = new ArrayList(Main.config.getConfigurationSection("Event").getKeys(false));
        for (String s : IList.upsetList(list)) {
            if (IRandom.percentageChance(Main.config.getString("Event." + s))) {
                switch (s) {
                    case "火灾": {
                        Bukkit.broadcastMessage(IString.color(Main.message.getString("Blaze").replace("[name]", Main.config.getString(bank + ".Name"))));
                        for (String name : Main.data.getConfigurationSection("Player").getKeys(false)) {
                            int money = Main.data.getInt("Player." + name + "." + bank);
                            if (money > 0) {
                                money -= IRandom.randomNumber(Main.config.getInt("EventInfo.火灾.MinLostMoney"), Main.config.getInt("EventInfo.火灾.MaxLostMoney"));
                                money -= money * IDouble.percentageNumber(Main.config.getString("EventInfo.火灾.LostMoneyPercentage"));
                                money = Math.max(0, money);
                                Main.data.set("Player." + name + "." + bank, money);
                                if (Bukkit.getPlayerExact(name) != null) {
                                    Player player = Bukkit.getPlayerExact(name);
                                    player.sendMessage(IString.color(Main.message.getString("PlayerBlaze").replace("[name]", Main.config.getString(bank + ".Name"))));
                                }
                            }
                        }
                        Main.data.set("BankStop." + bank, Main.data.getInt("BankStop." + bank) + Main.config.getInt("EventInfo.火灾.StopInterest"));
                        break;
                    }
                    case "水灾": {
                        Bukkit.broadcastMessage(IString.color(Main.message.getString("Flood").replace("[name]", Main.config.getString(bank + ".Name"))));
                        for (String name : Main.data.getConfigurationSection("Player").getKeys(false)) {
                            int money = Main.data.getInt("Player." + name + "." + bank);
                            if (money > 0) {
                                money -= IRandom.randomNumber(Main.config.getInt("EventInfo.水灾.MinLostMoney"), Main.config.getInt("EventInfo.水灾.MaxLostMoney"));
                                money -= money * IDouble.percentageNumber(Main.config.getString("EventInfo.水灾.LostMoneyPercentage"));
                                money = Math.max(0, money);
                                Main.data.set("Player." + name + "." + bank, money);
                                if (Bukkit.getPlayerExact(name) != null) {
                                    Player player = Bukkit.getPlayerExact(name);
                                    player.sendMessage(IString.color(Main.message.getString("PlayerFlood").replace("[name]", Main.config.getString(bank + ".Name"))));
                                }
                            }
                        }
                        Main.data.set("BankStop." + bank, Main.data.getInt("BankStop." + bank) + Main.config.getInt("EventInfo.水灾.StopInterest"));
                        break;
                    }
                    case "地震": {
                        Bukkit.broadcastMessage(IString.color(Main.message.getString("Earthquake").replace("[name]", Main.config.getString(bank + ".Name"))));
                        for (String name : Main.data.getConfigurationSection("Player").getKeys(false)) {
                            int money = Main.data.getInt("Player." + name + "." + bank);
                            if (money > 0) {
                                money -= IRandom.randomNumber(Main.config.getInt("EventInfo.地震.MinLostMoney"), Main.config.getInt("EventInfo.地震.MaxLostMoney"));
                                money -= money * IDouble.percentageNumber(Main.config.getString("EventInfo.地震.LostMoneyPercentage"));
                                money = Math.max(0, money);
                                Main.data.set("Player." + name + "." + bank, money);
                                if (Bukkit.getPlayerExact(name) != null) {
                                    Player player = Bukkit.getPlayerExact(name);
                                    player.sendMessage(IString.color(Main.message.getString("PlayerEarthquake").replace("[name]", Main.config.getString(bank + ".Name"))));
                                }
                            }
                        }
                        Main.data.set("BankStop." + bank, Main.data.getInt("BankStop." + bank) + Main.config.getInt("EventInfo.地震.StopInterest"));
                        break;
                    }
                    case "抢劫": {
                        Bukkit.broadcastMessage(IString.color(Main.message.getString("Robbery").replace("[name]", Main.config.getString(bank + ".Name"))));
                        for (String name : Main.data.getConfigurationSection("Player").getKeys(false)) {
                            int money = Main.data.getInt("Player." + name + "." + bank);
                            if (money > 0) {
                                money -= IRandom.randomNumber(Main.config.getInt("EventInfo.抢劫.MinLostMoney"), Main.config.getInt("EventInfo.抢劫.MaxLostMoney"));
                                money -= money * IDouble.percentageNumber(Main.config.getString("EventInfo.抢劫.LostMoneyPercentage"));
                                money = Math.max(0, money);
                                Main.data.set("Player." + name + "." + bank, money);
                                if (Bukkit.getPlayerExact(name) != null) {
                                    Player player = Bukkit.getPlayerExact(name);
                                    player.sendMessage(IString.color(Main.message.getString("PlayerRobbery").replace("[name]", Main.config.getString(bank + ".Name"))));
                                }
                            }
                        }
                        Main.data.set("BankStop." + bank, Main.data.getInt("BankStop." + bank) + Main.config.getInt("EventInfo.抢劫.StopInterest"));
                        break;
                    }
                    case "金融危机": {
                        Bukkit.broadcastMessage(IString.color(Main.message.getString("FinancialCrisis").replace("[name]", Main.config.getString(bank + ".Name"))));
                        for (String name : Main.data.getConfigurationSection("Player").getKeys(false)) {
                            for (String bank : Main.data.getConfigurationSection("Player." + name).getKeys(false)) {
                                int money = Main.data.getInt("Player." + name + "." + bank);
                                if (money > 0) {
                                    money -= IRandom.randomNumber(Main.config.getInt("EventInfo.金融危机.MinLostMoney"), Main.config.getInt("EventInfo.金融危机.MaxLostMoney"));
                                    money -= money * IDouble.percentageNumber(Main.config.getString("EventInfo.金融危机.LostMoneyPercentage"));
                                    money = Math.max(0, money);
                                    Main.data.set("Player." + name + "." + bank, money);
                                    if (Bukkit.getPlayerExact(name) != null) {
                                        Player player = Bukkit.getPlayerExact(name);
                                        player.sendMessage(IString.color(Main.message.getString("PlayerFinancialCrisis").replace("[name]", Main.config.getString(bank + ".Name"))));
                                    }
                                }
                            }
                        }
                        Main.data.set("BankStop." + bank, Main.data.getInt("BankStop." + bank) + Main.config.getInt("EventInfo.金融危机.StopInterest"));
                        break;
                    }
                    case "破产": {
                        Bukkit.broadcastMessage(IString.color(Main.message.getString("Collapse").replace("[name]", Main.config.getString(bank + ".Name"))));
                        for (String name : Main.data.getConfigurationSection("Player").getKeys(false)) {
                            int money = Main.data.getInt("Player." + name + "." + bank);
                            if (money > 0) {
                                money -= IRandom.randomNumber(Main.config.getInt("EventInfo.破产.MinLostMoney"), Main.config.getInt("EventInfo.破产.MaxLostMoney"));
                                money -= money * IDouble.percentageNumber(Main.config.getString("EventInfo.破产.LostMoneyPercentage"));
                                money = Math.max(0, money);
                                Main.data.set("Player." + name + "." + bank, money);
                                if (Bukkit.getPlayerExact(name) != null) {
                                    Player player = Bukkit.getPlayerExact(name);
                                    player.sendMessage(IString.color(Main.message.getString("PlayerCollapse").replace("[name]", Main.config.getString(bank + ".Name"))));
                                }
                            }
                        }
                        Main.data.set("BankStop." + bank, Main.data.getInt("BankStop." + bank) + Main.config.getInt("EventInfo.破产.StopInterest"));
                        break;
                    }
                    default: {
                        break;
                    }
                }
                IConfig.saveConfig(Main.plugin, Main.data, "", "data");
                return;
            }
        }
    }

}
