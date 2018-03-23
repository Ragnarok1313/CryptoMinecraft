package ragnarok.mysql.sqlservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import static ragnarok.mysql.dbconf.columnAmount;
import static ragnarok.mysql.dbconf.columnWager;
import static ragnarok.mysql.dbconf.columnWin;
import static ragnarok.mysql.sqlservice.DatabaseConnectionConfig.*;

public class DatabaseConnection {
    private Logger log;
    private static DatabaseConnectionConfig config;
    private static Connection connection;
    public static boolean connected;
    private static PreparedStatement statementUniverse;
    private static long lastRequestDate;
    static boolean sqloff = false;

    public DatabaseConnection(Logger log) {
        this.log = log;
    }

    public static boolean init(DatabaseConnectionConfig config) {
        try {
            System.out.println("CasinoX Connecting to database...");
            DatabaseConnection.config = config;
            connection = DriverManager.getConnection(url, username, pass);
            System.out.println("CasinoX Connected to database...");
            lastRequestDate = System.currentTimeMillis();
            initTable();
            initBankTable();
            prepareStatements();
            connected = true;
            return true;
        } catch (SQLException e) {
            connected = false;
            System.out.println("CasinoX Couldn't connect to database... " + e);
        }
        return connected;
    }

    public boolean close() {
        try {
            System.out.println("CasinoX Closing DB connection...");
            connected = false;
            connection.close();
            System.out.println("CasinoX DB connection closed");
            return true;
        } catch (SQLException e) {
            System.out.println("CasinoX Error closing DB connection: " + e);
        }
        return false;
    }

    private static void prepareStatements()
            throws SQLException {
        String selectColumnNames = "`" + columnId + "`,`" + columnPlayerName + "`,`" + columnAmount;

        statementUniverse = connection.prepareStatement("SELECT " + selectColumnNames + " FROM `" + tableName + "` WHERE `" + columnPlayerName + "` = ? ORDER BY `" + columnId + "`");
    }

    private static void initTable()
            throws SQLException {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("CREATE TABLE IF NOT EXISTS `").append(tableName).append("` (");
        queryBuilder.append("`").append(columnId).append("`").append(" INT AUTO_INCREMENT, ");
        queryBuilder.append("`").append(columnPlayerName).append("`").append(" VARCHAR(255), ");
        queryBuilder.append("`").append(columnAmount).append("`").append(" INT NOT NULL DEFAULT 1, ");
        queryBuilder.append("`").append(columnWin).append("`").append(" INT NOT NULL DEFAULT 1, ");
        queryBuilder.append("`").append(columnWager).append("`").append(" INT NOT NULL DEFAULT 1, ");
        queryBuilder.append("PRIMARY KEY(`").append(columnId).append("`),");
        queryBuilder.append("KEY(`").append(columnId).append("`))");
        queryBuilder.append("CHARACTER SET utf8");
        String query = queryBuilder.toString();
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        statement.close();

        query = "SHOW COLUMNS FROM `" + tableName + "` LIKE '" + columnAmount + "';";
        statement = connection.createStatement();
        ResultSet set = statement.executeQuery(query);
        if ((set == null) || (!set.first())) {
            if (set != null) {
                set.close();
            }
        }
        statement.close();
    }
    private static void initBankTable()
            throws SQLException {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("CREATE TABLE IF NOT EXISTS `").append(tableBankName).append("` (");
        queryBuilder.append("`").append(columnId).append("`").append(" INT AUTO_INCREMENT, ");
        queryBuilder.append("`").append(columnPlayerName).append("`").append(" VARCHAR(255), ");
        queryBuilder.append("`").append(columnAmount).append("`").append(" INT NOT NULL DEFAULT 1, ");
        queryBuilder.append("PRIMARY KEY(`").append(columnId).append("`),");
        queryBuilder.append("KEY(`").append(columnId).append("`))");
        queryBuilder.append("CHARACTER SET utf8");
        String query = queryBuilder.toString();
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        statement.close();

        query = "SHOW COLUMNS FROM `" + tableBankName + "` LIKE '" + columnAmount + "';";
        statement = connection.createStatement();
        ResultSet set = statement.executeQuery(query);
        if ((set == null) || (!set.first())) {
            if (set != null) {
                set.close();
            }
        }
        statement.close();
    }

    public static boolean isConnected() {
        return connected;
    }

    private static boolean isActuallyConnected() {
        if (!connected) {
            return false;
        }
        try {
            Statement statement = connection.createStatement();
            statement.execute("SELECT 1");
            return true;
        } catch (SQLException e) {
        }
        return false;
    }

    private static void reconnectIfNeeded() {
        long TIMEOUT = 30000L;
        if (System.currentTimeMillis() - lastRequestDate > TIMEOUT) {
            if (!isActuallyConnected()) {
                //this.log.info("Connection lost. Reconnecting...");
                System.out.println("CasinoX Connection lost. Reconnecting...");
                init(config);
            } else {
                lastRequestDate = (System.currentTimeMillis() + TIMEOUT);
            }
        }
    }

    public static String SelectBal(String query){
        reconnectIfNeeded();
        Statement selectStmt = null;
        ResultSet result = null;
        String resultStr = "null";
        try {
            selectStmt = connection.createStatement();
            result = selectStmt.executeQuery(query);
            while (result.next()) {
                resultStr = result.getString(columnAmount);
            }
        } catch (Exception e) {
            System.out.println("Failed to execute query: " + query);
            System.out.println("Exception: " + e);
        }
            try {
                if (result != null) result.close();
                if (selectStmt != null) selectStmt.close();
            } catch (Exception ignored) {
            }
        return resultStr;
    }

    public static String SelectWin(String query){
        reconnectIfNeeded();
        Statement selectStmt = null;
        ResultSet result = null;
        String resultStr = "null";
        try {
            selectStmt = connection.createStatement();
            result = selectStmt.executeQuery(query);
            while (result.next()) {
                resultStr = result.getString(columnWin);
                //System.out.println(resultStr);
            }
        } catch (Exception e) {
            System.out.println("Failed to execute query: " + query);
            System.out.println("Exception: " + e);
        }
        try {
            if (result != null) result.close();
            if (selectStmt != null) selectStmt.close();
        } catch (Exception ignored) {
        }
        return resultStr;
    }

    public static String SelectWager(String query){
        reconnectIfNeeded();
        Statement selectStmt = null;
        ResultSet result = null;
        String resultStr = "null";
        try {
            selectStmt = connection.createStatement();
            result = selectStmt.executeQuery(query);
            while (result.next()) {
                resultStr = result.getString(columnWager);
            }
        } catch (Exception e) {
            System.out.println("Failed to execute query: " + query);
            System.out.println("Exception: " + e);
        }
        try {
            if (result != null) result.close();
            if (selectStmt != null) selectStmt.close();
        } catch (Exception ignored) {
        }
        return resultStr;
    }

    public static boolean UpdateBal(String query){
        reconnectIfNeeded();
        if (connection == null) {
            return false;
        }
        Statement statement = null;
        boolean ok = false;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            ok = true;
        } catch (Exception e) {
            System.out.println("Failed to execute query: " + query);
            System.out.println("Exception: " + e);
            e.printStackTrace();
        }
            try {
                if (statement != null) statement.close();
            } catch (Exception ignored) {
            }
        return ok;
    }
}
