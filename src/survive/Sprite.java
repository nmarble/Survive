 package survive;
 
 import java.awt.Graphics;
 import java.awt.Image;
 
 public class Sprite
 {
   private Image image;
   
   public Sprite(Image image)
   {
     this.image = image;
   }
   
   public int getWidth()
   {
     return this.image.getWidth(null);
   }
   
   public int getHeight()
   {
     return this.image.getHeight(null);
   }
   
   public void draw(Graphics g, int x, int y)
   {
     g.drawImage(this.image, x, y, null);
   }
 }









