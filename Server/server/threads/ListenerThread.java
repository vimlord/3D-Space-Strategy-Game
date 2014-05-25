/*
 * The ListenerThread fulfills the requests made by a client.
 */

package server.threads;

import engine.entities.Entity;
import engine.gameMechanics.factions.FactionList;
import engine.gameMechanics.gameModes.GameMode;
import engine.main.CycleRunner;
import engine.main.EntityList;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import server.main.Server;
import server.object_wrappers.EntityListWrapper;

/**
 *
 * @author Christopher
 */
public class ListenerThread extends Thread{
    private Socket socket = null;
    
    private PrintWriter out;
    private BufferedReader in;
    private ObjectInputStream inStream;
    private ObjectOutputStream outputStream;
    
    private String connectionUser = null;
    
    private ArrayList<Serializable> outputQueue = new ArrayList<>();
    
    public ListenerThread(Socket socket) {
        this.socket = socket;
        try{
            socket.getOutputStream().flush();
            out = new PrintWriter(socket.getOutputStream(), true);
            out.flush();
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.flush();
            in = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()));
            inStream = new ObjectInputStream(socket.getInputStream());
        } catch(IOException e){
            e.printStackTrace();
            return;
        }
        
    }
    
    public void run(){
        
        try{
            
            Server.addLogEvent("\"" + connectionUser + "\" has connected to the server.");
            System.out.println(Server.getLatestLogEvent());
            
            Object input = null;
            
            //This next line of code takes an Object as input. The Object will
            //then be tested to see if it matches any of the known Object types.
            while((input = inStream.readObject()) != null){
                processInput(input);
                
                if(input.equals("[EXIT]")){
                    break;
                }
                
                //Processes the outgoing objects, if there are any.
                if(outputQueue.size() > 0){
                    //Outputs whatever is on the top of the list
                    Object outputObject = outputQueue.remove(0);
                    //System.out.println(outputObject);
                    outputStream.writeObject(outputObject);
                }
                
            }
            
            socket.close();
            
        } catch(IOException e){
            e.printStackTrace();
            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        
    }
    
    
    public void processInput(Object obj){
        if(obj instanceof String)
            processInput((String) obj);
        else if(obj instanceof GameMode){
            //Insert code here for setting the current GameMode. It doesn't have
            //to be added, but you never know.
        }
        
    }
    
    public void processInput(String input){
        //Makes sure the Alpha tag is correct
        if(!input.substring(0,1).equals("[")){
            return;
        }
        String alphaTag = input.substring(0,input.indexOf("]") + 1);
        
        if(alphaTag.equals("[ORDER]")){
            String order = input.substring(alphaTag.length());
            String betaTag = input.substring(0,order.indexOf("]"));
            order = order.substring(betaTag.length());
            
            int faction = Integer.parseInt(betaTag.substring(betaTag.indexOf("(") + 1, betaTag.indexOf(")")));
            
            long ship = Long.parseLong(betaTag.substring(betaTag.indexOf("(", betaTag.indexOf(")")) + 1, betaTag.indexOf(")",betaTag.indexOf(")")+1)));
            
            new OrderThread(faction, ship, order).start();
            
        } else if(alphaTag.equals("[SEND]")){
            String order = input.substring(alphaTag.length());
            String betaTag = order.substring(0,order.indexOf("]"));
            
            if(betaTag.equals("[ENTITY_LIST]")){
                //Sends the EntityList to the client
                ArrayList<Entity> entity = EntityList.getEntityList();
                outputQueue.add((Serializable) new EntityListWrapper(entity));
            }
            if(betaTag.equals("[GAMEMODE]")){
                outputQueue.add(CycleRunner.getGamemode());
            }
        }
        
        
        if(alphaTag.equals("[CONNECT]")){
            connectionUser = input.substring(alphaTag.length());
            int size = 3;
            if(size > connectionUser.length()){
                size = connectionUser.length();
            }
            
            String tag = (connectionUser.substring(0, size));
            
            FactionList.addFaction(tag);
            
            int assignedID = FactionList.getFaction(tag).getID();
            
            String idSent = ("[FACTIONID]" + assignedID);
            
            outputQueue.add(idSent);
        }
        
        if(alphaTag.equals("[EXIT]")){
            return;
        }
    }
    
}
