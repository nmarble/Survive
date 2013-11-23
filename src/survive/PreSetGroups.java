package survive;
 

import survive.Entities.WaterEntity;

 
public class PreSetGroups
{
  
   
   public PreSetGroups()
   {
     
   }
   public static int[][] pond(String side, int xLoc, int yLoc, int size)
   {
      
      int[][] locs = new int [500][2];
      int x = 0;
      int y = 0 - size;
      int c = 0;
      switch(side)
      {          
          case "down":
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
              
          case "up":
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
              
          case "right":
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
              
          case "left":
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
}









