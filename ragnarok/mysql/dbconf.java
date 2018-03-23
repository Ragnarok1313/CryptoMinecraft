package ragnarok.mysql;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;


public class dbconf {
    public static String tableName;
    public static String columnId;
    public static String columnPlayerName;
    public static String columnAmount;
    public static String columnWin;
    public static String columnWager;

    public dbconf() {
    }

    public static void load(FileConfiguration config) {
        ConfigurationSection section = config.getConfigurationSection("unitweaks");
    }

    public static void loaddb(FileConfiguration conf)
    {
        tableName = getNotnullString(conf, "db.table");
        columnId = getNotnullString(conf, "db.column.id");
        columnPlayerName = getNotnullString(conf, "db.column.player");
        columnAmount = getNotnullString(conf, "db.column.amount");
        columnWin = getNotnullString(conf, "db.column.win");
        columnWager = getNotnullString(conf, "db.column.wager");
    }
    private static String getNotnullString(Configuration conf, String key)
    {
        if (key == null) {
            throw new NullPointerException("key");
        }
        String value = conf.getString(key);
        if (value == null) {
            throw new NullPointerException(key + " = null");
        }
        return value;
    }
}
