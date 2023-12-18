package orco;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Orco extends JFrame implements ActionListener, ItemListener {

   private Checkbox rGuerreros, rDioses, rTecnicas;
    private Button btnAceptar;
    private Label label2 = new Label("ESCRIBE UNA LETRA GUERRERO Z");
    private TextField txtLetras;
    private Font fuente;
    private int juegos_ganados = 0;
    private int juegos_perdidos = 0;
    private int error = 0;
    private int lugar = 0;
    private int maxErrores = 6; // Número máximo de errores permitidos
    private boolean habilitado = true;
    private char letra;

    String[] Guerreros = {"Gohan", "SonGoku", "Vegeta", "Piccolo", "AndroideDe", "AndroideNumeroDieciocho",
            "Krilin", "Tenshinhan", "KameSennin", "Freezer"};
    String[] Dioses = {"Arak", "Beerus", "Champa", "Geene", "Iwen", "Jerez", "Liquier", "Mosco", "Quitela", "Rhumoushi"};
    private String[] Tecnicas = {"LaOladeContenciondeMaldad", "ElagarredeDios", "KaioKen", "Genkidama", "Kamehameha",
            "UltraInstintoincompleto", "Regeneración", "Hakai", "LaDanzadelaFusión", "ElUltraInstintoCompleto"};

    private Image Img;
    private int numeroPalabra = 0;
    private String palabra = "";
    private String lineas = "";

    public static void main(String[] args) {
        Orco O = new Orco();
    }

    public Orco() {
        setSize(400, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add("North", crearRadios());
        add("South", crearObjetos());
        setTitle("ORCO");
        setVisible(true);
    }

    private Panel crearRadios() {
        Panel p = new Panel(new GridLayout(1, 3));

        CheckboxGroup grupo = new CheckboxGroup();
        rGuerreros = new Checkbox("GUERREROS Z", grupo, true);
        rDioses = new Checkbox("DIOSES", grupo, false);
        rTecnicas = new Checkbox("TECNICAS GOKU", grupo, false);

        rGuerreros.addItemListener(this);
        rDioses.addItemListener(this);
        rTecnicas.addItemListener(this);

        p.add(rGuerreros);
        p.add(rDioses);
        p.add(rTecnicas);

        return p;
    }

    private Panel crearObjetos() {
        Panel p = new Panel(new GridLayout(1, 3));

        btnAceptar = new Button("ACEPTAR");
        txtLetras = new TextField();
        label2 = new Label("ESCRIBE UNA LETRA");

        txtLetras.setVisible(true);
        btnAceptar.addActionListener(this);

        p.add(label2);
        p.add(txtLetras);
        p.add(btnAceptar);

        return p;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Img = Toolkit.getDefaultToolkit().getImage("C:/img/" + error + ".PNG");
        g.drawImage(Img, 100, 100, 200, 200, this);

        fuente = new Font("Garamond", Font.BOLD, 11);
        g.setFont(fuente);
        g.setColor(Color.ORANGE);
        g.drawString("GAMES WON:", 20, 80);

        fuente = new Font("Garamond", Font.BOLD, 11);
        g.setFont(fuente);
        g.setColor(Color.BLUE);
        g.drawString("LOST GAMES", 310, 80);

        g.drawString("" + juegos_ganados, 44, 95);
        g.drawString("" + juegos_perdidos, 333, 95);

        if (habilitado == true) {
            habilitado = false;
            numeroPalabra = (int) (Math.random() * 10);

            if (rGuerreros.getState()) {
                palabra = Guerreros[numeroPalabra];
                lineas = "";
            } else if (rDioses.getState()) {
                palabra = Dioses[numeroPalabra];
                lineas = "";
            } else if (rTecnicas.getState()) {
                palabra = Tecnicas[numeroPalabra];
                lineas = "";
            }

            for (int i = 0; i < palabra.length(); i++) {
                lineas += "_ ";
            }
        }

        g.drawString(lineas, 150, 400);
    }

    private void activarRadios(boolean act1) {
        rGuerreros.setEnabled(act1);
        rDioses.setEnabled(act1);
        rTecnicas.setEnabled(act1);
    }

    @Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btnAceptar) {
        String input = txtLetras.getText().trim().toUpperCase();
        
        if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
            // Mostrar alerta si no se ingresa una sola letra
            JOptionPane.showMessageDialog(this, "Por favor, ingresa una letra .", "Error", JOptionPane.ERROR_MESSAGE);
            txtLetras.setText("");
            txtLetras.requestFocus();
            return;
        }

        letra = input.charAt(0);
        txtLetras.setText("");
        txtLetras.requestFocus();
        activarRadios(false);

        StringBuffer lineasBuffer = new StringBuffer(lineas);

        boolean letraCorrecta = false;

        for (int i = 0; i < palabra.length(); i++) {
            if (letra == palabra.toUpperCase().charAt(i)) {
                lineasBuffer.replace(i * 2, i * 2 + 1, "" + letra);
                letraCorrecta = true;
            }
        }

        if (!letraCorrecta) {
            error++;
            if (error >= maxErrores) {
                String palabraCorrecta = palabra; // Guarda la palabra correcta antes de mostrar el mensaje
        if (JOptionPane.showConfirmDialog(this, "Jugar de nuevo", "Juego Perdido",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            error = 0;
            juegos_perdidos++;
            activarRadios(true);
            habilitado = true;
        } else {
            juegos_perdidos++;
            JOptionPane.showMessageDialog(null, "Ganados " + juegos_ganados + " Perdidos " + juegos_perdidos + "");
            JOptionPane.showMessageDialog(null, "La palabra correcta era: " + palabraCorrecta, "Palabra correcta", JOptionPane.INFORMATION_MESSAGE); // Muestra la palabra correcta
            System.exit(0);
        }
    }
}
            
        

        lineas = "" + lineasBuffer;

        if (!lineas.contains("_")) { 
            
            JOptionPane.showMessageDialog(this, "¡Has adivinado la palabra!", "Victoria", JOptionPane.INFORMATION_MESSAGE); //alerta de victoria
            if (JOptionPane.showConfirmDialog(this, "Volver a jugar", "Has ganado", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                error = 0;
                juegos_ganados++;
                activarRadios(true);
                habilitado = true;
            } else {
                juegos_ganados += 1;
                JOptionPane.showMessageDialog(null, "Ganados " + juegos_ganados + " Perdidos " + juegos_perdidos + "");
                System.exit(0);
            }
        }

        System.out.println("Lineas " + lineas);
        repaint();
    }
}


    @Override
    public void itemStateChanged(ItemEvent e) {
        if (habilitado) {
        habilitado = false;
        numeroPalabra = (int) (Math.random() * 10);

        if (rGuerreros.getState()) {
            palabra = Guerreros[numeroPalabra];
        } else if (rDioses.getState()) {
            lugar = (int) (Math.random() * 10);
            palabra = Dioses[lugar];
        } else if (rTecnicas.getState()) {
            lugar = (int) (Math.random() * 10);
            palabra = Tecnicas[lugar];
        }

        // Inicializar líneas después de seleccionar una palabra
        lineas = "";

        for (int i = 0; i < palabra.length(); i++) {
            lineas += "_ ";
        }
    }
}
    } 
