 package survive.Entities.middlelayer;

import survive.Coords;
import survive.MiddleLayer;
import survive.SpriteStore;
import survive.Survive;

public class AmmoEntity
        extends MiddleLayer
{

  private Survive survive;

  public AmmoEntity(Survive survive, final Coords coords, int type)
  {
    super(coords, type);
    setSprite(SpriteStore.get().getSprite("sprites/object/ammobox.png"));

    this.survive = survive;
  }

  public boolean interact()
  {
    survive.addToInventory(9, 1);
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
