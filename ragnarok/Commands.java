package ragnarok;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static ragnarok.CasinoX.*;


public class Commands
        implements Listener {

    public static CasinoX plugin;
    private Inventory MultiplyInventory = null;



    @EventHandler
    public void onCommands(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        String command = e.getMessage().toLowerCase();
        if (command.equals("/casx")) {
            e.setCancelled(true);
            //if (DatabaseConnection.isConnected()) {
                if (!Modify.containsKey(player.getName())) {
                    Modify.put(player.getName(), 2.0);
                }
                if (!Bet.containsKey(player.getName())) {
                    Bet.put(player.getName(), 1);
                }
                if (!Balance.containsKey(player.getName())) {
                    Balance.put(player.getName(), 0);
                }
                if (!LastNumber.containsKey(player.getName())) {
                    LastNumber.put(player.getName(), 11000);
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
                this.MultiplyInventory = Bukkit.createInventory(player, 54, CasinoX.plugin.str_multiplyinvname);
                player.openInventory(this.MultiplyInventory);
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
                    this.MultiplyInventory.setItem(13, num1);
                    this.MultiplyInventory.setItem(14, num2);
                    this.MultiplyInventory.setItem(15, num3);
                    this.MultiplyInventory.setItem(16, num4);
                    this.MultiplyInventory.setItem(17, num5);
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
                    this.MultiplyInventory.setItem(13, num1);
                    this.MultiplyInventory.setItem(14, num2);
                    this.MultiplyInventory.setItem(15, num3);
                    this.MultiplyInventory.setItem(16, num4);
                    this.MultiplyInventory.setItem(17, num5);
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
                this.MultiplyInventory.setItem(25, betLO);
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
                this.MultiplyInventory.setItem(23, betHI);

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
                this.MultiplyInventory.setItem(36, bett1);

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
                this.MultiplyInventory.setItem(37, bettUP1);

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
                this.MultiplyInventory.setItem(38, bettUP2);

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
                this.MultiplyInventory.setItem(46, bettDOWN1);

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
                this.MultiplyInventory.setItem(47, bettDOWN2);

                //Кнопка TODO модификатор
                ItemStack mods1 = new ItemStack(CasinoX.plugin.int_mods_id, 0);
                ItemMeta IMmods1 = mods1.getItemMeta();
                IMmods1.setDisplayName(CasinoX.plugin.str_multiplymodsname + " " + Modify.get(player.getName()));
                ArrayList<String> loreIMmods1 = new ArrayList<String>();
                loreIMmods1.add(CasinoX.plugin.str_multiplymodsname + " " + Modify.get(player.getName()));
                loreIMmods1.add(CasinoX.plugin.str_multiplymodslore.replaceFirst("%winchance", str_winchance));
                IMmods1.setLore(loreIMmods1);
                mods1.setItemMeta(IMmods1);
                this.MultiplyInventory.setItem(18, mods1);

                //кнопка TODO увеличить модификатор
                ItemStack modsUP1 = new ItemStack(CasinoX.plugin.int_modsinc_id, 0);
                ItemMeta IMmodsUP1 = modsUP1.getItemMeta();
                IMmodsUP1.setDisplayName(CasinoX.plugin.str_multiplymodupname.replaceFirst("%int_modup", "0.1"));
                ArrayList<String> loreIMmodsUP1 = new ArrayList<String>();
                loreIMmodsUP1.add(CasinoX.plugin.str_multiplymodsname + " " + Modify.get(player.getName()));
                loreIMmodsUP1.add(CasinoX.plugin.str_multiplymodslore.replaceFirst("%winchance", str_winchance));
                IMmodsUP1.setLore(loreIMmodsUP1);
                modsUP1.setItemMeta(IMmodsUP1);
                this.MultiplyInventory.setItem(19, modsUP1);

                ItemStack modsUP2 = new ItemStack(CasinoX.plugin.int_modsinc_id, 0);
                ItemMeta IMmodsUP2 = modsUP2.getItemMeta();
                IMmodsUP2.setDisplayName(CasinoX.plugin.str_multiplymodupname.replaceFirst("%int_modup", "0.5"));
                ArrayList<String> loreIMmodsUP2 = new ArrayList<String>();
                loreIMmodsUP2.add(CasinoX.plugin.str_multiplymodsname + " " + Modify.get(player.getName()));
                loreIMmodsUP2.add(CasinoX.plugin.str_multiplymodslore.replaceFirst("%winchance", str_winchance));
                IMmodsUP2.setLore(loreIMmodsUP2);
                modsUP2.setItemMeta(IMmodsUP2);
                this.MultiplyInventory.setItem(20, modsUP2);

                //кнопка TODO уменьшить модификатор
                ItemStack modsDOWN1 = new ItemStack(CasinoX.plugin.int_modsdec_id, 0);
                ItemMeta IMmodsDOWN1 = modsDOWN1.getItemMeta();
                IMmodsDOWN1.setDisplayName(CasinoX.plugin.str_multiplymoddownname.replaceFirst("%int_moddown", "0.1"));
                ArrayList<String> loreIMmodsDOWN1 = new ArrayList<String>();
                loreIMmodsDOWN1.add(CasinoX.plugin.str_multiplymodsname + " " + Modify.get(player.getName()));
                loreIMmodsDOWN1.add(CasinoX.plugin.str_multiplymodslore.replaceFirst("%winchance", str_winchance));
                IMmodsDOWN1.setLore(loreIMmodsDOWN1);
                modsDOWN1.setItemMeta(IMmodsDOWN1);
                this.MultiplyInventory.setItem(28, modsDOWN1);

                ItemStack modsDOWN2 = new ItemStack(CasinoX.plugin.int_modsdec_id, 0);
                ItemMeta IMmodsDOWN2 = modsDOWN2.getItemMeta();
                IMmodsDOWN2.setDisplayName(CasinoX.plugin.str_multiplymoddownname.replaceFirst("%int_moddown", "0.5"));
                ArrayList<String> loreIMmodsDOWN2 = new ArrayList<String>();
                loreIMmodsDOWN2.add(CasinoX.plugin.str_multiplymodsname + " " + Modify.get(player.getName()));
                loreIMmodsDOWN2.add(CasinoX.plugin.str_multiplymodslore.replaceFirst("%winchance", str_winchance));
                IMmodsDOWN2.setLore(loreIMmodsDOWN2);
                modsDOWN2.setItemMeta(IMmodsDOWN2);
                this.MultiplyInventory.setItem(29, modsDOWN2);

                //кнопки TODO баланса пополнить
                ItemStack balance1 = new ItemStack(CasinoX.plugin.depomoney_ico, 0);
                ItemMeta IMbalance1 = balance1.getItemMeta();
                IMbalance1.setDisplayName(CasinoX.plugin.str_multiplydepositname.replaceFirst("%depocount", "4"));
                ArrayList<String> loreIMbalance1 = new ArrayList<String>();
                loreIMbalance1.add(CasinoX.plugin.str_balancelore.replaceFirst("%balance", String.valueOf(Balance.get(player.getName()))));
                IMbalance1.setLore(loreIMbalance1);
                balance1.setItemMeta(IMbalance1);
                this.MultiplyInventory.setItem(0, balance1);

                ItemStack balance2 = new ItemStack(CasinoX.plugin.depomoney_ico, 0);
                ItemMeta IMbalance2 = balance2.getItemMeta();
                IMbalance2.setDisplayName(CasinoX.plugin.str_multiplydepositname.replaceFirst("%depocount", "32"));
                ArrayList<String> loreIMbalance2 = new ArrayList<String>();
                loreIMbalance2.add(CasinoX.plugin.str_balancelore.replaceFirst("%balance", String.valueOf(Balance.get(player.getName()))));
                IMbalance2.setLore(loreIMbalance2);
                balance2.setItemMeta(IMbalance2);
                this.MultiplyInventory.setItem(1, balance2);

                ItemStack balance3 = new ItemStack(CasinoX.plugin.depomoney_ico, 0);
                ItemMeta IMbalance3 = balance3.getItemMeta();
                IMbalance3.setDisplayName(CasinoX.plugin.str_multiplydepositname.replaceFirst("%depocount", "64"));
                ArrayList<String> loreIMbalance3 = new ArrayList<String>();
                loreIMbalance3.add(CasinoX.plugin.str_balancelore.replaceFirst("%balance", String.valueOf(Balance.get(player.getName()))));
                IMbalance3.setLore(loreIMbalance3);
                balance3.setItemMeta(IMbalance3);
                this.MultiplyInventory.setItem(2, balance3);
                //кнопки TODO баланс снять
                ItemStack balanceW1 = new ItemStack(CasinoX.plugin.withmoney_ico, 0);
                ItemMeta IMbalanceW1 = balanceW1.getItemMeta();
                IMbalanceW1.setDisplayName(CasinoX.plugin.str_multiplywithdrawname.replaceAll("%withcountalm", "32").replaceAll("%withcountgold", "1600"));
                ArrayList<String> loreIMbalanceW1 = new ArrayList<String>();
                loreIMbalanceW1.add(CasinoX.plugin.str_balancelore.replaceFirst("%balance", String.valueOf(Balance.get(player.getName()))));
                IMbalanceW1.setLore(loreIMbalanceW1);
                balanceW1.setItemMeta(IMbalanceW1);
                this.MultiplyInventory.setItem(9, balanceW1);

                ItemStack balanceW2 = new ItemStack(CasinoX.plugin.withmoney_ico, 0);
                ItemMeta IMbalanceW2 = balanceW2.getItemMeta();
                IMbalanceW2.setDisplayName(CasinoX.plugin.str_multiplywithdrawname.replaceAll("%withcountalm", "64").replaceAll("%withcountgold", "3200"));
                ArrayList<String> loreIMbalanceW2 = new ArrayList<String>();
                loreIMbalanceW2.add(CasinoX.plugin.str_balancelore.replaceFirst("%balance", String.valueOf(Balance.get(player.getName()))));
                IMbalanceW2.setLore(loreIMbalanceW2);
                balanceW2.setItemMeta(IMbalanceW2);
                this.MultiplyInventory.setItem(10, balanceW2);

                //кнопки TODO выход
                ItemStack exit = new ItemStack(CasinoX.plugin.int_exit_id, 0);
                ItemMeta IMexit = exit.getItemMeta();
                IMexit.setDisplayName("§4ВЫХОД");
                exit.setItemMeta(IMexit);
                this.MultiplyInventory.setItem(53, exit);
            }
        }
}