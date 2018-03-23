package ragnarok;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ragnarok.inventory.MultiplyInv;
import ragnarok.joinleave.PlayerJCas;
import ragnarok.joinleave.PlayerLCas;
import ragnarok.mysql.dbconf;
import ragnarok.mysql.sqlservice.DatabaseConnection;
import ragnarok.mysql.sqlservice.DatabaseConnectionConfig;
import ragnarok.task.UpdateBalance;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Logger;

import static ragnarok.inventory.MultiplyInv.casxCD;
import static ragnarok.mysql.dbconf.columnWager;
import static ragnarok.mysql.dbconf.columnWin;
import static ragnarok.mysql.dbconf.tableName;
import static ragnarok.mysql.sqlservice.DatabaseConnectionConfig.columnAmount;
import static ragnarok.mysql.sqlservice.DatabaseConnectionConfig.columnPlayerName;
import static ragnarok.mysql.sqlservice.DatabaseConnectionConfig.tableBankName;

public class CasinoX extends JavaPlugin {
    public static CasinoX plugin;
    public Logger log;
    public MultiplyInv onMultiplyInv;
    public Commands onCommands;
    public PlayerJCas onPlayerJCas;
    public PlayerLCas onPlayerLCas;
    public UpdateBalance updateBalance;
    protected DatabaseConnection connection;
    protected DatabaseConnectionConfig dbConfig;
    //пермишен
    private Permission reload = new Permission("casx.reload");
    public static HashMap<String, Double> Modify = new HashMap();
    public static HashMap<String, Integer> Bet = new HashMap();
    public static HashMap<String, Integer> Balance = new HashMap();
    public static HashMap<String, Integer> BalanceBank = new HashMap();
    public static HashMap<String, Integer> LastNumber = new HashMap();
    public static HashMap<String, Integer> PlayerWin = new HashMap();
    public static HashMap<String, Integer> PlayerWager = new HashMap();
    //конфиг
    //сообщения
    public String str_denied = "";
    public String str_reload = "";
    public String str_multiplyinvname = "";
    public String str_multiplydepositname = "";
    public String str_multiplywithdrawname = "";
    public String str_multiplymodsname = "";
    public String str_multiplymodslore = "";
    public String str_winlore = "";
    public String str_balancelore = "";
    public String str_nobalance = "";
    public String str_wastelore = "";
    public String str_bethilore = "";
    public String str_betlolore = "";
    public String str_bethiname = "";
    public String str_betloname = "";
    public String str_multiplymodupname = "";
    public String str_multiplymoddownname = "";
    public String str_multiplybetupname = "";
    public String str_multiplybetdownname = "";
    public String str_multiplybetlore = "";
    public String str_deposit_message = "";
    public String str_withdraw_message = "";
    public String str_fullinventory = "";
    public String str_donthavebalance = "";
    public String str_randommessage = "";
    public String str_winnermessage = "";
    public String str_wastedmessage = "";
    public String str_multiplycommand = "multiply";
    public Integer int_comission = 0;
    public boolean b_logger = false;
    public String str_logfile = "CasLog.txt";
    //бабки
    public Integer depomoney_id = 26508;
    public Integer withmoney_id = 26507;

    public Integer depomoney_ico = 26508;
    public Integer withmoney_ico = 26507;

    //кнопки
    public Integer int_num_id = 26000;
    public Integer int_balance_id = 26509;

    public Integer int_bethi_id = 26509;
    public Integer int_betlo_id = 26510;

    public Integer int_mods_id = 26503;
    public Integer int_modsinc_id = 26504;
    public Integer int_modsdec_id = 26505;

    public Integer int_bet_id = 26500;
    public Integer int_exit_id = 26506;
    public Integer int_betinc_id = 26501;
    public Integer int_betdec_id = 26502;

    public void loadConfig() {
        this.saveDefaultConfig();
        this.reloadConfig();
        this.str_denied = this.getConfig().getString("Denied").replaceAll("&", "§");
        this.str_reload = this.getConfig().getString("Reload").replaceAll("&", "§");
        this.str_randommessage = this.getConfig().getString("RandomMessage").replaceAll("&", "§");
        this.str_winnermessage = this.getConfig().getString("WinnerMessage").replaceAll("&", "§");
        this.str_wastedmessage = this.getConfig().getString("WastedMessage").replaceAll("&", "§");
        this.str_fullinventory = this.getConfig().getString("FullInventory").replaceAll("&", "§");
        this.str_donthavebalance = this.getConfig().getString("DontHaveBalance").replaceAll("&", "§");
        this.str_deposit_message = this.getConfig().getString("DepositMessage").replaceAll("&", "§");
        this.str_withdraw_message = this.getConfig().getString("WithdrawMessage").replaceAll("&", "§");
        this.str_multiplybetlore = this.getConfig().getString("MultiplyBetLore").replaceAll("&", "§");
        this.str_multiplybetupname = this.getConfig().getString("MultiplyBetUpName").replaceAll("&", "§");
        this.str_multiplybetdownname = this.getConfig().getString("MultiplyBetDownName").replaceAll("&", "§");
        this.str_multiplymodupname = this.getConfig().getString("MultiplyModUpName").replaceAll("&", "§");
        this.str_multiplymoddownname = this.getConfig().getString("MultiplyModDownName").replaceAll("&", "§");
        this.str_bethiname = this.getConfig().getString("MultiplyBetHiName").replaceAll("&", "§");
        this.str_betloname = this.getConfig().getString("MultiplyBetLoName").replaceAll("&", "§");
        this.str_bethilore = this.getConfig().getString("MultiplyBetHiLore").replaceAll("&", "§");
        this.str_betlolore = this.getConfig().getString("MultiplyBetLoLore").replaceAll("&", "§");
        this.str_balancelore = this.getConfig().getString("MultiplyBalanceLore").replaceAll("&", "§");
        this.str_nobalance = this.getConfig().getString("MultiplyNoBalance").replaceAll("&", "§");
        this.str_multiplydepositname = this.getConfig().getString("MultiplyDepositName").replaceAll("&", "§");
        this.str_multiplywithdrawname = this.getConfig().getString("MultiplyWithdrawName").replaceAll("&", "§");
        this.str_multiplyinvname = this.getConfig().getString("MultiplyInvName").replaceAll("&", "§");
        this.str_multiplymodsname = this.getConfig().getString("MultiplyModsName").replaceAll("&", "§");
        this.str_multiplymodslore = this.getConfig().getString("MultiplyModsLore").replaceAll("&", "§");
        this.str_winlore = this.getConfig().getString("MultiplyWinLore").replaceAll("&", "§");
        this.str_wastelore = this.getConfig().getString("MultiplyWasteLore").replaceAll("&", "§");

        this.str_multiplycommand = this.getConfig().getString("MultiplyCommand");
        this.int_exit_id = this.getConfig().getInt("exit_id");
        this.depomoney_ico = this.getConfig().getInt("depomoney_ico");
        this.withmoney_ico = this.getConfig().getInt("withmoney_ico");
        this.depomoney_id = this.getConfig().getInt("depomoney_id");
        this.withmoney_id = this.getConfig().getInt("withmoney_id");
        this.int_comission = this.getConfig().getInt("Comission");
        this.int_bethi_id = this.getConfig().getInt("bethi_id");
        this.int_betlo_id = this.getConfig().getInt("betlo_id");
        this.int_num_id = this.getConfig().getInt("num_id");
        this.int_mods_id = this.getConfig().getInt("mods_id");
        this.int_modsinc_id = this.getConfig().getInt("modsinc_id");
        this.int_modsdec_id = this.getConfig().getInt("modsdec_id");
        this.int_bet_id = this.getConfig().getInt("bet_id");
        this.int_betinc_id = this.getConfig().getInt("betinc_id");
        this.int_betdec_id = this.getConfig().getInt("betdec_id");
        this.int_balance_id = this.getConfig().getInt("balance_id");
        this.b_logger = this.getConfig().getBoolean("Logger");
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if((label.equalsIgnoreCase("casx")) && sender instanceof Player) {
            Player player = (Player)sender;
            if(args.length >= 1) {
                if(args[0].equalsIgnoreCase("reload")) {
                    if(!player.hasPermission(this.reload) && !player.isOp()) {
                        player.sendMessage(this.str_denied);
                    } else {
                        this.loadConfig();
                        player.sendMessage(this.str_reload);
                    }
                    return false;
                }
            }
        }
        return false;
    }

    private void updateMessagingSettings() {
        File file = null;
        if (str_logfile != null) {
            file = new File(this.getServer().getPluginManager().getPlugin("CasinoX").getDataFolder(), str_logfile);
        }
        Messaging.configure(file, b_logger);
    }

    public void onEnable() {
        this.loadConfig();
        this.dbConfig = new DatabaseConnectionConfig();
        DatabaseConnectionConfig.load(this.getConfig());
        dbconf.loaddb(this.getConfig());

        initDBConnection();
        updateMessagingSettings();
        plugin = this;
        PluginManager PluginManager = getServer().getPluginManager();

        this.onPlayerJCas = new PlayerJCas();
        PluginManager.registerEvents(this.onPlayerJCas, this);

        this.onPlayerLCas = new PlayerLCas();
        PluginManager.registerEvents(this.onPlayerLCas, this);

        this.onMultiplyInv = new MultiplyInv();
        PluginManager.registerEvents(this.onMultiplyInv, this);

        this.onCommands = new Commands();
        PluginManager.registerEvents(this.onCommands, this);

        this.updateBalance = new UpdateBalance(CasinoX.plugin);

        if (DatabaseConnection.isConnected()) {
            String querySelectWON = ("SELECT " + columnAmount + " FROM " + tableBankName + " WHERE " + columnPlayerName + "=" + "'won_by_user'" + ";");
            String resultatWON = String.valueOf(DatabaseConnection.SelectBal(querySelectWON));
            if (!Objects.equals(resultatWON, "null")) {
                int bank = Integer.parseInt(resultatWON);
                BalanceBank.put("won_by_user", bank);
            } else {
                String querybank = ("INSERT INTO " + tableBankName + " (" + dbconf.columnPlayerName + ", " + dbconf.columnAmount + ") VALUES (" + "'won_by_user'" + ", '0');");
                DatabaseConnection.UpdateBal(querybank);
                BalanceBank.put("won_by_user", 0);
            }
            String querySelectWAG = ("SELECT " + columnAmount + " FROM " + tableBankName + " WHERE " + columnPlayerName + "=" + "'wagered'" + ";");
            String resultatWAG = String.valueOf(DatabaseConnection.SelectBal(querySelectWAG));
            if (!Objects.equals(resultatWAG, "null")) {
                int bank = Integer.parseInt(resultatWAG);
                BalanceBank.put("wagered", bank);
            } else {
                String querybank = ("INSERT INTO " + tableBankName + " (" + dbconf.columnPlayerName + ", " + dbconf.columnAmount + ") VALUES (" + "'wagered'" + ", '0');");
                DatabaseConnection.UpdateBal(querybank);
                BalanceBank.put("wagered", 0);
            }
        } else {
            System.out.print("CasinoX WARNING! Mysql DB not connected!");
        }
        System.out.print("CasinoX is ENABLED!");
    }
    public void onDisable(){
        casxCD.clear();
        Modify.clear();
        Bet.clear();
        Balance.clear();
        BalanceBank.clear();
        LastNumber.clear();
        PlayerWin.clear();
        PlayerWager.clear();
        if (DatabaseConnection.isConnected()) {
            Player[] players = Bukkit.getOnlinePlayers();
            for (Player player : players) {
                String query = ("SELECT " + dbconf.columnAmount + " FROM " + tableName + " WHERE " + dbconf.columnPlayerName + "=" + "'" + player.getName() + "'" + ";");
                String resultat = String.valueOf(DatabaseConnection.SelectBal(query));
                if (!Objects.equals(resultat, "null")) {
                    if (Balance.containsKey(player.getName())) {
                        String queryU = ("UPDATE " + tableName + " SET " + dbconf.columnAmount + "=" + "'" + Balance.get(player.getName()) + "'" + " WHERE " + dbconf.columnPlayerName + "=" + "'" + player.getName() + "'" + ";");
                        DatabaseConnection.UpdateBal(queryU);
                        String queryU2 = ("UPDATE " + tableName + " SET " + columnWin + "=" + "'" + PlayerWin.get(player.getName()) + "'" + " WHERE " + dbconf.columnPlayerName + "=" + "'" + player.getName() + "'" + ";");
                        DatabaseConnection.UpdateBal(queryU2);
                        String queryU3 = ("UPDATE " + tableName + " SET " + columnWager + "=" + "'" + PlayerWager.get(player.getName()) + "'" + " WHERE " + dbconf.columnPlayerName + "=" + "'" + player.getName() + "'" + ";");
                        DatabaseConnection.UpdateBal(queryU3);
                    }
                } else {
                    if (Balance.containsKey(player.getName())) {
                        String query2 = ("INSERT INTO " + tableName + " (" + dbconf.columnPlayerName + ", " + dbconf.columnAmount +  ", " + columnWin + ", " + columnWager + ") VALUES (" + "'" + player.getName() + "'" + ", " + Balance.get(player.getName()) + ", " + PlayerWin.get(player.getName()) + ", " + PlayerWager.get(player.getName()) + ");");
                        DatabaseConnection.UpdateBal(query2);
                    }
                }
            }
            if (BalanceBank.containsKey("wagered") && BalanceBank.get("wagered") > 0) {
                String querybankWAG = ("UPDATE " + tableBankName + " SET " + DatabaseConnectionConfig.columnAmount + "=" + "'" + BalanceBank.get("wagered") + "'" + " WHERE " + DatabaseConnectionConfig.columnPlayerName + "=" + "'wagered'" + ";");
                DatabaseConnection.UpdateBal(querybankWAG);
                System.out.print("[CasinoX INFO] Total WAGERED " + BalanceBank.get("wagered"));
            }
            if (BalanceBank.containsKey("won_by_user") && BalanceBank.get("won_by_user") > 0) {
                String querybankWON = ("UPDATE " + tableBankName + " SET " + DatabaseConnectionConfig.columnAmount + "=" + "'" + BalanceBank.get("won_by_user") + "'" + " WHERE " + DatabaseConnectionConfig.columnPlayerName + "=" + "'won_by_user'" + ";");
                DatabaseConnection.UpdateBal(querybankWON);
                System.out.print("[CasinoX INFO] Total WON BY USERS " + BalanceBank.get("won_by_user"));
            }
            System.out.print("[Cas Disable] CasinoX DataBase save OKAY");
        } else {
            System.out.print("[Cas Disable] WARNING! CasinoX save fail!");
        }
        System.out.print("CasinoX is disabled!");
    }

    private void initDBConnection()
    {
        this.connection = new DatabaseConnection(log);
        DatabaseConnection.init(this.dbConfig);
        System.out.println("UNItweaks DatabaseInit...");
    }
}
