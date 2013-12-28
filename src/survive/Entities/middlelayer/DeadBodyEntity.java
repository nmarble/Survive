package survive.Entities;

import survive.Coords;
import survive.MiddleLayer;
import survive.SpriteStore;
import survive.Survive;

public class DeadBodyEntity
        extends MiddleLayer
{

  private Survive survive;

  public DeadBodyEntity(Survive survive, final Coords coords, int type)
  {
    super(coords, type);
    setSprite(SpriteStore.get().getSprite("sprites/object/deadzomb.png"));

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
