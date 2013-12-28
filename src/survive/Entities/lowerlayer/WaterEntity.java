package survive.Entities.lowerlayer;
 
import survive.Coords;
 import survive.LowerLayer;
import survive.Sprite;
import survive.SpriteStore;
 import survive.Survive;
 
 public class WaterEntity
   extends LowerLayer
 {
   private Survive survive;
   private Sprite[] frames = new Sprite[11];
   int fNumber = 0;
   public WaterEntity(Survive survive, final Coords coords, int type)
   {
     super(coords, type);
     setSprite(SpriteStore.get().getSprite("sprites/lowerlayer/waterborder.png"));
     frames[0] = SpriteStore.get().getSprite("sprites/lowerlayer/waterborder.png");
     frames[1] = SpriteStore.get().getSprite("sprites/lowerlayer/waterborder3.png");
     frames[2] = SpriteStore.get().getSprite("sprites/lowerlayer/waterborder6.png");
     frames[3] = SpriteStore.get().getSprite("sprites/lowerlayer/waterborder2.png");
     frames[4] = SpriteStore.get().getSprite("sprites/lowerlayer/waterborder4.png");
     frames[5] = SpriteStore.get().getSprite("sprites/lowerlayer/waterborder5.png");
     frames[6] = SpriteStore.get().getSprite("sprites/lowerlayer/waterborder7.png");
     frames[7] = SpriteStore.get().getSprite("sprites/lowerlayer/waterborder8.png");
     frames[8] = SpriteStore.get().getSprite("sprites/lowerlayer/waterborder9.png");
     frames[9] = SpriteStore.get().getSprite("sprites/lowerlayer/waterborder10.png");
     frames[10] = SpriteStore.get().getSprite("sprites/lowerlayer/waterborder11.png");
     this.survive = survive;
   }
   public boolean passable()
   {
       return false;
   }
   public void changeFrame(int frameNumber) 
   {
       setSprite(frames[frameNumber]);
   }
   public int nextFrame()
   {
       fNumber = (int) (Math.random() * frames.length);
       if (fNumber >= frames.length) {
           fNumber = 0;
       }
       return fNumber;
   }
 }









