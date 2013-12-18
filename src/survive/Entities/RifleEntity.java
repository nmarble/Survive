package survive.Entities;

import survive.Coords;
import survive.MiddleLayer;
import survive.Survive;

public class RifleEntity
        extends MiddleLayer
{

  private Survive survive;

  public RifleEntity(Survive survive, String ref, final Coords coords, int type)
  {
    super(ref, coords, type);

    this.survive = survive;
  }

  public boolean interact()
  {
    survive.addToInventory(6, 1);
    return true;
  }

  public boolean passable()
  {
    return true;
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
