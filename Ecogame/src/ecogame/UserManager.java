package ecogame;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserManager {

    public static boolean registrarUsuario(String username, String password) {
        // Configura tus datos de conexión a la base de datos
        String url = "jdbc:mysql://localhost:3306/ecogame";
        String user = "root";
        String pass = "piteravi07";

        try (Connection con = DriverManager.getConnection(url, user, pass)) {
            String query = "INSERT INTO usuarios (username, password) VALUES (?, ?)";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, username);
                ps.setString(2, password);
                ps.executeUpdate();
            }
            return true; // El registro fue exitoso
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // El registro no fue exitoso
        }
    }
}