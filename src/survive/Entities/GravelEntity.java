package survive.Entities;

import survive.Coords;
import survive.LowerLayer;
import survive.Survive;

public class GravelEntity
        extends LowerLayer
{

  private Survive survive;

  public GravelEntity(Survive survive, String ref, final Coords coords, String type)
  {
    super(ref, coords, type);

    this.survive = survive;
  }

  public boolean passable()
  {
    return true;
  }
}
