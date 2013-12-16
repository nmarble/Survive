 package survive.Entities;
 
import static java.lang.Math.abs;
import survive.Coords;
 import survive.Hud;
 import survive.Sprite;
 import survive.SpriteStore;
 import survive.Survive;
 import survive.Direction;
import survive.Drawable;
 
 public class BulletEntity
 extends Drawable
 {
   private Survive survive;
   private Sprite[] frames = new Sprite[2];
   private Coords endCoords = new Coords(0,0);
   private int rotation = 0;
   double x = 0;
   double y = 0;
   int time = 0;
   double xV, yV, xD, yD, xE, yE, xO, yO;
   public BulletEntity(Survive survive, String ref, final Coords coords, final Coords endCoords, int rotation)
   {
     super(coords, SpriteStore.get().getSprite(ref));

     frames[0] = SpriteStore.get().getSprite("sprites/bullet.png");
     xE = 0;
     yE = 0;
     xD = abs((double)coords.getX() - (double) endCoords.getX());
     yD = abs((double)coords.getY() - (double) endCoords.getY());
     xO = (double)coords.getX();
     yO = (double)coords.getY();
     
     
     this.rotation = rotation;
     this.endCoords = endCoords;
     this.survive = survive;
   }
  
   public void changeFrame(int frameNumber)
   {
     if (frameNumber < 2) {
       setSprite(frames[frameNumber]);
     }
   }
   public Coords getFinal()
   {
       return endCoords;
   }
   public void moveBullet()
   {
       
       x = (double)coords.getX();
       y = (double)coords.getY();
       
       if (xO < endCoords.getX() && xD > yD) {    
       xV = 20;
       yV = -(yD / xD);       
       x = x + xV;
       yE = yE + yV;
       }
       if (xO > endCoords.getX() && xD > yD) {    
       xV = -20;
       yV = -(yD / xD);       
       x = x + xV;
       yE = yE + yV;
       }
       if (xD < yD && yO < endCoords.getY()) {
           yV = 20;
           xV = -(xD / yD);
           y = y + yV;
           xE = xE + xV;
       }
       if (xD < yD && yO > endCoords.getY()) {
           yV = -20;
           xV = -(xD / yD);
           y = y + yV;
           xE = xE + xV;
       }
       if (yE < -1) {
           yE = yE + 1;
           if (yO > endCoords.getY()&& xD > yD) {
            y = (double)coords.getY() - 20;
           }
           if (yO < endCoords.getY()&& xD > yD) {
            y = (double)coords.getY() + 20;
           }
       }
       if (xE < -1) {
           xE = xE + 1;
           if (xO > endCoords.getX()&& yD > xD) {
            x = (double)coords.getX() - 20;
           }
           if (xO < endCoords.getX()&& yD > xD) {
            x = (double)coords.getX() + 20;
           }

       }
       
       time++;

       coords = new Coords((int)Math.round(x / 20) *20,(int)Math.round(y / 20) *20);
   }
   public int getRot()
   {
       return rotation;
   }
   public int getTime()
   {
       return time;
   }
 }









