package pers.tany.gardenbank.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.scheduler.BukkitRunnable;
import pers.tany.gardenbank.Main;
import pers.tany.yukinoaapi.interfacepart.builder.IItemBuilder;
import pers.tany.yukinoaapi.interfacepart.configuration.IConfig;
import pers.tany.yukinoaapi.interfacepart.item.IItem;
import pers.tany.yukinoaapi.interfacepart.other.IList;
import pers.tany.yukinoaapi.interfacepart.other.IRandom;
import pers.tany.yukinoaapi.interfacepart.other.IString;
import pers.tany.yukinoaapi.interfacepart.other.ITime;
import pers.tany.yukinoaapi.realizationpart.VaultUtil;
import pers.tany.yukinoaapi.realizationpart.builder.ItemBuilder;
import pers.tany.yukinoaapi.realizationpart.item.GlassPaneUtil;
import pers.tany.yukinoaapi.realizationpart.player.Ask;

import java.util.List;

public class BankInterface implements InventoryHolder, Listener {
    private final String serial;
    private final Inventory inventory;
    private final Player player;
    private final int taskID;

    public BankInterface(Player player) {
        Inventory inventory = Bukkit.createInventory(this, 27, IString.color(Main.message.getString("Title")));

        IItemBuilder frame = GlassPaneUtil.getStainedGlass(0);
        frame.setDisplayName(Main.message.getString("HelpName")).setLore(Main.message.getStringList("HelpLore"));
        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, frame.getItemStack());
        }

        String url = "Gold.";
        IItemBuilder gold = new ItemBuilder("GOLD_INGOT");
        gold.setDisplayName(Main.message.getString("GoldName"));
        List<String> list = Main.message.getStringList("GoldLore");
        list = IList.listReplace(list, "[hasMoney]", Main.data.getInt("Player." + player.getName() + ".Gold") + "");
        list = IList.listReplace(list, "[minDepositMoney]", Main.config.getInt(url + "MinDepositMoney") + "");
        list = IList.listReplace(list, "[maxDepositMoney]", Main.config.getInt(url + "MaxDepositMoney") + "");
        list = IList.listReplace(list, "[minWithdrawalMoney]", Main.config.getInt(url + "MinWithdrawalMoney") + "");
        list = IList.listReplace(list, "[maxWithdrawalMoney]", Main.config.getInt(url + "MaxWithdrawalMoney") + "");
        list = IList.listReplace(list, "[interestTime]", Main.config.getInt(url + "InterestTime") + "");
        list = IList.listReplace(list, "[interest]", Main.config.getString(url + "Interest"));
        list = IList.listReplace(list, "[operationalCooling]", Main.config.getInt(url + "OperationalCooling") + "");
        gold.setLore(list);
        inventory.setItem(10, gold.getItemStack());

        url = "Diamond.";
        IItemBuilder diamond = null;
        try {
            diamond = new ItemBuilder("DOUBLE_PLANT");
        } catch (Exception e) {
            diamond = new ItemBuilder("SUNFLOWER");
        }
        diamond.setDisplayName(Main.message.getString("DiamondName"));
        list = Main.message.getStringList("DiamondLore");
        list = IList.listReplace(list, "[hasMoney]", Main.data.getInt("Player." + player.getName() + ".Diamond") + "");
        list = IList.listReplace(list, "[minDepositMoney]", Main.config.getInt(url + "MinDepositMoney") + "");
        list = IList.listReplace(list, "[maxDepositMoney]", Main.config.getInt(url + "MaxDepositMoney") + "");
        list = IList.listReplace(list, "[minWithdrawalMoney]", Main.config.getInt(url + "MinWithdrawalMoney") + "");
        list = IList.listReplace(list, "[maxWithdrawalMoney]", Main.config.getInt(url + "MaxWithdrawalMoney") + "");
        list = IList.listReplace(list, "[interestTime]", Main.config.getInt(url + "InterestTime") + "");
        list = IList.listReplace(list, "[interest]", Main.config.getString(url + "Interest"));
        list = IList.listReplace(list, "[operationalCooling]", Main.config.getInt(url + "OperationalCooling") + "");
        diamond.setLore(list);
        inventory.setItem(13, diamond.getItemStack());

        url = "PacificOceanOceannd.";
        IItemBuilder pacificOceanOceannd = new ItemBuilder("GOLD_BLOCK");
        pacificOceanOceannd.setDisplayName(Main.message.getString("PacificOceanOceanndName"));
        list = Main.message.getStringList("PacificOceanOceanndLore");
        list = IList.listReplace(list, "[hasMoney]", Main.data.getInt("Player." + player.getName() + ".PacificOceanOceannd") + "");
        list = IList.listReplace(list, "[minDepositMoney]", Main.config.getInt(url + "MinDepositMoney") + "");
        list = IList.listReplace(list, "[maxDepositMoney]", Main.config.getInt(url + "MaxDepositMoney") + "");
        list = IList.listReplace(list, "[minWithdrawalMoney]", Main.config.getInt(url + "MinWithdrawalMoney") + "");
        list = IList.listReplace(list, "[maxWithdrawalMoney]", Main.config.getInt(url + "MaxWithdrawalMoney") + "");
        list = IList.listReplace(list, "[interestTime]", Main.config.getInt(url + "InterestTime") + "");
        list = IList.listReplace(list, "[interest]", Main.config.getString(url + "Interest"));
        list = IList.listReplace(list, "[operationalCooling]", Main.config.getInt(url + "OperationalCooling") + "");
        pacificOceanOceannd.setLore(list);
        inventory.setItem(16, pacificOceanOceannd.getItemStack());


        this.inventory = inventory;
        this.player = player;
        this.serial = IRandom.createRandomString(8);

        Bukkit.getPluginManager().registerEvents(this, Main.plugin);
        taskID = new BukkitRunnable() {

            @Override
            public void run() {
                update();
            }

        }.runTaskTimerAsynchronously(Main.plugin, 1, 1).getTaskId();
    }

    public String getSerial() {
        return serial;
    }

    private void update() {
        String url = "Gold.";
        IItemBuilder gold = new ItemBuilder("GOLD_INGOT");
        gold.setDisplayName(Main.message.getString("GoldName"));
        List<String> list = Main.message.getStringList("GoldLore");
        list = IList.listReplace(list, "[hasMoney]", Main.data.getInt("Player." + player.getName() + ".Gold") + "");
        list = IList.listReplace(list, "[minDepositMoney]", Main.config.getInt(url + "MinDepositMoney") + "");
        list = IList.listReplace(list, "[maxDepositMoney]", Main.config.getInt(url + "MaxDepositMoney") + "");
        list = IList.listReplace(list, "[minWithdrawalMoney]", Main.config.getInt(url + "MinWithdrawalMoney") + "");
        list = IList.listReplace(list, "[maxWithdrawalMoney]", Main.config.getInt(url + "MaxWithdrawalMoney") + "");
        list = IList.listReplace(list, "[interestTime]", Main.config.getInt(url + "InterestTime") + "");
        list = IList.listReplace(list, "[interest]", Main.config.getString(url + "Interest"));
        list = IList.listReplace(list, "[operationalCooling]", Main.config.getInt(url + "OperationalCooling") + "");
        gold.setLore(list);
        inventory.setItem(10, gold.getItemStack());

        url = "Diamond.";
        IItemBuilder diamond = new ItemBuilder("SUNFLOWER");
        diamond.setDisplayName(Main.message.getString("DiamondName"));
        list = Main.message.getStringList("DiamondLore");
        list = IList.listReplace(list, "[hasMoney]", Main.data.getInt("Player." + player.getName() + ".Diamond") + "");
        list = IList.listReplace(list, "[minDepositMoney]", Main.config.getInt(url + "MinDepositMoney") + "");
        list = IList.listReplace(list, "[maxDepositMoney]", Main.config.getInt(url + "MaxDepositMoney") + "");
        list = IList.listReplace(list, "[minWithdrawalMoney]", Main.config.getInt(url + "MinWithdrawalMoney") + "");
        list = IList.listReplace(list, "[maxWithdrawalMoney]", Main.config.getInt(url + "MaxWithdrawalMoney") + "");
        list = IList.listReplace(list, "[interestTime]", Main.config.getInt(url + "InterestTime") + "");
        list = IList.listReplace(list, "[interest]", Main.config.getString(url + "Interest"));
        list = IList.listReplace(list, "[operationalCooling]", Main.config.getInt(url + "OperationalCooling") + "");
        diamond.setLore(list);
        inventory.setItem(13, diamond.getItemStack());

        url = "PacificOceanOceannd.";
        IItemBuilder pacificOceanOceannd = new ItemBuilder("GOLD_BLOCK");
        pacificOceanOceannd.setDisplayName(Main.message.getString("PacificOceanOceanndName"));
        list = Main.message.getStringList("PacificOceanOceanndLore");
        list = IList.listReplace(list, "[hasMoney]", Main.data.getInt("Player." + player.getName() + ".PacificOceanOceannd") + "");
        list = IList.listReplace(list, "[minDepositMoney]", Main.config.getInt(url + "MinDepositMoney") + "");
        list = IList.listReplace(list, "[maxDepositMoney]", Main.config.getInt(url + "MaxDepositMoney") + "");
        list = IList.listReplace(list, "[minWithdrawalMoney]", Main.config.getInt(url + "MinWithdrawalMoney") + "");
        list = IList.listReplace(list, "[maxWithdrawalMoney]", Main.config.getInt(url + "MaxWithdrawalMoney") + "");
        list = IList.listReplace(list, "[interestTime]", Main.config.getInt(url + "InterestTime") + "");
        list = IList.listReplace(list, "[interest]", Main.config.getString(url + "Interest"));
        list = IList.listReplace(list, "[operationalCooling]", Main.config.getInt(url + "OperationalCooling") + "");
        pacificOceanOceannd.setLore(list);
        inventory.setItem(16, pacificOceanOceannd.getItemStack());
    }


    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent evt) {
        if (evt.getWhoClicked() instanceof Player && evt.getWhoClicked().equals(player)) {
            int rawSlot = evt.getRawSlot();
            if (rawSlot != -999) {
                if (evt.getInventory().getHolder() instanceof BankInterface) {
                    evt.setCancelled(true);
                    if (evt.getClickedInventory().getHolder() instanceof BankInterface) {
                        if (!IItem.isEmpty(evt.getCurrentItem())) {
                            if (rawSlot == 10) {
                                if (!player.hasPermission("gb.gold")) {
                                    player.sendMessage("§c你没有权限进行此操作");
                                    return;
                                }
                                String url = "Gold.";
                                if (evt.getClick().isLeftClick()) {
                                    if (Main.data.getInt("PlayerCoolding." + player.getName() + ".Gold") > 0) {
                                        String[] s = ITime.getDay(Main.data.getInt("PlayerCoolding." + player.getName() + ".Gold")).split(":");
                                        player.sendMessage(IString.color(Main.message.getString("Coolding").replace("[name]", Main.config.getString(url + "Name")).replace("[time]", s[0] + "日" + s[1] + "时" + s[2] + "分")));
                                        return;
                                    }
                                    player.closeInventory();
                                    new BukkitRunnable() {

                                        @Override
                                        public void run() {
                                            player.sendMessage(IString.color(Main.message.getString("Deposit").replace("[name]", Main.config.getString(url + "Name"))));
                                            while (true) {
                                                Ask ask = new Ask(player);
                                                if (ask.getReason().isAnswer()) {
                                                    try {
                                                        int money = Integer.parseInt(ask.getAnswer());
                                                        if (!VaultUtil.hasMoney(player, money)) {
                                                            player.sendMessage(IString.color(Main.message.getString("NoMoney").replace("[name]", Main.config.getString(url + "Name"))));
                                                            return;
                                                        }
                                                        if (money < Main.config.getInt(url + "MinDepositMoney") || money > Main.config.getInt(url + "MaxDepositMoney")) {
                                                            player.sendMessage(IString.color(Main.message.getString("NoDepositMoney").replace("[name]", Main.config.getString(url + "Name")).replace("[minDepositMoney]", Main.config.getInt(url + "MinDepositMoney") + "").replace("[maxDepositMoney]", Main.config.getInt(url + "MaxDepositMoney") + "")));
                                                            return;
                                                        }
                                                        Main.economy.withdrawPlayer(player, money);
                                                        Main.data.set("Player." + player.getName() + ".Gold", Main.data.getInt("Player." + player.getName() + ".Gold") + money);
                                                        Main.data.set("PlayerCoolding." + player.getName() + ".Gold", Main.config.getInt(url + "OperationalCooling"));
                                                        IConfig.saveConfig(Main.plugin, Main.data, "", "data");

                                                        player.sendMessage(IString.color(Main.message.getString("DepositMoney").replace("[name]", Main.config.getString(url + "Name")).replace("[money]", money + "")));
                                                        return;
                                                    } catch (NumberFormatException numberFormatException) {
                                                        player.sendMessage("§c请输入整数！");
                                                    }
                                                } else {
                                                    HandlerList.unregisterAll(BankInterface.this);
                                                    Bukkit.getScheduler().cancelTask(taskID);
                                                    return;
                                                }
                                            }
                                        }

                                    }.runTaskAsynchronously(Main.plugin);
                                } else if (evt.getClick().isRightClick()) {
                                    if (Main.data.getInt("PlayerCoolding." + player.getName() + ".Gold") > 0) {
                                        String[] s = ITime.getDay(Main.data.getInt("PlayerCoolding." + player.getName() + ".Gold")).split(":");
                                        player.sendMessage(IString.color(Main.message.getString("Coolding").replace("[name]", Main.config.getString(url + "Name")).replace("[time]", s[0] + "日" + s[1] + "时" + s[2] + "分")));
                                        return;
                                    }
                                    player.closeInventory();
                                    new BukkitRunnable() {

                                        @Override
                                        public void run() {
                                            player.sendMessage(IString.color(Main.message.getString("Withdrawal").replace("[name]", Main.config.getString(url + "Name"))));
                                            while (true) {
                                                Ask ask = new Ask(player);
                                                if (ask.getReason().isAnswer()) {
                                                    try {
                                                        int money = Integer.parseInt(ask.getAnswer());
                                                        if (money < Main.config.getInt(url + "MinWithdrawalMoney") || money > Main.config.getInt(url + "MaxWithdrawalMoney")) {
                                                            player.sendMessage(IString.color(Main.message.getString("NoWithdrawalMoney").replace("[name]", Main.config.getString(url + "Name")).replace("[minWithdrawalMoney]", Main.config.getInt(url + "MinWithdrawalMoney") + "").replace("[maxWithdrawalMoney]", Main.config.getInt(url + "MaxWithdrawalMoney") + "")));
                                                            return;
                                                        }
                                                        if (money > Main.data.getInt("Player." + player.getName() + ".Gold")) {
                                                            player.sendMessage(IString.color(Main.message.getString("NoMoney").replace("[name]", Main.config.getString(url + "Name"))));
                                                            return;
                                                        }
                                                        Main.economy.depositPlayer(player, money);
                                                        Main.data.set("Player." + player.getName() + ".Gold", Main.data.getInt("Player." + player.getName() + ".Gold") - money);
                                                        Main.data.set("PlayerCoolding." + player.getName() + ".Gold", Main.config.getInt(url + "OperationalCooling"));
                                                        IConfig.saveConfig(Main.plugin, Main.data, "", "data");

                                                        player.sendMessage(IString.color(Main.message.getString("WithdrawalMoney").replace("[name]", Main.config.getString(url + "Name")).replace("[money]", money + "")));
                                                        return;
                                                    } catch (NumberFormatException numberFormatException) {
                                                        player.sendMessage("§c请输入整数！");
                                                    }
                                                } else {
                                                    HandlerList.unregisterAll(BankInterface.this);
                                                    Bukkit.getScheduler().cancelTask(taskID);
                                                    return;
                                                }
                                            }
                                        }

                                    }.runTaskAsynchronously(Main.plugin);
                                }
                            } else if (rawSlot == 13) {
                                if (!player.hasPermission("gb.diamond")) {
                                    player.sendMessage("§c你没有权限进行此操作");
                                    return;
                                }
                                String url = "Diamond.";
                                if (evt.getClick().isLeftClick()) {
                                    if (Main.data.getInt("PlayerCoolding." + player.getName() + ".Diamond") > 0) {
                                        String[] s = ITime.getDay(Main.data.getInt("PlayerCoolding." + player.getName() + ".Diamond")).split(":");
                                        player.sendMessage(IString.color(Main.message.getString("Coolding").replace("[name]", Main.config.getString(url + "Name")).replace("[time]", s[0] + "日" + s[1] + "时" + s[2] + "分")));
                                        return;
                                    }
                                    player.closeInventory();
                                    new BukkitRunnable() {

                                        @Override
                                        public void run() {
                                            player.sendMessage(IString.color(Main.message.getString("Deposit").replace("[name]", Main.config.getString(url + "Name"))));
                                            while (true) {
                                                Ask ask = new Ask(player);
                                                if (ask.getReason().isAnswer()) {
                                                    try {
                                                        int money = Integer.parseInt(ask.getAnswer());
                                                        if (!VaultUtil.hasMoney(player, money)) {
                                                            player.sendMessage(IString.color(Main.message.getString("NoMoney").replace("[name]", Main.config.getString(url + "Name"))));
                                                        }
                                                        if (money < Main.config.getInt(url + "MinDepositMoney") || money > Main.config.getInt(url + "MaxDepositMoney")) {
                                                            player.sendMessage(IString.color(Main.message.getString("NoDepositMoney").replace("[name]", Main.config.getString(url + "Name")).replace("[minDepositMoney]", Main.config.getInt(url + "MinDepositMoney") + "").replace("[maxDepositMoney]", Main.config.getInt(url + "MaxDepositMoney") + "")));
                                                            return;
                                                        }
                                                        Main.economy.withdrawPlayer(player, money);
                                                        Main.data.set("Player." + player.getName() + ".Diamond", Main.data.getInt("Player." + player.getName() + ".Diamond") + money);
                                                        Main.data.set("PlayerCoolding." + player.getName() + ".Diamond", Main.config.getInt(url + "OperationalCooling"));
                                                        IConfig.saveConfig(Main.plugin, Main.data, "", "data");

                                                        player.sendMessage(IString.color(Main.message.getString("DepositMoney").replace("[name]", Main.config.getString(url + "Name")).replace("[money]", money + "")));
                                                        return;
                                                    } catch (NumberFormatException numberFormatException) {
                                                        player.sendMessage("§c请输入整数！");
                                                    }
                                                } else {
                                                    HandlerList.unregisterAll(BankInterface.this);
                                                    Bukkit.getScheduler().cancelTask(taskID);
                                                    return;
                                                }
                                            }
                                        }

                                    }.runTaskAsynchronously(Main.plugin);
                                } else if (evt.getClick().isRightClick()) {
                                    if (Main.data.getInt("PlayerCoolding." + player.getName() + ".Diamond") > 0) {
                                        String[] s = ITime.getDay(Main.data.getInt("PlayerCoolding." + player.getName() + ".Diamond")).split(":");
                                        player.sendMessage(IString.color(Main.message.getString("Coolding").replace("[name]", Main.config.getString(url + "Name")).replace("[time]", s[0] + "日" + s[1] + "时" + s[2] + "分")));
                                        return;
                                    }
                                    player.closeInventory();
                                    new BukkitRunnable() {

                                        @Override
                                        public void run() {
                                            player.sendMessage(IString.color(Main.message.getString("Withdrawal").replace("[name]", Main.config.getString(url + "Name"))));
                                            while (true) {
                                                Ask ask = new Ask(player);
                                                if (ask.getReason().isAnswer()) {
                                                    try {
                                                        int money = Integer.parseInt(ask.getAnswer());
                                                        if (money < Main.config.getInt(url + "MinWithdrawalMoney") || money > Main.config.getInt(url + "MaxWithdrawalMoney")) {
                                                            player.sendMessage(IString.color(Main.message.getString("NoWithdrawalMoney").replace("[name]", Main.config.getString(url + "Name")).replace("[minWithdrawalMoney]", Main.config.getInt(url + "MinWithdrawalMoney") + "").replace("[maxWithdrawalMoney]", Main.config.getInt(url + "MaxWithdrawalMoney") + "")));
                                                            return;
                                                        }
                                                        if (money > Main.data.getInt("Player." + player.getName() + ".Diamond")) {
                                                            player.sendMessage(IString.color(Main.message.getString("NoMoney").replace("[name]", Main.config.getString(url + "Name"))));
                                                            return;
                                                        }
                                                        Main.economy.depositPlayer(player, money);
                                                        Main.data.set("Player." + player.getName() + ".Diamond", Main.data.getInt("Player." + player.getName() + ".Diamond") - money);
                                                        Main.data.set("PlayerCoolding." + player.getName() + ".Diamond", Main.config.getInt(url + "OperationalCooling"));
                                                        IConfig.saveConfig(Main.plugin, Main.data, "", "data");

                                                        player.sendMessage(IString.color(Main.message.getString("WithdrawalMoney").replace("[name]", Main.config.getString(url + "Name")).replace("[money]", money + "")));
                                                        return;
                                                    } catch (NumberFormatException numberFormatException) {
                                                        player.sendMessage("§c请输入整数！");
                                                    }
                                                } else {
                                                    HandlerList.unregisterAll(BankInterface.this);
                                                    Bukkit.getScheduler().cancelTask(taskID);
                                                    return;
                                                }
                                            }
                                        }

                                    }.runTaskAsynchronously(Main.plugin);
                                }
                            } else if (rawSlot == 16) {
                                if (!player.hasPermission("gb.pacificoceanoceannd")) {
                                    player.sendMessage("§c你没有权限进行此操作");
                                    return;
                                }
                                String url = "PacificOceanOceannd.";
                                if (evt.getClick().isLeftClick()) {
                                    if (Main.data.getInt("PlayerCoolding." + player.getName() + ".PacificOceanOceannd") > 0) {
                                        String[] s = ITime.getDay(Main.data.getInt("PlayerCoolding." + player.getName() + ".PacificOceanOceannd")).split(":");
                                        player.sendMessage(IString.color(Main.message.getString("Coolding").replace("[name]", Main.config.getString(url + "Name")).replace("[time]", s[0] + "日" + s[1] + "时" + s[2] + "分")));
                                        return;
                                    }
                                    player.closeInventory();
                                    new BukkitRunnable() {

                                        @Override
                                        public void run() {
                                            player.sendMessage(IString.color(Main.message.getString("Deposit").replace("[name]", Main.config.getString(url + "Name"))));
                                            while (true) {
                                                Ask ask = new Ask(player);
                                                if (ask.getReason().isAnswer()) {
                                                    try {
                                                        int money = Integer.parseInt(ask.getAnswer());
                                                        if (!VaultUtil.hasMoney(player, money)) {
                                                            player.sendMessage(IString.color(Main.message.getString("NoMoney").replace("[name]", Main.config.getString(url + "Name"))));
                                                            return;
                                                        }
                                                        if (money < Main.config.getInt(url + "MinDepositMoney") || money > Main.config.getInt(url + "MaxDepositMoney")) {
                                                            player.sendMessage(IString.color(Main.message.getString("NoDepositMoney").replace("[name]", Main.config.getString(url + "Name")).replace("[minDepositMoney]", Main.config.getInt(url + "MinDepositMoney") + "").replace("[maxDepositMoney]", Main.config.getInt(url + "MaxDepositMoney") + "")));
                                                            return;
                                                        }
                                                        Main.economy.withdrawPlayer(player, money);
                                                        Main.data.set("Player." + player.getName() + ".PacificOceanOceannd", Main.data.getInt("Player." + player.getName() + ".PacificOceanOceannd") + money);
                                                        Main.data.set("PlayerCoolding." + player.getName() + ".PacificOceanOceannd", Main.config.getInt(url + "OperationalCooling"));
                                                        IConfig.saveConfig(Main.plugin, Main.data, "", "data");

                                                        player.sendMessage(IString.color(Main.message.getString("DepositMoney").replace("[name]", Main.config.getString(url + "Name")).replace("[money]", money + "")));
                                                        return;
                                                    } catch (NumberFormatException numberFormatException) {
                                                        player.sendMessage("§c请输入整数！");
                                                    }
                                                } else {
                                                    HandlerList.unregisterAll(BankInterface.this);
                                                    Bukkit.getScheduler().cancelTask(taskID);
                                                    return;
                                                }
                                            }
                                        }

                                    }.runTaskAsynchronously(Main.plugin);
                                } else if (evt.getClick().isRightClick()) {
                                    if (Main.data.getInt("PlayerCoolding." + player.getName() + ".PacificOceanOceannd") > 0) {
                                        String[] s = ITime.getDay(Main.data.getInt("PlayerCoolding." + player.getName() + ".PacificOceanOceannd")).split(":");
                                        player.sendMessage(IString.color(Main.message.getString("Coolding").replace("[name]", Main.config.getString(url + "Name")).replace("[time]", s[0] + "日" + s[1] + "时" + s[2] + "分")));
                                        return;
                                    }
                                    player.closeInventory();
                                    new BukkitRunnable() {

                                        @Override
                                        public void run() {
                                            player.sendMessage(IString.color(Main.message.getString("Withdrawal").replace("[name]", Main.config.getString(url + "Name"))));
                                            while (true) {
                                                Ask ask = new Ask(player);
                                                if (ask.getReason().isAnswer()) {
                                                    try {
                                                        int money = Integer.parseInt(ask.getAnswer());
                                                        if (money < Main.config.getInt(url + "MinWithdrawalMoney") || money > Main.config.getInt(url + "MaxWithdrawalMoney")) {
                                                            player.sendMessage(IString.color(Main.message.getString("NoWithdrawalMoney").replace("[name]", Main.config.getString(url + "Name")).replace("[minWithdrawalMoney]", Main.config.getInt(url + "MinWithdrawalMoney") + "").replace("[maxWithdrawalMoney]", Main.config.getInt(url + "MaxWithdrawalMoney") + "")));
                                                            return;
                                                        }
                                                        if (money > Main.data.getInt("Player." + player.getName() + ".PacificOceanOceannd")) {
                                                            player.sendMessage(IString.color(Main.message.getString("NoMoney").replace("[name]", Main.config.getString(url + "Name"))));
                                                            return;
                                                        }
                                                        Main.economy.depositPlayer(player, money);
                                                        Main.data.set("Player." + player.getName() + ".PacificOceanOceannd", Main.data.getInt("Player." + player.getName() + ".PacificOceanOceannd") - money);
                                                        Main.data.set("PlayerCoolding." + player.getName() + ".PacificOceanOceannd", Main.config.getInt(url + "OperationalCooling"));
                                                        IConfig.saveConfig(Main.plugin, Main.data, "", "data");

                                                        player.sendMessage(IString.color(Main.message.getString("WithdrawalMoney").replace("[name]", Main.config.getString(url + "Name")).replace("[money]", money + "")));
                                                        return;
                                                    } catch (NumberFormatException numberFormatException) {
                                                        player.sendMessage("§c请输入整数！");
                                                    }
                                                } else {
                                                    HandlerList.unregisterAll(BankInterface.this);
                                                    Bukkit.getScheduler().cancelTask(taskID);
                                                    return;
                                                }
                                            }
                                        }

                                    }.runTaskAsynchronously(Main.plugin);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    private void onInventoryClose(InventoryCloseEvent evt) {
        if (evt.getInventory().getHolder() instanceof BankInterface && evt.getPlayer() instanceof Player) {
            BankInterface bankInterface = (BankInterface) evt.getInventory().getHolder();
            if (evt.getPlayer().equals(player) && bankInterface.getSerial().equals(serial)) {
                HandlerList.unregisterAll(this);
                Bukkit.getScheduler().cancelTask(taskID);
            }
        }
    }
}
