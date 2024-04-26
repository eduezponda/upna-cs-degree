package finalproject;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ikergo55
 */
public class LocalExecutor extends javax.swing.JFrame {

    DrawPanel drawPanel;
    Map <String, Team> teams;
    String teamPlaying;
    String enemyTeam;
    static String local;
    static String visita;
    String goalkeeperTeam;
    boolean Local;
    String otherTeam;
    int hasTheBall;
    Ball ball;
    Thread toStop;
    static Scoreboard scoreboard;
    Clock time;
    boolean passInProcess;
    
    public LocalExecutor() throws IOException {
        
        drawPanel = new DrawPanel();
        teams = new HashMap <>();
        teams.put("ESPAÑA", new Team());
        teams.put("BRASIL", new Team());
        teams.put("ARGENTINA", new Team());
        teamPlaying = "ESPAÑA";
        enemyTeam = "BRASIL";
        otherTeam = null;
        goalkeeperTeam = enemyTeam;
        scoreboard = new Scoreboard(teamPlaying, enemyTeam);
        hasTheBall = 1;
        local = teamPlaying;
        visita = enemyTeam;
        Local = true;
        time = new Clock(false, scoreboard);
        time.start(0, 1000);
        passInProcess = false;
        initTeams();
        ball = new Ball(teams.get(teamPlaying).get(hasTheBall));
        drawPanel.init(ball, scoreboard, teams, visita, true);
        drawPanel.setMinimumSize( new Dimension(500,  300));
        setResizable(true);
        initComponents();
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
        if (teamPlaying != null) {
            if (toStop != null) {
                toStop.stop();
                toStop = null;
            }
            
            passInProcess = !ball.inPlayer(drawPanel.getWidth());
            
            int index;
            if ((index = playerCliked(evt)) != -1 && !passInProcess) {
                teams.get(teamPlaying).get(index).setHasTheBall(true);
                teams.get(teamPlaying).get(hasTheBall).setHasTheBall(false);
                hasTheBall = index;
                ball.setP(teams.get(teamPlaying).get(hasTheBall));
                passInProcess = true;
                return;
            }
            toStop = teams.get(teamPlaying).get(hasTheBall).moveTo(evt.getX(), evt.getY(), drawPanel.getWidth());
        }
    }//GEN-LAST:event_MainPanelMouseClicked

    private void MainPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MainPanelMouseDragged
        List<Integer> l = new ArrayList<>();
        l.add(evt.getX());
        l.add(evt.getY());
        l.add(50);
        
        if (toStop != null) {
            toStop.stop();
            toStop = null;
        }
        if (drawPanel.isEmptyShoot() && ball.near(evt.getX(), evt.getY(), drawPanel.getWidth()) && teamPlaying != null) {
            drawPanel.setCorrectShoot(true);
            drawPanel.addShootLine(l);
            ball.setP(null);
            otherTeam = teamPlaying;
            teamPlaying = null;
            teams.get(otherTeam).get(hasTheBall).setHasTheBall(false);
            teams.get(enemyTeam).shootFrom(evt.getX(), evt.getY());
        }
        else
            drawPanel.addShootLine(l);
    }//GEN-LAST:event_MainPanelMouseDragged

    public static void main(String args[]) throws IOException {
        LocalExecutor server = new LocalExecutor();
        server.setLocationRelativeTo(null);
        server.setVisible(true);
        
        while (!scoreboard.difGreaterThan1()){
            
        }
        
        if (scoreboard.getNumber1() > scoreboard.getNumber2())
            System.out.println(local + " WIN");
        else if (scoreboard.getNumber1() != scoreboard.getNumber2())
            System.out.println(visita + " WIN");
        else
            System.out.println("TIE");
        
        System.exit(0);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MainPanel;
    // End of variables declaration//GEN-END:variables

    private int playerCliked(java.awt.event.MouseEvent evt) {
        return teams.get(teamPlaying).clicked(evt.getX(), evt.getY(), drawPanel.getWidth());
    }

    private void initTeams() {
        teams.get(teamPlaying).init(teamPlaying, 500, 300, true);
        teams.get(enemyTeam).init(enemyTeam, 500, 300, false);
        teams.get(teamPlaying).get(hasTheBall).setHasTheBall(true);
        
        Thread EnemyTeamSteal = new Thread( () -> {
            Utils.sleepMillis(100);
            while (!Thread.interrupted()) {
                
                Utils.sleepMillis(200);
                if (teamPlaying != null) {
                    for (Player p : teams.get(enemyTeam).getPlayers()) {
                        if (p.stealBall(ball.getX(), ball.getY(), drawPanel.getWidth())) {
                            p.setHasTheBall(true);
                            teams.get(teamPlaying).get(hasTheBall).setHasTheBall(false);
                            hasTheBall = teams.get(enemyTeam).getPlayers().indexOf(p);
                            ball.setP(p);
                            String aux = enemyTeam;
                            enemyTeam = teamPlaying;
                            teamPlaying = aux;
                            Utils.sleepMillis(500);
                            break;
                        }
                    }
                }
                else {
                    if (!drawPanel.isCorrectShoot()) {
                        Utils.sleepMillis(500);
                        for (Player p : teams.get(enemyTeam).getPlayers()) {
                            if (p.stealBall(ball.getX(), ball.getY(), drawPanel.getWidth())) {
                                drawPanel.setHasToStop(true);
                                p.setHasTheBall(true);
                                hasTheBall = teams.get(enemyTeam).getPlayers().indexOf(p);
                                ball.setP(p);
                                teamPlaying = enemyTeam;
                                enemyTeam = otherTeam;
                                Utils.sleepMillis(500);
                                break;
                            }
                        }
                        for (Player p : teams.get(otherTeam).getPlayers()) {
                            if (p.stealBall(ball.getX(), ball.getY(), drawPanel.getWidth())) {
                                drawPanel.setHasToStop(true);
                                p.setHasTheBall(true);
                                hasTheBall = teams.get(otherTeam).getPlayers().indexOf(p);;
                                ball.setP(p);
                                teamPlaying = otherTeam;
                                Utils.sleepMillis(500);
                                break;
                            }
                        }
                    }
                }
                
                if (drawPanel.isReinicio()) {
                    Utils.sleepMillis(1250);
                    if (drawPanel.isGol()) {
                        if (Local)
                            scoreboard.setNumber1(String.valueOf(scoreboard.getNumber1() + 1));
                        else
                            scoreboard.setNumber2(String.valueOf(scoreboard.getNumber2() + 1));
                    }
                    if (toStop != null) {
                        toStop.stop();
                        toStop = null;
                    }
                    teamPlaying = visita;
                    enemyTeam = local;
                    String aux = local;
                    local = visita;
                    visita = aux;
                    Local = !Local;
                    teams.get(teamPlaying).reinit(true);
                    teams.get(enemyTeam).reinit(false);
                    hasTheBall = 1;
                    teams.get(teamPlaying).get(hasTheBall).setHasTheBall(true);
                    ball.reinit(teams.get(teamPlaying).get(hasTheBall));
                    drawPanel.reinit(enemyTeam);
                    drawPanel.setGol(false);
                    drawPanel.setReinicio(false);
                    passInProcess = false;
                    goalkeeperTeam = enemyTeam;
                }
            }
        });
        Thread goalkeeper = new Thread( () -> {
            Utils.sleepMillis(100);
            
            while (!Thread.interrupted()) {
                
                Utils.sleepMillis(200);
                Goalkeeper g = teams.get(goalkeeperTeam).getGoalkeeper();
                
                if (g.stealBall(ball.getX(), ball.getY(), drawPanel.getWidth())) {
                    drawPanel.setHasToStop(true);
                    g.setHasTheBall(true);
                    hasTheBall = teams.get(enemyTeam).getPlayers().indexOf(g);
                    ball.setP(g);
                    if (teamPlaying != null) {
                        teams.get(teamPlaying).get(hasTheBall).setHasTheBall(false);
                        String aux = enemyTeam;
                        enemyTeam = teamPlaying;
                        teamPlaying = aux;
                    }
                    else {
                        teamPlaying = enemyTeam;
                        enemyTeam = otherTeam;
                    }
                    
                    Utils.sleepMillis(500);
                    break;
                }
            }
        });
        goalkeeper.start();
        EnemyTeamSteal.start();
    }
}
