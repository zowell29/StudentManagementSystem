package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static DatabaseConfig databaseConfig;
    private Connection connection;

    public static DatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }

    public Connection getConnection() {
        return connection;
    }

    private DatabaseConfig() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost/student-management-system", "root", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized static DatabaseConfig GET_INSTANCE() {
        if (databaseConfig == null) {
            databaseConfig = new DatabaseConfig();
        }
        return databaseConfig;
    }
}
