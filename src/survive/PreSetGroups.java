package survive;
 

import survive.Entities.WaterEntity;

 
public class PreSetGroups
{
  
   
   public PreSetGroups()
   {
     
   }
   public static int[][] pond(Direction side, int xLoc, int yLoc, int size)
   {
      
      int[][] locs = new int [500][2];
      int x = 0;
      int y = 0 - size;
      int c = 0;
      switch(side)
      {          
          case DOWN:
          for (int i = 0; i<=size*2; i++)
          { 
              x = (int)Math.sqrt((size*size) - y*y);
              int dist = Math.abs(-x)+ x;
              
              locs[c][0] = (x*20) + xLoc; 
              locs[c][1] = ((y*20) + yLoc) + (size*20);
              c++;
              for (int r = 1; r <= dist; r++)
              {
                  
                  locs[c][0] = ((x-r)*20)+xLoc;
                  locs[c][1] = ((y*20) + yLoc) + (size*20);
                  c++;
                  locs[c][0] = ((-x+r)*20)+xLoc;
                  locs[c][1] = ((y*20) + yLoc) + (size*20);
                  c++;
              }
              locs[c][0] = (-x*20) + xLoc; 
              locs[c][1] = ((y*20) + yLoc) + (size*20);
              c++;
              y++;
          }
          break;
              
          case UP:
          for (int i = 0; i<=size*2; i++)
          {  
              x = (int)Math.sqrt((size*size) - y*y);
              int dist = Math.abs(-x)+ x;
              locs[c][0] = (x*20) + xLoc; 
              locs[c][1] = ((y*20) + yLoc) - (size*20);
              c++;
              for (int r = 1; r <= dist; r++)
              {
                  locs[c][0] = ((x-r)*20)+xLoc;
                  locs[c][1] = ((y*20) + yLoc) - (size*20);
                  c++;
                  locs[c][0] = ((-x+r)*20)+xLoc;
                  locs[c][1] = ((y*20) + yLoc) - (size*20);
                  c++;
              }
              locs[c][0] = (-x*20) + xLoc; 
              locs[c][1] = ((y*20) + yLoc) - (size*20);
              c++;
              y++;
          }
          break;
              
          case RIGHT:
          for (int i = 0; i<=size*2; i++)
          {
              x = (int)Math.sqrt((size*size) - y*y);
              int dist = Math.abs(-x)+ x;
              locs[c][0] = (x*20) + xLoc + (size*20); 
              locs[c][1] = ((y*20) + yLoc);
              c++;
              for (int r = 1; r <= dist; r++)
              {
                  locs[c][0] = ((x-r)*20)+xLoc + (size*20);
                  locs[c][1] = ((y*20) + yLoc) ;
                  c++;
                  locs[c][0] = ((-x+r)*20)+xLoc + (size*20);
                  locs[c][1] = (y*20) + yLoc;
                  c++;
              }
              locs[c][0] = (-x*20) + xLoc + (size*20); 
              locs[c][1] = ((y*20) + yLoc);
              c++;
              y++;
          }
          break;
              
          case LEFT:
          for (int i = 0; i<=size*2; i++)
          {
              x = (int)Math.sqrt((size*size) - y*y);
              int dist = Math.abs(-x)+ x;
              locs[c][0] = (x*20) + xLoc - (size*20); 
              locs[c][1] = ((y*20) + yLoc);
              c++;
              for (int r = 1; r <= dist; r++)
              {
                  locs[c][0] = ((x-r)*20)+xLoc - (size*20);
                  locs[c][1] = (y*20) + yLoc;
                  c++;    
                  locs[c][0] = ((-x+r)*20)+xLoc - (size*20);
                  locs[c][1] = (y*20) + yLoc;
                  c++;
              }
              locs[c][0] = (-x*20) + xLoc - (size*20); 
              locs[c][1] = ((y*20) + yLoc);
              c++;
              y++;
          }
          break;
      }
      return locs;
   }
   public static int[][] houseFloor(Direction side,int xLoc, int yLoc, int size)
   {
       int yOffSet = 0;
       int xOffSet = 0;      
       switch (side) {
           case UP:
               yOffSet = -(size * 20);
               break;
           case LEFT:
               xOffSet = -(size * 20);
               break;
       }
       int[][] locs = new int [400][2];
       int i = 0;
       for (int x = 0; x <= (size * 20); x = x + 20)
       {
           for (int y = 0; y <= (size * 20); y = y + 20)
           {
               locs[i][0] = x + xLoc + xOffSet;
               locs[i][1] = y + yLoc + yOffSet;
               i++;
           }
       }
       return locs;
   }
   public static int[][] houseWalls(Direction side,int xLoc, int yLoc, int size)
   {
       int yOffSet = 0;
       int xOffSet = 0;      
       switch (side) {
           case UP:
               yOffSet = -(size * 20);
               break;
           case LEFT:
               xOffSet = -(size * 20);
               break;  
       }
       int[][] locs = new int [400][2];
       int i = 0;
       int x;
       for (x = 0; x <= (size * 20); x = x + 20)
       {
               for (int y = 0; y <= (size * 20); y = y + (size*20))
               {
               locs[i][0] = x + xLoc + xOffSet;
               locs[i][1] = y + yLoc + yOffSet;
               i++;
               }
 
       }
       x = size*20;
       for (int y = 20; y <= (size * 20)-20; y = y + 20)
       {
            locs[i][0] = x + xLoc + xOffSet;
            locs[i][1] = y + yLoc + yOffSet;
            i++;   
       }
       x = 0;
       for (int y = 20; y <= (size * 20) / 2 - 20; y = y + 20)
       {
            locs[i][0] = x + xLoc + xOffSet;
            locs[i][1] = y + yLoc + yOffSet;
            i++;  
       }
       for (int y = size * 20; y >= (size * 20) / 2 + 20; y = y - 20)
       {
            locs[i][0] = x + xLoc + xOffSet;
            locs[i][1] = y + yLoc + yOffSet;
            i++;  
       }
       return locs;
   }
    public static int[][] houseItems(Direction side, int amount,int xLoc, int yLoc, int size)
    {
       int yOffSet = 0;
       int xOffSet = 0;      
       switch (side) {
           case UP:
               yOffSet = -(size * 20);
               break;
           case LEFT:
               xOffSet = -(size * 20);
               break; 
       }
       int[][] locs = new int [amount][2];
       int x;
       int y;
       for (int i = 0; i < amount; i++)
       {
           x = (int)(Math.random() * size) * 20;
           y = (int)(Math.random() * size) * 20;
           while (x == 0 || y == 0 || x == size *20 || y == size *20)
           {
               x = (int)(Math.random() * size) * 20;
               y = (int)(Math.random() * size) * 20;
           }
           locs[i][0] = x + xLoc + xOffSet;
           locs[i][1] = y + yLoc + yOffSet;              
       }
       return locs;
    }
    public static int[][] tree(Direction side, int xLoc, int yLoc)
    {
       int yOffSet = 0;
       int xOffSet = 0;      
       switch (side) {
           case UP:
               yOffSet = -(80);
               break;
           case LEFT:
               xOffSet = -(80);
               break; 
       }
        int[][] locs = new int [13][2];
        int i = 0;
        for (int x = -20; x <= 20; x = x + 20) {
            for (int y = -20; y <= 20; y = y + 20) {
                locs[i][0] = x + xLoc + xOffSet;
                locs[i][1] = y + yLoc + yOffSet;
                i++;
            }
        }
        locs[9][0] = xLoc + xOffSet;
        locs[9][1] = -40 + yLoc + yOffSet;
        locs[10][0] = xLoc + xOffSet;
        locs[10][1] = 40 + yLoc + yOffSet;
        locs[11][0] = -40 + xLoc + xOffSet;
        locs[11][1] = yLoc + yOffSet;
        locs[12][0] = 40 + xLoc + xOffSet;
        locs[12][1] = yLoc + yOffSet;
        return locs;
    }
}









