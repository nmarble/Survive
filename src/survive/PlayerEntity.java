 package survive;
 
import survive.Coords;
 import survive.Hud;
 import survive.Sprite;
 import survive.SpriteStore;
 import survive.Survive;
 import survive.Direction;
import survive.Drawable;
 
 public class PlayerEntity
 extends Drawable
 {
   private Survive survive;
   private Sprite[] frames = new Sprite[2];
   private int life = 100;
   private int STR = 10;
   
   public PlayerEntity(Survive survive, String ref, final Coords coords, String type)
   {
     super(coords, SpriteStore.get().getSprite(ref));

     frames[0] = SpriteStore.get().getSprite("sprites/player/playern.png");
     frames[1] = SpriteStore.get().getSprite("sprites/player/playern2.png");

     
     this.survive = survive;
   }
   public void setLife(int newLife)
   {
       life = newLife;
   }
   public int getLife()
   {
       return life;
   }
   public int getSTR()
   {
       return STR;
   }
   public void setSTR(int newSTR)
   {
       STR = newSTR;
   }
   public void changeFrame(int frameNumber)
   {
     if (frameNumber < 2) {
       setSprite(frames[frameNumber]);
     }
   }
   
 }









