package survive.Entities;

import survive.Coords;
import survive.MiddleLayer;
import survive.SpriteStore;
import survive.Survive;

public class WaterBorderEntity extends MiddleLayer
{

  private Survive survive;

  public WaterBorderEntity(Survive survive, final Coords coords, int type)
  {
    super(coords, type);
    setSprite(SpriteStore.get().getSprite("sprites/object/axe.png"));

    this.survive = survive;
  }

  public boolean interact()
  {
    return false;
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
