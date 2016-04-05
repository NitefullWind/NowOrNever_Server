/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myClass;

/**
 *
 * @author NitefullWind
 */
public class MyJSON {
    private String json;
    private int nodeCount;
    public MyJSON() {
        json = "{";
        nodeCount = 0;
    }
    public void put(String key, Object value) {
        if(nodeCount>0) {
            json += ", "; 
        }
        json += "\"" + key + "\": \"" + value + "\"";
        nodeCount++;
    }
    
    public String toString() {
        json += "}";
        return json;
    }
}
