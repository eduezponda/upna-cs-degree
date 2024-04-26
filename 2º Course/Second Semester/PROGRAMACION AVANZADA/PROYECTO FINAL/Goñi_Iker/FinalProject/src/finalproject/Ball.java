package finalproject;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author ikergo55
 */
public class Ball {
    private int x;
    private int y;
    private int diagonal;
    private Player p;
    private final Image image;
    private boolean moving;
    private int checker;
    private boolean shootInProcess;
    
      // <editor-fold defaultstate="collapsed" desc="Constructor, Getters and Setters">
    public Ball(Player p) {
        this.x = p.getX();
        this.y = p.getY();
        this.p = p;
        this.diagonal = 0;
        this.image = new ImageIcon(getClass().getResource("/Images/balón.png")).getImage();
        this.moving = false;
        this.checker = 0;
        this.shootInProcess = false;
        
    }

    public int getX() {
        return x;
    }

    synchronized public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    synchronized public void setY(int y) {
        this.y = y;
    }

    public Player getP() {
        return p;
    }

    synchronized public void setP(Player p) {
        this.p = p;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isShootInProcess() {
        return shootInProcess;
    }

    public void setShootInProcess(boolean shootInProcess) {
        this.shootInProcess = shootInProcess;
    }
      // </editor-fold>
    
    public void paint (Graphics2D g2, int w, boolean solo) {
        if (!solo) {
            g2.drawImage(image, x, y+28*w/500, 8*w/500, 8*w/500, null);
            return;
        }

        g2.drawImage(image, x*w/500, y*w/500+28*w/500, 8*w/500, 8*w/500, null);
    }
    
    public void move(){
        if (p != null)
            movement();
    }
    
    private void movement () {

        int jugX = p.getX();
        int jugY = p.getY();
        int movX = 0, movY = 0;
        
        if (jugX-x > 0) {
            movX = 1;
        }
        else if (jugX-x < 0) {
            movX = -1;
        }

        if (jugY-y > 0) {
            movY = 1;
        }
        else if (jugY-y < 0) {
            movY = -1;
        }

        if (Math.abs(jugX-x) + Math.abs(jugY-y) > 1) {
            moving = true;
            checker = 0;
            if (diagonal == 0) {
                
                x += movX;
                y += movY;
                
                if (jugX-x == 0 || jugY-y == 0) {
                    return;
                }
                if (Math.abs(jugX-x) > Math.abs(jugY-y)) {
                    diagonal = Math.abs(jugX-x) / Math.abs(jugY-y);
                    return;
                }

                diagonal = Math.abs(jugY-y) / Math.abs(jugX-x);
                return;
            }
            if (Math.abs(jugX-x) > Math.abs(jugY-y)) {
                
                x += movX;
                
                diagonal -= 1;
                return;
            }
            
            y += movY;

            diagonal -= 1;
        }
        if (checker > 10) {
            moving = false;
            checker = 0;
        }
        else
            checker ++;
    }

    boolean near(int x, int y, int w) {
        return Math.abs(this.x*w/500+4*w/500 - x) < 40*w/500 && Math.abs(this.y*w/500+32*w/500 - y) < 40*w/500;
    }
    
    Thread shoot(int x, int y, int w, double power) {
        
        moving = true;
        
        int movX, movY;
        
        if (x > this.x*w/500)
            movX = 1;
        else
            movX = -1;
        
        if (y > this.y*w/500+28*w/500)
            movY = 1;
        else
            movY = -1;
        
        int diag;
        boolean mov;
        if (Math.abs(x-this.x*w/500) >= Math.abs(y-this.y*w/500+28*w/500)) {
            if (Math.abs(y-this.y*w/500+28*w/500) == 0)
                diag = 50;
            else
                diag = Math.abs(x-this.x*w/500) / Math.abs(y-this.y*w/500+28*w/500);
            mov = true;
        }
        else {
            if (Math.abs(x-this.x*w/500) == 0)
                diag = 50;
            else
                diag = Math.abs(y-this.y*w/500+28*w/500) / Math.abs(x-this.x*w/500);
            mov = false;
        }
        
        Thread Shoot = new Thread( () -> {
            int d = diag;
            double vel = power, roz = 0.03;
            while (!Thread.interrupted()) {
                Utils.sleepMillis(20);
                if (d == 0) {
                    this.x += movX*vel;
                    this.y += movY*vel;
                    d = diag;
                }
                else {
                    if (mov)
                        this.x += movX*vel;
                    else
                        this.y += movY*vel;
                    d--;
                }
                vel -= roz;
                roz += 0.0003;
                if (vel <= 0)
                    break;
            }
            shootInProcess = false;
            moving = false;
        });
        Shoot.start();
        return Shoot;
    }

    boolean inPlayer(int w) {
        if (p != null)
            return near(p.getX()*w/500, p.getY()*w/500, w);
        else
            return false;
    }
    
    public boolean checkGol(int w) {
        return y*w/500 < 25*w/500 && w/2-50*w/500 < x*w/500 && x*w/500 < w/2+42*w/500;
    }
    
    public boolean checkOut(int w) {
        boolean out =  y*w/500 < 25*w/500 || x*w/500 <= 1 || x*w/500 >= w-9*w/500;
        return out;
    }
    
    public void reinit(Player p) {
        this.x = p.getX();
        this.y = p.getY();
        this.p = p;
        this.diagonal = 0;
        this.moving = false;
        this.checker = 0;
        this.shootInProcess = false;
    }
}
