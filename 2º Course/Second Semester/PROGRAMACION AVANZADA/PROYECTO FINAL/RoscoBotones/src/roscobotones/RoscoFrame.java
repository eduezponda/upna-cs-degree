
package roscobotones;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import static roscobotones.ClientRoscoFrame.sendMessage;


public final class RoscoFrame extends javax.swing.JFrame implements Runnable{

    private final Thread time;
    private static QuestionFrame questionFrame = null;
    private static final int MAXTIMEINSECONDS = 700;
    private Map<Character, JButton> mapaDeBotones;
    
    public RoscoFrame() {
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        inicialiseMap();
        disappearBorderButtons(mapaDeBotones);
        colorButtons();
        setResizable(false);
        setVisible(true);
        time = new Thread(this);
        time.start();
    }
    @Override
    public void run(){
        int min = 0, seg = 0;
        
        for(;;){
            try{
                seg++;
                if (seg > 59){
                    seg = 0;
                    min++;
                }
                if (seg == MAXTIMEINSECONDS)
                {
                    chronometer.setText(min + ":" + seg);
                    JOptionPane.showMessageDialog(null, "The time has finished");
                    sendMessage("GAME NOT FINISHED");
                    System.exit(0);
                }
                chronometer.setText(min + ":" + seg);
                Thread.sleep(999);
            }
            catch (InterruptedException e)
            {
                
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imagePasaPalabra = new javax.swing.JLabel();
        buttonA = new javax.swing.JButton();
        buttonB = new javax.swing.JButton();
        buttonC = new javax.swing.JButton();
        buttonD = new javax.swing.JButton();
        buttonE = new javax.swing.JButton();
        buttonF = new javax.swing.JButton();
        buttonG = new javax.swing.JButton();
        buttonH = new javax.swing.JButton();
        buttonI = new javax.swing.JButton();
        buttonJ = new javax.swing.JButton();
        buttonK = new javax.swing.JButton();
        buttonL = new javax.swing.JButton();
        buttonM = new javax.swing.JButton();
        buttonN = new javax.swing.JButton();
        buttonO = new javax.swing.JButton();
        buttonP = new javax.swing.JButton();
        buttonQ = new javax.swing.JButton();
        buttonR = new javax.swing.JButton();
        buttonS = new javax.swing.JButton();
        buttonT = new javax.swing.JButton();
        buttonU = new javax.swing.JButton();
        buttonV = new javax.swing.JButton();
        buttonW = new javax.swing.JButton();
        buttonX = new javax.swing.JButton();
        buttonY = new javax.swing.JButton();
        buttonZ = new javax.swing.JButton();
        chronometer = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        imagePasaPalabra.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imagePasaPalabra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/roscobotones/pasapalabra.jpeg"))); // NOI18N

        buttonA.setText("A");
        buttonA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAActionPerformed(evt);
            }
        });

        buttonB.setText("B");
        buttonB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBActionPerformed(evt);
            }
        });

        buttonC.setText("C");
        buttonC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCActionPerformed(evt);
            }
        });

        buttonD.setText("D");
        buttonD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDActionPerformed(evt);
            }
        });

        buttonE.setText("E");
        buttonE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEActionPerformed(evt);
            }
        });

        buttonF.setText("F");
        buttonF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonFActionPerformed(evt);
            }
        });

        buttonG.setText("G");
        buttonG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGActionPerformed(evt);
            }
        });

        buttonH.setText("H");
        buttonH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHActionPerformed(evt);
            }
        });

        buttonI.setText("I");
        buttonI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonIActionPerformed(evt);
            }
        });

        buttonJ.setText("J");
        buttonJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonJActionPerformed(evt);
            }
        });

        buttonK.setText("K");
        buttonK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonKActionPerformed(evt);
            }
        });

        buttonL.setText("L");
        buttonL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLActionPerformed(evt);
            }
        });

        buttonM.setText("M");
        buttonM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMActionPerformed(evt);
            }
        });

        buttonN.setText("N");
        buttonN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNActionPerformed(evt);
            }
        });

        buttonO.setText("O");
        buttonO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOActionPerformed(evt);
            }
        });

        buttonP.setText("P");
        buttonP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPActionPerformed(evt);
            }
        });

        buttonQ.setText("Q");
        buttonQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonQActionPerformed(evt);
            }
        });

        buttonR.setText("R");
        buttonR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRActionPerformed(evt);
            }
        });

        buttonS.setText("S");
        buttonS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSActionPerformed(evt);
            }
        });

        buttonT.setText("T");
        buttonT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTActionPerformed(evt);
            }
        });

        buttonU.setText("U");
        buttonU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonUActionPerformed(evt);
            }
        });

        buttonV.setText("V");
        buttonV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonVActionPerformed(evt);
            }
        });

        buttonW.setText("W");
        buttonW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonWActionPerformed(evt);
            }
        });

        buttonX.setText("X");
        buttonX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonXActionPerformed(evt);
            }
        });

        buttonY.setText("Y");
        buttonY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonYActionPerformed(evt);
            }
        });

        buttonZ.setText("Z");
        buttonZ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonZActionPerformed(evt);
            }
        });

        chronometer.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        chronometer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        chronometer.setText("0:0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(imagePasaPalabra, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(216, 216, 216)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(buttonU, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(buttonP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(71, 71, 71)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(buttonQ, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                                    .addComponent(buttonV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(buttonF, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                                    .addComponent(buttonK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(buttonA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(71, 71, 71)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(buttonB, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buttonG, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(buttonL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(78, 78, 78)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonW, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(buttonR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonM, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                                .addComponent(buttonC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(buttonZ, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(69, 69, 69)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(buttonI, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonN, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                            .addComponent(buttonS, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonX, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonT, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                            .addComponent(buttonO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonY, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonJ, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(113, 113, 113)
                        .addComponent(chronometer, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(imagePasaPalabra, javax.swing.GroupLayout.PREFERRED_SIZE, 250, Short.MAX_VALUE)
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonI, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonJ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chronometer, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(buttonQ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(buttonP, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonW, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonY, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(buttonZ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('A'));
    }//GEN-LAST:event_buttonAActionPerformed

    private void buttonBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('B'));
    }//GEN-LAST:event_buttonBActionPerformed

    private void buttonQActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonQActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('Q'));
    }//GEN-LAST:event_buttonQActionPerformed

    private void buttonSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('S'));
    }//GEN-LAST:event_buttonSActionPerformed

    private void buttonDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('D'));
    }//GEN-LAST:event_buttonDActionPerformed

    private void buttonCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('C'));
    }//GEN-LAST:event_buttonCActionPerformed

    private void buttonEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('E'));
    }//GEN-LAST:event_buttonEActionPerformed

    private void buttonFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonFActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('F'));
    }//GEN-LAST:event_buttonFActionPerformed

    private void buttonGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('G'));
    }//GEN-LAST:event_buttonGActionPerformed

    private void buttonHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('H'));
    }//GEN-LAST:event_buttonHActionPerformed

    private void buttonIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonIActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('I'));
    }//GEN-LAST:event_buttonIActionPerformed

    private void buttonJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonJActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('J'));
    }//GEN-LAST:event_buttonJActionPerformed

    private void buttonKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonKActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('K'));
    }//GEN-LAST:event_buttonKActionPerformed

    private void buttonLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('L'));
    }//GEN-LAST:event_buttonLActionPerformed

    private void buttonMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('M'));
    }//GEN-LAST:event_buttonMActionPerformed

    private void buttonNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('N'));
    }//GEN-LAST:event_buttonNActionPerformed

    private void buttonOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('O'));
    }//GEN-LAST:event_buttonOActionPerformed

    private void buttonPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('P'));
    }//GEN-LAST:event_buttonPActionPerformed

    private void buttonRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('R'));
    }//GEN-LAST:event_buttonRActionPerformed

    private void buttonTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('T'));
    }//GEN-LAST:event_buttonTActionPerformed

    private void buttonUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('U'));
    }//GEN-LAST:event_buttonUActionPerformed

    private void buttonVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonVActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('V'));
    }//GEN-LAST:event_buttonVActionPerformed

    private void buttonWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonWActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('W'));
    }//GEN-LAST:event_buttonWActionPerformed

    private void buttonXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonXActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('X'));
    }//GEN-LAST:event_buttonXActionPerformed

    private void buttonYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonYActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('Y'));
    }//GEN-LAST:event_buttonYActionPerformed

    private void buttonZActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonZActionPerformed
        if (isNotInstantiate(questionFrame))
        {
            try {
                questionFrame = instantiate();
            } catch (IOException ex) {
                Logger.getLogger(RoscoFrame.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        questionFrame.play(indexStringQuestions('Z'));
    }//GEN-LAST:event_buttonZActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RoscoFrame roscoFrame = new RoscoFrame();
        });
    }

    
    private int indexStringQuestions(char c) {
       return ((int) c - (int) 'A');
    }
    
    private boolean isNotInstantiate (QuestionFrame questionFrame){
        return questionFrame == null;
    }
    
    private QuestionFrame instantiate () throws IOException{
        try {
            return new QuestionFrame(this);
        } catch (IOException ex) {
            Logger.getLogger(RoscoFrame.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return null;
    }

    void disableBotton(int index) {
        mapaDeBotones.get((char)(index + (int) 'A')).setEnabled(false);
    }

    private void inicialiseMap() {
        mapaDeBotones = new HashMap<>();
        mapaDeBotones.put('A', buttonA);
        mapaDeBotones.put('B', buttonB);
        mapaDeBotones.put('C', buttonC);
        mapaDeBotones.put('D', buttonD);
        mapaDeBotones.put('E', buttonE);
        mapaDeBotones.put('F', buttonF);
        mapaDeBotones.put('G', buttonG);
        mapaDeBotones.put('H', buttonH);
        mapaDeBotones.put('I', buttonI);
        mapaDeBotones.put('J', buttonJ);
        mapaDeBotones.put('K', buttonK);
        mapaDeBotones.put('L', buttonL);
        mapaDeBotones.put('M', buttonM);
        mapaDeBotones.put('N', buttonN);
        mapaDeBotones.put('O', buttonO);
        mapaDeBotones.put('P', buttonP);
        mapaDeBotones.put('Q', buttonQ);
        mapaDeBotones.put('R', buttonR);
        mapaDeBotones.put('S', buttonS);
        mapaDeBotones.put('T', buttonT);
        mapaDeBotones.put('U', buttonU);
        mapaDeBotones.put('V', buttonV);
        mapaDeBotones.put('W', buttonW);
        mapaDeBotones.put('X', buttonX);
        mapaDeBotones.put('Y', buttonY);
        mapaDeBotones.put('Z', buttonZ);
    }

    private void colorButtons() {
        Color color = new Color(70, 130, 180);
        
        mapaDeBotones.entrySet().stream().map(entry -> {
            entry.getValue().setBackground(color);
            return entry;
        }).forEachOrdered((Map.Entry<Character, JButton> entry) -> {
            entry.getValue().setForeground(Color.WHITE);
        });
        
    }
    
    private void disappearBorderButtons(Map<Character, JButton> mapaDeBotones) {
        mapaDeBotones.entrySet().forEach(entry -> {
            entry.getValue().setFocusable(false);
        });
    }

    public void changeColourToGreen(int index) {
        mapaDeBotones.get((char)(index + (int) 'A')).setBackground(Color.GREEN);
    }

    public void changeColourToRed(int index) {
        mapaDeBotones.get((char)(index + (int) 'A')).setBackground(Color.RED);
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonA;
    private javax.swing.JButton buttonB;
    private javax.swing.JButton buttonC;
    private javax.swing.JButton buttonD;
    private javax.swing.JButton buttonE;
    private javax.swing.JButton buttonF;
    private javax.swing.JButton buttonG;
    private javax.swing.JButton buttonH;
    private javax.swing.JButton buttonI;
    private javax.swing.JButton buttonJ;
    private javax.swing.JButton buttonK;
    private javax.swing.JButton buttonL;
    private javax.swing.JButton buttonM;
    private javax.swing.JButton buttonN;
    private javax.swing.JButton buttonO;
    private javax.swing.JButton buttonP;
    private javax.swing.JButton buttonQ;
    private javax.swing.JButton buttonR;
    private javax.swing.JButton buttonS;
    private javax.swing.JButton buttonT;
    private javax.swing.JButton buttonU;
    private javax.swing.JButton buttonV;
    private javax.swing.JButton buttonW;
    private javax.swing.JButton buttonX;
    private javax.swing.JButton buttonY;
    private javax.swing.JButton buttonZ;
    private javax.swing.JLabel chronometer;
    private javax.swing.JLabel imagePasaPalabra;
    // End of variables declaration//GEN-END:variables


}
