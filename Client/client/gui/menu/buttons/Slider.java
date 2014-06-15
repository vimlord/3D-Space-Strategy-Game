/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.gui.menu.buttons;

import java.awt.Graphics2D;

/**
 *
 * @author Christopher
 */
public class Slider extends Button{

    private final boolean vertical, bottomOrLeft;
    
    private int value = 0;
    private final int maxValue;
    private int X, Y;
    
    
    public Slider(int x, int y, int width, int height, String command, boolean isVertical, boolean bottomOrLeft) {
        super(x, y, width, height, command, "");
        vertical = isVertical;
        this.bottomOrLeft = bottomOrLeft;
        if(isVertical){
            maxValue = height - 20;
        } else {
            maxValue = width - 20;
        }
    }
    
    public boolean testHit(int x_, int y_){
        boolean result = super.testHit(x_, y_);
        
        if(result){
            X = x_;
            Y = y_;
        }
        
        return result;
    }
    
    public String pushButton(){
        if(!testHit(X,Y))
            return null;
        
        if(vertical){
            if(Y < y + 10){
                if(bottomOrLeft)
                    value = maxValue;
                else
                    value = 0;
            } else if(Y < y + height - 10){
                int difference = Y - (y + 10);
                if(bottomOrLeft)
                    value = maxValue - difference;
                else
                    value = difference;
            } else {
                if(!bottomOrLeft)
                    value = maxValue;
                else
                    value = 0;
            }
        } else {
            if(X < x + 10){
                if(bottomOrLeft)
                    value = maxValue;
                else
                    value = 0;
            } else if(X < x + height - 10){
                int difference = X - (x + 10);
                if(bottomOrLeft)
                    value = maxValue - difference;
                else
                    value = difference;
            } else {
                if(!bottomOrLeft)
                    value = maxValue;
                else
                    value = 0;
            }
        }
        
        return super.getCommand() + "(" + (1.0 * value / maxValue);
    }
    
    public void drawButton(Graphics2D g2){
        super.drawButton(g2);
        if(!active) return;
        
        if(vertical){
            if(bottomOrLeft){
                g2.drawRect(x,y + maxValue - value,width,20);
            } else {
                g2.drawRect(x,y + value,width,20);
            }
        } else {
            if(bottomOrLeft){
                g2.drawRect(x + value,y, 20, height);
            } else {
                g2.drawRect(x + maxValue - value,y, 20, height);
            }
        }
        
    }
    
    /**
     *
     * @param number Double that is <= 1 and >= 0.
     */
    public void setValue(double number){
        if(number < 0){
            value = 0;
        } else if(number > 1){
            value = maxValue;
        } else {
            value = (int)(maxValue * number);
        }
    }
    
}
