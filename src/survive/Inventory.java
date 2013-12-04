 package survive;

 public class Inventory extends Drawable
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
   public int getX()
   {
       return x;
   }
   public int getY()
   {
       return y;
   }
 }









