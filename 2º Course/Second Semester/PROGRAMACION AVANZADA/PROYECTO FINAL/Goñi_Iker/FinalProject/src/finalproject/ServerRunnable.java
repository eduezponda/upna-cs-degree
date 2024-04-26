package finalproject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ikergo55
 */
public class ServerRunnable {

    final int port = 5000;
    
    int clicked;
    Map <String, Team> teams;
    String teamPlaying;
    String enemyTeam;
    static String local;
    static String visita;
    boolean Local;
    String otherTeam;
    int hasTheBall;
    Ball ball;
    boolean reinicio;
    boolean gol;
    boolean timePause;
    boolean correctShoot;
    int count;
    int movement;
    Thread shootToStop;
    boolean hasToStop;
    List<List<Integer>> shootLine;
    Thread toStop1;
    Thread toStop2;
    Thread Waiter;
    static Scoreboard scoreboard;
    Clock time;
    boolean passInProcess;
    ServerSocket serverSocket1;
    Socket clientSocket1;
    Socket clientSocket2;
    DataOutputStream out[] = new DataOutputStream[2];
    DataInputStream in[] = new DataInputStream[2];
    
    public ServerRunnable() throws IOException {
        
        serverSocket1 = new ServerSocket(port);
        
        while (true) {
            clientSocket1 = serverSocket1.accept();
            in[0] = new DataInputStream(clientSocket1.getInputStream());
            if (rvMessage(0).equals("READY"))
                break;
            else
                clientSocket1.close();
        }

        while (true) {
            clientSocket2 = serverSocket1.accept();
            in[1] = new DataInputStream(clientSocket2.getInputStream());
            if (rvMessage(1).equals("READY"))
                break;
            else
                clientSocket2.close();
        }
        
        out[0] = new DataOutputStream(clientSocket1.getOutputStream());
        out[1] = new DataOutputStream(clientSocket2.getOutputStream());
        
        teamPlaying = "ESPAÑA";
        enemyTeam = "BRASIL";
        local = teamPlaying;
        visita = enemyTeam;
        
        sendMessage("START ESPAÑA " + local, 0);
        sendMessage("START BRASIL " + local, 1);
        
        teams = new HashMap <>();
        teams.put("ESPAÑA", new Team());
        teams.put("BRASIL", new Team());
        teams.put("ARGENTINA", new Team());
        otherTeam = null;
        scoreboard = new Scoreboard(teamPlaying, enemyTeam);
        hasTheBall = 1;
        clicked = 1;
        Local = true;
        time = new Clock(false, scoreboard);
        time.start(0, 1000);
        passInProcess = false;
        reinicio = false;
        gol = false;
        timePause = false;
        correctShoot = false;
        count = 100;
        hasToStop = false;
        shootToStop = null;
        movement = 150;
        shootLine = new LinkedList<>();
        initTeams();
        ball = new Ball(teams.get(teamPlaying).get(hasTheBall));
        
        initReader(0);
        initReader(1);
        initWritter();
        initSteal();
        initMov();
    }

    public static void main(String args[]) throws IOException {
        ServerRunnable server = new ServerRunnable();
    }

    private String rvMessage(int socket) throws IOException {
        return in[socket].readUTF();
    }
    
    private void sendMessage ( String line, int socket) throws IOException {
        out[socket].writeUTF(line.toUpperCase());
        out[socket].flush();
    }
    
    private void mouseDragged(String team, int x, int y) { 
        List<Integer> l = new ArrayList<>();
        l.add(x);
        l.add(y);
        l.add(50);
        
        if (team.equals(teamPlaying)) {

            synchronized (teamPlaying) {
                if (toStop1 != null) {
                    toStop1.stop();
                    toStop1 = null;
                }

                if (!correctShoot && ball.near(x, y, 500)) {
                    teams.get(enemyTeam).shootFrom(x, y);
                    correctShoot = true;
                    ball.setP(null);
                    otherTeam = teamPlaying;
                    teamPlaying = null;
                    teams.get(team).get(hasTheBall).setHasTheBall(false);
                    teams.get(enemyTeam).get(clicked).setHasTheBall(false);
                }
            }
        }
        synchronized (shootLine) {
            shootLine.add(l);
        }
    }    
    
    private int playerCliked(String team, int x, int y) {
        return teams.get(team).clicked(x, y, 500);
    }

    private void mouseClicked(String team, int x, int y) {
        if (teamPlaying != null) {
            if (team.equals(teamPlaying)) {
                if (toStop1 != null) {
                    toStop1.stop();
                    toStop1 = null;
                }

                passInProcess = !ball.inPlayer(500);

                int index;
                if ((index = playerCliked(teamPlaying, x, y)) != -1 && !passInProcess) {
                    teams.get(teamPlaying).get(index).setHasTheBall(true);
                    teams.get(teamPlaying).get(hasTheBall).setHasTheBall(false);
                    hasTheBall = index;
                    ball.setP(teams.get(teamPlaying).get(hasTheBall));
                    passInProcess = true;
                    return;
                }
                toStop1 = teams.get(teamPlaying).get(hasTheBall).moveTo(x, y, 500);
            }
            else if (!timePause){
                if (toStop2 != null) {
                    toStop2.stop();
                    toStop2 = null;
                    teams.get(team).get(clicked).setHasTheBall(false);
                }

                int index;
                if ((index = playerCliked(team, x, y)) != -1) {
                    teams.get(team).get(clicked).setHasTheBall(false);
                    clicked = index;
                    return;
                }
                teams.get(team).get(clicked).setHasTheBall(true);
                toStop2 = teams.get(team).get(clicked).moveTo(x, y, 500);
            }
            else {
                
                if (Waiter != null) {
                    Waiter.stop();
                    Waiter = null;
                }
                
                Waiter = new Thread( () -> {
                    while (timePause) {
                        Utils.sleepMillis(250);
                    }
                    mouseClicked(team, x, y);
                    Waiter = null;
                });
                Waiter.start();
            }
        }
    }

    private void initReader(int socket) {
        Thread Reading = new Thread( () -> {
            Utils.sleepMillis(100);
            while (!Thread.interrupted()) {
                try {
                    String line = rvMessage(socket);
                    String[] parts = line.split(" ");
                    switch(parts[0]) {
                        case "CLICKED": {
                            mouseClicked(parts[1], Integer.parseInt(parts[2])
                                    , Integer.parseInt(parts[3]));
                            break;
                        }
                        case "DRAGGED": {
                            mouseDragged(parts[1], Integer.parseInt(parts[2])
                                    , Integer.parseInt(parts[3]));
                            break;
                        }
                        default: {
                            System.out.println("Mensaje no entendido");
                            break;
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ServerRunnable.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            }
        });
        Reading.start();
        Reading.setName("Read" + socket);
    }

    private void initWritter() {
        Thread Writing = new Thread( () -> {
            
            while (!Thread.interrupted()) {
                Utils.sleepMillis(20);
                for (Player p : teams.get(local).getPlayers()) {
                    String msg = "PLAYER " + local + " " 
                            + teams.get(local).getPlayers().indexOf(p) 
                            + " " + p.getX() + " " + p.getY();
                    try {
                        sendMessage(msg, 0);
                        sendMessage(msg, 1);
                    } catch (IOException ex) {
                        Logger.getLogger(ServerRunnable.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }
                }
                for (Player p : teams.get(visita).getPlayers()) {
                    String msg = "PLAYER " + visita + " " 
                            + teams.get(visita).getPlayers().indexOf(p) 
                            + " " + p.getX() + " " + p.getY();
                    try {
                        sendMessage(msg, 0);
                        sendMessage(msg, 1);
                    } catch (IOException ex) {
                        Logger.getLogger(ServerRunnable.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }
                }
                String msg1 = "BALL " + ball.getX() + " " + ball.getY();
                String msg2 = "GOALKEEPER " + local + " " 
                        + teams.get(local).getGoalkeeper().getX() + " " 
                        + teams.get(local).getGoalkeeper().getY();
                String msg5 = "GOALKEEPER " + visita + " " 
                        + teams.get(visita).getGoalkeeper().getX() + " " 
                        + teams.get(visita).getGoalkeeper().getY();
                String msg3 = "MOVEMENT " + movement;
                String msg4 = "TIMEPAUSE " + timePause;
                String msg6 = "SHOOT " + visita + " " 
                        + teams.get(local).getGoalkeeper().isShoot() 
                        + " " + teams.get(local).getGoalkeeper().getMovement();
                String msg7 = "SHOOT " + local + " " 
                        + teams.get(visita).getGoalkeeper().isShoot() 
                        + " " + teams.get(visita).getGoalkeeper().getMovement();
                try {
                    sendMessage(msg1, 0);
                    sendMessage(msg1, 1);
                    sendMessage(msg2, 0);
                    sendMessage(msg2, 1);
                    sendMessage(msg3, 0);
                    sendMessage(msg3, 1);
                    sendMessage(msg4, 0);
                    sendMessage(msg4, 1);
                    sendMessage(msg5, 0);
                    sendMessage(msg5, 1);
                    sendMessage(msg6, 0);
                    sendMessage(msg6, 1);
                    sendMessage(msg7, 0);
                    sendMessage(msg7, 1);
                } catch (IOException ex) {
                    Logger.getLogger(ServerRunnable.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
                
            }
        });
        Writing.start();
        Writing.setName("Writing");
        Writing.setPriority(Thread.MAX_PRIORITY);
    }
    
    private void initTeams() {
        teams.get(teamPlaying).init(teamPlaying, 500, 300, true);
        teams.get(enemyTeam).init(enemyTeam, 500, 300, false);
        teams.get(teamPlaying).get(hasTheBall).setHasTheBall(true);
    }
    
      // <editor-fold defaultstate="collapsed" desc="initSteal">
    private void initSteal() {
        Thread EnemyTeamSteal = new Thread( () -> {
            Utils.sleepMillis(100);
            while (!Thread.interrupted()) {
                
                Utils.sleepMillis(200);
                if (!timePause) {
                    if (teamPlaying != null) {
                        synchronized(teamPlaying) {
                            
                            Team t = teams.get(enemyTeam);

                            for (Player p : t.getPlayers()) {
                                if (p.stealBall(ball.getX(), ball.getY(), 500)) {

                                    if (toStop2 != null) {
                                        toStop2.stop();
                                        toStop2 = null;
                                        t.get(clicked).setHasTheBall(false);
                                    }

                                    teams.get(teamPlaying).getGoalkeeper().setShoot(false, 0);
                                    teams.get(enemyTeam).getGoalkeeper().setShoot(false, 0);
                                    teams.get(enemyTeam).get(clicked).setHasTheBall(false);
                                    p.setHasTheBall(true);
                                    teams.get(teamPlaying).get(hasTheBall).setHasTheBall(false);
                                    clicked = 1;
                                    teams.get(teamPlaying).get(clicked).setHasTheBall(true);

                                    hasTheBall = t.getPlayers().indexOf(p);
                                    ball.setP(p);
                                    String aux = enemyTeam;
                                    enemyTeam = teamPlaying;
                                    teamPlaying = aux;
                                    Utils.sleepMillis(500);
                                    break;
                                }
                            }
                        }
                    }
                    else {
                        if (!correctShoot) {
                            Utils.sleepMillis(500);

                            Team t = teams.get(enemyTeam);

                            for (Player p : t.getPlayers()) {
                                if (p.stealBall(ball.getX(), ball.getY(), 500)) {

                                    if (toStop2 != null) {
                                        toStop2.stop();
                                        toStop2 = null;
                                        t.get(clicked).setHasTheBall(false);
                                    }

                                    p.setHasTheBall(true);
                                    clicked = 1;

                                    teams.get(otherTeam).getGoalkeeper().setShoot(false, 0);
                                    teams.get(enemyTeam).getGoalkeeper().setShoot(false, 0);
                                    hasToStop = true;
                                    p.setHasTheBall(true);
                                    hasTheBall = t.getPlayers().indexOf(p);
                                    ball.setP(p);
                                    teamPlaying = enemyTeam;
                                    enemyTeam = otherTeam;
                                    Utils.sleepMillis(500);
                                    break;
                                }
                            }
                            
                            if (teamPlaying != null)
                                break;

                            t = teams.get(otherTeam);

                            for (Player p : t.getPlayers()) {
                                if (p.stealBall(ball.getX(), ball.getY(), 500)) {

                                    if (toStop2 != null) {
                                        toStop2.stop();
                                        toStop2 = null;
                                        t.get(clicked).setHasTheBall(false);
                                    }

                                    p.setHasTheBall(true);
                                    clicked = 1;
                                    
                                    teams.get(otherTeam).getGoalkeeper().setShoot(false, 0);
                                    teams.get(enemyTeam).getGoalkeeper().setShoot(false, 0);
                                    hasToStop = true;
                                    p.setHasTheBall(true);
                                    hasTheBall = t.getPlayers().indexOf(p);
                                    ball.setP(p);
                                    teamPlaying = otherTeam;
                                    Utils.sleepMillis(500);
                                    break;
                                }
                            }
                        }
                    }
                }
                else {
                    if (toStop2 != null) {
                        toStop2.stop();
                        toStop2 = null;
                        teams.get(enemyTeam).get(clicked).setHasTheBall(false);
                    }
                }
                
                if (reinicio) {
                    Utils.sleepMillis(1250);
                    if (gol) {
                        String msg;
                        
                        if (Local) {
                            scoreboard.setNumber1(String.valueOf(scoreboard.getNumber1() + 1));
                            msg = "SCORE " + local;
                        }
                        else {
                            scoreboard.setNumber2(String.valueOf(scoreboard.getNumber2() + 1));
                            msg = "SCORE " + visita;
                        }
                        
                        try {
                            sendMessage(msg, 0);
                            sendMessage(msg, 1);
                        } catch (IOException ex) {
                            Logger.getLogger(ServerRunnable.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (toStop1 != null) {
                        toStop1.stop();
                        toStop1 = null;
                    }
                    if (toStop2 != null) {
                        toStop2.stop();
                        toStop2 = null;
                    }
                    if (shootToStop != null) {
                        shootToStop.stop();
                        shootToStop = null;
                    }
                    hasToStop = false;
                    
                    if (Local) {
                        teamPlaying = visita;
                        enemyTeam = local;
                    }
                    else {
                        teamPlaying = local;
                        enemyTeam = visita;
                    }
                    
                    try {
                        sendMessage("REINIT " + teamPlaying + " " + enemyTeam, 0);
                        sendMessage("REINIT " + teamPlaying + " " + enemyTeam, 1);
                    } catch (IOException ex) {
                        Logger.getLogger(ServerRunnable.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    Local = !Local;
                    teams.get(teamPlaying).reinit(true);
                    teams.get(enemyTeam).reinit(false);
                    hasTheBall = 1;
                    clicked = 1;
                    teams.get(teamPlaying).get(hasTheBall).setHasTheBall(true);
                    ball.reinit(teams.get(teamPlaying).get(hasTheBall));
                    gol = false;
                    reinicio = false;
                    passInProcess = false;
                    gol = false;
                    timePause = false;
                    correctShoot = false;
                    movement = 150;
                    count = 100;
                    synchronized (shootLine) {
                        shootLine.clear();
                    }
                    
                    if (scoreboard.difGreaterThan1()){
                        String s;
                        if (scoreboard.getNumber1() > scoreboard.getNumber2())
                            s = "WIN " + local;
                        else if (scoreboard.getNumber1() != scoreboard.getNumber2())
                            s = "WIN " + visita;
                        else
                            s = "TIE";
                        
                        try {
                            sendMessage(s, 0);
                            sendMessage(s, 1);
                        } catch (IOException ex) {
                            Logger.getLogger(ServerRunnable.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        System.exit(0);
                    }
                }
            }
        });

        EnemyTeamSteal.start();
        EnemyTeamSteal.setName("EnemyTeamSteal");
    }
      // </editor-fold>
    
    private void initMov() {
        Thread UpMov = new Thread( () -> {
            while (!Thread.interrupted()) {
                Utils.sleepMillis(10);
                updateMov();
                if (shootToStop != null && hasToStop) {
                    shootToStop.stop();
                    shootToStop = null;
                    hasToStop = false;
                    correctShoot = false;
                    ball.setShootInProcess(false);
                    ball.setMoving(false);
                }
                timePause = !ball.isMoving() && ball.getP() != null;
                reiniciar();
                shoot();
                ball.move();
                for (String key : teams.keySet())
                    teams.get(key).move(500, ball.getX(), ball.getY(), timePause, ball.getP(), movement);
            }
        });
        UpMov.start();
        UpMov.setName("UpdateMov");
        UpMov.setPriority(Thread.MAX_PRIORITY);
    }
    
    synchronized private void updateMov() {
        int posIdeal = 150;
        
        if (ball.getY() < posIdeal && movement > 0) {
            if (movement - (posIdeal - ball.getY()) < 0) {
                ball.setY(ball.getY() + movement);
                updatePosPlayers(movement);
                movement = 0;
            }
            else {
                movement -= (posIdeal - ball.getY());
                updatePosPlayers(posIdeal-ball.getY());
                ball.setY(posIdeal);
            }
        }
        else if (ball.getY() > posIdeal) {
            movement += ball.getY() - posIdeal;
            updatePosPlayers(posIdeal-ball.getY());
            ball.setY(posIdeal);
        }
    }
    
    private void updatePosPlayers(int change) {
        for(String s : teams.keySet()) {
            teams.get(s).updatePos(change);
        }
    }
    
    private void reiniciar() {
        if (!reinicio) {
            reinicio = ball.checkOut(500);
        }
        if (reinicio) {
            if (count == 0) {
                if (ball.checkGol(500))
                    gol = true;
            }
            else {
                reinicio = false;
                if (shootToStop != null) {
                    shootToStop.stop();
                    hasToStop = false;
                }
                
                count --;
                for(String s : teams.keySet()) {
                    teams.get(s).setTimePause(true);
                }
            }
        }
    }
    
    private void shoot() {
        
        List<List<Integer>> toRemove = new ArrayList<>();
        synchronized (shootLine) {
            
            for (List<Integer> l : shootLine) {
                if (l.get(2) > 0) {
                    l.set(2, l.get(2)-1);
                }
                else
                    toRemove.add(l);
            }

            for (List<Integer> l : toRemove) {
                shootLine.remove(l);
            }
        
            if (!ball.isShootInProcess() && correctShoot && shootLine.isEmpty() && !toRemove.isEmpty()) {
                ball.setShootInProcess(true);
                int pox = toRemove.get(toRemove.size()-1).get(0);
                int poy = toRemove.get(toRemove.size()-1).get(1);
                shootToStop = ball.shoot(pox, poy, 500, 5.0);
                teams.get(otherTeam).shootTo(pox, poy);
                Utils.sleepMillis(200);
                correctShoot = false;
            }
        }
    }
}