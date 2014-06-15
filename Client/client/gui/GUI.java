/*
 * The GUI class is designed to output data onto the game's applet.
 */

package client.gui;

import client.game.*;
import client.gui.menu.*;
import client.gui.menu.buttons.Button;
import client.main.Client;
import client.settings.Option;
import client.settings.Options;
import engine.entities.Entity;
import engine.entities.celestialBodies.*;
import engine.entities.projectiles.*;
import engine.entities.ships.*;
import engine.entities.structures.*;
import engine.gameMechanics.factions.*;
import engine.main.EntityList;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
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
    private static double oldXZ = XZ_ROT,              oldY = Y_ROT;
    private static double x = 0, y = 0, z = 0,
                          X = 0, Y = 0, Z = 0;
    private static double pressMouseX = 0, pressMouseY = 0;
    
    //For value n, one pixel will represent n meters
    private static double pixelMeterRatio = 5;
    
    
    private Graphics graphics;
    private JFrame frame;
    private double mouseSensitivity = 1, movementSensitivity = 1;
    
    //These will hold the values for how thick the interface will be in pixels
    private int topThickness = 0, 
                bottomThickness = 0,
                leftThickness = 0,
                rightThickness = 200;
    
    private boolean Ctrl_Held = false;
    
    private int viewNumber = 0;
    
    
    public GUI(){
        this(800,600);
    }
    
    public GUI(int width, int height){
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        
        //Creates a JFrame with a title
        frame = new JFrame("SpaceCraft Gamma - Client");
        //Puts the Tester object into thhe JFrame
	frame.add(this);
        //Sets the size of the applet
	frame.setSize(width, height);
        WIDTH = width;
        HEIGHT = height;
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
        if(MenuManager.getMenuIndex() < -1)
            drawGameInterface(g);
        
    }
    
    public void drawGameInterface(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        
        if(viewNumber == 0 || GameControlSettings.getSelectedIDs().size() <= 0){
            
            x = X;
            y = Y;
            z = Z;
        } else if(viewNumber == 1){
            ArrayList<Entity> e = new ArrayList<Entity>();
            for(long l : GameControlSettings.getSelectedIDs()){
                e.add(EntityList.getEntity(l));
            }
            
            x = 0;
            y = 0;
            z = 0;
            
            for(Entity ent : e){
                x += ent.getX();
                y += ent.getY();
                z += ent.getZ();
            }
            
            x /= e.size();
            y /= e.size();
            z /= e.size();
        } else {
            
            ArrayList<Entity> e = new ArrayList<Entity>();
            
            for(Entity ent : EntityList.getEntityList()){
                if(ent instanceof Ship && ((Ship) ent).getFactionID() == Client.getID()){
                    e.add(ent);
                }
            }
            
            if(e.size() > 0){
                x = 0;
                y = 0;
                z = 0;
                
                for(Entity ent : e){
                    x += ent.getX();
                    y += ent.getY();
                    z += ent.getZ();
                }

                x /= e.size();
                y /= e.size();
                z /= e.size();
            } else {
                x = X;
                y = Y;
                z = Z;
            }
        }
        
        X = x;
        Y = y;
        Z = z;
        
        ////////////////////////////////////////////////////
        //The first thing that is drawn is the map itself.//
        ////////////////////////////////////////////////////
        
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0,getAppletWidth(), getAppletHeight());
        
        //Debug mode that draws the origin
        if(debug){
            //X Axis
            g2.setColor(Color.red);
            g2.drawLine(frame.getWidth()/2 - (int)(Math.cos(XZ_ROT) * 200) + (leftThickness - rightThickness)/2,  (frame.getHeight()/2 - 18) + (int)(Math.sin(XZ_ROT) * 200 * Math.sin(Y_ROT)), frame.getWidth()/2 + (int)(Math.cos(XZ_ROT) * 200) + (leftThickness - rightThickness)/2, (frame.getHeight()/2 - 18) - (int)(Math.sin(XZ_ROT) * 200 * Math.sin(Y_ROT)));

            //Y Axis
            g2.setColor(Color.blue);
            g2.drawLine(frame.getWidth()/2 - (int)(Math.sin(XZ_ROT) * 200) + (leftThickness - rightThickness)/2,  (frame.getHeight()/2 - 18) - (int)(Math.cos(XZ_ROT) * 200 * Math.sin(Y_ROT)), frame.getWidth()/2 + (int)(Math.sin(XZ_ROT) * 200) + (leftThickness - rightThickness)/2, (frame.getHeight()/2 - 18) + (int)(Math.cos(XZ_ROT) * 200 * Math.sin(Y_ROT)));

            //Z Axis
            g2.setColor(Color.green);
            g2.drawLine(frame.getWidth()/2 + (leftThickness - rightThickness)/2, (frame.getHeight()/2 - 18) + (int)(200 * Math.cos(Y_ROT)), frame.getWidth()/2 + (leftThickness - rightThickness)/2, (frame.getHeight()/2 - 18) - (int)(200 * Math.cos(Y_ROT)));
            
            g2.setColor(Color.black);
        }
        
        ArrayList<Entity> list = EntityList.getEntityList();
        for(Entity e : list){
            drawEntity(g2, e);
            //drawTagLines(g2,e);
        }
        
        ArrayList<Integer[]> circles = new ArrayList<>();
        ArrayList<Long> IDs = new ArrayList<>();
        for(Entity e : list){
            int[] circle = buildSphere(e.getX(), e.getY(), e.getZ(), e.getRadius());
            if(circle != null){
                Integer[] arr = new Integer[3];
                for(int i = 0; i < circle.length; i++){
                    arr[i] = circle[i];
                }
                circles.add(arr);
                IDs.add(e.getID());
                
            //drawTag(g2, e);
                
            }
            
        }
        //Sets the new click detector circles up circles
        GameControlSettings.setCircles(circles, IDs);
        
        //////////////////////////////////////////////////
        //The next thing that is drawn is the interface.//
        //////////////////////////////////////////////////
        
        
        g2.setColor(Color.GRAY);
        g2.fillRect(0, 0, getAppletWidth(), topThickness);
        g2.fillRect(0, getAppletHeight() - bottomThickness, getAppletWidth(), bottomThickness);
        g2.fillRect(0, 0, leftThickness, frame.getHeight());
        g2.fillRect(getAppletWidth() - rightThickness - 9, 0, rightThickness, getAppletHeight());
        
        //g2.setColor(new Color(192,192,255,127));
        //g2.fillRect(frame.getWidth() - 250,25,240,300);
        
        //g2.setColor(Color.WHITE);
        //g2.setFont(new Font("Courier New", Font.PLAIN, 16));
        //g2.drawString("USS Valkyrie", frame.getWidth() - 245, 40);
        
        
        ArrayList<Long> ids = GameControlSettings.getSelectedIDs();
        
        for(Button b : (MenuManager.getMenu("gameInterface")).getButtons()){
            if(b.getCommand().substring(0,b.getCommand().indexOf("]") + 1).equals("[INTERFACE]"))
                b.setStatus(ids.size() == 1);
        }
        
        if(ids.size() == 1){
            //Things to display: Name and Type, Velocity.
            //If it's a Ship, show the vitals and Rotation.
            
            Entity selected = EntityList.getEntity(ids.get(0));
            
            g2.setColor(new Color(192,192,255,127));
            g2.fillRect(frame.getWidth() - 240,25,240,300);
            
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Courier New", Font.PLAIN, 15));
            
            //Outputs the name of the Entity
            g2.drawString(selected.getName(), getAppletWidth() - rightThickness + 5, 40);
            
            //Finds the type of Entity
            String type = "Entity";
            if(selected instanceof Corvette)
                type = "Corvette";
            else if(selected instanceof Frigate)
                type = "Frigate";
            else if(selected instanceof Destroyer)
                type = "Destroyer";
            else if(selected instanceof Cruiser)
                type = "Cruiser";
            else if(selected instanceof Ship)
                type = "Ship";
            else if(selected instanceof Missile)
                type = "Missile";
            else if(selected instanceof Slug)
                type = "Railgun Slug";
            else if(selected instanceof Projectile)
                type = "Missile";
            else if(selected instanceof BlackHole)
                type = "Black Hole";
            else if(selected instanceof Planet)
                type = "Planet";
            else if(selected instanceof Star)
                type = "Star";
            else if(selected instanceof CelestialBody)
                type = "Celestial Body";
            else if(selected instanceof WarpGate)
                type = "Warp Gate";
            else if(selected instanceof Structure)
                type = "Structure";
            
            //The type of Entity selected
            g2.drawString(type, getAppletWidth() - rightThickness + 5, 60);
            
            //The velocity is described
            double velXZ = Math.sqrt(Math.pow(selected.getSpeedX(),2) + Math.pow(selected.getSpeedZ(),2));
            double velocity = Math.sqrt(Math.pow(velXZ,2) + Math.pow(selected.getSpeedZ(),2));
            double XZ = Math.atan(selected.getSpeedZ()/velXZ);
            if(selected.getSpeedZ() < 0)
                XZ *= -1;
            double Y = Math.asin(selected.getSpeedY()/velocity);
            if(velocity != 0){
                g2.drawString(((int)(100 * velocity))/100.0 + " m/s", getAppletWidth() - rightThickness + 5, 80);
                g2.drawString(((int)(100 * Math.toDegrees(XZ)))/100.0 + " degrees", getAppletWidth() - rightThickness + 5, 100);
                g2.drawString(((int)(100 * Math.toDegrees(Y)))/100.0 + " degrees", getAppletWidth() - rightThickness + 5, 120);
            }else
                g2.drawString(((int)(100 * velocity))/100.0 + " m/s", getAppletWidth() - rightThickness + 5, 80);
            
            if(selected instanceof Ship){
                //xGUI - 160
                //yGUI - 400 to 320
                Ship s = (Ship) selected;
                //Background bara
                g2.setColor(Color.BLACK);
                g2.drawRect(getAppletWidth() - 160, getAppletHeight() - 325, 150, 20);
                g2.drawRect(getAppletWidth() - 160, getAppletHeight() - 350, 150, 20);
                g2.drawRect(getAppletWidth() - 160, getAppletHeight() - 375, 150, 20);
                //Health
                g2.setColor(Color.RED);
                g2.drawRect(getAppletWidth() - 160, getAppletHeight() - 325, (int)(150 * (s.getHealth()/s.getMaxHealth())), 20);
                //Shields
                g2.setColor(Color.BLUE);
                g2.drawRect(getAppletWidth() - 160, getAppletHeight() - 350, (int)(150 * (s.getShields()/s.getMaxShields())), 20);
                //Warp Charge
                g2.setColor(Color.GREEN);
                g2.drawRect(getAppletWidth() - 160, getAppletHeight() - 375, (int)(150 * (s.getWarpCharge()/s.getMaxWarpCharge())), 20);
                
            } else if(selected instanceof CelestialBody){
                g2.drawString("Mass:   " + selected.getMass(false) + " kg", frame.getWidth() - 235, 140);
                g2.drawString("Radius: " + selected.getRadius() + " m", frame.getWidth() - 235, 160);
                if(selected instanceof Planet){
                    g2.drawString("Atmosphere Height: " + ((Planet) selected).getAtmosphereHeight() + " m", frame.getWidth() - 235, 180);
                } else {
                    g2.drawString("No Atmosphere Present", frame.getWidth() - 235, 180);
                }
                
                
            }
            
        } else {
            
        }
        
        //Draws the frame for the displayer for the selected Entity
        g2.setColor(new Color(36,36,180,255));
        g2.fillRect(frame.getWidth() - 240, 0, 250, 25);
        g2.setColor(new Color(36,36,180,255));
        g2.fillOval(frame.getWidth() - 60, -50, 120, 120);
        
        
    }
    
    public void drawEntity(Graphics2D g2, Entity e){
        if(e instanceof Ship){
            int foundFacID = ((Ship) e).getFactionID();
            
            Faction thisFaction = FactionList.getFaction(Client.getID());
            if(thisFaction == null || foundFacID < 0){
                //Not in a faction
                g2.setColor(Color.WHITE);
            }if(foundFacID == Client.getID()){
                //Your team
                g2.setColor(Color.BLUE);
            } else try{
                if(thisFaction.getDiplomaticStatus(foundFacID) == 1){
                    //Ally
                    g2.setColor(Color.GREEN);
                } else if(thisFaction.getDiplomaticStatus(foundFacID) == -1){
                    //Enemy
                    g2.setColor(Color.RED);
                } else if(thisFaction.getDiplomaticStatus(foundFacID) == 0){
                    //Neutral
                    g2.setColor(Color.YELLOW);
                } else {
                    //Unpredicated instance
                    g2.setColor(Color.WHITE);
                }
            } catch(Exception ex){
                g2.setColor(Color.WHITE);
            }
            
        } else if(e instanceof Structure){
            int foundFacID = ((Ship) e).getFactionID();
            
            Faction thisFaction = FactionList.getFaction(Client.getID());
            if(thisFaction == null || foundFacID < 0){
                //Not in a faction
                g2.setColor(Color.WHITE);
            }if(foundFacID == Client.getID()){
                //Your team
                g2.setColor(Color.BLUE);
            } else if(thisFaction.getDiplomaticStatus(foundFacID) == 1){
                //Ally
                g2.setColor(Color.GREEN);
            } else if(thisFaction.getDiplomaticStatus(foundFacID) == -1){
                //Enemy
                g2.setColor(Color.RED);
            } else if(thisFaction.getDiplomaticStatus(foundFacID) == 0){
                //Neutral
                g2.setColor(Color.YELLOW);
            } else {
                //Unpredicated instance
                g2.setColor(Color.WHITE);
            }
        } else {
            g2.setColor(Color.WHITE);
        }
        
        if(e instanceof BlackHole){
            
            
            g2.setColor(Color.BLACK);
            fillSphere(g2, e.getX(), e.getY(), e.getZ(), 2.0 * e.getRadius());
            
            
        } else if(e instanceof Planet){
            
            
            Planet p = (Planet) e;
            drawSphere(g2, e.getX(), e.getY(), e.getZ(), e.getRadius());
            drawSphere(g2, e.getX(), e.getY(), e.getZ(), 2.0 * p.getAtmosphereHeight());
            
            
        } else if(e instanceof Structure){
            
            
            if(e instanceof WarpGate)
                drawRing(g2, e.getX(), e.getY(), e.getZ(), e.getRadius());
            /*else
                drawDiamond(g2, e.getX(), e.getY(), e.getZ(), e.getRadius());*/
            
            
        } else if(e instanceof Ship){
            
            
            /*if(e instanceof Corvette)
                drawTriangle(g2, e.getX(), e.getY(), e.getZ(), e.getRadius());
            else if(e instanceof Corvette)
                drawSquare(g2, e.getX(), e.getY(), e.getZ(), e.getRadius());
            else if(e instanceof Corvette)
                drawPentagon(g2, e.getX(), e.getY(), e.getZ(), e.getRadius());
            else if(e instanceof Corvette)
                drawHexagon(g2, e.getX(), e.getY(), e.getZ(), e.getRadius());
            else
                drawSphere(g2, e.getX(), e.getY(), e.getZ(), e.getRadius());
            
            
        } else { */
            drawSphere(g2, e.getX(), e.getY(), e.getZ(), e.getRadius());
        }
        
    }
    
    //Tags
    
    public void drawTag(Graphics2D g2, Entity e){
        String[] tag = e.getTag();
        drawTag(g2, e.getX(), e.getY(), e.getZ(), tag);
    }
    public void drawTagLines(Graphics2D g2, Entity e){
        String[] tag = e.getTag();
        drawTagLines(g2, e.getX(), e.getY(), e.getZ(), tag);
    }
    
    //Spheres
    
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
        
        values[0] += (leftThickness - rightThickness)/2;
        values[1] += (topThickness - bottomThickness)/2;
        
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
        
        /*if(dist > 250 * pixelMeterRatio && distCam < dist){
            return null;
        }*/
        
        int radius = (int)(250 * R/(distCam));
        
        values[2] = (int)(radius);
        
        if(radius < 0.5){
            return null;
        }
        
        //I think these two lines of code were wrong
        //values[0] += (leftThickness - rightThickness)/2;
        //values[1] += (topThickness - bottomThickness)/2;
        
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
    
    //Triangles
    
    public void drawTriangle(Graphics2D g, double X, double Y, double Z, double R){
        int[] points = buildSphere(X,Y,Z,R);
        
        if(points == null){
            return;
        }
        
        int x_ = points[0];
        int y_ = points[1];
        int rad = points[2];
        int[] xPts = {(x_)      ,(int)(x_ + (rad * Math.acos(Math.toRadians(210)))), (int)(x_ + (rad * Math.acos(Math.toRadians(330))))};
        int[] yPts = {(y_ - rad),(int)(y_ - (rad * Math.asin(Math.toRadians(210)))), (int)(y_ - (rad * Math.asin(Math.toRadians(210))))};
        
        g.drawPolygon(xPts, yPts, 2);
        
    }
    
    public void fillTriangle(Graphics2D g, double X, double Y, double Z, double R){
        int[] points = buildSphere(X,Y,Z,R);
        
        if(points == null){
            return;
        }
        
        int x_ = points[0];
        int y_ = points[1];
        int rad = points[2];
        int[] xPts = {(x_)      ,(int)(x_ + (rad * Math.acos(Math.toRadians(210)))), (int)(x_ + (rad * Math.acos(Math.toRadians(330))))};
        int[] yPts = {(y_ - rad),(int)(y_ - (rad * Math.asin(Math.toRadians(210)))), (int)(y_ - (rad * Math.asin(Math.toRadians(210))))};
        
        g.fillPolygon(xPts, yPts, 3);
        
    }
    
    //Squares
    
    public void drawSquare(Graphics2D g, double X, double Y, double Z, double R){
        int[] points = buildSphere(X,Y,Z,R);
        
        if(points == null){
            return;
        }
        
        int x_ = points[0];
        int y_ = points[1];
        int rad = points[2];
        g.drawRect(x_ - rad, y_ - rad, 2 * rad, 2 * rad);
        
    }
    
    public void fillSquare(Graphics2D g, double X, double Y, double Z, double R){
        int[] points = buildSphere(X,Y,Z,R);
        
        if(points == null){
            return;
        }
        
        int x_ = points[0];
        int y_ = points[1];
        int rad = points[2];
        g.fillRect(x_ - rad, y_ - rad, 2 * rad, 2 * rad);
        
    }
    
    //Diamond
    
    public void drawDiamond(Graphics2D g, double X, double Y, double Z, double R){
        int[] points = buildSphere(X,Y,Z,R);
        
        if(points == null){
            return;
        }
        
        int x_ = points[0];
        int y_ = points[1];
        int rad = points[2];
        double arc = Math.acos(Math.toRadians(45));
        int[] xPts = {x_                   , x_ + (int)(rad * arc), x_                    , x_ - (int)(rad * arc) };
        int[] yPts = {y_ + (int)(rad * arc), y_                   , y_ - (int)(rad * arc) , y_                    };
        
        g.drawPolygon(xPts, yPts, 4);
        
    }
    
    public void fillDiamond(Graphics2D g, double X, double Y, double Z, double R){
        int[] points = buildSphere(X,Y,Z,R);
        
        if(points == null){
            return;
        }
        
        int x_ = points[0];
        int y_ = points[1];
        int rad = points[2];
        double arc = Math.acos(Math.toRadians(45));
        int[] xPts = {x_                   , x_ + (int)(rad * arc), x_                    , x_ - (int)(rad * arc) };
        int[] yPts = {y_ + (int)(rad * arc), y_                   , y_ - (int)(rad * arc) , y_                    };
        
        g.fillPolygon(xPts, yPts, 4);
        
    }
    
    //Pentagon
    
    public void drawPentagon(Graphics2D g, double X, double Y, double Z, double R){
        int[] points = buildSphere(X,Y,Z,R);
        
        if(points == null){
            return;
        }
        
        int x_ = points[0];
        int y_ = points[1];
        int rad = points[2];
        int[] xPts = {(x_)      ,(int)(x_ + (rad * Math.acos(Math.toRadians(162)))), (int)(x_ + (rad * Math.acos(Math.toRadians(234)))),(int)(x_ + (rad * Math.acos(Math.toRadians(306)))), (int)(x_ + (rad * Math.acos(Math.toRadians(18))))};
        int[] yPts = {(y_ - rad),(int)(y_ - (rad * Math.asin(Math.toRadians(162)))), (int)(y_ - (rad * Math.asin(Math.toRadians(234)))),(int)(y_ - (rad * Math.asin(Math.toRadians(306)))), (int)(y_ - (rad * Math.asin(Math.toRadians(18))))};
        
        g.drawPolygon(xPts, yPts, 5);
        
    }
    
    public void fillPentagon(Graphics2D g, double X, double Y, double Z, double R){
        int[] points = buildSphere(X,Y,Z,R);
        
        if(points == null){
            return;
        }
        
        int x_ = points[0];
        int y_ = points[1];
        int rad = points[2];
        int[] xPts = {(x_)      ,(int)(x_ + (rad * Math.acos(Math.toRadians(162)))), (int)(x_ + (rad * Math.acos(Math.toRadians(234)))),(int)(x_ + (rad * Math.acos(Math.toRadians(306)))), (int)(x_ + (rad * Math.acos(Math.toRadians(18))))};
        int[] yPts = {(y_ - rad),(int)(y_ - (rad * Math.asin(Math.toRadians(162)))), (int)(y_ - (rad * Math.asin(Math.toRadians(234)))),(int)(y_ - (rad * Math.asin(Math.toRadians(306)))), (int)(y_ - (rad * Math.asin(Math.toRadians(18))))};
        
        g.fillPolygon(xPts, yPts, 5);
        
    }
    
    //Hexagon
    
    public void drawHexagon(Graphics2D g, double X, double Y, double Z, double R){
        int[] points = buildSphere(X,Y,Z,R);
        
        if(points == null){
            return;
        }
        
        int x_ = points[0];
        int y_ = points[1];
        int rad = points[2];
        double sin = Math.asin(Math.toRadians(60));
        int[] xPts = {x_ + (rad/2),x_ + (rad/-2),(x_ - rad),x_ + (rad/-2),(int)(x_ + (rad/2)),x_ + rad};
        int[] yPts = {y_ - (int)(rad * sin), y_ - (int)(rad * sin), y_, y_ - (int)(rad * -sin), y_ - (int)(rad * -sin), y_};
        
        g.drawPolygon(xPts, yPts, 6);
        
    }
    
    public void fillHexagon(Graphics2D g, double X, double Y, double Z, double R){
        int[] points = buildSphere(X,Y,Z,R);
        
        if(points == null){
            return;
        }
        
        int x_ = points[0];
        int y_ = points[1];
        int rad = points[2];
        double sin = Math.asin(Math.toRadians(60));
        int[] xPts = {x_ + (rad/2),x_ + (rad/-2),(x_ - rad),x_ + (rad/-2),(int)(x_ + (rad/2)),x_ + rad};
        int[] yPts = {y_ - (int)(rad * sin), y_ - (int)(rad * sin), y_, y_ - (int)(rad * -sin), y_ - (int)(rad * -sin), y_};
        
        g.fillPolygon(xPts, yPts, 6);
        
    }
    
    //Rings
    
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
        
        
        int keyID = e.getKeyCode();
        
        if(keyID == 0){
            return;
        }
        
        try{
            String bindingName = Options.getOption(e.getKeyCode()).name;
            
            if(MenuManager.getMenu() instanceof GameMenu)
            switch (bindingName) {
                //Movement
                case "MOVE_FORWARD":
                    X += Math.cos(XZ_ROT) * movementSensitivity * pixelMeterRatio;
                    Z += Math.sin(XZ_ROT) * movementSensitivity * pixelMeterRatio;
                    viewNumber = 0;
                    break;
                case "MOVE_BACKWARD":
                    X -= Math.cos(XZ_ROT) * movementSensitivity * pixelMeterRatio;
                    Z -= Math.sin(XZ_ROT) * movementSensitivity * pixelMeterRatio;
                    viewNumber = 0;
                    break;
                case "MOVE_LEFT":
                    X += Math.cos(XZ_ROT + 90) * movementSensitivity * pixelMeterRatio;
                    Z += Math.sin(XZ_ROT + 90) * movementSensitivity * pixelMeterRatio;
                    viewNumber = 0;
                    break;
                case "MOVE_RIGHT":
                    X -= Math.cos(XZ_ROT + 90) * movementSensitivity * pixelMeterRatio;
                    Z -= Math.sin(XZ_ROT + 90) * movementSensitivity * pixelMeterRatio;
                    viewNumber = 0;
                    break;
                case "MOVE_UP":
                    Y += movementSensitivity * movementSensitivity * pixelMeterRatio;
                    viewNumber = 0;
                    break;
                case "MOVE_DOWN":
                    Y -= movementSensitivity * movementSensitivity * pixelMeterRatio;
                    viewNumber = 0;
                    break;
                case "CHANGE_VIEW":
                    viewNumber++;
                    viewNumber %= 3;
                    break;

            }
            
        } catch(NullPointerException npe){
            
        }
        
        
        
        
        if(e.getKeyCode() == KeyEvent.VK_CONTROL){
            Ctrl_Held = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        
        
        
        if(e.getKeyCode() == KeyEvent.VK_CONTROL){
            Ctrl_Held = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        MenuManager.executeButtons(e.getX(), e.getY());
        
        //Allows for Entity objects to be selected only if clicking inside the battlefield display.
        if(MenuManager.getMenu() instanceof GameMenu && e.getX() < getAppletWidth() - rightThickness){

            long clickedID = GameControlSettings.getClickedEntityID(e.getX(), e.getY());

            //This should allow Entity objects to be selected
            if(MenuManager.getMenu() instanceof GameMenu){
                if(Ctrl_Held){
                    GameControlSettings.addSelectedID(clickedID);
                } else {
                    GameControlSettings.setSelectedID(clickedID);
                }
            } else {
                GameControlSettings.setSelectedID(-1);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressMouseX = e.getX();
        pressMouseY = e.getY();
        oldXZ = XZ_ROT;
        oldY = Y_ROT;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(MenuManager.getMenu() instanceof GameMenu){
            oldXZ = XZ_ROT;
            oldY = Y_ROT;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    
    }

    @Override
    public void mouseExited(MouseEvent e) {
    
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(MenuManager.getMenu() instanceof GameMenu){
            XZ_ROT = oldXZ + (e.getX() - pressMouseX) * Math.toRadians(mouseSensitivity);
            Y_ROT = oldY + (e.getY() - pressMouseY) * Math.toRadians(mouseSensitivity);
        }
        
        MenuManager.executeSliders(e.getX(), e.getY());
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
    
    public int getAppletWidth(){
        return frame.getContentPane().getWidth() + 9;
    }
    
    public int getAppletHeight(){
        return frame.getContentPane().getHeight() + 9;
    }
    
    /**
     * Activates or deactivates the debug mode
     * @param mode
     */
    public void setDebug(boolean mode){
        debug = mode;
    }
    
}
