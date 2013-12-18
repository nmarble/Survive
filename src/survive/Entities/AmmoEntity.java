package survive.Entities;

import survive.Coords;
import survive.MiddleLayer;
import survive.Survive;

public class AmmoEntity
        extends MiddleLayer
{

  private Survive survive;

  public AmmoEntity(Survive survive, String ref, final Coords coords, int type)
  {
    super(ref, coords, type);

    this.survive = survive;
  }

  public boolean interact()
  {
    survive.addToInventory(9, 1);
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
