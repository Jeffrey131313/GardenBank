package pers.tany.gardenbank;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import pers.tany.gardenbank.command.Commands;
import pers.tany.gardenbank.listenevent.Events;
import pers.tany.gardenbank.task.AccidentTask;
import pers.tany.yukinoaapi.interfacepart.configuration.IConfig;
import pers.tany.yukinoaapi.interfacepart.other.IDouble;
import pers.tany.yukinoaapi.interfacepart.other.IRandom;
import pers.tany.yukinoaapi.interfacepart.other.IString;
import pers.tany.yukinoaapi.interfacepart.register.IRegister;
import pers.tany.yukinoaapi.realizationpart.VaultUtil;

public class Main extends JavaPlugin {
    public static Plugin plugin = null;
    public static YamlConfiguration config;
    public static YamlConfiguration data;
    public static YamlConfiguration message;
    public static Economy economy;

    @Override
    public void onEnable() {
        plugin = this;

        Bukkit.getConsoleSender().sendMessage("§6[§eGardenBank§6]§a已启用");

        IConfig.createResource(this, "", "config.yml", false);
        IConfig.createResource(this, "", "data.yml", false);
        IConfig.createResource(this, "", "message.yml", false);

        config = IConfig.loadConfig(this, "", "config");
        data = IConfig.loadConfig(this, "", "data");
        message = IConfig.loadConfig(this, "", "message");

        IRegister.registerEvents(this, new Events());
        IRegister.registerCommands(this, "GardenBank", new Commands());

        economy = VaultUtil.getEconomy();

        new AccidentTask("Gold").runTaskLater(this, IRandom.randomNumber(config.getInt("Gold.MinMinute"), config.getInt("Gold.MaxMinute")) * 1200L);
        new AccidentTask("Diamond").runTaskLater(this, IRandom.randomNumber(config.getInt("Diamond.MinMinute"), config.getInt("Diamond.MaxMinute")) * 1200L);
        new AccidentTask("PacificOceanOceannd").runTaskLater(this, IRandom.randomNumber(config.getInt("PacificOceanOceannd.MinMinute"), config.getInt("PacificOceanOceannd.MaxMinute")) * 1200L);

        new BukkitRunnable() {
            private int minute;

            @Override
            public void run() {
                for (String name : data.getConfigurationSection("PlayerCoolding").getKeys(false)) {
                    for (String bank : data.getConfigurationSection("PlayerCoolding." + name).getKeys(false)) {
                        int coolding = data.getInt("PlayerCoolding." + name + "." + bank);
                        data.set("PlayerCoolding." + name + "." + bank, coolding == 1 ? null : coolding - 1);
                    }
                }
                if (++minute % Main.config.getInt("Gold.InterestTime") == 0) {
                    int stop = data.getInt("BankStop.Gold");
                    if (stop > 0) {
                        data.set("BankStop.Gold", stop == 1 ? null : stop - 1);
                    } else {
                        for (String name : data.getConfigurationSection("Player").getKeys(false)) {
                            int money = data.getInt("Player." + name + ".Gold");
                            if (money > 0) {
                                data.set("Player." + name + ".Gold", (int) (money + money * IDouble.percentageNumber(Main.config.getString("Gold.Interest"))));
                                if (Bukkit.getPlayerExact(name) != null) {
                                    Player player = Bukkit.getPlayerExact(name);
                                    player.sendMessage(IString.color(Main.message.getString("Interest").replace("[name]", Main.config.getString("Gold.Name")).replace("[money]", (int) (money * IDouble.percentageNumber(Main.config.getString("Gold.Interest"))) + "")));
                                }
                            }
                        }
                    }
                }
                if (minute % Main.config.getInt("Diamond.InterestTime") == 0) {
                    int stop = data.getInt("BankStop.Diamond");
                    if (stop > 0) {
                        data.set("BankStop.Diamond", stop == 1 ? null : stop - 1);
                    } else {
                        for (String name : data.getConfigurationSection("Player").getKeys(false)) {
                            int money = data.getInt("Player." + name + ".Diamond");
                            if (money > 0) {
                                data.set("Player." + name + ".Diamond", (int) (money + money * IDouble.percentageNumber(Main.config.getString("Diamond.Interest"))));
                                if (Bukkit.getPlayerExact(name) != null) {
                                    Player player = Bukkit.getPlayerExact(name);
                                    player.sendMessage(IString.color(Main.message.getString("Interest").replace("[name]", Main.config.getString("Diamond.Name")).replace("[money]", (int) (money * IDouble.percentageNumber(Main.config.getString("Diamond.Interest"))) + "")));
                                }
                            }
                        }
                    }
                }
                if (minute % Main.config.getInt("PacificOceanOceannd.InterestTime") == 0) {
                    int stop = data.getInt("BankStop.PacificOceanOceannd");
                    if (stop > 0) {
                        data.set("BankStop.PacificOceanOceannd", stop == 1 ? null : stop - 1);
                    } else {
                        for (String name : data.getConfigurationSection("Player").getKeys(false)) {
                            int money = data.getInt("Player." + name + ".PacificOceanOceannd");
                            if (money > 0) {
                                data.set("Player." + name + ".PacificOceanOceannd", (int) (money + money * IDouble.percentageNumber(Main.config.getString("PacificOceanOceannd.Interest"))));
                                if (Bukkit.getPlayerExact(name) != null) {
                                    Player player = Bukkit.getPlayerExact(name);
                                    player.sendMessage(IString.color(Main.message.getString("Interest").replace("[name]", Main.config.getString("PacificOceanOceannd.Name")).replace("[money]", (int) (money * IDouble.percentageNumber(Main.config.getString("PacificOceanOceannd.Interest"))) + "")));
                                }
                            }
                        }
                    }
                }
                IConfig.saveConfig(Main.plugin, data, "", "data");
            }

        }.runTaskTimer(Main.plugin, 1200, 1200);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§6[§eGardenBank§6]§c已卸载");
    }
}
