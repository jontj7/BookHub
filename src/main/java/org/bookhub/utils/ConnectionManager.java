package org.bookhub.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String URL = "jdbc:sqlserver://BookHub.mssql.somee.com;databaseName=BookHub;packetSize=4096;trustServerCertificate=true";
    private static final String USER = "erickcaceres17_SQLLogin_3";
    private static final String PASSWORD = "3wgmei85xa";

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
