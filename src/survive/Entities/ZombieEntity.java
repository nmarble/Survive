 package survive.Entities;
 
 import survive.EnemyLayer;
 import survive.Global;
 import survive.Sprite;
 import survive.SpriteStore;
 import survive.Survive;
 
 public class ZombieEntity
   extends EnemyLayer
 {
   private Survive survive;
   private Sprite[] frames = new Sprite[8];
   private int lastX = 0;
   private int lastY = 0;
   public ZombieEntity(Survive survive, String ref, int x, int y, String type, String direction)
   {
     super(ref, x, y, type, direction);
     
     frames[0] = SpriteStore.get().getSprite("sprites/ZombieN1.png");
     frames[1] = SpriteStore.get().getSprite("sprites/ZombieN2.png");
     frames[2] = SpriteStore.get().getSprite("sprites/ZombieE1.png");
     frames[3] = SpriteStore.get().getSprite("sprites/ZombieE2.png");
     frames[4] = SpriteStore.get().getSprite("sprites/ZombieS1.png");
     frames[5] = SpriteStore.get().getSprite("sprites/ZombieS2.png");
     frames[6] = SpriteStore.get().getSprite("sprites/ZombieW1.png");
     frames[7] = SpriteStore.get().getSprite("sprites/ZombieW2.png");
     this.survive = survive;
   }
   
   public void changeFrame(int frameNumber)
   {
     sprite = frames[frameNumber];
   }
   public void interact()
   {
     survive.removeEnemyLayer(this);
   }
    public boolean passable()
   {
       return false;
   }
  public void changeDirection(String d)
  {
      direction = d;
      switch (direction)
      {
        case "left":
            if (sprite == frames[7])
            {
                changeFrame(6);    
            }
            else 
            {        
                changeFrame(7);
            }
              break;
        case "right":
            if (sprite == frames[2])
            {
            changeFrame(3);    
            }
            else 
            {        
            changeFrame(2);
            }
            break;
        case "up":
            if (sprite == frames[0])
            {
            changeFrame(1);    
            }
            else 
            {        
            changeFrame(0);
            }
            break;
        case "down":
            if (sprite == frames[5])
            {
            changeFrame(4);    
            }
            else 
            {        
            changeFrame(5);
            }
            break;
          
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









