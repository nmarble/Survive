/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package survive.Entities;

/**
 *
 * @author Marble
 */
public class ItemDescription {
    
    int itemCode;
    public ItemDescription(int itemCode) {
        this.itemCode = itemCode;
    }
    public String getDescription () {
        switch (itemCode) {
            case 1:
                return "A log used in crafting";
        }
        return "No description";
    }
    public String getName () {
        switch (itemCode) {
            case 1:
                return "Log";
            case 2:
                return "Stone";
            case 3:
                return "Log Wall";
            case 4:
                return "Barrel";
            case 5:
                return "Axe";
            case 6:
                return "Rifle";
            case 7:
                return "Window";
            case 8:
                return "Leaves";
            case 9:
                return "Ammo";
            case 10:
                return "Dead Body";
            case 11:
                return "Grass";
            case 12:
                return "Gravel";
            case 13:
                return "Water";
            case 14:
                return "Wood Floor";
            case 15:
                return "Torch";
            case 16:
                return "Trunk";
            case 17:
                return "Boulder";
            case 18:
                return "Door";
        }
        return "No Name";
    }
}
