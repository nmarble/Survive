 package survive;

 public class Inventory extends Drawable implements java.io.Serializable
 {
   protected int itemCode;
   protected int quantity;
   protected int x, y;
   public Inventory(String ref, int itemCode, int quantity, Coords coords)
   {
     super(coords, SpriteStore.get().getSprite(ref));
     this.itemCode = itemCode;
     this.quantity = quantity;
   }
   
   public int getItemCode()
   {
     return itemCode;
   }
   
   public int getQuantity()
   {
     return quantity;
   }
   
   public void addQuantity(int quantity)
   {
     this.quantity += quantity;
   }
   public void removeQuantity(int quantity)
   {
     this.quantity -= quantity;
   }
   public void setXY(int incX, int incY)
   {
       x = incX;
       y = incY;
   }
   public boolean equipable()
   {
       boolean equip = false;
       if (itemCode == 5) {
           equip = true;
       }
       if (itemCode == 6) {
           equip = true;
       }
       return equip;
   }
   public int getX()
   {
       return x;
   }
   public int getY()
   {
       return y;
   }
   public int[] interactableCodes(int code)
   {
       if (code == 5) {
          int[] interCodes = {1,3,4,5,6,7,8,9,15,16,18,19};
          return interCodes;
       }
       else if (code == 0) {
          int[] interCodes = {4,5,6,7,8,9,15,18,19};
          return interCodes;
       }
       return new int[]{0};
   }
 }
//Item Codes
//Log 1
//Stone 2
//Log Wall 3
//Barrel 4
//Axe 5
//Rifle 6
//Window 7
//Leaves 8
//Ammo 9
//Dead Body 10
//Grass 11
//Gravel 12
//Water 13
//WoodFloor 14
//Torch 15
//Trunk 16
//Boulder 17
//Door 18









