/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package survive;

/**
 *
 * @author Marble
 */
public class SaveVariables implements java.io.Serializable{
    
    private final int xCoords;
    private final int yCoords;
    private final int type;
    
    public SaveVariables(int xCoords, int yCoords, int type) {
        
        this.xCoords = xCoords;
        this.yCoords = yCoords;
        this.type = type;
   }
    public int getX() {
        return xCoords;
    }
    public int getY() {
        return yCoords;
    }
    public int getType() {
        return type;
    }
}
