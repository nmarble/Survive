package survive.Entities;

import survive.Coords;
import survive.MiddleLayer;
import survive.SpriteStore;
import survive.Survive;

public class LogEntity
        extends MiddleLayer
{

  private Survive survive;

  public LogEntity(Survive survive, final Coords coords, int type)
  {
    super(coords, type);
    setSprite(SpriteStore.get().getSprite("sprites/object/tree/log.png"));

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
  public boolean walkUnder()
   {
       return false;
   }
  public boolean seePassed()
   {
       return true;
   }
}
