 package survive;
 
import java.awt.AlphaComposite;
import java.awt.Composite;
 import java.awt.Graphics;
 import java.awt.Graphics2D;
 import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
 
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
   public void fadeRotDraw(Graphics2D g, int x, int y, int degree, float alpha)
   {
       AffineTransform old = g.getTransform();
       g.translate(x, y);
       g.rotate(Math.toRadians(degree), 10, 10);
       Composite oldComp = g.getComposite();
       g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
       g.drawImage(image, 0, 0, null);
       g.setComposite(oldComp);
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
   public void changeBrightness(float val) 
   {
       RescaleOp newOp = new RescaleOp(val, 0, null);
       BufferedImage newImg = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);
       image = newOp.filter(newImg, null);
   }
 }









