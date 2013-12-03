package survive.Entities;

import survive.Coords;
import survive.MiddleLayer;
import survive.Survive;

public class LeavesEntity extends MiddleLayer
{

  private Survive survive;

  public LeavesEntity(Survive survive, String ref, final Coords coords, String type)
  {
    super(ref, coords, type);

    this.survive = survive;
  }

  public boolean interact()
  {
    survive.addToInventory(1, 2);
    return true;
  }

  public boolean passable()
  {
    return true;
  }
  public boolean walkUnder()
   {
       return true;
   }
}
