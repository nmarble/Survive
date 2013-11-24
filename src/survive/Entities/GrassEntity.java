package survive.Entities;

import survive.Coords;
import survive.LowerLayer;
import survive.Survive;

public class GrassEntity
  extends LowerLayer
{
  private Survive survive;
  
  public GrassEntity(Survive survive, String ref, final Coords coords, String type)
  {
    super(ref, coords, type);
    
    this.survive = survive;
  }
  public boolean passable()
   {
       return true;
   }
}









