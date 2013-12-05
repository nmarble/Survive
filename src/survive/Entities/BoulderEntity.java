package survive.Entities;

import survive.Coords;
import survive.MiddleLayer;
import survive.Survive;

public class BoulderEntity
        extends MiddleLayer
{

  private Survive survive;

  public BoulderEntity(Survive survive, String ref, final Coords coords, String type)
  {
    super(ref, coords, type);

    this.survive = survive;
  }

  public boolean interact()
  {
    survive.addToInventory(2, 1);
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
