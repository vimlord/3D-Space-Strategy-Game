/*
 * The ListenerThread fulfills the requests made by a client.
 */

package server.threads;

import engine.entities.Entity;
import engine.main.EntityList;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
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
    
    private ArrayList<Serializable> outputQueue = new ArrayList<>();
    
    public ListenerThread(Socket socket) {
        super("ListenerThread");
        this.socket = socket;
    }
    
    public void run(){
        
        try{
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch(Exception e){
            e.printStackTrace();
            return;
        }
        try(
                BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()));
            ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ){
            
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
                    outputStream.writeObject(outputQueue.remove(0));
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
        
    }
    
    public void processInput(String input){
        //Makes sure the Alpha tag is correct
        if(!input.substring(0,1).equals("[")){
            return;
        }
        String alphaTag = input.substring(0,input.indexOf("]"));
        
        if(alphaTag.equals("[ORDER]")){
            String order = input.substring(alphaTag.length());
            String betaTag = input.substring(0,order.indexOf("]"));
            order = order.substring(betaTag.length());
            
            int faction = Integer.parseInt(betaTag.substring(betaTag.indexOf("(") + 1, betaTag.indexOf(")")));
            
            long ship = Long.parseLong(betaTag.substring(betaTag.indexOf("(", betaTag.indexOf(")")) + 1, betaTag.indexOf(")",betaTag.indexOf(")")+1)));
            
            new OrderThread(faction, ship, order).start();
            
        } else if(alphaTag.equals("[SEND]")){
            String order = input.substring(alphaTag.length());
            String betaTag = input.substring(0,order.indexOf("]"));
            
            if(betaTag.equals("[ENTITY_LIST]")){
                //Sends the EntityList to the client
                ArrayList<Entity> entity = EntityList.getEntityList();
                outputQueue.add((Serializable) new EntityListWrapper(entity));
            }
        }
        
        if(alphaTag.equals("[EXIT]")){
            return;
        }
    }
    
}
