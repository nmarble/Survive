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
  private Sprite[] frames = new Sprite[6];
  
  public GrassEntity(Survive survive, String ref, final Coords coords, int type)
  {
    super(ref, coords, type);
    
    frames[0] = SpriteStore.get().getSprite("sprites/grass.png");
    frames[1] = SpriteStore.get().getSprite("sprites/grass3.png");
    frames[2] = SpriteStore.get().getSprite("sprites/grass6.png");
    frames[3] = SpriteStore.get().getSprite("sprites/grass2.png");
    frames[4] = SpriteStore.get().getSprite("sprites/grass4.png");
    frames[5] = SpriteStore.get().getSprite("sprites/grass5.png");
    
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









