package org.bookhub.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=BookHub;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "123456";

    private static Connection connection;

    // Singleton: sólo una instancia de conexión
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Conexión establecida con éxito.");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("❌ Error: No se encontró el driver JDBC.", e);
            } catch (SQLException e) {
                throw new RuntimeException("❌ Error al conectar a la base de datos.", e);
            }
        }
        return connection;
    }
}
