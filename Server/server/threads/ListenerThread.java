/*
 * The ListenerThread fulfills the requests made by a client.
 */

package server.threads;

import engine.entities.Entity;
import engine.entities.ships.*;
import engine.gameMechanics.factions.FactionList;
import engine.gameMechanics.gameModes.GameMode;
import engine.main.CycleRunner;
import engine.main.EntityList;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import server.main.Server;
import engine.object_wrappers.EntityListWrapper;
import server.players.Player;
import server.players.PlayerList;

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
    
    private boolean listening = true, inGame = false;
    
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
            
            return;
        }
        
    }
    
    public void run(){
        
        Object input = null;
        
        try{
            
            
            //This next line of code takes an Object as input. The Object will
            //then be tested to see if it matches any of the known Object types.
            while(listening){
                try{
                if(inGame != CycleRunner.getGamemode().getStatus()){
                    inGame = !inGame;
                    sendObject("[GAMESTATUS]STARTING");
                } else if(PlayerList.getPlayerList().size() >= Server.getPlayerLimit() && Server.getPlayerLimit() > 0){
                    break;
                }
                } catch(NullPointerException n){
                    
                }
                
                input = null;
                try{
                    input = inStream.readObject();
                } catch(StreamCorruptedException s){
                    input = "[UNKNOWN]";
                } catch(OptionalDataException o){
                    input = "[UNKNOWN]";
                }
                
                processInput(input);
                
                
                if(input.equals("[EXIT]") || input == null || input.equals("[INVALIDUSER]")){
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
            
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            try{
                if(input.equals("[EXIT]") || input == null){
                    Server.addLogEvent("\"" + connectionUser + "\" has disconnected from the server.");
                    System.out.println(Server.getLatestLogEvent());
                    PlayerList.getPlayer(connectionUser).setActive(false);
                    PlayerList.getPlayer(connectionUser).setReadiness(false);
                } else {
                    Server.addLogEvent("A user posing as \"" + connectionUser + "\" has been removed from the server.");
                    System.out.println(Server.getLatestLogEvent());

                }
            } catch(NullPointerException e){
                Server.addLogEvent("\"" + connectionUser + "\" has disconnected from the server.");
                System.out.println(Server.getLatestLogEvent());
                PlayerList.getPlayer(connectionUser).setActive(false);
                PlayerList.getPlayer(connectionUser).setReadiness(false);
            }
        }
        
        //If the game isn't running, removes the player from the list.
        if(!CycleRunner.getGamemode().getStatus()){
            FactionList.removeFaction(PlayerList.getPlayer(connectionUser).getFactionID());
            PlayerList.getPlayerList().remove(PlayerList.getPlayer(connectionUser));
        }
        
        Server.getConnections().remove(this);
        
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
            String betaTag = order.substring(0,order.indexOf("]"));
            order = order.substring(betaTag.length());
            
            int faction = Integer.parseInt(betaTag.substring(betaTag.indexOf("(") + 1, betaTag.indexOf(")")));
            
            long ship = Long.parseLong(betaTag.substring(betaTag.indexOf("(", betaTag.indexOf(")")) + 1, betaTag.indexOf(")",betaTag.indexOf(")")+1)));
            
            new OrderThread(faction, ship, order).start();
            
        } else if(alphaTag.equals("[SEND]")){
            String order = input.substring(alphaTag.length());
            String betaTag = order.substring(0,order.indexOf("]") + 1);
            
            if(betaTag.equals("[ENTITY_LIST]")){
                //Sends the EntityList to the client
                ArrayList<Entity> entity = EntityList.getEntityList();
                outputQueue.add(new EntityListWrapper(entity));
            }
            if(betaTag.equals("[GAMEMODE]")){
                outputQueue.add(CycleRunner.getGamemode());
            }
            if(betaTag.equals("[STATUS]")){
                
                if(inGame != CycleRunner.getGamemode().getStatus()){
                    inGame = !inGame;
                    sendObject("[GAMESTATUS]STARTING");
                
                }
            }
        } else if(alphaTag.equals("[CONNECT]")){
            connectionUser = input.substring(alphaTag.length());
            int size = 3;
            if(size > connectionUser.length()){
                size = connectionUser.length();
            }
            
            String tag = (connectionUser.substring(0, size));
            
            
            listening = true;
            
            Player foundPlayer = PlayerList.getPlayer(connectionUser);
            
            
            if(foundPlayer == null){
                FactionList.addFaction(tag);
                
                int assignedID = FactionList.getFaction(tag).getID();

                String idSent = ("[FACTIONID]" + assignedID);

                outputQueue.add(idSent);
                
                PlayerList.addPlayer(new Player(connectionUser, assignedID));
                PlayerList.getPlayer(assignedID).setActive(true);
                
                Server.addLogEvent("\"" + connectionUser + "\" has connected to the server.");
                
            }  else if(!foundPlayer.isActive()) {
                int assignedID = FactionList.getFaction(tag).getID();

                String idSent = ("[FACTIONID]" + assignedID);
                
                
                outputQueue.add(idSent);

                Server.addLogEvent("\"" + connectionUser + "\" has reconnected to the server.");
                System.out.println(Server.getLatestLogEvent());
                
                PlayerList.getPlayer(connectionUser).setActive(true);
                
            } else {
                listening = false;
            }
            
            /*
            
            if(FactionList.getFaction(tag) == null){
                //The user is legitimate
                allowConnection = true;
                FactionList.addFaction(tag);
            } else {
                //The client is trying to pose as another person
                allowConnection = false;
                listening = false;
            }
            
            //Only connects if allowed
            if(allowConnection){
                int assignedID = FactionList.getFaction(tag).getID();

                String idSent = ("[FACTIONID]" + assignedID);

                outputQueue.add(idSent);

                Server.addLogEvent("\"" + connectionUser + "\" has connected to the server.");
                System.out.println(Server.getLatestLogEvent());
            }
                    */
            
        } else if(alphaTag.equals("[SERVERMESSAGE]")){
            String message = input.substring(alphaTag.length());
            if(message.equals("Ready")){
                PlayerList.getPlayer(connectionUser).setReadiness(true);
            }
            
        }
        
        if(alphaTag.equals("[EXIT]")){
            return;
        }
    }
    
    public void sendObject(Serializable obj) {
        if(obj.equals("[GAMESTATUS]STARTING"))
            inGame = true;
        
        try{
            outputStream.writeObject(obj);
        } catch (Exception e){
            outputQueue.add(obj);
        }
    }
    
}
