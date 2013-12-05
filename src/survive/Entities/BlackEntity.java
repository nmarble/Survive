package survive.Entities;

import survive.Coords;
import survive.UpperLayer;
import survive.Survive;

public class BlackEntity
  extends UpperLayer
{
  private Survive survive;
  
  public BlackEntity(Survive survive, String ref, final Coords coords, String type)
  {
    super(ref, coords, type);
    
    this.survive = survive;
  }
}









