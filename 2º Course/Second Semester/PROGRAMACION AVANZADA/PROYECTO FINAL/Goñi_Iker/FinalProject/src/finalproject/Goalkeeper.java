package finalproject;

import java.awt.Graphics2D;
import javax.swing.ImageIcon;

/**
 *
 * @author ikergo55
 */
public class Goalkeeper extends Player {
    
    private boolean shoot;
    private boolean inMyField;
    private int count;
    private int movement;
    private int grados;

    
    public Goalkeeper(int team, int zona, boolean Attacking, boolean inMyField) {
        super(team, zona, Attacking);
        this.shoot = false;
        this.movement = 0;
        this.inMyField = inMyField;
        this.count = 0;
        this.grados = 0;
        initZona();
    }

    public void setShoot(boolean shoot, int movement) {
        this.shoot = shoot;
        this.movement = movement;
    }

    public void calcShoot(boolean shoot, int[] ini, int[] fin) {
        this.shoot = shoot;
        whereShoot(ini, fin);
    }

    public boolean isShoot() {
        return shoot;
    }

    public int getMovement() {
        return movement;
    }

    @Override
    protected int selectImage(int i, int y, int w) {
        String dir;
        if (!shoot) {
            dir = "/Images/Players/Goalkeeper" + Integer.toString(team) + ".png";
        }
        else {
            dir = "/Images/Players/Goalkeeper_" + Integer.toString(team) + ".png";

            switch (movement) {
                case (0): {
                    if (grados == 0)
                        break;
                    if (grados > 0)
                        grados --;
                    else
                        grados ++;
                    break;
                }
                case (1): {
                    if (grados == 90)
                        break;
                    grados ++;
                    break;
                }
                default: {
                    if (grados == -90)
                        break;
                    grados --;
                    break;
                }
            }  
        }
        image = new ImageIcon(getClass().getResource(dir)).getImage();
        return 0;
    }

    @Override
    public void paint(Graphics2D g2, int w, boolean solo) {
        if (!inMyField)
            return;
        
        selectImage(0, 0, 0);
        if (!shoot) {
            if (!solo)
                g2.drawImage(image, getX(), getY(), 18*w/500, 36*w/500, null);
            else
                g2.drawImage(image, getX()*w/500, getY()*w/500, 18*w/500, 36*w/500, null);
        }
        else {
            g2.rotate(Math.toRadians(grados), getX()+9
                    , getY()+36);
            g2.drawImage(image, getX(), getY(), 18*w/500, 36*w/500, null);
            g2.rotate(Math.toRadians(-grados), getX()+9
                    , getY()+36);
        }
    }
    
    @Override
    public void move(boolean timePause, int xBall, int yBall, int mov, int w, Player p) {
        selectImage(0, 0, 0);
        
        if (!timePause && !this.timePause) {
            MovIA(w, xBall, yBall, mov, p);
        }
    }
    
      // <editor-fold defaultstate="collapsed" desc="movement IA">
    protected void MovIA(int w, int xB, int yB, int mov, Player p) {
        if (isHasTheBall())
            return;
        
        if (count < 5) {
            count ++;
            return;
        }
        else
            count = 0;
        
        int movX, movY;
        int yBall = yB*w/500+28*w/500;
        int xBall = xB*w/500;
        
        zona[0] = w/2-50*w/500;
        zona[1] = w/2+32*w/500;
        zona[2] = 73*w/500-mov*w/500;
        
        if ((isAttacking() || yBall > 200*w/500) && getY() < zona[2]+20*w/500) {
            return;
        }
        if ((isAttacking() || yBall > 200*w/500) && getY() >= zona[2]+20*w/500) {
            movX = (int)Utils.uniform(zona[0], zona[1]);
            movY = (int)Utils.uniform(zona[2]+20*w/500, zona[2]+30*w/500);
        }
        else if (p == null) {
            movX = xBall;
            movY = yBall;
        }
        else if (xBall < zona[0]-28*w/500) {
            movX = zona[0]+3*w/500;
            movY = zona[2];
        }
        else if (xBall > zona[1]+38*w/500) {
            movX = zona[1]-3*w/500;
            movY = zona[2];
        }
        else {
            movX = xBall;
            if (yBall < zona[2]+100*w/500)
                movY = yBall-5;
            else
                movY = (int)Utils.uniform(zona[2]+40*w/500, zona[2]+60*w/500);
        }
        
        movement(movX, movY-36*w/500, w, 0);
    }
      // </editor-fold>

    @Override
    public synchronized boolean stealBall(int x, int y, int w) {
        if (!inMyField)
            return false;
        
        boolean enLaZona;
        
        if (!shoot) {
            enLaZona = getY()+15 >= y;
            enLaZona &= getY() <= y+10;
            enLaZona &= getX() >= x-22;
            enLaZona &= getX() <= x+30;
        }
        else {
            switch (movement) {
                case 0:
                    enLaZona = getY()+15 >= y;
                    enLaZona &= getY() <= y+10;
                    enLaZona &= getX() >= x-32;
                    enLaZona &= getX() <= x+40;
                    break;
                case 1:
                    enLaZona = getY()+15 >= y;
                    enLaZona &= getY() <= y+10;
                    enLaZona &= getX() >= x-22;
                    enLaZona &= getX() <= x+50;
                    break;
                default:
                    enLaZona = getY()+15 >= y;
                    enLaZona &= getY() <= y+10;
                    enLaZona &= getX() >= x-42;
                    enLaZona &= getX() <= x+30;
                    break;
            }
        }
        return enLaZona;
    }

    
    @Override
    public void initZona() {
        zona[0] = 200;
        zona[1] = 300;
        zona[2] = 45-150;
        setX((int)Utils.uniform(zona[0],zona[1]));
        setY((int)Utils.uniform(zona[2], zona[2]+50));
    }
    
    @Override
    public void reinit(boolean Attacking) {
        super.reinit(Attacking);
        this.shoot = false;
        this.inMyField = !Attacking;
        this.movement = 0;
        this.count = 0;
        this.grados = 0;
    }

    private void whereShoot(int[] ini, int[] fin) {
        
        float times;
        if (Math.abs(ini[1]-fin[1]) == 0)
            times = 1;
        else
            times = (ini[1]-20)/Math.abs(ini[1]-fin[1]);
        
        int end = ini[0] + (int)(times*(fin[0]-ini[0]));
        
        if (Math.abs(getX()+9-end) < 25)
            movement = 0;
        else if (getX()-end < 0)
            movement = 1;
        else
            movement = -1;
    }
}
