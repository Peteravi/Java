import ecogame.JuegoVentana;
import ecogame.conexion;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ecogame extends Application {
    private Stage primaryStage;
    private TextField usernameField;
    private int puntos = 0;
    private int preguntaActual = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        Image logo = new Image("https://play-lh.googleusercontent.com/wkAY7yqY-Dir5dCJCLxMkj28dJfksfvLsfFyuRgIe9E6Zho7HWvQ3am3AVTC3bVB7Y4");
        ImageView imageView = new ImageView(logo);
        imageView.setFitWidth(300);
        imageView.setFitHeight(150);
        grid.add(imageView, 0, 0, 2, 1);

        Label usernameLabel = new Label("Usuario:");
        usernameField = new TextField(); // Corregir aquí
        Label passwordLabel = new Label("Contraseña:");
        PasswordField passwordField = new PasswordField();
        Button registerButton = new Button("Registrar");
        Button startGameButton = new Button("Iniciar la Partida");

        grid.add(usernameLabel, 0, 1);
        grid.add(usernameField, 1, 1);
        grid.add(passwordLabel, 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(registerButton, 1, 3);
        grid.add(startGameButton, 1, 4);

        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHgrow(javafx.scene.layout.Priority.ALWAYS);
        grid.getColumnConstraints().addAll(column1, column2);

        registerButton.setOnAction(e -> {
            String usuario = usernameField.getText();
            String contraseña = passwordField.getText();

            if (verificarYGuardarUsuario(usuario, contraseña)) {
                mostrarMensajeExito("Registro Exitoso", "Usuario registrado correctamente.");
            } else {
                mostrarMensajeError("Error de Registro", "El registro no fue exitoso. Por favor, inténtalo de nuevo.");
            }
        });

        startGameButton.setOnAction(e -> {
            String usuario = usernameField.getText();
            String contraseña = passwordField.getText();
            if (verificarUsuarioRegistrado(usuario, contraseña)) {
                mostrarMensajeExito("Inicio de Partida", "¡Bienvenido de nuevo! Iniciando la partida.");

                // Create an instance of JuegoVentana and pass the primaryStage
                JuegoVentana juegoVentana = new JuegoVentana();
                juegoVentana.start(primaryStage);

                // Ahora que el usuario ha tenido la oportunidad de ingresar su nombre de usuario,
                // podemos obtener el puntaje del juego.
                int puntajeObtenido = obtenerPuntajeDelJuego();

                if (guardarPuntaje(usuario, puntajeObtenido)) {
                    mostrarMensajeExito("Usuario y Puntaje Guardados", "Tu usuario, contraseña y puntaje han sido guardados correctamente.");
                } else {
                    mostrarMensajeError("Error al Guardar Usuario y Puntaje", "Hubo un error al intentar guardar tu usuario, contraseña y puntaje.");
                }
            } else {
                mostrarMensajeError("Usuario no registrado", "Primero debes registrarte antes de iniciar la partida.");
            }
        });

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        scene.setFill(Color.CYAN);
        primaryStage.setTitle("EcoQuiz - Registro de Usuario");
        primaryStage.show();
    }

    private boolean verificarYGuardarUsuario(String username, String password) {
        try {
            Connection connection = conexion.getConnection();
            if (connection != null) {
                if (verificarUsuarioExistente(username, connection)) {
                    mostrarMensajeError("Usuario Existente", "El usuario ya está registrado. Por favor, elige otro.");
                    return false;
                }

                String query = "INSERT INTO usuario (Usuario, Contraseña) VALUES (?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    int rowsAffected = preparedStatement.executeUpdate();

                    return rowsAffected > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean verificarUsuarioRegistrado(String username, String password) {
        try {
            Connection connection = conexion.getConnection();
            if (connection != null) {
                return verificarUsuarioExistente(username, connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean verificarUsuarioExistente(String username, Connection connection) throws SQLException {
        String query = "SELECT * FROM usuario WHERE Usuario = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }

    private boolean guardarPuntaje(String usuario, int puntaje) {
        try {
            Connection connection = conexion.getConnection();
            if (connection != null) {
                String query = "INSERT INTO puntajes (usuario,puntaje) VALUES (?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, usuario);
                    preparedStatement.setInt(2, puntaje);
                    int rowsAffected = preparedStatement.executeUpdate();

                    return rowsAffected > 0;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private int obtenerPuntajeDelJuego() {
        int puntajeObtenido = preguntaActual * 5;
        String usuario = usernameField.getText();
        guardarPuntaje(usuario, puntajeObtenido);

        return puntajeObtenido;
    }

    private void reiniciarJuego(Stage primaryStage) {
        puntos = 0;
        preguntaActual = 0;
        primaryStage.close();
        start(new Stage());
    }

    private void mostrarMensajeExito(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
    }
     
     private void mostrarMensajeError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
