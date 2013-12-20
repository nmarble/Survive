package survive.Entities;

import survive.Coords;
import survive.LowerLayer;
import survive.Sprite;
import survive.SpriteStore;
import survive.Survive;

public class GravelEntity
        extends LowerLayer
{

  private Survive survive;
  private Sprite[] frames = new Sprite[10];
  
  public GravelEntity(Survive survive, String ref, final Coords coords, int type)
  {
    super(ref, coords, type);
    
    frames[0] = SpriteStore.get().getSprite("sprites/lowerlayer/gravel.png");
    frames[1] = SpriteStore.get().getSprite("sprites/lowerlayer/gravel.png");
    frames[2] = SpriteStore.get().getSprite("sprites/lowerlayer/gravel.png");
    frames[3] = SpriteStore.get().getSprite("sprites/lowerlayer/gravel.png");
    frames[4] = SpriteStore.get().getSprite("sprites/lowerlayer/gravel.png");
    frames[5] = SpriteStore.get().getSprite("sprites/lowerlayer/gravel.png");
    frames[6] = SpriteStore.get().getSprite("sprites/lowerlayer/gravel.png");
    frames[7] = SpriteStore.get().getSprite("sprites/lowerlayer/gravel.png");
    frames[8] = SpriteStore.get().getSprite("sprites/lowerlayer/gravel.png");
    frames[9] = SpriteStore.get().getSprite("sprites/lowerlayer/gravel.png");
    
    this.survive = survive;
  }

  public boolean passable()
  {
    return true;
  }
}
