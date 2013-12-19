 package survive.Entities;
 
import survive.Coords;
import survive.Direction;
 import survive.EnemyLayer;
 import survive.Sprite;
 import survive.SpriteStore;
 import survive.Survive;
 
 public class ZombieEntity
   extends EnemyLayer
 {
   private Survive survive;
   private Sprite[] frames = new Sprite[4];
   private int lastX = 0;
   private int lastY = 0;
   private int fNumber = 0;
   private int life = 50;
   private int STR = 20;
   private boolean passable = false;
  
   public ZombieEntity(Survive survive, String ref, final Coords coords, String type, final Direction direction)
   {
     super(ref, coords, type, direction);
     
     frames[0] = SpriteStore.get().getSprite("sprites/zombie1.png");
     frames[1] = SpriteStore.get().getSprite("sprites/zombie2.png");
     frames[2] = SpriteStore.get().getSprite("sprites/zombie1.png");


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
   public boolean interact()
   {
       return true;
   }
    public boolean passable()
   {
       return passable;
   }
    public void setPassable(boolean newPass)
   {
       passable = newPass;
   }
  public void changeDirection(final Direction d, boolean attack)
  {
      direction = d;
      fNumber++;
      if (fNumber > 1) {fNumber = 0;}
      if (!attack) {
          changeFrame(fNumber);
      }
      if (attack) {
          changeFrame(2);
      }
  }
   public long getSpeed()
   {
       return (10);
   }
   public int getLastX()
   {
       return lastX;
   }
   public int getLastY()
   {
       return lastY;
   }
   public void setLast(int lX, int lY)
   {
       lastX = lX;
       lastY = lY;
   }
 }









