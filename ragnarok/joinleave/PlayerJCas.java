package ragnarok.joinleave;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ragnarok.mysql.sqlservice.DatabaseConnection;

import java.util.Objects;
import static ragnarok.CasinoX.Balance;
import static ragnarok.CasinoX.PlayerWager;
import static ragnarok.CasinoX.PlayerWin;
import static ragnarok.mysql.dbconf.columnAmount;
import static ragnarok.mysql.dbconf.tableName;
import static ragnarok.mysql.dbconf.columnPlayerName;
import static ragnarok.mysql.dbconf.columnWin;
import static ragnarok.mysql.dbconf.columnWager;

public class PlayerJCas implements Listener {
    @EventHandler
    public void onPlayerJCas(PlayerJoinEvent e) {
        if (!Balance.containsKey(e.getPlayer().getName())) {
            if (DatabaseConnection.isConnected()) {
                String query = ("SELECT " + columnAmount + " FROM " + tableName + " WHERE " + columnPlayerName + "=" + "'" + e.getPlayer().getName() + "'" + ";");
                String resultat = String.valueOf(DatabaseConnection.SelectBal(query));
                if (!Objects.equals(resultat, "null")) {
                    int balanc = Integer.parseInt(resultat);
                    Balance.put(e.getPlayer().getName(), balanc);
                } else {
                    Balance.put(e.getPlayer().getName(), 0);
                }

                String query2 = ("SELECT " + columnWin + " FROM " + tableName + " WHERE " + columnPlayerName + "=" + "'" + e.getPlayer().getName() + "'" + ";");
                String resultat2 = String.valueOf(DatabaseConnection.SelectWin(query2));
                if (!Objects.equals(resultat2, "null")) {
                    int win = Integer.parseInt(resultat2);
                    PlayerWin.put(e.getPlayer().getName(), win);
                } else {
                    PlayerWin.put(e.getPlayer().getName(), 0);
                }

                String query3 = ("SELECT " + columnWager + " FROM " + tableName + " WHERE " + columnPlayerName + "=" + "'" + e.getPlayer().getName() + "'" + ";");
                String resultat3 = String.valueOf(DatabaseConnection.SelectWager(query3));
                if (!Objects.equals(resultat3, "null")) {
                    int wager = Integer.parseInt(resultat3);
                    PlayerWager.put(e.getPlayer().getName(), wager);
                } else {
                    PlayerWager.put(e.getPlayer().getName(), 0);
                }

            } else {
                System.out.print("[CasinoX] ERROR onJoin MYSQL " + e.getPlayer().getName());
            }
        }
    }
}
