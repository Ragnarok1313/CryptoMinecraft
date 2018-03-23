package ragnarok.task;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ragnarok.CasinoX;
import ragnarok.mysql.sqlservice.DatabaseConnection;
import ragnarok.mysql.sqlservice.DatabaseConnectionConfig;

import java.util.Objects;

import static ragnarok.CasinoX.*;
import static ragnarok.mysql.dbconf.*;
import static ragnarok.mysql.sqlservice.DatabaseConnectionConfig.tableBankName;


public class UpdateBalance {
    private CasinoX plugin;
    public UpdateBalance(CasinoX plugin) {
        this.plugin = plugin;
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(CasinoX.plugin, new BukkitRunnable() {
            public void run() {
                if (DatabaseConnection.isConnected()) {
                    Player[] players = Bukkit.getOnlinePlayers();
                    for (Player player : players) {
                        String query = ("SELECT " + columnAmount + " FROM " + tableName + " WHERE " + columnPlayerName + "=" + "'" + player.getName() + "'" + ";");
                        String resultat = String.valueOf(DatabaseConnection.SelectBal(query));
                        if (!Objects.equals(resultat, "null")) {
                            if (Balance.containsKey(player.getName())) {
                                String queryU = ("UPDATE " + tableName + " SET " + columnAmount + "=" + "'" + Balance.get(player.getName()) + "'" + " WHERE " + columnPlayerName + "=" + "'" + player.getName() + "'" + ";");
                                DatabaseConnection.UpdateBal(queryU);
                                String queryU2 = ("UPDATE " + tableName + " SET " + columnWin + "=" + "'" + PlayerWin.get(player.getName()) + "'" + " WHERE " + columnPlayerName + "=" + "'" + player.getName() + "'" + ";");
                                DatabaseConnection.UpdateBal(queryU2);
                                String queryU3 = ("UPDATE " + tableName + " SET " + columnWager + "=" + "'" + PlayerWager.get(player.getName()) + "'" + " WHERE " + columnPlayerName + "=" + "'" + player.getName() + "'" + ";");
                                DatabaseConnection.UpdateBal(queryU3);
                            }
                        } else {
                            if (Balance.containsKey(player.getName())) {
                                String query2 = ("INSERT INTO " + tableName + " (" + columnPlayerName + ", " + columnAmount +  ", " + columnWin + ", " + columnWager + ") VALUES (" + "'" + player.getName() + "'" + ", " + Balance.get(player.getName()) + ", " + PlayerWin.get(player.getName()) + ", " + PlayerWager.get(player.getName()) + ");");
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
                    System.out.print("CasinoX DataBase Task OKAY");
                } else {
                    System.out.print("WARNING! CasinoX NO DB CONNECT!");
                }
            }
        }, 0L, 3000L);
    }
}