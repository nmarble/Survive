package survive.Entities.middlelayer;

import survive.Coords;
import survive.MiddleLayer;
import survive.Sprite;
import survive.SpriteStore;
import survive.Survive;

public class LeavesEntity extends MiddleLayer
{

  private Survive survive;
  private Sprite[] frames = new Sprite[6];
  
  public LeavesEntity(Survive survive, final Coords coords, int type)
  {
    super(coords, type);
    setSprite(SpriteStore.get().getSprite("sprites/object/tree/leaves1_1.png"));
    
    frames[0] = SpriteStore.get().getSprite("sprites/object/tree/leaves1_1.png");
    frames[1] = SpriteStore.get().getSprite("sprites/object/tree/leaves1_1.png");
    frames[2] = SpriteStore.get().getSprite("sprites/object/tree/leaves1_1.png");
    frames[3] = SpriteStore.get().getSprite("sprites/object/tree/leaves1_1.png");
    frames[4] = SpriteStore.get().getSprite("sprites/object/tree/leaves1_u.png");
    frames[5] = SpriteStore.get().getSprite("sprites/object/tree/leaves1_1.png");
    
    this.survive = survive;
  }

  public boolean interact()
  {
    return true;
  }
  public void changeFrame(int frameNumber)
  {
      if (frameNumber > 5) {
          frameNumber = 4;
      }
      setSprite(frames[frameNumber]);
  }
  public boolean passable()
  {
    return true;
  }
  public boolean walkUnder()
   {
       return true;
   }
  public boolean seePassed()
   {
       return true;
   }
}
