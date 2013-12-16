 package survive;

 public class Inventory extends Drawable
 {
   protected int itemCode;
   protected int quantity;
   protected int x, y;
   protected boolean[] slotEquipped = new boolean[4];
   public Inventory(String ref, int itemCode, int quantity, Coords coords)
   {
     super(coords, SpriteStore.get().getSprite(ref));
     this.itemCode = itemCode;
     this.quantity = quantity;
     for(int i = 0; i < 4; i++) {
         slotEquipped[i] = false;
     }

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
   public boolean isEquipped()
   {
       boolean isEquipped = false;
       for(int i = 0; i < slotEquipped.length; i++) {
           if (slotEquipped[i] == true) {
               isEquipped = true;
           }          
       } 
       return isEquipped;
   }
   public int getX()
   {
       return x;
   }
   public int getY()
   {
       return y;
   }
 }
//Item Codes
//Log 1
//Stone 2
//Log Wall 3
//Barrel 4
//Axe 5








