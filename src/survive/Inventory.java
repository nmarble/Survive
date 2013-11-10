 package survive;
 
import java.awt.Graphics;

 public class Inventory
 {
   protected int itemCode;
   protected int quantity;
   protected int x;
   protected int y;
   protected Sprite sprite;
   
   public Inventory(String ref, int itemCode, int quantity)
   {
     sprite = SpriteStore.get().getSprite(ref);
     this.itemCode = itemCode;
     this.quantity = quantity;
     x = 0;
     y = 0;
   }
   
   public int getItemCode()
   {
     return itemCode;
   }
   
   public int getQuantity()
   {
     return quantity;
   }
   public int getX()
   {
       return x;
   }
   public int getY()
   {
       return y;
   }
   public void addQuantity(int quantity)
   {
     this.quantity += quantity;
   }
   public void removeQuantity(int quantity)
   {
     this.quantity -= quantity;
   }
   public void changeX(int x)
   {
     this.x = x; 
   }
   public void changeY(int y)
   {
     this.y = y; 
   }
   public void draw(Graphics g)
  {
    sprite.draw(g, (int)x, (int)y);
  }
 }









