package survive.Entities;

import survive.Coords;
import survive.MiddleLayer;
import survive.Survive;

public class DeadBodyEntity
        extends MiddleLayer
{

  private Survive survive;

  public DeadBodyEntity(Survive survive, String ref, final Coords coords, int type)
  {
    super(ref, coords, type);

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
