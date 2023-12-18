package calculadora;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame implements ActionListener {

    // Componentes de la calculadora
    private JTextField pantalla;
    private JButton[] botones;
    private JPanel panel;
    private String operador;
    private double num1, num2, resultado;
// Constructor
    public Calculadora() {
        pantalla = new JTextField(10);
        pantalla.setEditable(false);
        operador = "";
        num1 = num2 = resultado = 0;

        // Crear botones
        botones = new JButton[16];
        String[] nombresBotones = {
            "7", "8", "9", "+",
            "4", "5", "6", "-",
            "1", "2", "3", "*",
            "0", ".", "=", "/"
        };

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));

        for (int i = 0; i < 16; i++) {
            botones[i] = new JButton(nombresBotones[i]);
            botones[i].addActionListener(this);
            panel.add(botones[i]);
        }

        // Agregar componentes al JFrame
        add(pantalla, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        // Configurar JFrame
        setTitle("Calculadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    // Manejar eventos de botones
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.matches("[0-9]")) {
            pantalla.setText(pantalla.getText() + comando);
        } else if (comando.equals(".")) {
            if (!pantalla.getText().contains(".")) {
                pantalla.setText(pantalla.getText() + comando);
            }
        } else if (comando.matches("[+\\-*/]")) {
            if (!operador.isEmpty()) {
                calcular();
                operador = comando;
                num1 = resultado;
                pantalla.setText("");
            } else {
                operador = comando;
                num1 = Double.parseDouble(pantalla.getText());
                pantalla.setText("");
            }
        } else if (comando.equals("=")) {
            calcular();
            operador = "";
        }
    }

    // Realizar cálculos
    private void calcular() {
        num2 = Double.parseDouble(pantalla.getText());

        switch (operador) {
            case "+":
                resultado = num1 + num2;
                break;
            case "-":
                resultado = num1 - num2;
                break;
            case "*":
                resultado = num1 * num2;
                break;
            case "/":
                if (num2 != 0) {
                    resultado = num1 / num2;
                } else {
                    pantalla.setText("Error");
                }
                break;
        }

        pantalla.setText(String.valueOf(resultado));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Calculadora().setVisible(true);
            }
        });
    }
    
}
