package ecogame;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JuegoVentana extends Application {
    private int puntos = 0;
    private int preguntaActual = 0;
    private Pregunta[] preguntas = {
            new Pregunta("¿Cuántas patas tiene una araña?", "6", "8", 2),
            new Pregunta("¿Cuál es el mamífero más grande del mundo?", "Ballena Azul", "Elefante", 1),
            new Pregunta("¿Cuál de los siguientes animales es un mamífero?", "Tortuga", "Pingüino", 3),
            new Pregunta("¿Cuál es el hábitat natural de los koalas?", "Desierto", "Bosque de eucaliptos", 2),
            new Pregunta("¿Cuál es el mayor contribuyente a la contaminación plástica en los océanos?", "Botellas de vidrio", "Envases de plástico", 2),
            new Pregunta("¿Cuál es el principal gas responsable del calentamiento global?", "Oxígeno", "Dióxido de carbono", 2),
            new Pregunta("¿Qué especie está en peligro crítico de extinción debido a la caza furtiva?", "Elefante", "Jirafa", 1),
            new Pregunta("¿Cuál de las siguientes acciones ayuda a conservar el agua?", "Dejar el grifo abierto mientras te lavas los dientes", "Reparar las fugas de agua", 2),
            new Pregunta("¿Cuál es el animal más grande del planeta?", "Elefante", "Ballena Azul", 2),
            new Pregunta("¿Qué porcentaje de la Tierra está cubierto por océanos?", "50%", "75%", 2),
            new Pregunta("¿Cuál de las siguientes aves no vuela?", "Águila", "Pingüino", 2),
            new Pregunta("¿Qué animal es símbolo de la conservación?", "Panda rojo", "Tigre", 1),
            new Pregunta("¿Cuál es la principal causa de la pérdida de biodiversidad?", "Contaminación del aire", "Deforestación", 2),
            new Pregunta("¿Cuál es el proceso natural que convierte la luz del sol en energía para las plantas?", "Fotosíntesis", "Respiración", 1),
            new Pregunta("¿Qué gas es esencial para todas las formas de vida en la Tierra?", "Oxígeno", "Dióxido de carbono", 1),
            new Pregunta("¿Cuál es la función principal de las abejas en el ecosistema?", "Polinizar plantas", "Producir miel", 1),
            new Pregunta("¿Cuál es el mayor consumidor de energía en un hogar promedio?", "Iluminación", "Refrigerador", 1),
            new Pregunta("¿Cuál de las siguientes acciones ayuda a reducir la huella de carbono?", "Conducir un automóvil grande", "Usar transporte público", 2),
            new Pregunta("¿Cuál es el mayor peligro para las poblaciones de tigres en la actualidad?", "Cambio climático", "Caza furtiva", 2),
            new Pregunta("¿Cuál de los siguientes animales está en peligro de extinción debido a la pérdida de su hábitat?", "Panda rojo", "Rinoceronte", 1),
            new Pregunta("¿Qué se considera un recurso renovable?", "Petróleo", "Energía solar", 2),
            new Pregunta("¿Cuál de las siguientes acciones ayuda a preservar la diversidad biológica?", "Monocultivo", "Reforestación", 2),
    };
    
    public static void main(String[] args) {
        launch(args);
    }

     @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("EcoQuiz - Juego Ambiental");

        ImageView imageView = new ImageView(new Image("https://cloud.educaplay.com/recursos/212/6815871/imagen_1_1599459394.jpg"));
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(primaryStage.widthProperty().multiply(0.8)); // Ajustar al 80% del ancho de la ventana

        Button btnComenzar = new Button("Comenzar EcoQuiz");
        btnComenzar.setOnAction(e -> mostrarPregunta(primaryStage));

        VBox buttonBox = new VBox(20);
        buttonBox.getChildren().addAll(btnComenzar);
        buttonBox.setAlignment(Pos.CENTER);

        StackPane imagePane = new StackPane(imageView);
        StackPane.setAlignment(buttonBox, Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setTop(imagePane);
        root.setCenter(buttonBox);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public void mostrarPregunta(Stage primaryStage) {
        if (preguntaActual < preguntas.length && primaryStage != null) {
            Pregunta pregunta = preguntas[preguntaActual];

            Label preguntaLabel = new Label(pregunta.getEnunciado());
            Button btnOpcion1 = new Button(pregunta.getOpcion1());
            Button btnOpcion2 = new Button(pregunta.getOpcion2());

            btnOpcion1.setOnAction(e -> verificarRespuesta(pregunta, pregunta.getOpcion1(), primaryStage));
            btnOpcion2.setOnAction(e -> verificarRespuesta(pregunta, pregunta.getOpcion2(), primaryStage));

            GridPane juegoLayout = new GridPane();
            juegoLayout.setVgap(10);
            juegoLayout.setAlignment(Pos.CENTER);

            juegoLayout.add(preguntaLabel, 0, 0, 2, 1);
            juegoLayout.add(btnOpcion1, 0, 1);
            juegoLayout.add(btnOpcion2, 1, 1);

            VBox layout = new VBox(20);
            layout.getChildren().addAll(juegoLayout);
            layout.setAlignment(Pos.CENTER);

            BorderPane root = new BorderPane();
            root.setPadding(new Insets(20));
            root.setTop(new ImageView(new Image("https://cloud.educaplay.com/recursos/212/6815871/imagen_1_1599459394.jpg")));
            root.setCenter(layout);

            Scene scene = new Scene(root, 800, 600);
            primaryStage.setScene(scene);
        } else {
            mostrarFinDelJuego(primaryStage);
        }
    }

    private void verificarRespuesta(Pregunta pregunta, String opcionSeleccionada, Stage primaryStage) {
        if (pregunta.esOpcionCorrecta(opcionSeleccionada)) {
            puntos += 5;
        } else {
            // puntos -= 2;
        }

        preguntaActual++;
        mostrarPregunta(primaryStage);
        primaryStage.sizeToScene(); // Ajustar tamaño de la ventana
    }

    private void mostrarFinDelJuego(Stage primaryStage) {
        if (primaryStage != null) {
            String mensaje = "Fin del juego. Puntos obtenidos: " + puntos;
            if (puntos < 0) {
                mensaje += "\nPerdiste puntos. ¡Mejor suerte la próxima vez!";
            } else if (puntos == 0) {
                mensaje += "\nNo ganaste ni perdiste puntos. ¡Inténtalo de nuevo para mejorar!";
            } else {
                mensaje += "\n¡Felicidades! Ganaste puntos. ¡Buen trabajo!";
            }

            Label finDelJuegoLabel = new Label(mensaje);
            Button btnVolverAlInicio = new Button("Volver al Inicio");
            btnVolverAlInicio.setOnAction(e -> reiniciarJuego(primaryStage));

            Label puntosLabel = new Label("Puntos: " + puntos);
            HBox puntosLayout = new HBox();
            puntosLayout.getChildren().add(puntosLabel);
            puntosLayout.setAlignment(Pos.CENTER);

            HBox finLayout = new HBox();
            finLayout.getChildren().addAll(finDelJuegoLabel, puntosLayout, btnVolverAlInicio);
            finLayout.setAlignment(Pos.CENTER);

            BorderPane root = new BorderPane();
            root.setPadding(new Insets(20));
            root.setCenter(finLayout);

            Scene scene = new Scene(root, 800, 600);
            primaryStage.setScene(scene);
        }
    }

    private void reiniciarJuego(Stage primaryStage) {
        puntos = 0;
        preguntaActual = 0;
        primaryStage.close(); // Cerrar la ventana actual
        start(new Stage()); // Crear una nueva instancia de la ventana
    }
}