package rosco;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Rosco {
    private NewJFrame juegoRoscoUI;
    
    public Rosco() {
        
        juegoRoscoUI = new NewJFrame();
        // Agregar ActionListener al botón "Enviar"
        juegoRoscoUI.addEnviarActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener la respuesta ingresada por el jugador
                String respuesta = juegoRoscoUI.getRespuesta();
                
                // Procesar la respuesta (lógica del juego)
                // ...
            }
        });
        
        // Agregar ActionListener al botón "Siguiente"
        juegoRoscoUI.addSiguienteActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Procesar el evento de siguiente pregunta (lógica del juego)
                // ...
            }
        });
        
        // Agregar ActionListener al botón "Reiniciar"
        juegoRoscoUI.addReiniciarActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Procesar el evento de reiniciar el juego (lógica del juego)
                // ...
            }
        });
        
    }
    
    // Método para iniciar la aplicación
    public void iniciar() {
        // Configurar la categoría y pregunta inicial (lógica del juego)
        juegoRoscoUI.setCategoria("Geografía");
        juegoRoscoUI.setPregunta("¿Cuál es la capital de Francia?");
        
        // Iniciar la interfaz de usuario
        juegoRoscoUI.setVisible(true);
    }
    
    public static void main(String[] args) {
        Rosco juegoRoscoApp = new Rosco();
        juegoRoscoApp.iniciar();
    }
}
