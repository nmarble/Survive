package survive.Entities;

import survive.Coords;
import survive.Hud;
import survive.Sprite;
import survive.Survive;

public class HealthEntity
        extends Hud
{

  private Survive survive;
  private Sprite[] frames = new Sprite[4];

  public HealthEntity(Survive survive, String ref, final Coords coords, String type, int imageSize)
  {
    super(ref, coords, type, imageSize);

    this.survive = survive;
  }

}
