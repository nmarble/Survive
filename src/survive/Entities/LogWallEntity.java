package survive.Entities;

import survive.Coords;
import survive.MiddleLayer;
import survive.Survive;

public class LogWallEntity
        extends MiddleLayer
{

  private Survive survive;

  public LogWallEntity(Survive survive, String ref, final Coords coords, int type)
  {
    super(ref, coords, type);

    this.survive = survive;
  }

  public boolean interact()
  {
    survive.addToInventory(3, 1);
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
