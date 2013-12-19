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
public class Surrounding {
    private final int frame;
    private final int rotate;
    
    public Surrounding (int frame, int rotate) {
        this.frame = frame;
        this.rotate = rotate;        
    }
    public int getFrame() {
        return frame;
    }
    public int getRotate() {
        return rotate;
    }
}
