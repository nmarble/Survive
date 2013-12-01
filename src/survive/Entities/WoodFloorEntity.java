 package survive.Entities;
 
import survive.Global;
 import survive.LowerLayer;
 import survive.Survive;
 import survive.Coords;
 
 public class WoodFloorEntity
   extends LowerLayer
 {
   private Survive survive;
   
   public WoodFloorEntity(Survive survive, String ref, Coords coords, String type)
   {
     super(ref, coords, type);
     
     this.survive = survive;
   }

   public boolean passable()
   {
       return true;
   }

 }









