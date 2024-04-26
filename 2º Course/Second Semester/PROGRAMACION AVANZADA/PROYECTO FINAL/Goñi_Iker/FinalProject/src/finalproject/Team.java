package finalproject;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ikergo55
 */
public class Team {
    private final List<Player> players;
    private boolean isAttacking;
    private Goalkeeper goalkeeper;
    private boolean gIsPlaying;
    private final int[] shoot;

      // <editor-fold defaultstate="collapsed" desc="Constructor, Getters and Setters">
    public Team() {
        this.shoot = new int[3];
        this.players = new ArrayList<>();
    }

    public void setIsAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }
    
    public void add(Player player) {
        players.add(player);
    }
    
    public Player get(int index) {
        return players.get(index);
    }

    public List<Player> getPlayers() {
        return players;
    }
    
    public List<Player> getOnlyPlayers() {
        List<Player> l = new ArrayList<>();
        l.addAll(players);
        l.remove(3);
        return l;
    }

    public Goalkeeper getGoalkeeper() {
        return goalkeeper;
    }
    
      // </editor-fold>
    
    public void init(String team, int w, int h, boolean isAttacking) {
        this.isAttacking = isAttacking;
        for (int i = 0; i < 3; i++) {
            Player player = new Player (numberTeam(team), i, isAttacking);
            player.selectImage(0, 1, 0);
            players.add(player);
        }
        goalkeeper = new Goalkeeper(numberTeam(team), 1, isAttacking, !isAttacking);
        goalkeeper.selectImage(0, 0, 0);
        players.add(goalkeeper);
        
        this.shoot[0] = 0;
        this.shoot[1] = 0;
        
        gIsPlaying = !isAttacking;
    }
    
    public void reinit(boolean isAttacking) {
        this.isAttacking = isAttacking;
        for (Player p : players) {
            p.reinit(isAttacking);
            p.initZona();
            p.selectImage(0, 1, 0);
        }
        gIsPlaying = !isAttacking;
        this.shoot[0] = 0;
        this.shoot[1] = 0;
    }
    
    void paint (Graphics2D g2, int w, boolean solo) {
        for (Player p : players)
            p.paint(g2, w, solo);
    }
    
    void move (int w, int xBall, int yBall, boolean timePause, Player hasBall, int mov) {
        for (Player p : players)
            p.move(timePause, xBall, yBall, mov, w, hasBall);
    }

    synchronized int clicked(int x, int y, int w) {
        for (Player p : players){
            int op1 = (x - p.getX()*w/500);
            int op2 = (y - p.getY()*w/500);
            if (op1 < 20*w/500 && op1 > 0) {
                if (op2 < 40*w/500 && op2 > 0)
                    if (players.indexOf(p) != 3 || gIsPlaying)
                        return players.indexOf(p);
            }
        }
        return -1;
    }
    
    private int numberTeam(String team){
        switch (team){
            case "ESPAÑA": {
                return 1;
            }
            case "BRASIL": {
                return 2;
            }
            case "ARGENTINA": {
                return 3;
            }
            default: {
                return 1;
            }
        }
    }

    void updatePos(int change) {
        for (Player p : players) {
            p.setY(p.getY()+change);
        }
    }
    
    void setTimePause(boolean time) {
        for (Player p : players) {
            p.setTimePause(time);
        }
    }

    void shootFrom(int x, int y) {
        this.shoot[0] = x;
        this.shoot[1] = y;
    }

    void shootTo(int pox, int poy) {
        int[] p = new int[2];
        p[0] = pox;
        p[1] = poy;
        goalkeeper.calcShoot(true, shoot, p);
        this.shoot[0] = 0;
        this.shoot[1] = 0;
    }
}
