package finalproject;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author ikergo55
 */
 
public class Clock {
 
    private int minute = 0;
    private int second = 0;
    private Timer timer;
    private boolean isTimerRunning;
    private boolean secondHalf;
    private final Scoreboard score;
 
    public Clock(boolean sec, Scoreboard s) {
        timer = new Timer();
        secondHalf = sec;
        score = s;
    }
 
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            isTimerRunning = true;
            if(second < 50) {
                second += 14;
            } else {
                second = 00;
                if(minute < 45 || (minute < 90 && secondHalf))
                    minute += 3;
                else {
                    if (!secondHalf)
                        secondHalf = true;
                    else {
                        isTimerRunning = false;
                        timer.cancel();
                        timer.purge();
                    }
                }
            }
            if(isTimerRunning)
                returnTime(minute, second);
        }
    };
 
    public void start(int timeout, int interval) {
        timer.schedule(task, timeout, interval);
    }
    
    public void returnTime(int minute, int second) {
        String fullHour = "";
 
        if (!secondHalf) {
            fullHour += (minute > 9) ? "" + minute : "0" + minute;
        }
        else {
            fullHour += (minute > 9) ? "" + minute : "0" + (minute+45);
        }
        fullHour += (second > 9) ? ":" + second : ":0" + second;
 
        score.setTime(fullHour);
    }
 
}