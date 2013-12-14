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
   private Sprite[] frames = new Sprite[2];
   private int lastX = 0;
   private int lastY = 0;
   
   private int life = 50;
   private int STR = 20;
   public ZombieEntity(Survive survive, String ref, final Coords coords, String type, final Direction direction)
   {
     super(ref, coords, type, direction);
     
     frames[0] = SpriteStore.get().getSprite("sprites/zombien1.png");
     frames[1] = SpriteStore.get().getSprite("sprites/zombien2.png");

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
       return false;
   }

  public void changeDirection(final Direction d)
  {
      direction = d;
      if (getSprite() == frames[1])
            {
                changeFrame(0);    
            }
            else 
            {        
                changeFrame(1);
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









