 package survive.Entities;
 
 import survive.LowerLayer;
 import survive.Survive;
 
 public class GravelEntity
   extends LowerLayer
 {
   private Survive survive;
   
   public GravelEntity(Survive survive, String ref, int x, int y, String type)
   {
     super(ref, x, y, type);
     
     this.survive = survive;
   }
   public boolean passable()
   {
       return true;
   }
 }









