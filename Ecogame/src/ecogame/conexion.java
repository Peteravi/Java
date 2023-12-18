package ecogame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/ecogame";
    private static final String USER = "root";
    private static final String PASSWORD = "piteravi07";

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (connection != null) {
                System.out.println("Conexión exitosa a la base de datos.");
            } else {
                System.err.println("No se pudo establecer la conexión a la base de datos.");
            }
            return connection;
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos:");
            e.printStackTrace();
            return null;
        }
    }

    public static boolean testConnection() {
        Connection connection = getConnection();
        return connection != null;
    }

    public static void main(String[] args) {
        if (testConnection()) {
            System.out.println("Hay conexión con la base de datos.");
        } else {
            System.out.println("No hay conexión con la base de datos.");
        }
    }
}