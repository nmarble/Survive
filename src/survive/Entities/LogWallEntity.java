 package survive.Entities;
 
 import survive.MiddleLayer;
 import survive.Survive;
 
 public class LogWallEntity
   extends MiddleLayer
 {
   private Survive survive;
   
   public LogWallEntity(Survive survive, String ref, int x, int y, String type)
   {
     super(ref, x, y, type);
     
     this.survive = survive;
   }
   
   public int getModifiedX()
   {
     int modifiedX = 0;
     return modifiedX;
   }
   
   public int getModifiedY()
   {
     int modifiedY = 0;
     return modifiedY;
   }

    public void interact()
   {
     this.survive.removeMiddleLayer(this);
     this.survive.addToInventory(3, 1);
   }
 }









