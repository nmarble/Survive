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
     this.sprite = SpriteStore.get().getSprite(ref);
     this.itemCode = itemCode;
     this.quantity = quantity;
     this.x = 0;
     this.y = 0;
   }
   
   public int getItemCode()
   {
     return this.itemCode;
   }
   
   public int getQuantity()
   {
     return this.quantity;
   }
   public int getX()
   {
       return this.x;
   }
   public int getY()
   {
       return this.y;
   }
   public void addQuantity()
   {
     this.quantity += 1;
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
    this.sprite.draw(g, (int)this.x, (int)this.y);
  }
 }









