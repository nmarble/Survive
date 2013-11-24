package survive.Entities;

import survive.Coords;
import survive.MiddleLayer;
import survive.Survive;

public class LogWallEntity
        extends MiddleLayer
{

  private Survive survive;

  public LogWallEntity(Survive survive, String ref, final Coords coords, String type)
  {
    super(ref, coords, type);

    this.survive = survive;
  }

  public int getModifiedX()
  {
    int modifiedX = 0;
    return modifiedX;
  }

  public int getModifiedY()
  {
    int modifiedY = 0;
    return modifiedY;
  }

  public void interact()
  {
    survive.removeMiddleLayer(this);
    survive.addToInventory(3, 1);
  }

  public boolean passable()
  {
    return false;
  }

}
