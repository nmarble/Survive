package survive.Entities;

import survive.Coords;
import survive.MiddleLayer;
import survive.Survive;

public class StoneEntity
        extends MiddleLayer
{

  private Survive survive;

  public StoneEntity(Survive survive, String ref, final Coords coords, String type)
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
    survive.addToInventory(2, 1);
  }

  public boolean passable()
  {
    return true;
  }

}
