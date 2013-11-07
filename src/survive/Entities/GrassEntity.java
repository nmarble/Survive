package survive.Entities;

import survive.LowerLayer;
import survive.Survive;

public class GrassEntity
  extends LowerLayer
{
  private Survive survive;
  
  public GrassEntity(Survive survive, String ref, int x, int y, String type)
  {
    super(ref, x, y, type);
    
    this.survive = survive;
  }
}









