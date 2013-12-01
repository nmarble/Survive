package survive.Entities;

import survive.Coords;
import survive.MiddleLayer;
import survive.Survive;

public class LogEntity
        extends MiddleLayer
{

  private Survive survive;

  public LogEntity(Survive survive, String ref, final Coords coords, String type)
  {
    super(ref, coords, type);

    this.survive = survive;
  }

  public boolean interact()
  {
    survive.addToInventory(1, 1);
    return true;
  }

  public boolean passable()
  {
    return true;
  }

}
