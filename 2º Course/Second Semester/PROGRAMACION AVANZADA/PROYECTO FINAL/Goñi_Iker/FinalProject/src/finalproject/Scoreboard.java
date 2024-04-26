package finalproject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author ikergo55
 */
public class Scoreboard {
    
    private final int x;
    private final int y;
    private Image number1;
    private Image number2;
    private String n1;
    private String n2;
    private final Image local;
    private final Image visita;
    private String time = "00:00";

      // <editor-fold defaultstate="collapsed" desc="Constructor, Getters and Setters">
    public Scoreboard(String l, String v) {
        Image img = selectImage("0");
        this.number1 = img;
        this.number2 = img;
        this.local = selectImage(l);
        this.visita = selectImage(v);
        this.x = 15;
        this.y = 10;
        this.n1 = "0";
        this.n2 = "0";
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setNumber1(String number) {
        this.number1 = selectImage(number);
        this.n1 = number;
    }

    public void setNumber2(String number) {
        this.number2 = selectImage(number);
        this.n2 = number;
    }

    public int getNumber1() {
        return Integer.parseInt(n1);
    }

    public int getNumber2() {
        return Integer.parseInt(n2);
    }
    
    
      // </editor-fold>
    
    public void paint(Graphics2D g2, int w){
        g2.setColor(Color.BLACK);
        g2.fillRect(x*w/500+30*w/500, y*w/500, 25*w/500, 10*w/500);
        g2.fillRect(x*w/500+75*w/500, y*w/500, 25*w/500, 10*w/500);
        g2.fillRect(x*w/500, y*w/500, 30*w/500, 10*w/500);
        g2.setColor(Color.WHITE);
        g2.fillRect(x*w/500+54*w/500, y*w/500, 24*w/500, 10*w/500);
        if (w/500 > 1) {
            g2.scale(2.0, 2.0);
            g2.drawString(time, (x*w/500+5*w/500)/2, (y*w/500+9*w/500)/2);
            g2.scale(0.5, 0.5);
        }
        else {
            g2.drawString(time, x*w/500, y*w/500+10*w/500);
        }
        g2.drawImage(local, x*w/500+34*w/500, y*w/500+w/500, 20*w/500, 10*w/500, null);
        g2.drawImage(visita, x*w/500+80*w/500, y*w/500+w/500, 20*w/500, 10*w/500, null);
        g2.drawImage(number1, x*w/500+56*w/500, y*w/500+w/500, 8*w/500, 8*w/500, null);
        g2.drawImage(number2, x*w/500+68*w/500, y*w/500+w/500, 8*w/500, 8*w/500, null);
    }

    private Image selectImage(String l) {
        String s;
        switch (l){
            case ("ESPAÑA"): {
                s = "/Images/Esp.png";
                break;
            }
            case ("BRASIL"): {
                s = "/Images/Bra.png";
                break;
            }
            case ("ARGENTINA"): {
                s = "/Images/Arg.png";
                break;
            }
            case ("0"): {
                s = "/Images/0.png";
                break;
            }
            case ("1"): {
                s = "/Images/1.png";
                break;
            }
            case ("2"): {
                s = "/Images/2.png";
                break;
            }
            case ("3"): {
                s = "/Images/3.png";
                break;
            }
            case ("4"): {
                s = "/Images/4.png";
                break;
            }
            default: {
                s = "/Images/5.png";
                break;
            }
        }
        return new ImageIcon(getClass().getResource(s)).getImage();
    }

    boolean difGreaterThan1() {
        return Math.abs(getNumber1() - getNumber2()) > 1 || (getNumber1() == getNumber2() && getNumber1() == 5);
    }
}
