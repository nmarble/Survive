package survive;

import java.awt.Graphics;

public abstract class MiddleLayer extends Drawable implements java.io.Serializable
{

  protected int type;
  protected double movementSpeed;
  protected Sprite sprite;
  protected int modifiedX;
  protected int modifiedY;

  public MiddleLayer(String ref, final Coords coords, int type)
  {
    super(coords, SpriteStore.get().getSprite(ref));
    this.type = type;

  }

  public void changeFrame(int frameNumber)
  {
  }

  public int getType()
  {
    return type;
  }

  public abstract boolean interact();

  public abstract boolean passable();
  
  public abstract boolean walkUnder();
  
  public abstract boolean seePassed();

}
