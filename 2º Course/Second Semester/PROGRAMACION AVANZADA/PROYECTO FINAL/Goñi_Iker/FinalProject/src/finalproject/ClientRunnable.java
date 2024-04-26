package finalproject;

import java.awt.Dimension;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ikergo55
 */
public class ClientRunnable extends javax.swing.JFrame {

    Socket socket;
    final int PORT = 5000;
    InetAddress ipAddress;
    DrawPanel drawPanel;
    Map <String, Team> teams;
    String myTeam;
    String teamPlaying;
    String enemyTeam;
    int hasTheBall;
    Ball ball;
    Scoreboard scoreboard;
    Clock time;
    boolean local;
    DataInputStream in;
    DataOutputStream out;
    
    public ClientRunnable() throws IOException {
        ipAddress = InetAddress.getByName("localhost");
        socket = new Socket(ipAddress, PORT);
        
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        
        sendMessage("READY");
        String[] parts = rvMessage().split(" ");
        
        if (parts[0].equals("START"))
            myTeam = parts[1];
        else
            System.exit(-1);
        local = parts[2].equals(parts[1]);
        
        Utils.sleepMillis(250);
        
        drawPanel = new DrawPanel();
        teams = new HashMap <>();
        teams.put("ESPAÑA", new Team());
        teams.put("BRASIL", new Team());
        teams.put("ARGENTINA", new Team());
        teamPlaying = "ESPAÑA";
        enemyTeam = "BRASIL";
        scoreboard = new Scoreboard(teamPlaying, enemyTeam);
        hasTheBall = 1;
        time = new Clock(false, scoreboard);
        time.start(0, 1000);
        teams.get(teamPlaying).init(teamPlaying, 500, 300, true);
        teams.get(enemyTeam).init(enemyTeam, 500, 300, false);
        teams.get(teamPlaying).get(hasTheBall).setHasTheBall(true);
        ball = new Ball(teams.get(teamPlaying).get(hasTheBall));
        drawPanel.init(ball, scoreboard, teams, enemyTeam, false);
        drawPanel.setMinimumSize( new Dimension(500,  300));
        setResizable(true);
        initComponents();
        
        initLecture();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainPanel = drawPanel;

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        MainPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                MainPanelMouseDragged(evt);
            }
        });
        MainPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MainPanelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout MainPanelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MainPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MainPanelMouseClicked
        String s = "CLICKED " + myTeam + " " + resetProportion(evt.getX()) 
                + " " + resetProportion(evt.getY());
        try {
            sendMessage(s);
        } catch (IOException ex) {
            Logger.getLogger(ClientRunnable.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_MainPanelMouseClicked

    private void MainPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MainPanelMouseDragged
        List<Integer> l = new ArrayList<>();
        l.add(evt.getX());
        l.add(evt.getY());
        l.add(100);
        drawPanel.addShootLine(l);
        String s = "DRAGGED " + myTeam + " " + resetProportion(evt.getX()) 
                + " " + resetProportion(evt.getY());
        try {
            sendMessage(s);
        } catch (IOException ex) {
            Logger.getLogger(ClientRunnable.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_MainPanelMouseDragged

    public static void main(String args[]) throws IOException {
        ClientRunnable client = new ClientRunnable();
        client.setLocationRelativeTo(null);
        client.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MainPanel;
    // End of variables declaration//GEN-END:variables
    private void sendMessage(String msg) throws IOException {
        out.writeUTF(msg); 
        out.flush();
    }
    
    private String rvMessage() throws IOException {
        return in.readUTF();
    }
    
    private int setProportion(int value) {
        return value * drawPanel.getWidth() / 500;
    }
    
    private int resetProportion(int value) {
        return value * 500 / drawPanel.getWidth();
    }

    private void initLecture() {
        Thread Reading = new Thread( () -> {
            int k = 0;
            Utils.sleepMillis(100);
            while (!Thread.interrupted()) {
                try {
                    String line = rvMessage();
                    String[] parts = line.split(" ");
                    switch(parts[0]) {
                        case "BALL": {
                            ball.setX(setProportion(Integer.parseInt(parts[1])));
                            ball.setY(setProportion(Integer.parseInt(parts[2])));
                            break;
                        }
                        case "PLAYER": {
                            teams.get(parts[1]).get(Integer.parseInt(parts[2]))
                                    .setX(setProportion(Integer.parseInt(parts[3])));
                            teams.get(parts[1]).get(Integer.parseInt(parts[2]))
                                    .setY(setProportion(Integer.parseInt(parts[4])));
                            
                            if (k > 3 && !drawPanel.isTimePause()) {
                                teams.get(parts[1]).get(Integer.parseInt(parts[2]))
                                        .selectImage((int)(Math.random()*2),
                                                setProportion(Integer.parseInt(parts[4])), 500);
                                k = 0;
                            }
                            else if (!drawPanel.isTimePause())
                                k++;
                            
                            break;
                        }
                        case "GOALKEEPER": {
                            teams.get(parts[1]).getGoalkeeper()
                                    .setX(setProportion(Integer.parseInt(parts[2])));
                            teams.get(parts[1]).getGoalkeeper()
                                    .setY(setProportion(Integer.parseInt(parts[3])));
                            break;
                        }
                        case "MOVEMENT": {
                            drawPanel.setMovement(Integer.parseInt(parts[1]));
                            break;
                        }
                        case "TIMEPAUSE": {
                            drawPanel.setTimePause(parts[1].equals("TRUE"));
                            break;
                        }
                        case "REINIT": {
                            teams.get(parts[1]).reinit(true);
                            teams.get(parts[2]).reinit(false);
                            break;
                        }
                        case "SHOOT": {
                            teams.get(parts[1]).getGoalkeeper()
                                    .setShoot(Boolean.parseBoolean(parts[2])
                                            , Integer.parseInt(parts[3]));
                            break;
                        }
                        case "SCORE": {
                            if (parts[1].equals(myTeam) && local 
                                    || (!parts[1].equals(myTeam) && !local))
                                scoreboard.setNumber1(String.valueOf(scoreboard.getNumber1()+1));
                            else
                                scoreboard.setNumber2(String.valueOf(scoreboard.getNumber2()+1));
                            break;
                        }
                        case "WIN": {
                            System.out.println(line);
                            System.exit(0);
                            break;
                        }
                        case "TIE": {
                            System.out.println(line);
                            System.exit(0);
                            break;
                        }
                        default: {
                            System.out.println("Mensaje no entendido");
                            break;
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ClientRunnable.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            }
        });
        Reading.start();
        Reading.setName("Reading");
        Reading.setPriority(Thread.MAX_PRIORITY);
    }
}
