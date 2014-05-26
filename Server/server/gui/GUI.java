/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.gui;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import server.main.Server;

/**
 *
 * @author Christopher Hittner
 */
public class GUI extends Applet implements KeyListener, MouseListener, MouseMotionListener{
    
    
    private Graphics graphics;
    private JFrame frame;
    
    public GUI(){
        this(600,700);
    }
    
    public GUI(int width, int height){
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        
        //Creates a JFrame with a title
        frame = new JFrame("3D Viewer");
        //Puts the Tester object into thhe JFrame
	frame.add(this);
        //Sets the size of the applet to be 800 pixels wide  by 600 pixels high
	frame.setSize(width, height);
        //Makes the applet visible
	frame.setVisible(true);
        //Sets the applet so that it can't be resized
        frame.setResizable(false);
        //This will make the program close when the red X in the top right is
        //clicked on
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    public void redraw(){
        repaint();
    }

    public void update(Graphics g){
        Image image = null;
        if (image == null) {
            image = createImage(this.getWidth(), this.getHeight());
            graphics = image.getGraphics();
        }
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0,  0,  this.getWidth(),  this.getHeight());
        graphics.setColor(getForeground());
        paint(graphics);
        g.drawImage(image, 0, 0, this);
    }
    
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        
        
        
        g2.setColor(Color.BLACK);
        g2.drawRect(0, 0, frame.getWidth() - 7, frame.getHeight() - 29);
        g2.drawLine(200, 0, 200, frame.getHeight());
        g2.drawLine(0,50,frame.getWidth(),50);
        
        drawLog(g2);
        drawDataBar(g2);
        
    }
    
    //The following elements will be drawn on the server:
    //The log
    //The time elapsed
    //A space for extra data
    
    public void drawDataBar(Graphics2D g){
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("Stats:", 20, 30);
        
        g.setFont(new Font("Courier New", Font.PLAIN, 12));
        
        String time = Server.getTime(true);
        
        g.drawString("Time Elapsed:", 15, 70);
        g.drawString(time, 15, 85);
        g.drawLine(0,100,200,100);
    }
    
    public void drawLog(Graphics2D g){
        //Draws the title
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("Server Log:", 220, 30);
        
        //Draws the log
        g.setFont(new Font("Courier New", Font.PLAIN, 12));
        ArrayList<String> theLog = Server.getLog();
        int entries = (frame.getHeight() - 200)/16;
        
        for(int i = 0; i < theLog.size(); i++){
            if(i >= entries){
                break;
            }
            String s = theLog.get(i);
            g.drawString(s, 215, 70 + 16 * i);
        }
        
        
    }
    
    
    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
