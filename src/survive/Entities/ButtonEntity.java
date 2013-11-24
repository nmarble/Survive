package survive.Entities;

import survive.Coords;
import survive.Hud;
import survive.Sprite;
import survive.Survive;

public class ButtonEntity
        extends Hud
{

  private Survive survive;
  private Sprite[] frames = new Sprite[4];
  private String direction = "North";

  public ButtonEntity(Survive survive, String ref, final Coords coords, String type, int imageSize)
  {
    super(ref, coords, type, imageSize);

    this.survive = survive;
  }

}
