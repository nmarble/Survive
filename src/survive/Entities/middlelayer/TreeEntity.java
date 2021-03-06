package survive.Entities.middlelayer;

import survive.Coords;
import survive.MiddleLayer;
import survive.SpriteStore;
import survive.Survive;

public class TreeEntity extends MiddleLayer
{

  private Survive survive;

  public TreeEntity(Survive survive, final Coords coords, int type)
  {
    super(coords, type);
    setSprite(SpriteStore.get().getSprite("sprites/object/tree/trunk.png"));

    this.survive = survive;
  }

  public boolean interact()
  {
    survive.addToInventory(1, 2);
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
       return false;
   }
}
