 package survive.Entities;
 
import survive.Coords;
 import survive.LowerLayer;
import survive.Sprite;
import survive.SpriteStore;
 import survive.Survive;
 
 public class WaterEntity
   extends LowerLayer
 {
   private Survive survive;
   private Sprite[] frames = new Sprite[3];
   int fNumber = 0;
   public WaterEntity(Survive survive, String ref, final Coords coords, int type)
   {
     super(ref, coords, type);
     
     frames[0] = SpriteStore.get().getSprite("sprites/lowerlayer/water1.png");
     frames[1] = SpriteStore.get().getSprite("sprites/lowerlayer/water2.png");
     frames[2] = SpriteStore.get().getSprite("sprites/lowerlayer/water3.png");
     
     this.survive = survive;
   }
   public boolean passable()
   {
       return false;
   }
   public void changeFrame(int frameNumber) 
   {
      if (frameNumber < 3) {
       setSprite(frames[frameNumber]);
     }
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









