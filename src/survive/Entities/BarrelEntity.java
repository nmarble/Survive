 package survive.Entities;
 
 import survive.Global;
 import survive.MiddleLayer;
 import survive.Survive;
 import survive.Coords;
 
 public class BarrelEntity
   extends MiddleLayer
 {
   private Survive survive;
   private int itemCode;
   private int itemQ;
   private int[] randomChance = new int[Global.totalItemRandom];
   public BarrelEntity(Survive survive, String ref, final Coords coords, int type)
   {
     super(ref, coords, type);
     this.survive = survive;
     
     int choice = 0;
     int choiceNum = 0;
     randomChance[1] = 20;
     randomChance[2] = 10;
     randomChance[3] = 5;
     
     for (int i = 1; i < Global.totalItemRandom; i++ )
     {
         int chance = (int)(Math.random() * randomChance[i]);
          
         if (chance > choice)
         {
             choice = chance;
             itemCode = i;
         }
     }
     itemQ = (int)(Math.random() * 5)+1;
   }

   public boolean interact()
   {
     if (itemQ > 0) {
        survive.addToInventory(itemCode, itemQ);
        itemQ = 0;
        return false;
     }
     survive.addToInventory(4, 1);
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









