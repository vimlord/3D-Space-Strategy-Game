/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.gui;

import client.gui.menu.MenuManager;
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



/**
 *
 * @author Christopher
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
        frame = new JFrame("SpaceCraft Gamma - Client");
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
        
        MenuManager.drawCurrentMenu(g);
        
    }
    
    
    @Override
    public void keyTyped(KeyEvent e) {
    
    }

    @Override
    public void keyPressed(KeyEvent e) {
    
    }

    @Override
    public void keyReleased(KeyEvent e) {
    
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    
    }

    @Override
    public void mousePressed(MouseEvent e) {
    
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    
    }

    @Override
    public void mouseExited(MouseEvent e) {
    
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    
    }
    
    
    
}
