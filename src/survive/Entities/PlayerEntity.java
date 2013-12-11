 package survive.Entities;
 
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
   private Sprite[] frames = new Sprite[8];
   private int life = 100;
   private int STR = 25;
   
   public PlayerEntity(Survive survive, String ref, final Coords coords, String type)
   {
     super(coords, SpriteStore.get().getSprite(ref));

     frames[0] = SpriteStore.get().getSprite("sprites/PlayerN.png");
     frames[1] = SpriteStore.get().getSprite("sprites/PlayerS.png");
     frames[2] = SpriteStore.get().getSprite("sprites/PlayerW.png");
     frames[3] = SpriteStore.get().getSprite("sprites/PlayerE.png");
     frames[4] = SpriteStore.get().getSprite("sprites/PlayerN2.png");
     frames[5] = SpriteStore.get().getSprite("sprites/PlayerS2.png");
     frames[6] = SpriteStore.get().getSprite("sprites/PlayerW2.png");
     frames[7] = SpriteStore.get().getSprite("sprites/PlayerE2.png");
     
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
   public void changeFrame(int frameNumber)
   {
     setSprite(frames[frameNumber]);
   }
   public void changeDirection(Direction d)
   {
      switch (d)
      {
        case LEFT:
            if (getSprite() == frames[6])
            {
                changeFrame(2);    
            }
            else 
            {        
                changeFrame(6);
            }
              break;
        case RIGHT:
            if (getSprite() == frames[7])
            {
            changeFrame(3);    
            }
            else 
            {        
            changeFrame(7);
            }
            break;
        case UP:
            if (getSprite() == frames[0])
            {
            changeFrame(4);    
            }
            else 
            {        
            changeFrame(0);
            }
            break;
        case DOWN:
            if (getSprite() == frames[5])
            {
            changeFrame(1);    
            }
            else 
            {        
            changeFrame(5);
            }
            break;
          
      } 
   }
 }









