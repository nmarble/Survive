 package survive.Entities.middlelayer;
 
 import survive.Global;
 import survive.MiddleLayer;
 import survive.Survive;
 import survive.Coords;
import survive.SpriteStore;
 
 public class BarrelEntity
   extends MiddleLayer
 {
   private Survive survive;
   private int itemCode = 0;
   private int itemQ = 0;
   public BarrelEntity(Survive survive, final Coords coords, int type)
   {
     super(coords, type);
     setSprite(SpriteStore.get().getSprite("sprites/object/barrel.png"));
     
     int choice = 0;
     int choiceNum = 0;
     int[] randomChance = {0,20,0,0,0,10,8,15,0,20,0,0,0,0,0,15,0,0,10};
         
     for (int i = 0; i < randomChance.length; i++ )
     {
         int chance = (int)(Math.random() * randomChance[i]);
          
         if (chance > choice)
         {
             choice = chance;
             itemCode = i;
         }
     }
     if (itemCode == 5 || itemCode == 6) {
         itemQ = 1;
     }
     else {
     itemQ = (int)(Math.random() * 5)+1;
     }
   }
   public int getItemCode()
   {
       return itemCode;
   }
   public int getItemQ()
   {
       return itemQ;
   }
   public boolean interact()
   {
        return true;   
   }
   public boolean passable()
   {
       return false;
   }
   public boolean walkUnder()
   {
       return false;
   }
   public boolean seePassed()
   {
       return true;
   }
 }









