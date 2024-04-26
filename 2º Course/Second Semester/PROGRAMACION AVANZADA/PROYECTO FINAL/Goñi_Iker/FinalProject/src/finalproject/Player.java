package finalproject;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author ikergo55
 */
public class Player {
    
    private int x;
    private int y;
    private boolean hasTheBall;
    private boolean Attacking;
    protected Image image;
    protected boolean timePause;
    protected final int team;
    protected boolean running;
    protected final int[] zona = {0, 0, 0};
    private boolean toStop;
    private Thread movIA;
    private boolean init;
    private int I;
    
      // <editor-fold defaultstate="collapsed" desc="Constructor, Getters and Setters">
    public Player(final int team, int zona, boolean Attacking) {
        this.hasTheBall = false;
        this.team = team;
        this.running = false;
        this.zona[0] = zona;
        this.toStop = false;
        this.movIA = null;
        this.Attacking = Attacking;
        this.init = true;
        this.timePause = false;
        this.I = 0;
        initZona();
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

    public boolean isHasTheBall() {
        return hasTheBall;
    }

    synchronized public void setHasTheBall(boolean hasTheBall) {
        this.hasTheBall = hasTheBall;
    }

    public boolean isAttacking() {
        return Attacking;
    }
    
    public void setAttacking(boolean Attacking) {
        this.Attacking = Attacking;
    }

    public int getTeam() {
        return team;
    }

    public void setTimePause(boolean timePause) {
        this.timePause = timePause;
    }
      // </editor-fold>
    
    public void paint(Graphics2D g2, int w, boolean solo){
        if (!solo) {
            g2.drawImage(image, x, y, 18*w/500, 36*w/500, null);
            return;
        }
        g2.drawImage(image, x*w/500, y*w/500, 18*w/500, 36*w/500, null);
    }
    
    public void move(boolean timePause, int xBall, int yBall, int mov, int w, Player p) {
        if (timePause || this.timePause) {
            if (movIA != null) {
                movIA.stop();
                movIA = null;
                toStop = true;
            }
            return;
        }
        serchNewMovement(w, xBall, yBall);
    }
    
    synchronized public boolean stealBall(int x, int y, int w) {
        boolean enLaZona = this.y+20 >= y;
        enLaZona &= this.y <= y+15;
        enLaZona &= this.x >= x-20;
        enLaZona &= this.x <= x+20;
        return enLaZona;
    } 
    
      // <editor-fold defaultstate="collapsed" desc="movement">
    Thread moveTo(int x, int y, int w) {
        Thread moving = new Thread( () -> {
            
            int diagonal = 0, i = 0, k = 0, acc = 0;
            int localY, Yideal = 150;
            boolean out;
            int[] res;
            
            Utils.sleepMillis(100);
            
            while (!Thread.interrupted()) {
                
                Utils.sleepMillis(20);
                
                out = Math.abs(this.y - Yideal) < 8;
                localY = y-36*w/500 - acc*w/500;
                
                res = movement(x-9*w/500, localY, w, diagonal);
                
                diagonal = res[0];
                if (out)
                    acc += res[1];
                
                if (k > 5 && running) {
                    i = selectImage(i, y, w);
                    k = 0;
                }
                else
                    k++;
            }
        });
        moving.start();
        running = true;
        return moving;
    }
    
    protected int[] movement (int finX, int finY, int w, int diagonal) {
        
        int posX = x*w/500;
        int posY = y*w/500;
        int[] sol = {0, 0};
        int movX = 0, movY = 0;
        
        if (finX-posX > 0) {
            movX = 1;
        }
        else if (finX-posX < 0) {
            movX = -1;
        }

        if (finY-posY > 0) {
            movY = 1;
        }
        else if (finY-posY < 0) {
            movY = -1;
        }
        
        if (Math.abs(finX-posX) + Math.abs(finY-posY) > 5*w/500) {
            running = true;
            if (diagonal == 0) {
                
                x += movX;
                y += movY;
                sol[1] += movY;
                
                if (finY-posY == 0 || finX-posX == 0) {
                    sol[0] = 0;
                    return sol;
                }
                if (Math.abs(finX-posX) > Math.abs(finY-posY)) {
                    sol[0] = Math.abs(finX-posX) / Math.abs(finY-posY);
                    return sol;
                }
                
                sol[0] = Math.abs(finY-posY) / Math.abs(finX-posX);
                return sol;
            }
            if (Math.abs(finX-posX) > Math.abs(finY-posY)) {
                
                x += movX;
                
                sol[0] = diagonal - 1;
                return sol;
            }
            
            y += movY;
            sol[1] += movY;

            sol[0] = diagonal - 1;
            return sol;
        }
        running = false;
        sol[0] = diagonal;
        return sol;
    }
    
      // <editor-fold defaultstate="collapsed" desc="movement IA">
    private void serchNewMovement(int w, int xB, int yB) {
        if (hasTheBall) {
            if (movIA != null) {
                movIA.stop();
                movIA = null;
            }
            return;
        }
        if (!toStop && !init && movIA != null)
            return;
        init = false;
        
        if (toStop && movIA != null) {
            movIA.stop();
            movIA = null;
            toStop = false;
        }
        
        int movX, movY;
        int yBall = yB*w/500+34*w/500;
        int xBall = xB*w/500;
        
        zona[1] = 10 + ((w-60)/3 + 20)*zona[0];
        zona[2] = zona[1] + (w-60)/3;
        
        if (!Attacking && xBall > zona[1]-10 && xBall < zona[2]+10) {
            movX = xBall;
            movY = yBall;
        }
        else if (!Attacking) {
            movX = (int)Utils.uniform(zona[1], zona[2]);
            movY = yBall-60*w/500;
        }
        else {
            movX = (int)Utils.uniform(zona[1], zona[2]);
            movY = yBall+(int)Utils.uniform(-20*w/500, 20*w/500);
        }
        movIA = movementIA(movX, movY, w);
    }
    
    private Thread movementIA(int x, int y, int w) {
        Thread moving = new Thread( () -> {
            
            int diagonal = 0, i = 0, k = 0, acc = 0;
            int localY, Yinit = this.y;
            boolean out;
            int[] res;
            
            while (!Thread.interrupted()) {
                
                Utils.sleepMillis(30);
                
                out = Math.abs(this.y - Yinit) < 8*w/500;
                localY = y-36*w/500 - acc*w/500;
                
                res = movement(x-9*w/500, localY, w, diagonal);
                
                diagonal = res[0];
                if (out)
                    acc += res[1];
                else
                    Yinit = this.y;
                
                if (k > 5 && running) {
                    i = selectImage(i, y, w);
                    k = 0;
                    toStop = false;
                }
                else if (k > 5 && !running)
                    toStop = true;
                else
                    k++;
            }
        });
        moving.start();
        running = true;
        return moving;
    }
      // </editor-fold>
      // </editor-fold>
    
    protected int selectImage(int i, int y, int w) {
        String dir;
        if (y-36*w/500-this.y*w/500 > 0) {
            dir = "/Images/Players/Player" + Integer.toString(team) + Integer.toString(i) + ".png";
        }
        else {
            dir = "/Images/Players/Player_" + Integer.toString(team) + Integer.toString(i) + ".png";
        }
        image = new ImageIcon(getClass().getResource(dir)).getImage();
        
        if (i == 2)
            i = 0;
        else
            i++;
        
        return i;
    }
    
    public void initZona() {
        zona[1] = 10 + (440/3 + 20)*zona[0];
        zona[2] = zona[1] + 440/3;
        x = (int)Utils.uniform(zona[1],zona[2]);
        y = (int)Utils.uniform(75, 230);
    }
    
    public void updateImage(int dir, int w) {
        I = selectImage(I, dir, w);
    }
    
    public void reinit(boolean Attacking) {
        this.hasTheBall = false;
        this.running = false;
        this.toStop = false;
        this.Attacking = Attacking;
        this.init = true;
        this.timePause = false;
        this.toStop = false;
        this.I = 0;
    }
}
