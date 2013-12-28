package survive.Entities.middlelayer;

import survive.Coords;
import survive.MiddleLayer;
import survive.Sprite;
import survive.SpriteStore;
import survive.Survive;

public class DoorEntity
        extends MiddleLayer
{
  private Survive survive;
  private Sprite[] frames = new Sprite[21];
  private boolean open = false;
  public DoorEntity(Survive survive, final Coords coords, int type)
  {
    super(coords, type);
    setSprite(SpriteStore.get().getSprite("sprites/object/doorclose1.png"));

    frames[0] = SpriteStore.get().getSprite("sprites/object/doorclose1.png");
    frames[1] = SpriteStore.get().getSprite("sprites/object/doorclose1.png");
    frames[2] = SpriteStore.get().getSprite("sprites/object/doorclose1.png");
    frames[3] = SpriteStore.get().getSprite("sprites/object/doorclose1.png");
    frames[4] = SpriteStore.get().getSprite("sprites/object/doorclose1.png");
    frames[5] = SpriteStore.get().getSprite("sprites/object/doorclose1.png");
    frames[20] = SpriteStore.get().getSprite("sprites/object/dooropen1.png");

    this.survive = survive;
  }

  public boolean interact()
  {
    open = !open;
    return false;
  }
  public void changeFrame(int frameNumber)
  {
      if (open) {frameNumber = frameNumber + 20;}
      if (frameNumber > 5 && frameNumber < 20) {
          frameNumber = 3;
      }
      else if (frameNumber > 20) {
          frameNumber = 20;
      }
      setSprite(frames[frameNumber]);
  }

  public boolean passable()
  {
    return open;
  }
  public boolean walkUnder()
   {
       return false;
   }
  public boolean seePassed()
   {
       return open;
   }
}
