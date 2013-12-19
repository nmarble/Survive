 package survive.Entities;
 
 import survive.LowerLayer;
 import survive.Survive;
 import survive.Coords;
 
 public class MudEntity
   extends LowerLayer
 {
   private Survive survive;
   
   public MudEntity(Survive survive, String ref, final Coords coords, int type)
   {
     super(ref, coords, type);
     
     this.survive = survive;
   }
   public boolean passable()
   {
       return true;
   }

 }









