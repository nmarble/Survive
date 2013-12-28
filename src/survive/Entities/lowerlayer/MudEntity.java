package survive.Entities.lowerlayer;
 
 import survive.LowerLayer;
 import survive.Survive;
 import survive.Coords;
import survive.SpriteStore;
 
 public class MudEntity
   extends LowerLayer
 {
   private Survive survive;
   
   public MudEntity(Survive survive, final Coords coords, int type)
   {
     super(coords, type);
     setSprite(SpriteStore.get().getSprite("sprites/lowerlayer/mud.gif"));
     this.survive = survive;
   }
   public boolean passable()
   {
       return true;
   }

 }









