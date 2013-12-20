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

    frames[0] = SpriteStore.get().getSprite("sprites/object/woodwallintersect.png");
    frames[1] = SpriteStore.get().getSprite("sprites/object/woodwallcorner.png");
    frames[2] = SpriteStore.get().getSprite("sprites/object/woodwall.png");
    frames[3] = SpriteStore.get().getSprite("sprites/object/woodwallsplit.png");
    frames[4] = SpriteStore.get().getSprite("sprites/object/woodentryway1.png");
    frames[5] = SpriteStore.get().getSprite("sprites/object/woodwallintersect.png");

    this.survive = survive;
  }

  public boolean interact()
  {
    survive.addToInventory(3, 1);
    return true;
  }
  public void changeFrame(int frameNumber)
  {
      if (frameNumber > 5) {
          frameNumber = 3;
      }
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
