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
    private int frame;
    private int rotate;
    
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
    public void setFrame(int newFrame) {
        frame = newFrame;
    }
    public void setRotate (int newRotate) {
        rotate = newRotate;
    }
    public void addRotate (int newRotate) {
        rotate = rotate + newRotate;
    }
}
