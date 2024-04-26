
package roscobotones;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public final class QuestionFrame extends javax.swing.JFrame {
    
    private final int RANDOMSUFFLE = 4;
    
    private final String nameFile;
    private final String textoBaseDePreguntas;
    private final String[] renglones;
    private final int cantidadDePreguntas;
    private int index;

    private final String[][] baseDePreguntas;
    private final RoscoFrame frameFinal;
    private int n_pregunta;
    private int correct;

    private String[] preguntaEscogida;
    private String pregunta;
    private String respuesta;
    private String img;
    private final ArrayList<String> opciones = new ArrayList<>();


    public QuestionFrame(RoscoFrame frameFinal) throws IOException {
        this.n_pregunta = this.correct = 0;
        this.nameFile = "questions.csv";
        this.frameFinal = frameFinal;
        
        ReadFromCSV readFromCSV = new ReadFromCSV(nameFile);
        textoBaseDePreguntas = readFromCSV.getText();
        renglones = textoBaseDePreguntas.split("\n");
        cantidadDePreguntas = renglones.length;

        baseDePreguntas = new String[cantidadDePreguntas][5];
        
        for (int i = 0; i < cantidadDePreguntas; i++) 
        {
            baseDePreguntas[i] = renglones[i].split("\t");
            
        }
        initComponents();
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        disappearBorderButtons();
    }
    public void play(int indexQuestion) {
        index = indexQuestion;
        chooseQuestion(index);
        showQuestion();
    } 
    
    public void chooseQuestion(int n) {
        preguntaEscogida = baseDePreguntas[n];
        pregunta = preguntaEscogida[0];
        respuesta = preguntaEscogida[1];
        if (preguntaEscogida.length > 5) 
        {
            img = preguntaEscogida[5];
        } 
        else
        {
            img = "";
        }
        opciones.clear();
        for (int i = 1; i < 5; i++) 
        {
            opciones.add(preguntaEscogida[i]);
        }
        for (int i = 0; i < RANDOMSUFFLE; i++) 
        {
            Collections.shuffle(opciones);
        }
    }
    public void showQuestion() {
        setVisible(true);
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setText(pregunta);
        if (img.equals("")) 
        {
            imageLabel.setVisible(false);
        } 
        else
        {
            imageLabel.setVisible(true);
            imageLabel.setText("");
            try {
                BufferedImage imagen = ImageIO.read(new URL(img));
                Image imagenEscalada = imagen.getScaledInstance(-1, 200,
                                       Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(imagenEscalada));
            } catch (IOException e) {
                imageLabel.setText("La imágen no se pudo cargar");
                imageLabel.setIcon(null);
            }
        }
        buttonOptionA.setText(opciones.get(0));
        buttonOptionB.setText(opciones.get(1));
        buttonOptionC.setText(opciones.get(2));
        buttonOptionD.setText(opciones.get(3));
    }
    void chooseResponse(int n) throws IOException, InterruptedException {
        if (opciones.get(n).equals(respuesta))
        {
            JOptionPane.showMessageDialog(
                    this,
                    "Your answer is correct",
                    "Very good :)",
                    JOptionPane.INFORMATION_MESSAGE
            );
            correct++;
            frameFinal.disableBotton(index);
            frameFinal.changeColourToGreen(index);
        } 
        else 
        {
            JOptionPane.showMessageDialog(
                    this,
                    "Your answer is wrong, the correct answer is: " + respuesta,
                    "Very bad :(",
                    JOptionPane.ERROR_MESSAGE
            );
            frameFinal.disableBotton(index);
            frameFinal.changeColourToRed(index);
        }
        n_pregunta++;
        dispose();
        if (n_pregunta == cantidadDePreguntas)
        {
            JOptionPane.showMessageDialog(
                    this,
                    "Game finished. Success "
                    + "rate: " + correct * 100 / n_pregunta,
                    "%" + "Very good :)",
                    JOptionPane.PLAIN_MESSAGE
            );
            ClientRoscoFrame.sendMessage("GAME: " + correct);
            ClientRoscoFrame.receiveMessageFinal();
        }
    }   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        optionsLabel = new javax.swing.JPanel();
        buttonOptionB = new javax.swing.JButton();
        buttonOptionC = new javax.swing.JButton();
        buttonOptionD = new javax.swing.JButton();
        buttonOptionA = new javax.swing.JButton();
        buttonPasaPalabra = new javax.swing.JButton();
        imageLabel = new javax.swing.JLabel();
        questionLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        buttonOptionB.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        buttonOptionB.setText("Option B");
        buttonOptionB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOptionBActionPerformed(evt);
            }
        });

        buttonOptionC.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        buttonOptionC.setText("Option C");
        buttonOptionC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOptionCActionPerformed(evt);
            }
        });

        buttonOptionD.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        buttonOptionD.setText("Option D");
        buttonOptionD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOptionDActionPerformed(evt);
            }
        });

        buttonOptionA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        buttonOptionA.setText("Option A");
        buttonOptionA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOptionAActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout optionsLabelLayout = new javax.swing.GroupLayout(optionsLabel);
        optionsLabel.setLayout(optionsLabelLayout);
        optionsLabelLayout.setHorizontalGroup(
            optionsLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(buttonOptionC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(buttonOptionD, javax.swing.GroupLayout.DEFAULT_SIZE, 991, Short.MAX_VALUE)
            .addComponent(buttonOptionB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(buttonOptionA, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        optionsLabelLayout.setVerticalGroup(
            optionsLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsLabelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(buttonOptionA, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonOptionB, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonOptionC, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonOptionD, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                .addContainerGap())
        );

        buttonPasaPalabra.setText("PASAPALABRA");
        buttonPasaPalabra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPasaPalabraActionPerformed(evt);
            }
        });

        imageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageLabel.setText("Image");

        questionLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        questionLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        questionLabel.setText("Question");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(optionsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonPasaPalabra)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(questionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(imageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(questionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(imageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                .addGap(26, 26, 26)
                .addComponent(buttonPasaPalabra, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(optionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonOptionAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOptionAActionPerformed
        try {
            try {
                chooseResponse(0);
            } catch (InterruptedException ex) {
                Logger.getLogger(QuestionFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(QuestionFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonOptionAActionPerformed

    private void buttonOptionCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOptionCActionPerformed
        try {
            chooseResponse(2);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(QuestionFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonOptionCActionPerformed

    private void buttonOptionBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOptionBActionPerformed
        try {
            try {
                chooseResponse(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(QuestionFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(QuestionFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonOptionBActionPerformed

    private void buttonOptionDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOptionDActionPerformed
        try {
            try {
                chooseResponse(3);
            } catch (InterruptedException ex) {
                Logger.getLogger(QuestionFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(QuestionFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonOptionDActionPerformed

    private void buttonPasaPalabraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPasaPalabraActionPerformed
        dispose();
    }//GEN-LAST:event_buttonPasaPalabraActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonOptionA;
    private javax.swing.JButton buttonOptionB;
    private javax.swing.JButton buttonOptionC;
    private javax.swing.JButton buttonOptionD;
    private javax.swing.JButton buttonPasaPalabra;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JPanel optionsLabel;
    private javax.swing.JLabel questionLabel;
    // End of variables declaration//GEN-END:variables

    private void disappearBorderButtons() {
        buttonOptionA.setFocusable(false);
        buttonOptionB.setFocusable(false);
        buttonOptionC.setFocusable(false);
        buttonOptionD.setFocusable(false);
        buttonPasaPalabra.setFocusable(false);
        
    }
}
