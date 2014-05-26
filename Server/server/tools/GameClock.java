/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.tools;

import engine.main.CycleRunner;

/**
 *
 * @author Christopher Hittner
 */
public class GameClock {
    private double ticks = 0;
    private int seconds, minutes, hours, days, years;
    
    public GameClock(){
        seconds = 0;
        minutes = 0;
        hours = 0;
        days = 0;
        years = 0;
    }
    
    public void cycle(){
        cycle(CycleRunner.getTimeWarp());
    }
    
    public void cycle(double factor){
        ticks += factor;
        checkValues();
    }
    
    public void checkValues(){
        while(ticks >= CycleRunner.cyclesPerSecond){
            ticks -= CycleRunner.cyclesPerSecond;
            seconds++;
        }
        
        while(seconds >= 60){
            seconds -= 60;
            minutes++;
        }
        
        while(minutes >= 60){
            minutes -= 60;
            hours++;
        }
        
        while(hours >= 24){
            hours -= 24;
            days++;
        }
        
        while(days >= 365){
            days -= 365;
            years++;
        }
    }
    
    public String toString(boolean showTick){
        
        if(hours > 1){
            System.out.println("----->" + hours + "<-----");
        }
        
        String timeElapsed = "";
        if(years > 0){
            timeElapsed += getYear() + ":" + getDay() + ":";
        } else if(days > 0){
            timeElapsed += getDay() + ":";
        }
        
        timeElapsed += getHour() + ":" + getMinute() + ":" + getSecond();
        
        if(showTick){
            timeElapsed += ":" + (int)(ticks);
        }
        
        return timeElapsed;
        
    }
    
    private String getYear(){
        return years + "";
    }

    private String getDay() {
        if(days < 10){
            return "0" + days;
        }
        return "" + days;
    }
    
    private String getHour(){
        if(hours < 10){
            return "0" + hours;
        }
        return "" + hours;
    }
    
    private String getMinute(){
        if(minutes < 10){
            return "0" + minutes;
        }
        return "" + minutes;
    }
    
    private String getSecond(){
        if(seconds < 10){
            return "0" + seconds;
        }
        return "" + seconds;
    }
    
}
