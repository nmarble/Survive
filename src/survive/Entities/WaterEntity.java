 package survive.Entities;
 
 import survive.LowerLayer;
 import survive.Survive;
 
 public class WaterEntity
   extends LowerLayer
 {
   private Survive survive;
   
   public WaterEntity(Survive survive, String ref, int x, int y, String type)
   {
     super(ref, x, y, type);
     
     this.survive = survive;
   }
   public boolean passable()
   {
       return false;
   }
 }









