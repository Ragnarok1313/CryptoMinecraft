package ragnarok.joinleave;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ragnarok.mysql.sqlservice.DatabaseConnection;
import java.util.Objects;
import static ragnarok.CasinoX.Balance;
import static ragnarok.CasinoX.PlayerWager;
import static ragnarok.CasinoX.PlayerWin;
import static ragnarok.mysql.dbconf.*;
import static ragnarok.mysql.dbconf.columnWager;


public class PlayerLCas implements Listener {
    @EventHandler
    public void onPlayerLCas(PlayerQuitEvent e) {
        if (Balance.containsKey(e.getPlayer().getName())) {
            if (DatabaseConnection.isConnected()) {
                String queryU = ("UPDATE " + tableName + " SET " + columnAmount + "=" + "'" + Balance.get(e.getPlayer().getName()) + "'" + " WHERE " + columnPlayerName + "=" + "'" + e.getPlayer().getName() + "'" + ";");
                DatabaseConnection.UpdateBal(queryU);
                String queryU2 = ("UPDATE " + tableName + " SET " + columnWin + "=" + "'" + PlayerWin.get(e.getPlayer().getName()) + "'" + " WHERE " + columnPlayerName + "=" + "'" + e.getPlayer().getName() + "'" + ";");
                DatabaseConnection.UpdateBal(queryU2);
                String queryU3 = ("UPDATE " + tableName + " SET " + columnWager + "=" + "'" + PlayerWager.get(e.getPlayer().getName()) + "'" + " WHERE " + columnPlayerName + "=" + "'" + e.getPlayer().getName() + "'" + ";");
                DatabaseConnection.UpdateBal(queryU3);
                Balance.remove(e.getPlayer().getName());
                PlayerWin.remove(e.getPlayer().getName());
                PlayerWager.remove(e.getPlayer().getName());
            } else {
                System.out.print("[CasinoX] ERROR onQuit MYSQL save " + e.getPlayer().getName());
            }
        }
    }
}
