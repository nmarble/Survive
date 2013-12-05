package survive;

import java.awt.Graphics;

public abstract class UpperLayer extends Drawable
{

  protected String type;
  protected double movementSpeed;
  protected Sprite sprite;

  public UpperLayer(String ref, final Coords coords, String type)
  {
    super(coords, SpriteStore.get().getSprite(ref));
    this.type = type;

  }

  public void changeFrame(int frameNumber)
  {
  }

  public String getType()
  {
    return type;
  }

}
