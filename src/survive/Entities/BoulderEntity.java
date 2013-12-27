package survive.Entities;

import survive.Coords;
import survive.MiddleLayer;
import survive.SpriteStore;
import survive.Survive;

public class BoulderEntity
        extends MiddleLayer
{

  private Survive survive;

  public BoulderEntity(Survive survive, final Coords coords, int type)
  {
    super(coords, type);
    setSprite(SpriteStore.get().getSprite("sprites/object/boulder.png"));

    this.survive = survive;
  }

  public boolean interact()
  {
    survive.addToInventory(2, 1);
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
