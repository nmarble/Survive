package survive.Entities.lowerlayer;
 
import survive.Global;
 import survive.LowerLayer;
 import survive.Survive;
 import survive.Coords;
import survive.SpriteStore;
 
 public class WoodFloorEntity
   extends LowerLayer
 {
   private Survive survive;
   
   public WoodFloorEntity(Survive survive, Coords coords, int type)
   {
     super(coords, type);
     setSprite(SpriteStore.get().getSprite("sprites/lowerlayer/woodfloor.gif"));
     this.survive = survive;
   }

   public boolean passable()
   {
       return true;
   }

 }









