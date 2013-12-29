/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package survive;

import java.awt.Color;

/**
 *
 * @author Marble
 */
public class TextScroll {
    String text;
    int size;
    Color color;
    public TextScroll(String text, int size, Color color) {
        this.text = text;
        this.size = size;
        this.color = color;
    }
    public String getText() {
        return text;
    }
    public int getSize() {
        return size;
    }
    public Color getColor() {
        return color;
    }
}
