 package survive.Entities;
 
 import survive.Hud;
 import survive.Sprite;
 import survive.SpriteStore;
 import survive.Survive;
 
 public class ButtonEntity
   extends Hud
 {
   private Survive survive;
   private Sprite[] frames = new Sprite[4];
   private String direction = "North";
   
   public ButtonEntity(Survive survive, String ref, int x, int y, String type, int imageSize)
   {
     super(ref, x, y, type, imageSize);
         
     this.survive = survive;
   }
   
 
 }









