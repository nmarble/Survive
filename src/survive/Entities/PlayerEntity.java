 package survive.Entities;
 
import survive.Coords;
 import survive.Hud;
 import survive.Sprite;
 import survive.SpriteStore;
 import survive.Survive;
 
 public class PlayerEntity
   extends Hud
 {
   private Survive survive;
   private Sprite[] frames = new Sprite[4];
  
   
   public PlayerEntity(Survive survive, String ref, final Coords coords, String type, int imageSize)
   {
     super(ref, coords, type, imageSize);

     frames[0] = SpriteStore.get().getSprite("sprites/PlayerN.png");
     frames[1] = SpriteStore.get().getSprite("sprites/PlayerS.png");
     frames[2] = SpriteStore.get().getSprite("sprites/PlayerW.png");
     frames[3] = SpriteStore.get().getSprite("sprites/PlayerE.png");
     
     this.survive = survive;
   }
   
   public void changeFrame(int frameNumber)
   {
     setSprite(frames[frameNumber]);
   }
 }









