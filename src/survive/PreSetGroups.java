package survive;
 

import survive.Entities.WaterEntity;

 
public class PreSetGroups
{
  
   
   public PreSetGroups()
   {
     
   }
   public static int[][] pond(String side, int xLoc, int yLoc, int size)
   {
      
      int[][] locs = new int [size*4][2];
      int x = 0;
      int y = 0 - size;
      switch(side)
      {          
          case "down":
          for (int i = 0; i<size*2; i++)
          { 
              x = (int)Math.sqrt((size*size) - y*y);
              
              locs[i][0] = (x*20) + xLoc; 
              locs[i][1] = ((y*20) + yLoc) + (size*20);
              locs[i+size*2][0] = (-x*20) + xLoc; 
              locs[i+size*2][1] = ((y*20) + yLoc) + (size*20);
              System.err.println(locs[i][0]);
              y++;
          }
          break;
              
          case "up":
          for (int i = 0; i<size*2; i++)
          {  
              x = (int)Math.sqrt((size*size) - y*y);
              
              locs[i][0] = (x*20) + xLoc; 
              locs[i][1] = ((y*20) + yLoc) - (size*20);
              locs[i+size*2][0] = (-x*20) + xLoc; 
              locs[i+size*2][1] = ((y*20) + yLoc) - (size*20);
              System.err.println(locs[i][0]);
              y++;
          }
          break;
              
          case "right":
          for (int i = 0; i<size*2; i++)
          {
              x = (int)Math.sqrt((size*size) - y*y);
              
              locs[i][0] = (x*20) + xLoc + (size*20); 
              locs[i][1] = ((y*20) + yLoc);
              locs[i+size*2][0] = (-x*20) + xLoc + (size*20); 
              locs[i+size*2][1] = ((y*20) + yLoc);
              System.err.println(locs[i][0]);
              y++;
          }
          break;
              
          case "left":
          for (int i = 0; i<size*2; i++)
          {
              x = (int)Math.sqrt((size*size) - y*y);
              
              locs[i][0] = (x*20) + xLoc - (size*20); 
              locs[i][1] = ((y*20) + yLoc);
              locs[i+size*2][0] = (-x*20) + xLoc - (size*20); 
              locs[i+size*2][1] = ((y*20) + yLoc);
              System.err.println(locs[i][0]);
              y++;
          }
          break;
      }
     
      
      return locs;
   }
}









