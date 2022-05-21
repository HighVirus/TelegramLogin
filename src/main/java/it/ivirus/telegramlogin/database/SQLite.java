package it.ivirus.telegramlogin.database;

import it.ivirus.telegramlogin.TelegramLogin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLite extends SqlManager {

    public SQLite(TelegramLogin plugin) {
        super(plugin);
    }

    @Override
    public Connection getJdbcUrl() throws SQLException {
        String SQLITE_FILE_NAME = "database.db";
        String dataUrl = plugin.getDataFolder() + File.separator + "data" + File.separator + SQLITE_FILE_NAME;
        String url = "jdbc:sqlite:" + dataUrl;
        File dataFolder = new File(dataUrl);
        if (!dataFolder.exists())
            dataFolder.getParentFile().mkdirs();
        return DriverManager.getConnection(url);
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = getJdbcUrl();
        }
        return this.connection;
    }

    @Override
    public void createTables() {
        try (Connection connection = getConnection();
             PreparedStatement data = connection.prepareStatement("create TABLE if not exists " + TABLE_PLAYERS + " " +
                     "(AccountId INTEGER PRIMARY KEY AUTOINCREMENT, PlayerUUID VARCHAR(100), PlayerName VARCHAR(100), ChatID VARCHAR(100), Locked BOOLEAN NOT NULL," +
                     "RegistrationDate DATETIME NOT NULL)")) {

            data.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
