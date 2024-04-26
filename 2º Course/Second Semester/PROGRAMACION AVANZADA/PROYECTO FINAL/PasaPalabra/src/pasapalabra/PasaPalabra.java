import java.awt.*;
import javax.swing.*;
import pasapalabra.CircularLayout;

public class PasaPalabra extends JFrame {
    public PasaPalabra() {
        setTitle("Pasapalabra Rosco");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear los botones de letras
        JButton[] buttons = new JButton[26]; // 26 letras del alfabeto
        for (int i = 0; i < 26; i++) {
            char letter = (char) ('A' + i);
            buttons[i] = new JButton(String.valueOf(letter));
        }

        // Configurar el layout circular
        CircularLayout layout = new CircularLayout(150, 20);
        layout.setStartAngle(90); // Establecer el ángulo de inicio a 90 grados (parte superior)
        layout.setEndAngle(270); // Establecer el ángulo de fin a 270 grados (parte inferior)
        setLayout(layout);

        // Agregar los botones al contenedor
        for (JButton button : buttons) {
            add(button);
        }

        // Mover la letra "A" al principio del arreglo de componentes
        Component componentA = buttons[0];
        remove(componentA);
        add(componentA, 0);

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PasaPalabra pasapalabraRosco = new PasaPalabra();
            pasapalabraRosco.setVisible(true);
        });
    }
}
