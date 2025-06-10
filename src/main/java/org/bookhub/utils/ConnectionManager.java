package org.bookhub.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String URL = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=BookHub;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("❌ No se encontró el driver JDBC.", e);
        } catch (SQLException e) {
            throw new RuntimeException("❌ Error al conectar con la base de datos.", e);
        }
    }
}
