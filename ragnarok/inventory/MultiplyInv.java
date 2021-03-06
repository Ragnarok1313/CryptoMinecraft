package ragnarok.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import ragnarok.CasinoX;
import ragnarok.Messaging;
import ragnarok.mysql.sqlservice.DatabaseConnection;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static ragnarok.CasinoX.*;

public class MultiplyInv implements Listener {

    Inventory updInv = null;

    public MultiplyInv() {
    }

    public static HashMap<String, Integer> casxCD = new HashMap();
    static int casxtimeCD = 1;

    @EventHandler(
            priority = EventPriority.LOWEST
    )
    public void onMultiplyInv(InventoryClickEvent e) {
        Player clicker = (Player) e.getWhoClicked();
        ItemStack clicked = e.getCurrentItem();
        Inventory inventory = e.getInventory();
        if (inventory.getName() != null) {
            if (inventory.getName().equals(CasinoX.plugin.str_multiplyinvname)) {
                if (!casxCD.containsKey(clicker.getName())) {
                    casxCD.put(clicker.getName(), 0);
                }
                if (casxCD.containsKey(clicker.getName())) {
                    long playerID = System.currentTimeMillis();
                    int epoch = (int) (playerID / 1000L);
                    if ((Integer) casxCD.get(clicker.getName()) > epoch - casxtimeCD) {
                        e.setCancelled(true);
                        clicker.sendMessage("§4Нельзя использовать чаще раза в " + casxtimeCD + " сек.");
                    } else {
                        long epoch1 = System.currentTimeMillis();
                        int time = (int) (epoch1 / 1000L);
                        casxCD.put(clicker.getName(), time);
                        e.setCancelled(true);
                        if (clicked != null && clicked.hasItemMeta() && clicked.getItemMeta().hasDisplayName()) {
                            Player player = clicker;
                            //todo выход
                            if (clicked.getItemMeta().getDisplayName().equals("§4ВЫХОД")) {
                                player.closeInventory();
                            }
                            //todo модификатор ниже
                            if (clicked.getItemMeta().getDisplayName().equals(CasinoX.plugin.str_multiplymoddownname.replaceFirst("%int_moddown", "0.1"))) {
                                if (Modify.get(player.getName()) > 1.1) {
                                    String pattern = "##0.0";
                                    DecimalFormat decimalFormat = new DecimalFormat(pattern);
                                    String str_winchance = decimalFormat.format(Modify.get(player.getName()) - 0.1);
                                    Modify.put(player.getName(), Double.parseDouble(str_winchance));
                                    player.closeInventory();
                                    player.openInventory(getInv(player, 11000));
                                } else {
                                    Modify.put(player.getName(), 1.1);
                                    player.closeInventory();
                                    player.openInventory(getInv(player, 11000));
                                }
                            }
                            if (clicked.getItemMeta().getDisplayName().equals(CasinoX.plugin.str_multiplymoddownname.replaceFirst("%int_moddown", "0.5"))) {
                                if (Modify.get(player.getName()) > 1.6) {
                                    String pattern = "##0.0";
                                    DecimalFormat decimalFormat = new DecimalFormat(pattern);
                                    String str_winchance = decimalFormat.format(Modify.get(player.getName()) - 0.5);
                                    Modify.put(player.getName(), Double.parseDouble(str_winchance));
                                    player.closeInventory();
                                    player.openInventory(getInv(player, 11000));
                                } else {
                                    Modify.put(player.getName(), 1.1);
                                    player.closeInventory();
                                    player.openInventory(getInv(player, 11000));
                                }
                            }

                            //todo модификатор выше
                            if (clicked.getItemMeta().getDisplayName().equals(CasinoX.plugin.str_multiplymodupname.replaceFirst("%int_modup", "0.1"))) {
                                if (Modify.get(player.getName()) < 50.0) {
                                    String pattern = "##0.0";
                                    DecimalFormat decimalFormat = new DecimalFormat(pattern);
                                    String str_winchance = decimalFormat.format(Modify.get(player.getName()) + 0.1);
                                    Modify.put(player.getName(), Double.parseDouble(str_winchance));
                                    player.closeInventory();
                                    player.openInventory(getInv(player, 11000));
                                } else {
                                    Modify.put(player.getName(), 50.0);
                                    player.closeInventory();
                                    player.openInventory(getInv(player, 11000));
                                }
                            }
                            if (clicked.getItemMeta().getDisplayName().equals(CasinoX.plugin.str_multiplymodupname.replaceFirst("%int_modup", "0.5"))) {
                                if (Modify.get(player.getName()) < 50.0) {

                                    String pattern = "##0.0";
                                    DecimalFormat decimalFormat = new DecimalFormat(pattern);
                                    String str_winchance = decimalFormat.format(Modify.get(player.getName()) + 0.5);
                                    Modify.put(player.getName(), Double.parseDouble(str_winchance));

                                    player.closeInventory();
                                    player.openInventory(getInv(player, 11000));
                                } else {
                                    Modify.put(player.getName(), 50.0);
                                    player.closeInventory();
                                    player.openInventory(getInv(player, 11000));
                                }
                            }

                            //todo ставка ниже
                            if (clicked.getItemMeta().getDisplayName().equals(CasinoX.plugin.str_multiplybetdownname.replaceFirst("%int_betdown", "1"))) {
                                if (Bet.get(player.getName()) > 1) {
                                    Bet.put(player.getName(), Bet.get(player.getName()) - 1);
                                    player.closeInventory();
                                    player.openInventory(getInv(player, 11000));
                                } else {
                                    Bet.put(player.getName(), 1);
                                    player.closeInventory();
                                    player.openInventory(getInv(player, 11000));
                                }
                            }
                            if (clicked.getItemMeta().getDisplayName().equals(CasinoX.plugin.str_multiplybetdownname.replaceFirst("%int_betdown", " /2"))) {
                                if (Balance.get(player.getName()) != 0 && (Bet.get(player.getName()) > Bet.get(player.getName()) / 2)) {
                                    Bet.put(player.getName(), Bet.get(player.getName()) / 2);
                                    player.closeInventory();
                                    player.openInventory(getInv(player, 11000));
                                } else {
                                    Bet.put(player.getName(), 1);
                                    player.closeInventory();
                                    player.openInventory(getInv(player, 11000));
                                }
                            }
                            //todo ставка выше
                            if (clicked.getItemMeta().getDisplayName().equals(CasinoX.plugin.str_multiplybetupname.replaceFirst("%int_betup", "1"))) {
                                if (Bet.get(player.getName()) < 1000000) {
                                    if (Bet.get(player.getName()) + 1 > Balance.get(player.getName())){
                                        if (Balance.get(player.getName()) != 0) {
                                            Bet.put(player.getName(), Balance.get(player.getName()));
                                        } else {
                                            Bet.put(player.getName(), 1);
                                        }
                                        player.closeInventory();
                                        player.openInventory(getInv(player, 11000));
                                    } else {
                                        Bet.put(player.getName(), Bet.get(player.getName()) + 1);
                                        player.closeInventory();
                                        player.openInventory(getInv(player, 11000));
                                    }
                                } else {
                                    Bet.put(player.getName(), 1000000);
                                    player.closeInventory();
                                    player.openInventory(getInv(player, 11000));
                                }
                            }
                            if (clicked.getItemMeta().getDisplayName().equals(CasinoX.plugin.str_multiplybetupname.replaceFirst("%int_betup", " x2"))) {
                                if (Bet.get(player.getName()) < 1000000) {
                                    if (Bet.get(player.getName()) * 2 > Balance.get(player.getName())){
                                        if (Balance.get(player.getName()) != 0) {
                                            Bet.put(player.getName(), Balance.get(player.getName()));
                                        } else {
                                            Bet.put(player.getName(), 1);
                                        }
                                        player.closeInventory();
                                        player.openInventory(getInv(player, 11000));
                                    } else {
                                        Bet.put(player.getName(), Bet.get(player.getName()) * 2);
                                        player.closeInventory();
                                        player.openInventory(getInv(player, 11000));
                                    }
                                } else {
                                    Bet.put(player.getName(), 1000000);
                                    player.closeInventory();
                                    player.openInventory(getInv(player, 11000));
                                }
                            }
                            //todo Баланс пополнить
                            if (clicked.getItemMeta().getDisplayName().equals(CasinoX.plugin.str_multiplydepositname.replaceFirst("%depocount", "4"))) {
                                if (Balance.get(player.getName()) < 1000000000) {
                                    boolean hasmoney = false;
                                    PlayerInventory inv = player.getInventory();
                                    for (ItemStack playerItem : inv.getContents()) {
                                        if ((playerItem != null) && (playerItem.getTypeId() == CasinoX.plugin.depomoney_id)) {
                                            if (playerItem.getAmount() >= 4) {
                                                hasmoney = true;
                                                if (playerItem.getAmount() > 4) {
                                                    playerItem.setAmount(playerItem.getAmount() - 4);
                                                } else {
                                                    ItemStack remove = new ItemStack(CasinoX.plugin.depomoney_id, 4);
                                                    inv.removeItem(remove);
                                                }
                                                player.updateInventory();
                                                break;
                                            }
                                        }
                                    }
                                    if (hasmoney) {
                                        Balance.put(player.getName(), Balance.get(player.getName()) + 4);
                                        player.closeInventory();
                                        player.openInventory(getInv(player, 11000));
                                        player.sendMessage(CasinoX.plugin.str_balancelore.replaceFirst("%balance", String.valueOf(Balance.get(player.getName()))));
                                        //логирование
                                        if (CasinoX.plugin.b_logger) {
                                            String loggermessage = (" [DEPOSIT] " + player.getName() + " пополнил счет на 4 !");
                                            Messaging.CasXlog(loggermessage);
                                        }
                                    }
                                }
                            }
                            if (clicked.getItemMeta().getDisplayName().equals(CasinoX.plugin.str_multiplydepositname.replaceFirst("%depocount", "32"))) {
                                if (Balance.get(player.getName()) < 1000000000) {
                                    boolean hasmoney = false;
                                    PlayerInventory inv = player.getInventory();
                                    for (ItemStack playerItem : inv.getContents()) {
                                        if ((playerItem != null) && (playerItem.getTypeId() == CasinoX.plugin.depomoney_id)) {
                                            if (playerItem.getAmount() >= 32) {
                                                hasmoney = true;
                                                if (playerItem.getAmount() > 32) {
                                                    playerItem.setAmount(playerItem.getAmount() - 32);
                                                } else {
                                                    ItemStack remove = new ItemStack(CasinoX.plugin.depomoney_id, 32);
                                                    inv.removeItem(remove);
                                                }
                                                player.updateInventory();
                                                break;
                                            }
                                        }
                                    }
                                    if (hasmoney) {
                                        Balance.put(player.getName(), Balance.get(player.getName()) + 32);
                                        player.closeInventory();
                                        player.openInventory(getInv(player, 11000));
                                        player.sendMessage(CasinoX.plugin.str_balancelore.replaceFirst("%balance", String.valueOf(Balance.get(player.getName()))));
                                        //логирование
                                        if (CasinoX.plugin.b_logger) {
                                            String loggermessage = (" [DEPOSIT] " + player.getName() + " пополнил счет на 32 !");
                                            Messaging.CasXlog(loggermessage);
                                        }
                                    }
                                }
                            }
                            if (clicked.getItemMeta().getDisplayName().equals(CasinoX.plugin.str_multiplydepositname.replaceFirst("%depocount", "64"))) {
                                if (Balance.get(player.getName()) < 1000000000) {
                                    boolean hasmoney = false;
                                    PlayerInventory inv = player.getInventory();
                                    for (ItemStack playerItem : inv.getContents()) {
                                        if ((playerItem != null) && (playerItem.getTypeId() == CasinoX.plugin.depomoney_id)) {
                                            final ItemStack playerIt = playerItem;
                                            if (playerItem.getAmount() >= 64) {
                                                hasmoney = true;

                                                ItemStack remove = new ItemStack(CasinoX.plugin.depomoney_id, 64);
                                                inv.removeItem(remove);
                                                player.updateInventory();
                                                break;
                                            }
                                        }
                                    }
                                    if (hasmoney) {
                                        Balance.put(player.getName(), Balance.get(player.getName()) + 64);
                                        player.closeInventory();
                                        player.openInventory(getInv(player, 11000));
                                        player.sendMessage(CasinoX.plugin.str_balancelore.replaceFirst("%balance", String.valueOf(Balance.get(player.getName()))));
                                        //логирование
                                        if (CasinoX.plugin.b_logger) {
                                            String loggermessage = (" [DEPOSIT] " + player.getName() + " пополнил счет на 64 !");
                                            Messaging.CasXlog(loggermessage);
                                        }
                                    }
                                }
                            }
                            //todo Баланс снять
                            if (clicked.getItemMeta().getDisplayName().equals(CasinoX.plugin.str_multiplywithdrawname.replaceAll("%withcountalm", "32").replaceAll("%withcountgold", "1600"))) {
                                if (Balance.get(player.getName()) >= 1600) {
                                    if (hasAvaliableSlot(player, 1)) {
                                        ItemStack addIT = new ItemStack(CasinoX.plugin.withmoney_id, 32);
                                        player.getInventory().addItem(addIT);
                                        Balance.put(player.getName(), Balance.get(player.getName()) - 1600);
                                        player.closeInventory();
                                        player.openInventory(getInv(player, 11000));
                                        //логирование
                                        if (CasinoX.plugin.b_logger) {
                                            String loggermessage = (" [WITHDRAW] " + player.getName() + " снял со счета 1600 !");
                                            Messaging.CasXlog(loggermessage);
                                        }
                                    } else {
                                        player.sendMessage(CasinoX.plugin.str_fullinventory);
                                    }
                                } else {
                                    player.sendMessage(CasinoX.plugin.str_donthavebalance.replaceFirst("%dntbalance", "1600"));
                                    player.sendMessage(CasinoX.plugin.str_balancelore.replaceFirst("%balance", String.valueOf(Balance.get(player.getName()))));
                                    player.closeInventory();
                                }
                            }
                            if (clicked.getItemMeta().getDisplayName().equals(CasinoX.plugin.str_multiplywithdrawname.replaceAll("%withcountalm", "64").replaceAll("%withcountgold", "3200"))) {
                                if (Balance.get(player.getName()) >= 3200) {
                                    if (hasAvaliableSlot(player, 1)) {
                                        ItemStack addIT = new ItemStack(CasinoX.plugin.withmoney_id, 64);
                                        player.getInventory().addItem(addIT);
                                        Balance.put(player.getName(), Balance.get(player.getName()) - 3200);
                                        player.closeInventory();
                                        player.openInventory(getInv(player, 11000));
                                        //логирование
                                        if (CasinoX.plugin.b_logger) {
                                            String loggermessage = (" [WITHDRAW] " + player.getName() + " снял со счета 3200!");
                                            Messaging.CasXlog(loggermessage);
                                        }
                                    } else {
                                        player.sendMessage(CasinoX.plugin.str_fullinventory);
                                    }
                                } else {
                                    player.sendMessage(CasinoX.plugin.str_donthavebalance.replaceFirst("%dntbalance", "3200"));
                                    player.sendMessage(CasinoX.plugin.str_balancelore.replaceFirst("%balance", String.valueOf(Balance.get(player.getName()))));
                                    player.closeInventory();
                                }
                            }
                            //todo играть ставка ВЫШЕ
                            if (clicked.getItemMeta().getDisplayName().equals(CasinoX.plugin.str_bethiname)) {
                                if (Balance.get(player.getName()) != 0) {
                                    if (Balance.get(player.getName()) >= Bet.get(player.getName())) {
                                        int random = (new Random()).nextInt(10000);
                                        player.sendMessage(CasinoX.plugin.str_randommessage.replaceFirst("%randnum", String.valueOf(random)));

                                        double wincodouble = (((double) Bet.get(player.getName()) / 100) * (Modify.get(player.getName()) * 100)) - (double) Bet.get(player.getName());
                                        int wincount = (int) wincodouble;
                                        double betlodouble = (4750 / ((Modify.get(player.getName()) * 100) / 2)) * 100;
                                        int betlo = (int) betlodouble;
                                        int bethi = 10000 - betlo;

                                        if (random >= bethi) {
                                            player.sendMessage(CasinoX.plugin.str_winnermessage.replaceFirst("%cols", String.valueOf(wincount)));
                                            Balance.put(player.getName(), Balance.get(player.getName()) + wincount);
                                            //логирование
                                            if (CasinoX.plugin.b_logger) {
                                                String loggermessage = (" [WIN] " + player.getName() + " выиграл " + String.valueOf(wincount) + " ! ставка HI ВЫШЕ " + random + " >= " + bethi + ". Mod " + Modify.get(player.getName()));
                                                Messaging.CasXlog(loggermessage);
                                            }
                                            PlayerWin.put(player.getName(), PlayerWin.get(player.getName()) + wincount);
                                            BalanceBank.put("won_by_user", BalanceBank.get("won_by_user") + wincount);

                                        } else {
                                            player.sendMessage(CasinoX.plugin.str_wastedmessage.replaceFirst("%cols", String.valueOf(Bet.get(player.getName()))));
                                            Balance.put(player.getName(), Balance.get(player.getName()) - Bet.get(player.getName()));
                                            //логирование
                                            if (CasinoX.plugin.b_logger) {
                                                String loggermessage = (" [LOSE] " + player.getName() + " проиграл " + String.valueOf(Bet.get(player.getName())) + " ! ставка HI ВЫШЕ " + random + " < " + bethi + ". Mod " + Modify.get(player.getName()));
                                                Messaging.CasXlog(loggermessage);
                                            }
                                            PlayerWager.put(player.getName(), PlayerWager.get(player.getName()) + Bet.get(player.getName()));
                                            BalanceBank.put("wagered", BalanceBank.get("wagered") + Bet.get(player.getName()));
                                        }
                                        player.sendMessage(CasinoX.plugin.str_balancelore.replaceFirst("%balance", String.valueOf(Balance.get(player.getName()))));
                                        player.closeInventory();
                                        player.openInventory(getInv(player, random));
                                    } else {
                                        if (Balance.get(player.getName()) > 0) {
                                            Bet.put(player.getName(), Balance.get(player.getName()));
                                        } else {
                                            Bet.put(player.getName(), 1);
                                        }
                                        player.sendMessage(CasinoX.plugin.str_donthavebalance.replaceFirst("%dntbalance", String.valueOf(Bet.get(player.getName()))));
                                        player.sendMessage(CasinoX.plugin.str_balancelore.replaceFirst("%balance", String.valueOf(Balance.get(player.getName()))));
                                        player.closeInventory();
                                    }
                                } else {
                                    player.sendMessage(CasinoX.plugin.str_donthavebalance.replaceFirst("%dntbalance", String.valueOf(Bet.get(player.getName()))));
                                    player.closeInventory();
                                }
                            }
                            //todo играть ставка НИЖЕ
                            if (clicked.getItemMeta().getDisplayName().equals(CasinoX.plugin.str_betloname)) {
                                if (Balance.get(player.getName()) != 0) {
                                    if (Balance.get(player.getName()) >= Bet.get(player.getName())) {
                                        int random = (new Random()).nextInt(10000);
                                        player.sendMessage(CasinoX.plugin.str_randommessage.replaceFirst("%randnum", String.valueOf(random)));

                                        double wincodouble = (((double) Bet.get(player.getName()) / 100) * (Modify.get(player.getName()) * 100)) - (double) Bet.get(player.getName());
                                        int wincount = (int) wincodouble;

                                        double betlodouble = (4750 / ((Modify.get(player.getName()) * 100) / 2)) * 100;
                                        int betlo = (int) betlodouble;

                                        if (random <= betlo) {
                                            player.sendMessage(CasinoX.plugin.str_winnermessage.replaceFirst("%cols", String.valueOf(wincount)));
                                            Balance.put(player.getName(), Balance.get(player.getName()) + wincount);
                                            //логирование
                                            if (CasinoX.plugin.b_logger) {
                                                String loggermessage = (" [WIN] " + player.getName() + " выиграл " + String.valueOf(wincount) + " ! ставка LO НИЖЕ " + random + " <= " + betlo + ". Mod " + Modify.get(player.getName()));
                                                Messaging.CasXlog(loggermessage);
                                            }
                                            PlayerWin.put(player.getName(), PlayerWin.get(player.getName()) + wincount);
                                            BalanceBank.put("won_by_user", BalanceBank.get("won_by_user") + wincount);
                                        } else {
                                            player.sendMessage(CasinoX.plugin.str_wastedmessage.replaceFirst("%cols", String.valueOf(Bet.get(player.getName()))));
                                            Balance.put(player.getName(), Balance.get(player.getName()) - Bet.get(player.getName()));
                                            //логирование
                                            if (CasinoX.plugin.b_logger) {
                                                String loggermessage = (" [LOSE] " + player.getName() + " проиграл " + String.valueOf(Bet.get(player.getName())) + " ! ставка LO НИЖЕ " + random + " > " + betlo + ". Mod " + Modify.get(player.getName()));
                                                Messaging.CasXlog(loggermessage);
                                            }
                                            PlayerWager.put(player.getName(), PlayerWager.get(player.getName()) + Bet.get(player.getName()));
                                            BalanceBank.put("wagered", BalanceBank.get("wagered") + Bet.get(player.getName()));
                                        }
                                        player.sendMessage(CasinoX.plugin.str_balancelore.replaceFirst("%balance", String.valueOf(Balance.get(player.getName()))));
                                        player.closeInventory();
                                        player.openInventory(getInv(player, random));
                                    } else {
                                        if (Balance.get(player.getName()) > 0) {
                                            Bet.put(player.getName(), Balance.get(player.getName()));
                                        } else {
                                            Bet.put(player.getName(), 1);
                                        }
                                        player.sendMessage(CasinoX.plugin.str_donthavebalance.replaceFirst("%dntbalance", String.valueOf(Bet.get(player.getName()))));
                                        player.sendMessage(CasinoX.plugin.str_balancelore.replaceFirst("%balance", String.valueOf(Balance.get(player.getName()))));
                                        player.closeInventory();
                                    }
                                } else {
                                    player.sendMessage(CasinoX.plugin.str_donthavebalance.replaceFirst("%dntbalance", String.valueOf(Bet.get(player.getName()))));
                                    player.closeInventory();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public boolean hasAvaliableSlot(Player player,int howmanyclear){
        Inventory inv = player.getInventory();
        int check=0;
        for (ItemStack item: inv.getContents()) {
            if(item == null) {
                check++;
            }
        }
        if (check>howmanyclear){
            return true;
        }else{
            return false;
        }
    }

    public Inventory getInv(Player player, int random){
        //if (DatabaseConnection.isConnected()) {
            if (!Modify.containsKey(player.getName())) {
                Modify.put(player.getName(), 2.0);
            }
            if (!Bet.containsKey(player.getName())) {
                Bet.put(player.getName(), 1);
            }
            if (!LastNumber.containsKey(player.getName())) {
                LastNumber.put(player.getName(), 11000);
            }
            if (random != 11000){
                LastNumber.put(player.getName(), random);
            }
        if (!PlayerWin.containsKey(player.getName())) {
            PlayerWin.put(player.getName(), 0);
        }
        if (!PlayerWager.containsKey(player.getName())) {
            PlayerWager.put(player.getName(), 0);
        }
            double winchance = 4750 / ((Modify.get(player.getName()) * 100) / 2);
            double wincodouble = ((((double)Bet.get(player.getName()) / 100) * (Modify.get(player.getName()) * 100))) - (double)Bet.get(player.getName());
            int wincount = (int) wincodouble;

            double betlodouble = (4750 / ((Modify.get(player.getName()) * 100) / 2)) * 100;
            int betlo = (int) betlodouble;
            int bethi = 10000 - betlo;

            String pattern = "##0.0";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
            String str_winchance = decimalFormat.format(winchance);
            //TODO циферки
            this.updInv = Bukkit.createInventory(player, 54, CasinoX.plugin.str_multiplyinvname);
            player.openInventory(this.updInv);
            int int_num1 = 0;
            int int_num2 = 0;
            int int_num3 = 0;
            int int_num4 = 0;
            int int_num5 = 0;
            if (LastNumber.get(player.getName()).equals(11000)) {
                ItemStack num1 = new ItemStack(CasinoX.plugin.int_num_id + int_num1, 0);
                ItemMeta IMnum1 = num1.getItemMeta();
                IMnum1.setDisplayName("§b" + int_num1);
                ItemStack num2 = new ItemStack(CasinoX.plugin.int_num_id + int_num2, 0);
                ItemMeta IMnum2 = num2.getItemMeta();
                IMnum2.setDisplayName("§b" + int_num2);
                ItemStack num3 = new ItemStack(CasinoX.plugin.int_num_id + int_num3, 0);
                ItemMeta IMnum3 = num3.getItemMeta();
                IMnum3.setDisplayName("§b" + int_num3);
                ItemStack num4 = new ItemStack(CasinoX.plugin.int_num_id + int_num4, 0);
                ItemMeta IMnum4 = num4.getItemMeta();
                IMnum4.setDisplayName("§b" + int_num4);
                ItemStack num5 = new ItemStack(CasinoX.plugin.int_num_id + int_num5, 0);
                ItemMeta IMnum5 = num5.getItemMeta();
                IMnum5.setDisplayName("§b" + int_num5);
                num1.setItemMeta(IMnum1);
                num2.setItemMeta(IMnum2);
                num3.setItemMeta(IMnum3);
                num4.setItemMeta(IMnum4);
                num5.setItemMeta(IMnum5);
                this.updInv.setItem(13, num1);
                this.updInv.setItem(14, num2);
                this.updInv.setItem(15, num3);
                this.updInv.setItem(16, num4);
                this.updInv.setItem(17, num5);
            } else {
                String str_random = String.valueOf(LastNumber.get(player.getName()));
                int str_random_lenght = str_random.length();
                if (str_random_lenght == 5){
                    char num1 = str_random.charAt(0);
                    char num2 = str_random.charAt(1);
                    char num3 = str_random.charAt(2);
                    char num4 = str_random.charAt(3);
                    char num5 = str_random.charAt(4);
                    int_num1 = Integer.parseInt(String.valueOf(num1));
                    int_num2 = Integer.parseInt(String.valueOf(num2));
                    int_num3 = Integer.parseInt(String.valueOf(num3));
                    int_num4 = Integer.parseInt(String.valueOf(num4));
                    int_num5 = Integer.parseInt(String.valueOf(num5));
                } else if (str_random_lenght == 4) {
                    char num2 = str_random.charAt(0);
                    char num3 = str_random.charAt(1);
                    char num4 = str_random.charAt(2);
                    char num5 = str_random.charAt(3);
                    int_num1 = 0;
                    int_num2 = Integer.parseInt(String.valueOf(num2));
                    int_num3 = Integer.parseInt(String.valueOf(num3));
                    int_num4 = Integer.parseInt(String.valueOf(num4));
                    int_num5 = Integer.parseInt(String.valueOf(num5));
                } else if (str_random_lenght == 3) {
                    char num3 = str_random.charAt(0);
                    char num4 = str_random.charAt(1);
                    char num5 = str_random.charAt(2);
                    int_num1 = 0;
                    int_num2 = 0;
                    int_num3 = Integer.parseInt(String.valueOf(num3));
                    int_num4 = Integer.parseInt(String.valueOf(num4));
                    int_num5 = Integer.parseInt(String.valueOf(num5));
                } else if (str_random_lenght == 2){
                    char num4 = str_random.charAt(0);
                    char num5 = str_random.charAt(1);
                    int_num1 = 0;
                    int_num2 = 0;
                    int_num3 = 0;
                    int_num4 = Integer.parseInt(String.valueOf(num4));
                    int_num5 = Integer.parseInt(String.valueOf(num5));
                } else if (str_random_lenght == 1){
                    char num5 = str_random.charAt(0);
                    int_num1 = 0;
                    int_num2 = 0;
                    int_num3 = 0;
                    int_num4 = 0;
                    int_num5 = Integer.parseInt(String.valueOf(num5));
                }
                ItemStack num1 = new ItemStack(CasinoX.plugin.int_num_id + int_num1, 0);
                ItemMeta IMnum1 = num1.getItemMeta();
                IMnum1.setDisplayName("§b" + int_num1);
                ItemStack num2 = new ItemStack(CasinoX.plugin.int_num_id + int_num2, 0);
                ItemMeta IMnum2 = num2.getItemMeta();
                IMnum2.setDisplayName("§b" + int_num2);
                ItemStack num3 = new ItemStack(CasinoX.plugin.int_num_id + int_num3, 0);
                ItemMeta IMnum3 = num3.getItemMeta();
                IMnum3.setDisplayName("§b" + int_num3);
                ItemStack num4 = new ItemStack(CasinoX.plugin.int_num_id + int_num4, 0);
                ItemMeta IMnum4 = num4.getItemMeta();
                IMnum4.setDisplayName("§b" + int_num4);
                ItemStack num5 = new ItemStack(CasinoX.plugin.int_num_id + int_num5, 0);
                ItemMeta IMnum5 = num5.getItemMeta();
                IMnum5.setDisplayName("§b" + int_num5);
                num1.setItemMeta(IMnum1);
                num2.setItemMeta(IMnum2);
                num3.setItemMeta(IMnum3);
                num4.setItemMeta(IMnum4);
                num5.setItemMeta(IMnum5);
                this.updInv.setItem(13, num1);
                this.updInv.setItem(14, num2);
                this.updInv.setItem(15, num3);
                this.updInv.setItem(16, num4);
                this.updInv.setItem(17, num5);
            }
            //Кнопка TODO ставка НИЖЕ
            ItemStack betLO = new ItemStack(CasinoX.plugin.int_betlo_id, 0);
            ItemMeta IMbetLO = betLO.getItemMeta();
            IMbetLO.setDisplayName(CasinoX.plugin.str_betloname);
            ArrayList<String> loreIMbetLO = new ArrayList<String>();
            loreIMbetLO.add(CasinoX.plugin.str_betlolore.replaceFirst("%int_betlo", String.valueOf(betlo)));
            loreIMbetLO.add(CasinoX.plugin.str_multiplybetlore.replaceFirst("%int_bet", String.valueOf(Bet.get(player.getName()))));
            loreIMbetLO.add(CasinoX.plugin.str_multiplymodsname + " " + Modify.get(player.getName()));
            loreIMbetLO.add(CasinoX.plugin.str_multiplymodslore.replaceFirst("%winchance", str_winchance));
            loreIMbetLO.add(CasinoX.plugin.str_winlore.replaceFirst("%wincount", String.valueOf(wincount)));
            loreIMbetLO.add(CasinoX.plugin.str_wastelore.replaceFirst("%wastecount", String.valueOf(Bet.get(player.getName()))));
            IMbetLO.setLore(loreIMbetLO);
            betLO.setItemMeta(IMbetLO);
            this.updInv.setItem(25, betLO);
            //Кнопка TODO ставка ВЫШЕ
            ItemStack betHI = new ItemStack(CasinoX.plugin.int_bethi_id, 0);
            ItemMeta IMbetHI = betHI.getItemMeta();
            IMbetHI.setDisplayName(CasinoX.plugin.str_bethiname);
            ArrayList<String> loreIMbetHI = new ArrayList<String>();
            loreIMbetHI.add(CasinoX.plugin.str_bethilore.replaceFirst("%int_bethi", String.valueOf(bethi)));
            loreIMbetHI.add(CasinoX.plugin.str_multiplybetlore.replaceFirst("%int_bet", String.valueOf(Bet.get(player.getName()))));
            loreIMbetHI.add(CasinoX.plugin.str_multiplymodsname + " " + Modify.get(player.getName()));
            loreIMbetHI.add(CasinoX.plugin.str_multiplymodslore.replaceFirst("%winchance", str_winchance));
            loreIMbetHI.add(CasinoX.plugin.str_winlore.replaceFirst("%wincount", String.valueOf(wincount)));
            loreIMbetHI.add(CasinoX.plugin.str_wastelore.replaceFirst("%wastecount", String.valueOf(Bet.get(player.getName()))));
            IMbetHI.setLore(loreIMbetHI);
            betHI.setItemMeta(IMbetHI);
            this.updInv.setItem(23, betHI);

            //Кнопка TODO ставка
            ItemStack bett1 = new ItemStack(CasinoX.plugin.int_bet_id, 0);
            ItemMeta IMbett1 = bett1.getItemMeta();
            IMbett1.setDisplayName(CasinoX.plugin.str_multiplybetlore.replaceFirst("%int_bet", String.valueOf(Bet.get(player.getName()))));
            ArrayList<String> loreIMbett1 = new ArrayList<String>();
            loreIMbett1.add(CasinoX.plugin.str_multiplymodsname + " " + Modify.get(player.getName()));
            loreIMbett1.add(CasinoX.plugin.str_multiplymodslore.replaceFirst("%winchance", str_winchance));
            loreIMbett1.add(CasinoX.plugin.str_winlore.replaceFirst("%wincount", String.valueOf(wincount)));
            loreIMbett1.add(CasinoX.plugin.str_wastelore.replaceFirst("%wastecount", String.valueOf(Bet.get(player.getName()))));
            IMbett1.setLore(loreIMbett1);
            bett1.setItemMeta(IMbett1);
            this.updInv.setItem(36, bett1);

            //кнопка TODO увеличить ставку
            ItemStack bettUP1 = new ItemStack(CasinoX.plugin.int_betinc_id, 0);
            ItemMeta IMbettUP1 = bettUP1.getItemMeta();
            IMbettUP1.setDisplayName(CasinoX.plugin.str_multiplybetupname.replaceFirst("%int_betup", "1"));
            ArrayList<String> loreIMbettUP1 = new ArrayList<String>();
            loreIMbettUP1.add(CasinoX.plugin.str_multiplybetlore.replaceFirst("%int_bet", String.valueOf(Bet.get(player.getName()))));
            loreIMbettUP1.add(CasinoX.plugin.str_multiplymodsname + " " + Modify.get(player.getName()));
            loreIMbettUP1.add(CasinoX.plugin.str_multiplymodslore.replaceFirst("%winchance", str_winchance));
            loreIMbettUP1.add(CasinoX.plugin.str_winlore.replaceFirst("%wincount", String.valueOf(wincount)));
            loreIMbettUP1.add(CasinoX.plugin.str_wastelore.replaceFirst("%wastecount", String.valueOf(Bet.get(player.getName()))));
            IMbettUP1.setLore(loreIMbettUP1);
            bettUP1.setItemMeta(IMbettUP1);
            this.updInv.setItem(37, bettUP1);

            ItemStack bettUP2 = new ItemStack(CasinoX.plugin.int_betinc_id, 0);
            ItemMeta IMbettUP2 = bettUP2.getItemMeta();
            IMbettUP2.setDisplayName(CasinoX.plugin.str_multiplybetupname.replaceFirst("%int_betup", " x2"));
            ArrayList<String> loreIMbettUP2 = new ArrayList<String>();
            loreIMbettUP2.add(CasinoX.plugin.str_multiplybetlore.replaceFirst("%int_bet", String.valueOf(Bet.get(player.getName()))));
            loreIMbettUP2.add(CasinoX.plugin.str_multiplymodsname + " " + Modify.get(player.getName()));
            loreIMbettUP2.add(CasinoX.plugin.str_multiplymodslore.replaceFirst("%winchance", str_winchance));
            loreIMbettUP2.add(CasinoX.plugin.str_winlore.replaceFirst("%wincount", String.valueOf(wincount)));
            loreIMbettUP2.add(CasinoX.plugin.str_wastelore.replaceFirst("%wastecount", String.valueOf(Bet.get(player.getName()))));
            IMbettUP2.setLore(loreIMbettUP2);
            bettUP2.setItemMeta(IMbettUP2);
            this.updInv.setItem(38, bettUP2);

            //кнопка TODO уменьшить ставку
            ItemStack bettDOWN1 = new ItemStack(CasinoX.plugin.int_betdec_id, 0);
            ItemMeta IMbettDOWN1 = bettDOWN1.getItemMeta();
            IMbettDOWN1.setDisplayName(CasinoX.plugin.str_multiplybetdownname.replaceFirst("%int_betdown", "1"));
            ArrayList<String> loreIMbettDOWN1 = new ArrayList<String>();
            loreIMbettDOWN1.add(CasinoX.plugin.str_multiplybetlore.replaceFirst("%int_bet", String.valueOf(Bet.get(player.getName()))));
            loreIMbettDOWN1.add(CasinoX.plugin.str_multiplymodsname + " " + Modify.get(player.getName()));
            loreIMbettDOWN1.add(CasinoX.plugin.str_multiplymodslore.replaceFirst("%winchance", str_winchance));
            loreIMbettDOWN1.add(CasinoX.plugin.str_winlore.replaceFirst("%wincount", String.valueOf(wincount)));
            loreIMbettDOWN1.add(CasinoX.plugin.str_wastelore.replaceFirst("%wastecount", String.valueOf(Bet.get(player.getName()))));
            IMbettDOWN1.setLore(loreIMbettDOWN1);
            bettDOWN1.setItemMeta(IMbettDOWN1);
            this.updInv.setItem(46, bettDOWN1);

            ItemStack bettDOWN2 = new ItemStack(CasinoX.plugin.int_betdec_id, 0);
            ItemMeta IMbettDOWN2 = bettDOWN2.getItemMeta();
            IMbettDOWN2.setDisplayName(CasinoX.plugin.str_multiplybetdownname.replaceFirst("%int_betdown", " /2"));
            ArrayList<String> loreIMbettDOWN2 = new ArrayList<String>();
            loreIMbettDOWN2.add(CasinoX.plugin.str_multiplybetlore.replaceFirst("%int_bet", String.valueOf(Bet.get(player.getName()))));
            loreIMbettDOWN2.add(CasinoX.plugin.str_multiplymodsname + " " + Modify.get(player.getName()));
            loreIMbettDOWN2.add(CasinoX.plugin.str_multiplymodslore.replaceFirst("%winchance", str_winchance));
            loreIMbettDOWN2.add(CasinoX.plugin.str_winlore.replaceFirst("%wincount", String.valueOf(wincount)));
            loreIMbettDOWN2.add(CasinoX.plugin.str_wastelore.replaceFirst("%wastecount", String.valueOf(Bet.get(player.getName()))));
            IMbettDOWN2.setLore(loreIMbettDOWN2);
            bettDOWN2.setItemMeta(IMbettDOWN2);
            this.updInv.setItem(47, bettDOWN2);

            //Кнопка TODO модификатор
            ItemStack mods1 = new ItemStack(CasinoX.plugin.int_mods_id, 0);
            ItemMeta IMmods1 = mods1.getItemMeta();
            IMmods1.setDisplayName(CasinoX.plugin.str_multiplymodsname + " " + Modify.get(player.getName()));
            ArrayList<String> loreIMmods1 = new ArrayList<String>();
            loreIMmods1.add(CasinoX.plugin.str_multiplymodsname + " " + Modify.get(player.getName()));
            loreIMmods1.add(CasinoX.plugin.str_multiplymodslore.replaceFirst("%winchance", str_winchance));
            IMmods1.setLore(loreIMmods1);
            mods1.setItemMeta(IMmods1);
            this.updInv.setItem(18, mods1);

            //кнопка TODO увеличить модификатор
            ItemStack modsUP1 = new ItemStack(CasinoX.plugin.int_modsinc_id, 0);
            ItemMeta IMmodsUP1 = modsUP1.getItemMeta();
            IMmodsUP1.setDisplayName(CasinoX.plugin.str_multiplymodupname.replaceFirst("%int_modup", "0.1"));
            ArrayList<String> loreIMmodsUP1 = new ArrayList<String>();
            loreIMmodsUP1.add(CasinoX.plugin.str_multiplymodsname + " " + Modify.get(player.getName()));
            loreIMmodsUP1.add(CasinoX.plugin.str_multiplymodslore.replaceFirst("%winchance", str_winchance));
            IMmodsUP1.setLore(loreIMmodsUP1);
            modsUP1.setItemMeta(IMmodsUP1);
            this.updInv.setItem(19, modsUP1);

            ItemStack modsUP2 = new ItemStack(CasinoX.plugin.int_modsinc_id, 0);
            ItemMeta IMmodsUP2 = modsUP2.getItemMeta();
            IMmodsUP2.setDisplayName(CasinoX.plugin.str_multiplymodupname.replaceFirst("%int_modup", "0.5"));
            ArrayList<String> loreIMmodsUP2 = new ArrayList<String>();
            loreIMmodsUP2.add(CasinoX.plugin.str_multiplymodsname + " " + Modify.get(player.getName()));
            loreIMmodsUP2.add(CasinoX.plugin.str_multiplymodslore.replaceFirst("%winchance", str_winchance));
            IMmodsUP2.setLore(loreIMmodsUP2);
            modsUP2.setItemMeta(IMmodsUP2);
            this.updInv.setItem(20, modsUP2);

            //кнопка TODO уменьшить модификатор
            ItemStack modsDOWN1 = new ItemStack(CasinoX.plugin.int_modsdec_id, 0);
            ItemMeta IMmodsDOWN1 = modsDOWN1.getItemMeta();
            IMmodsDOWN1.setDisplayName(CasinoX.plugin.str_multiplymoddownname.replaceFirst("%int_moddown", "0.1"));
            ArrayList<String> loreIMmodsDOWN1 = new ArrayList<String>();
            loreIMmodsDOWN1.add(CasinoX.plugin.str_multiplymodsname + " " + Modify.get(player.getName()));
            loreIMmodsDOWN1.add(CasinoX.plugin.str_multiplymodslore.replaceFirst("%winchance", str_winchance));
            IMmodsDOWN1.setLore(loreIMmodsDOWN1);
            modsDOWN1.setItemMeta(IMmodsDOWN1);
            this.updInv.setItem(28, modsDOWN1);

            ItemStack modsDOWN2 = new ItemStack(CasinoX.plugin.int_modsdec_id, 0);
            ItemMeta IMmodsDOWN2 = modsDOWN2.getItemMeta();
            IMmodsDOWN2.setDisplayName(CasinoX.plugin.str_multiplymoddownname.replaceFirst("%int_moddown", "0.5"));
            ArrayList<String> loreIMmodsDOWN2 = new ArrayList<String>();
            loreIMmodsDOWN2.add(CasinoX.plugin.str_multiplymodsname + " " + Modify.get(player.getName()));
            loreIMmodsDOWN2.add(CasinoX.plugin.str_multiplymodslore.replaceFirst("%winchance", str_winchance));
            IMmodsDOWN2.setLore(loreIMmodsDOWN2);
            modsDOWN2.setItemMeta(IMmodsDOWN2);
            this.updInv.setItem(29, modsDOWN2);

            //кнопки TODO баланса пополнить
            ItemStack balance1 = new ItemStack(CasinoX.plugin.depomoney_ico, 0);
            ItemMeta IMbalance1 = balance1.getItemMeta();
            IMbalance1.setDisplayName(CasinoX.plugin.str_multiplydepositname.replaceFirst("%depocount", "4"));
            ArrayList<String> loreIMbalance1 = new ArrayList<String>();
            loreIMbalance1.add(CasinoX.plugin.str_balancelore.replaceFirst("%balance", String.valueOf(Balance.get(player.getName()))));
            IMbalance1.setLore(loreIMbalance1);
            balance1.setItemMeta(IMbalance1);
            this.updInv.setItem(0, balance1);

            ItemStack balance2 = new ItemStack(CasinoX.plugin.depomoney_ico, 0);
            ItemMeta IMbalance2 = balance2.getItemMeta();
            IMbalance2.setDisplayName(CasinoX.plugin.str_multiplydepositname.replaceFirst("%depocount", "32"));
            ArrayList<String> loreIMbalance2 = new ArrayList<String>();
            loreIMbalance2.add(CasinoX.plugin.str_balancelore.replaceFirst("%balance", String.valueOf(Balance.get(player.getName()))));
            IMbalance2.setLore(loreIMbalance2);
            balance2.setItemMeta(IMbalance2);
            this.updInv.setItem(1, balance2);

            ItemStack balance3 = new ItemStack(CasinoX.plugin.depomoney_ico, 0);
            ItemMeta IMbalance3 = balance3.getItemMeta();
            IMbalance3.setDisplayName(CasinoX.plugin.str_multiplydepositname.replaceFirst("%depocount", "64"));
            ArrayList<String> loreIMbalance3 = new ArrayList<String>();
            loreIMbalance3.add(CasinoX.plugin.str_balancelore.replaceFirst("%balance", String.valueOf(Balance.get(player.getName()))));
            IMbalance3.setLore(loreIMbalance3);
            balance3.setItemMeta(IMbalance3);
            this.updInv.setItem(2, balance3);
            //кнопки TODO баланс снять
            ItemStack balanceW1 = new ItemStack(CasinoX.plugin.withmoney_ico, 0);
            ItemMeta IMbalanceW1 = balanceW1.getItemMeta();
            IMbalanceW1.setDisplayName(CasinoX.plugin.str_multiplywithdrawname.replaceAll("%withcountalm", "32").replaceAll("%withcountgold", "1600"));
            ArrayList<String> loreIMbalanceW1 = new ArrayList<String>();
            loreIMbalanceW1.add(CasinoX.plugin.str_balancelore.replaceFirst("%balance", String.valueOf(Balance.get(player.getName()))));
            IMbalanceW1.setLore(loreIMbalanceW1);
            balanceW1.setItemMeta(IMbalanceW1);
            this.updInv.setItem(9, balanceW1);

            ItemStack balanceW2 = new ItemStack(CasinoX.plugin.withmoney_ico, 0);
            ItemMeta IMbalanceW2 = balanceW2.getItemMeta();
            IMbalanceW2.setDisplayName(CasinoX.plugin.str_multiplywithdrawname.replaceAll("%withcountalm", "64").replaceAll("%withcountgold", "3200"));
            ArrayList<String> loreIMbalanceW2 = new ArrayList<String>();
            loreIMbalanceW2.add(CasinoX.plugin.str_balancelore.replaceFirst("%balance", String.valueOf(Balance.get(player.getName()))));
            IMbalanceW2.setLore(loreIMbalanceW2);
            balanceW2.setItemMeta(IMbalanceW2);
            this.updInv.setItem(10, balanceW2);

            //кнопки TODO выход
            ItemStack exit = new ItemStack(CasinoX.plugin.int_exit_id, 0);
            ItemMeta IMexit = exit.getItemMeta();
            IMexit.setDisplayName("§4ВЫХОД");
            exit.setItemMeta(IMexit);
            this.updInv.setItem(53, exit);
        //}
        Inventory inv = this.updInv;
        return inv;
    }
}
