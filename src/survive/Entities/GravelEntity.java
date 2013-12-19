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
  private Sprite[] frames = new Sprite[6];
  
  public GravelEntity(Survive survive, String ref, final Coords coords, int type)
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

  public boolean passable()
  {
    return true;
  }
}
