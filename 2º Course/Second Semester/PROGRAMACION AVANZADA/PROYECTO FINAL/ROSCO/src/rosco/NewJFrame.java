/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rosco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewJFrame extends JFrame {

    private JLabel lblCategoria;
    private JLabel lblPregunta;
    private JTextField txtRespuesta;
    private JButton btnEnviar;
    private JButton btnSiguiente;
    private JButton btnReiniciar;
    
    public NewJFrame() {
        // Configurar el marco principal
        setTitle("Juego del Rosco");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Crear y configurar los componentes de la interfaz de usuario
        lblCategoria = new JLabel("Categoría: ");
        lblPregunta = new JLabel("Pregunta: ");
        txtRespuesta = new JTextField(20);
        btnEnviar = new JButton("Enviar");
        btnSiguiente = new JButton("Siguiente");
        btnReiniciar = new JButton("Reiniciar");
        
        // Agregar los componentes al marco principal
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout());
        panelSuperior.add(lblCategoria);
        panelSuperior.add(lblPregunta);
        add(panelSuperior, BorderLayout.NORTH);
        
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new FlowLayout());
        panelCentral.add(txtRespuesta);
        panelCentral.add(btnEnviar);
        add(panelCentral, BorderLayout.CENTER);
        
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new FlowLayout());
        panelInferior.add(btnSiguiente);
        panelInferior.add(btnReiniciar);
        add(panelInferior, BorderLayout.SOUTH);
        
        // Configurar el tamaño y visibilidad del marco principal
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void addEnviarActionListener(ActionListener listener) {
        btnEnviar.addActionListener(listener);
    }
    public void addSiguienteActionListener(ActionListener listener) {
        btnSiguiente.addActionListener(listener);
    }
    
    public void addReiniciarActionListener(ActionListener listener) {
        btnReiniciar.addActionListener(listener);
    }
    
    // Métodos para obtener la respuesta ingresada por el jugador
    public String getRespuesta() {
        return txtRespuesta.getText();
    }
    
    public void limpiarRespuesta() {
        txtRespuesta.setText("");
    }
    
    // Métodos para actualizar la categoría y la pregunta mostradas
    public void setCategoria(String categoria) {
        lblCategoria.setText("Categoría: " + categoria);
    }
    
    public void setPregunta(String pregunta) {
        lblPregunta.setText("Pregunta: " + pregunta);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
