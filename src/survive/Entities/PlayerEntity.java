 package survive.Entities;
 
 import survive.Hud;
 import survive.Sprite;
 import survive.SpriteStore;
 import survive.Survive;
 
 public class PlayerEntity
   extends Hud
 {
   private Survive survive;
   private Sprite[] frames = new Sprite[4];
   private String direction = "North";
   
   public PlayerEntity(Survive survive, String ref, int x, int y, String type, int imageSize)
   {
     super(ref, x, y, type, imageSize);
     
 
 
     this.frames[0] = SpriteStore.get().getSprite("sprites/PlayerN.png");
     this.frames[1] = SpriteStore.get().getSprite("sprites/PlayerS.png");
     this.frames[2] = SpriteStore.get().getSprite("sprites/PlayerW.png");
     this.frames[3] = SpriteStore.get().getSprite("sprites/PlayerE.png");
     
     this.survive = survive;
   }
   
   public void changeFrame(int frameNumber)
   {
     this.sprite = this.frames[frameNumber];
   }
 }









