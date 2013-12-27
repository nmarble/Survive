package survive;

import java.awt.Graphics;

public abstract class MiddleLayer extends Drawable implements java.io.Serializable
{

  protected int type;
  protected double movementSpeed;
  protected Sprite sprite;
  protected int modifiedX;
  protected int modifiedY;
  public MiddleLayer(final Coords coords, int type)
  {
    super(coords, SpriteStore.get().getSprite("sprites/null.png"));
    this.type = type;

  }

  public void changeFrame(int frameNumber)
  {
  }

  public int getType()
  {
    return type;
  }
  public int getItemCode()
   {
       return 0;
   }
   public int getItemQ()
   {
       return 0;
   }
  public abstract boolean interact();

  public abstract boolean passable();
  
  public abstract boolean walkUnder();
  
  public abstract boolean seePassed();

}
