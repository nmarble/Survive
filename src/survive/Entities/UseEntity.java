package survive.Entities;

import survive.Coords;
import survive.Hud;
import survive.Survive;

public class UseEntity
  extends Hud
{
  private Survive survive;
  
  public UseEntity(Survive survive, String ref, final Coords coords, String type, int size)
  {
    super(ref, coords, type, size);
    
    this.survive = survive;
  }
  
}









