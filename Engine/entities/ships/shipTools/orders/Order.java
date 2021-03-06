/*
 * This class stores code for a command to be executed by a Ship object
 */

package entities.ships.shipTools.orders;

/**
 *
 * @author Christopher Hittner
 */
public abstract class Order {
    protected String order;
    protected boolean status = true;
    
    /*
     * ORDER TYPES:
     *  (ACC): Accelerate
     *  (ROT): Rotate
     *  (ATK): Attack Target
     */
    
    
    public Order(){
        
    }
    
    public String getOrder(){
        return order;
    }
    public boolean getStatus(){
        return status;
    }
    
}
