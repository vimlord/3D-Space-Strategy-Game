/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.settings;

/**
 *
 * @author Christopher
 */
public class Option {
    
    public final String name;
    private int code;
    
    public Option(String nm, int keyCode){
        name = nm;
        code = keyCode;
    }
    
    public void setCode(int keyCode){
        code = keyCode;
    }
    
    public int getCode(){
        return code;
    }
    
}
