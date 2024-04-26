package finalproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

/**
 *
 * @author ikergo55
 */
public class DrawPanel extends JPanel{
    
    private int w;
    private int h;
    private int movement;
    private boolean timePause;
    private String teamPlaying;
    private Ball ball;
    private Scoreboard score;
    private List<List<Integer>> shootLine;
    private Map<String, Team> teams;
    private List<Color> color;
    private boolean isGreen;
    private boolean correctShoot;
    private Thread shootToStop;
    private boolean hasToStop;
    private boolean reinicio;
    private int count = 100;
    private boolean gol;
    private boolean solo;

      // <editor-fold defaultstate="collapsed" desc="Constructor, Getters and Setters">
    public void setTimePause(boolean timePause) {
        this.timePause = timePause;
    }

    public boolean isCorrectShoot() {
        return correctShoot;
    }

    public void setCorrectShoot(boolean correctShoot) {
        this.correctShoot = correctShoot;
    }

    public int getMovement() {
        return movement;
    }

    public void setMovement(int movement) {
        this.movement = movement;
    }

    public void setHasToStop(boolean hasToStop) {
        this.hasToStop = hasToStop;
    }
    
    public void addShootLine(List<Integer> l) {
        synchronized (shootLine) {
            this.shootLine.add(l);
        }
    }
    
    public boolean isEmptyShoot() {
        return shootLine.isEmpty();
    }

    public boolean isTimePause() {
        return timePause;
    }

    public boolean isReinicio() {
        return reinicio;
    }

    public boolean isGol() {
        return gol;
    }

    public void setReinicio(boolean reinicio) {
        this.reinicio = reinicio;
    }

    public void setGol(boolean gol) {
        this.gol = gol;
    }
    
    void init(Ball ball, Scoreboard score, Map<String, Team> teams, String teamPlaying, boolean solo) {
        this.ball = ball;
        this.gol = false;
        this.teamPlaying = teamPlaying;
        this.reinicio = false;
        this.score = score;
        this.shootLine = new ArrayList<>();
        this.teams = teams;
        this.timePause = true;
        this.movement = 150;
        this.isGreen = false;
        this.color = new ArrayList<>();
        this.correctShoot = false;
        this.hasToStop = false;
        this.solo = solo;
        color.add(new Color(175, 175, 175));
        color.add(new Color(125, 125, 125));
        color.add(new Color(225, 225, 225));
        color.add(new Color(75, 75, 75));
        Thread UpdateGraphics = new Thread( () -> {
            while (!Thread.interrupted()) {
                Utils.sleepMillis(10);
                if (solo) {
                    updateMov();
                    if (shootToStop != null && hasToStop) {
                        shootToStop.stop();
                        shootToStop = null;
                        hasToStop = false;
                        correctShoot = false;
                        ball.setShootInProcess(false);
                        ball.setMoving(false);
                    }
                }
                repaint();
            }
        });
        UpdateGraphics.start();
        UpdateGraphics.setName("UpdateGraphics");
        UpdateGraphics.setPriority(Thread.MAX_PRIORITY);
    }
      // </editor-fold>
    
      // <editor-fold defaultstate="collapsed" desc="Paint">
    @Override
    public void paint(Graphics g) {
        
        super.paint(g);
        w = getWidth();
        h = getHeight();  
        
        Graphics2D g2 = (Graphics2D) g;
        
        chooseColor();

        if (!reiniciar()) {
            g2.clearRect(0, 0, w, h);
            
            drawStadium(g2);
            
            drawShoot(g2);
            if (solo)
                ball.move();
            ball.paint(g2, w, solo);
            for (String key : teams.keySet()) {
                if (solo)
                    teams.get(key).move(w, ball.getX(), ball.getY(), timePause, ball.getP(), movement);
                teams.get(key).paint(g2, w, solo);
            }
            score.paint(g2, w);
        }
    }

      // <editor-fold defaultstate="collapsed" desc="drawField">
    private void drawStadium(Graphics2D g2) {
        final int prop = 500;
        final int mov = -movement*w/500;
        
        g2.setColor( color.get(0) );
        g2.fillRect(0, 0, w, h);
        g2.setColor( color.get(1) );
        for (int i = 0; i < h*2/45; i ++) {
            g2.fillRect(0, 2*i*45*w/prop+mov, w, 45*w/prop);
        }
        setOpaque(false);
        
        drawLines(g2, prop, mov);
        
        drawRed(g2, prop, mov);
        
        g2.setColor( Color.WHITE );
        g2.fillRect(w/2-55*w/prop, 20*w/prop+mov, 110*w/prop, 5*w/prop);
        g2.setColor( color.get(3) );
        for (int i = 0; i < 6; i ++) {
            g2.fillRect(w/2-55*w/prop+(i*20)*w/prop, 20*w/prop+mov, 11*w/prop, 6*w/prop);
        }
        
        g2.setColor( Color.WHITE );
        g2.fillRect(w/2-55*w/prop, 25*w/prop+mov, 5*w/prop, 35*w/prop);
        g2.fillRect(w/2+51*w/prop, 25*w/prop+mov, 5*w/prop, 35*w/prop);
        
        g2.setColor( color.get(3) );
        g2.fillRect(w/2-55*w/prop, 24*w/prop+mov, 5*w/prop, 6*w/prop);
        g2.fillRect(w/2-55*w/prop, 40*w/prop+mov, 5*w/prop, 10*w/prop);
        g2.fillRect(w/2+51*w/prop, 24*w/prop+mov, 5*w/prop, 6*w/prop);
        g2.fillRect(w/2+51*w/prop, 40*w/prop+mov, 5*w/prop, 10*w/prop);
    }

    private void drawRed(Graphics2D g2, final int p, final int mov) {
        g2.setColor( Color.WHITE );
        for (int i = 2; i < 8; i ++){
            g2.drawRect(w/2-55*w/p, i*5*w/p+mov, 110*w/p, 5*w/p);
        }
        for (int i = 0; i < 22; i ++){
            g2.drawLine(w/2-55*w/p+(5*i)*w/p, 10*w/p+mov, w/2-55*w/p+(5*i)*w/p, 40*w/p+mov);
        }
    }

    private void drawLines(Graphics2D g2, final int p, final int mov) {
        g2.setColor( color.get(2) );
        g2.fillArc(w/2-110*w/p, 35*w/p+mov, 220*w/p, 220*w/p, 225, 90);
        g2.fillOval(w/2-48*w/p, 447*w/p+mov, 96*w/p, 96*w/p);
        g2.setColor( color.get(0) );
        g2.fillArc(w/2-107*w/p, 37*w/p+mov, 214*w/p, 215*w/p, 225, 90);
        g2.fillArc(w/2-45*w/p, 450*w/p+mov, 90*w/p, 90*w/p, 180, 180);
        g2.setColor( color.get(1) );
        g2.fillRect(0, 4*45*w/p+mov, w, 42*w/p);
        g2.fillArc(w/2-45*w/p, 450*w/p+mov, 90*w/p, 90*w/p, 0, 180);
        g2.setColor( color.get(2) );
        g2.fillRect(0, 57*w/p+mov, w, 3*w/p);
        g2.fillRect(w/2-85*w/p, 107*w/p+mov, 169*w/p, 3*w/p);
        g2.fillRect(w/2-85*w/p, 57*w/p+mov, 3*w/p, 52*w/p);
        g2.fillRect(w/2+82*w/p, 57*w/p+mov, 3*w/p, 52*w/p);
        g2.fillRect(15*w/p, 220*w/p+mov, w-30*w/p, 3*w/p);
        g2.fillRect(15*w/p, 57*w/p+mov, 3*w/p, 164*w/p);
        g2.fillRect(w-18*w/p, 57*w/p+mov, 3*w/p, 164*w/p);
        
        g2.fillOval(w/2-6*w/p, 491*w/p+mov, 11*w/p, 11*w/p);
        g2.fillRect(0, 495*w/p+mov, w, 3*w/p);
    }
      // </editor-fold>
    
    private void drawShoot(Graphics2D g2) {
        
        g2.setColor(Color.WHITE);
        List<List<Integer>> toRemove = new ArrayList<>();
        synchronized (shootLine) {
            for (List<Integer> l : shootLine) {
                if (l.get(2) > 0) {
                    g2.fillOval(l.get(0)-2*w/500, l.get(1)-2*w/500, 4*w/500, 4*w/500);
                    l.set(2, l.get(2)-1);
                }
                else
                    toRemove.add(l);
            }

            for (List<Integer> l : toRemove) {
                shootLine.remove(l);
            }
        }
        
        if (solo) {
            synchronized (this) {
                if (!ball.isShootInProcess() && correctShoot && shootLine.isEmpty() && !toRemove.isEmpty()) {
                    ball.setShootInProcess(true);
                    int pox = toRemove.get(toRemove.size()-1).get(0);
                    int poy = toRemove.get(toRemove.size()-1).get(1);
                    shootToStop = ball.shoot(pox, poy, w, 5.0);
                    teams.get(teamPlaying).shootTo(pox, poy);
                    Utils.sleepMillis(200);
                    correctShoot = false;
                }
            }
        }
    }
      // </editor-fold>

    synchronized private void chooseColor() {
        if (solo)
            timePause = !ball.isMoving() && ball.getP() != null;
        
        if (!timePause && !isGreen) {
            color.set(0, new Color(0, 200, 50));
            color.set(1, new Color(0, 200, 0));
            color.set(2, new Color(150, 255, 150));
            color.set(3, Color.RED);
            isGreen = true;
            return;
        }
        if (timePause && isGreen) {
            color.set(0, new Color(175, 175, 175));
            color.set(1, new Color(150, 150, 150));
            color.set(2, new Color(225, 225, 225));
            color.set(3, new Color(75, 75, 75));
            isGreen = false;
        }
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

    private boolean reiniciar() {
        if (!solo)
            return false;
        if (!reinicio) {
            reinicio = ball.checkOut(w);
        }
        if (reinicio) {
            if (count == 0) {
                if (ball.checkGol(w))
                    gol = true;
            }
            else {
                if (shootToStop != null) {
                    shootToStop.stop();
                    hasToStop = false;
                }
                reinicio = false;
                count --;
                for(String s : teams.keySet()) {
                    teams.get(s).setTimePause(true);
                }
            }
        }
        return reinicio;
    }
    
    public void reinit(String teamPlaying) {
        this.timePause = true;
        this.gol = false;
        this.reinicio = false;
        this.shootLine.clear();
        this.movement = 150;
        this.isGreen = false;
        this.correctShoot = false;
        this.hasToStop = false;
        this.count = 100;
        this.teamPlaying = teamPlaying;
    }
}
