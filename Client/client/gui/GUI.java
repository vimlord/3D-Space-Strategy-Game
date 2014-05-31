/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.gui;

import client.gui.menu.MenuManager;
import engine.entities.Entity;
import engine.entities.celestialBodies.BlackHole;
import engine.entities.celestialBodies.Planet;
import engine.entities.ships.Ship;
import engine.entities.structures.WarpGate;
import engine.main.EntityList;
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
    
    
    private static int WIDTH = 800, HEIGHT = 600;
    private static boolean debug = true;
    private static double XZ_ROT = Math.toRadians(30), Y_ROT = Math.toRadians(30);
    private static double x = 0, y = 0, z = 0;
    
    //For value n, one pixel will represent n meters
    private static double pixelMeterRatio = 5;
    
    
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
        
        if(MenuManager.getMenuIndex() >= -1)
            MenuManager.drawCurrentMenu(g);
        else
            drawBattlefield(g);
        
    }
    
    public void drawBattlefield(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        
        //Debug mode that draws the origin
        if(debug){
            //X Axis
            g2.setColor(Color.red);
            g2.drawLine(frame.getWidth()/2 - (int)(Math.cos(XZ_ROT) * 200),  (frame.getHeight()/2 - 18) + (int)(Math.sin(XZ_ROT) * 200 * Math.sin(Y_ROT)), frame.getWidth()/2 + (int)(Math.cos(XZ_ROT) * 200), (frame.getHeight()/2 - 18) - (int)(Math.sin(XZ_ROT) * 200 * Math.sin(Y_ROT)));

            //Y Axis
            g2.setColor(Color.blue);
            g2.drawLine(frame.getWidth()/2 - (int)(Math.sin(XZ_ROT) * 200),  (frame.getHeight()/2 - 18) - (int)(Math.cos(XZ_ROT) * 200 * Math.sin(Y_ROT)), frame.getWidth()/2 + (int)(Math.sin(XZ_ROT) * 200), (frame.getHeight()/2 - 18) + (int)(Math.cos(XZ_ROT) * 200 * Math.sin(Y_ROT)));

            //Z Axis
            g2.setColor(Color.green);
            g2.drawLine(frame.getWidth()/2, (frame.getHeight()/2 - 18) + (int)(200 * Math.cos(Y_ROT)), frame.getWidth()/2, (frame.getHeight()/2 - 18) - (int)(200 * Math.cos(Y_ROT)));
            
            g2.setColor(Color.black);
        }
        
        ArrayList<Entity> list = EntityList.getEntityList();
        for(Entity e : list){
            drawEntity(g2, e);
            drawTagLines(g2,e);
        }
        for(Entity e : list){
            //drawTag(g2, e);
        }
        
        
        
    }
    
    public void drawEntity(Graphics2D g2, Entity e){
        if(e instanceof BlackHole){
            g2.setColor(Color.BLACK);
            fillSphere(g2, e.getX(), e.getY(), e.getZ(), 2.0 * e.getRadius());
        } else if(e instanceof Planet){
            Planet p = (Planet) e;
            drawSphere(g2, e.getX(), e.getY(), e.getZ(), e.getRadius());
            drawSphere(g2, e.getX(), e.getY(), e.getZ(), 2.0 * p.getAtmosphereHeight());
        } else if(e instanceof WarpGate){
            drawRing(g2, e.getX(), e.getY(), e.getZ(), e.getRadius());
        } else if(e instanceof Ship){
            drawSphere(g2, e.getX(), e.getY(), e.getZ(), e.getRadius());
        } else {
            drawSphere(g2, e.getX(), e.getY(), e.getZ(), e.getRadius());
        }
        
    }
    
    public void drawTag(Graphics2D g2, Entity e){
        String[] tag = e.getTag();
        drawTag(g2, e.getX(), e.getY(), e.getZ(), tag);
    }
    public void drawTagLines(Graphics2D g2, Entity e){
        String[] tag = e.getTag();
        drawTagLines(g2, e.getX(), e.getY(), e.getZ(), tag);
    }
    
    
    public int[] buildSphere(double X, double Y, double Z){
        int[] values = new int[2];
        
        double magnitudeXZ = Math.sqrt(Math.pow((X-x),2) + Math.pow((Z-z),2));
        if(magnitudeXZ == 0){
            values[0] = (int)(frame.getWidth()/2);
            values[1] = (int)((frame.getHeight()/2 - 18));
            return values;
        }
        
        double angleXZ = Math.atan((Z-z)/(X-x));
        if((X-x) < 0){
            angleXZ += Math.toRadians(180);
        }
        double magnitudeY = Y-y;
        
        
        values[0] = (int)(frame.getWidth()/2 + (Math.cos(angleXZ + XZ_ROT) * magnitudeXZ / pixelMeterRatio));
        values[1] = (int)((frame.getHeight()/2 - 18) - (Math.sin(angleXZ + XZ_ROT) * magnitudeXZ * Math.sin(Y_ROT) / pixelMeterRatio) - (magnitudeY * Math.cos(Y_ROT) / pixelMeterRatio));
        
        return values;
        
    }
    
    public int[] buildSphere(double X, double Y, double Z, double R){
        int[] values = new int[3];
        int[] input = buildSphere(X,Y,Z);
        values[0] = input[0];
        values[1] = input[1];
        
        double distX = X-x;
        double distY = Y-y;
        double distZ = Z-z;
        double dist = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2) + Math.pow(distZ, 2));
        
        
        double camX = x + 250 * pixelMeterRatio * (Math.cos(Math.toRadians(90) - XZ_ROT) * Math.cos(Math.toRadians(90) - Y_ROT));
        double camY = y + 250 * pixelMeterRatio * Math.cos(-Y_ROT);
        double camZ = z + 250 * pixelMeterRatio * (Math.sin(Math.toRadians(90) - XZ_ROT) * Math.cos(Math.toRadians(90) - Y_ROT));
        
        double distCamX = -X + camX;
        double distCamY = -Y + camY;
        double distCamZ = -Z + camZ;
        double distCam = Math.sqrt(Math.pow(distCamX, 2) + Math.pow(distCamY, 2) + Math.pow(distCamZ, 2));
        
        if(dist > 250 * pixelMeterRatio && distCam < dist){
            return null;
        }
        
        int radius = (int)(250 * R/(distCam));
        
        values[2] = (int)(radius);
        
        if(radius < 0.5){
            return null;
        }
        
        return values;
        
    }
    
    public void drawSphere(Graphics2D g, double X, double Y, double Z, double R){
        int[] points = buildSphere(X,Y,Z,R);
        
        if(points == null){
            return;
        }
        
        g.drawOval(points[0] - points[2], points[1] - points[2], (int)(2 * points[2]), (int)(2 * points[2]));
        
    }
    
    public void fillSphere(Graphics2D g, double X, double Y, double Z, double R){
        int[] points = buildSphere(X,Y,Z,R);
        
        if(points == null){
            return;
        }
        
        g.fillOval(points[0] - points[2], points[1] - points[2], (int)(2 * points[2]), (int)(2 * points[2]));
    }
    
    public void drawRing(Graphics2D g, double X, double Y, double Z, double R){
        int[] points = buildSphere(X,Y,Z,R);
        
        if(points == null){
            return;
        }
        
        
        g.drawOval(points[0] - points[2], (int)(points[1]  - (Math.sin(Y_ROT)) * points[2]), (int)(2 * points[2]), (int)(2 * points[2] * Math.sin(Y_ROT)));
        
        
    }
    
    public void drawTag(Graphics2D g, double X, double Y, double Z, String[] contents){
        int[] points = buildSphere(X,Y,Z);
        
        int lines = contents.length;
        
        g.setColor(Color.BLACK);
        g.drawRect(points[0] + 25, points[1] - (25 + 20 * lines) , 125, 20 * lines);
        g.setColor(Color.WHITE);
        g.fillRect(points[0] + 26, points[1] - (24 + 20 * lines) , 124, 20 * lines - 1);
        g.setColor(Color.BLACK);
        
        for(int i = 0; i < lines; i++){
            g.drawString(contents[i],points[0] + 27, points[1] - (7 + 20 * (lines - i)));
        }
        
    }
    public void drawTagLines(Graphics2D g, double X, double Y, double Z, String[] contents){
        int[] points = buildSphere(X,Y,Z);
        
        int lines = contents.length;
        
        g.drawLine(points[0],points[1],points[0] + 25, points[1] - 25);
        g.drawLine(points[0] + 25, points[1] - 25, points[0] + 25, points[1] - (25 + 20 * lines));
        g.drawLine(points[0] + 25, points[1] - 25, points[0] + 125, points[1] - 25);
        
        
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
    
    /**
     * Sets a new width for the Applet
     * @param width
     */
    public void setAppletWidth(int width){
        if(width > 0){
            WIDTH = width;
            frame.setSize(WIDTH, HEIGHT);
        }
    }
    
    /**
     * Sets a new height for the Applet
     * @param height
     */
    public void setAppletHeight(int height){
        if(height > 0){
            HEIGHT = height;
            frame.setSize(WIDTH, HEIGHT);
        }
    }
    
    /**
     * Activates or deactivates the debug mode
     * @param mode
     */
    public void setDebug(boolean mode){
        debug = mode;
    }
    
}
