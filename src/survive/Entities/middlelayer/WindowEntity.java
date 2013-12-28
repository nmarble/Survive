package survive.Entities.middlelayer;

import survive.Coords;
import survive.MiddleLayer;
import survive.Sprite;
import survive.SpriteStore;
import survive.Survive;

public class WindowEntity
        extends MiddleLayer
{

  private Survive survive;
  private Sprite[] frames = new Sprite[6];
  public WindowEntity(Survive survive, final Coords coords, int type)
  {
    super(coords, type);
    setSprite(SpriteStore.get().getSprite("sprites/object/window.png"));
    
    frames[0] = SpriteStore.get().getSprite("sprites/object/window.png");
    frames[1] = SpriteStore.get().getSprite("sprites/object/window.png");
    frames[2] = SpriteStore.get().getSprite("sprites/object/windowplace.png");
    frames[3] = SpriteStore.get().getSprite("sprites/object/window.png");
    frames[4] = SpriteStore.get().getSprite("sprites/object/window.png");
    frames[5] = SpriteStore.get().getSprite("sprites/object/window.png");
    this.survive = survive;
  }

  public boolean interact()
  {
    survive.addToInventory(7, 1);
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
       return true;
   }
}
