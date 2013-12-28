package survive.Entities;

import survive.Coords;
import survive.MiddleLayer;
import survive.SpriteStore;
import survive.Survive;

public class WindowEntity
        extends MiddleLayer
{

  private Survive survive;

  public WindowEntity(Survive survive, final Coords coords, int type)
  {
    super(coords, type);
    setSprite(SpriteStore.get().getSprite("sprites/object/window.png"));

    this.survive = survive;
  }

  public boolean interact()
  {
    survive.addToInventory(7, 1);
    return true;
  }

  public boolean passable()
  {
    return false;
  }
  public boolean walkUnder()
   {
       return false;
   }
  public boolean seePassed()
   {
       return true;
   }
}
