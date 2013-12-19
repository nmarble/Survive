package survive.Entities;

import survive.Coords;
import survive.MiddleLayer;
import survive.Sprite;
import survive.SpriteStore;
import survive.Survive;

public class LogWallEntity
        extends MiddleLayer
{

  private Survive survive;
  private Sprite[] frames = new Sprite[6];
  
  public LogWallEntity(Survive survive, String ref, final Coords coords, int type)
  {
    super(ref, coords, type);

    frames[0] = SpriteStore.get().getSprite("sprites/woodwallintersect.png");
    frames[1] = SpriteStore.get().getSprite("sprites/woodwallcorner.png");
    frames[2] = SpriteStore.get().getSprite("sprites/woodwall.png");
    frames[3] = SpriteStore.get().getSprite("sprites/woodwallsplit.png");
    frames[4] = SpriteStore.get().getSprite("sprites/woodentryway1.png");
    frames[5] = SpriteStore.get().getSprite("sprites/woodwallintersect.png");

    this.survive = survive;
  }

  public boolean interact()
  {
    survive.addToInventory(3, 1);
    return true;
  }
  public void changeFrame(int frameNumber)
  {
      setSprite(frames[frameNumber]);
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
