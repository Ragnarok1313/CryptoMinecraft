package ragnarok.mysql.sqlservice;

import org.bukkit.configuration.file.FileConfiguration;

public class DatabaseConnectionConfig
{
    public static  String url;
    public static  String username;
    public static  String pass;
    public static  String tableName;
    public static  String columnId;
    public static  String columnPlayerName;
    public static  String columnAmount;
    public static  String tableBankName;
    public static String columnWin;
    public static String columnWager;
    public static void load(FileConfiguration conf)
    {
        url = getNotnullString(conf, "db.url");
        username = getNotnullString(conf, "db.username");
        pass = getNotnullString(conf, "db.password");

        tableName = getNotnullString(conf, "db.table");
        columnId = getNotnullString(conf, "db.column.id");
        columnPlayerName = getNotnullString(conf, "db.column.player");
        columnAmount = getNotnullString(conf, "db.column.amount");
        columnWin = getNotnullString(conf, "db.column.win");
        columnWager = getNotnullString(conf, "db.column.wager");
        tableBankName = getNotnullString(conf, "db.banktable");

    }

    protected static String getNotnullString(FileConfiguration conf, String key)
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
