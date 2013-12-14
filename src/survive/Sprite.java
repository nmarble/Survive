 package survive;
 
 import java.awt.Graphics;
 import java.awt.Graphics2D;
 import java.awt.Image;
import java.awt.geom.AffineTransform;
 
 public class Sprite
 {
   private Image image;
   
   public Sprite(Image image)
   {
     this.image = image;
   }
   public Image getImage()
   {
       return image;
   }
   public int getWidth()
   {
     return image.getWidth(null);
   }
   
   public int getHeight()
   {
     return image.getHeight(null);
   }
   
   public void draw(Graphics g, int x, int y)
   {
     g.drawImage(image, x, y, null);
   }
   public void rotDraw(Graphics2D g, int x, int y, int degree)
   {
      AffineTransform old = g.getTransform();
      g.translate(x, y);
      g.rotate(Math.toRadians(degree),10,10);
      g.drawImage(image, 0, 0, null);
      g.setTransform(old);
   }
   public void scaleDraw(Graphics2D g, int x, int y, double xScale, double yScale)
   {
      AffineTransform old = g.getTransform();
      AffineTransform newG = new AffineTransform();
      newG.scale(xScale, yScale);
      g.drawImage(image, newG, null);
      g.setTransform(old);
   }
 }









