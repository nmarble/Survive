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

  public int getModifiedX()
  {
    int modifiedX = 0;
    return modifiedX;
  }

  public int getModifiedY()
  {
    int modifiedY = 40;
    return modifiedY;
  }

  public void interact()
  {
    survive.removeMiddleLayer(this);
    survive.addToInventory(1, 1);
  }

  public boolean passable()
  {
    return false;
  }

}
