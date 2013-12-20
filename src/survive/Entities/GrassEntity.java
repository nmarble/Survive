package survive.Entities;

import survive.Coords;
import survive.LowerLayer;
import survive.Sprite;
import survive.SpriteStore;
import survive.Survive;

public class GrassEntity
  extends LowerLayer
{
  private Survive survive;
  private Sprite[] frames = new Sprite[11];
  
  public GrassEntity(Survive survive, String ref, final Coords coords, int type)
  {
    super(ref, coords, type);
    
    frames[0] = SpriteStore.get().getSprite("sprites/lowerlayer/grass.png");
    frames[1] = SpriteStore.get().getSprite("sprites/lowerlayer/grass3.png");
    frames[2] = SpriteStore.get().getSprite("sprites/lowerlayer/grass6.png");
    frames[3] = SpriteStore.get().getSprite("sprites/lowerlayer/grass2.png");
    frames[4] = SpriteStore.get().getSprite("sprites/lowerlayer/grass4.png");
    frames[5] = SpriteStore.get().getSprite("sprites/lowerlayer/grass5.png");
    frames[6] = SpriteStore.get().getSprite("sprites/lowerlayer/grass7.png");
    frames[7] = SpriteStore.get().getSprite("sprites/lowerlayer/grass8.png");
    frames[8] = SpriteStore.get().getSprite("sprites/lowerlayer/grass9.png");
    frames[9] = SpriteStore.get().getSprite("sprites/lowerlayer/grass10.png");
    frames[10] = SpriteStore.get().getSprite("sprites/lowerlayer/grass11.png");
    this.survive = survive;
  }
  public void changeFrame(int frameNumber)
  {
      setSprite(frames[frameNumber]);
  }
  public boolean passable()
   {
       return true;
   }
}









