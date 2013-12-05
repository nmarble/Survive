package survive.Entities;

import survive.Coords;
import survive.MiddleLayer;
import survive.Survive;

public class TreeEntity extends MiddleLayer
{

  private Survive survive;

  public TreeEntity(Survive survive, String ref, final Coords coords, String type)
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
    return false;
  }
  public boolean walkUnder()
   {
       return false;
   }
  public boolean seePassed()
   {
       return false;
   }
}
