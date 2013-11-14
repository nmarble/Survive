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
   private long speed = 100000000;
   
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
     System.exit(0);
   }
    public boolean passable()
   {
       return false;
   }
   public void moveToPlayer()
  {
      if (Global.playerX < x)
      {
          direction = "left";
          x = x - 20;
          if (sprite == frames[7])
          {
          changeFrame(6);    
          }
          else 
          {        
          changeFrame(7);
          }
      }
      if (Global.playerX > x)
      {
          direction = "right";
          x = x + 20;
          if (sprite == frames[2])
          {
          changeFrame(3);    
          }
          else 
          {        
          changeFrame(2);
          }
      }
      if (Global.playerY < y)
      {
          direction = "up";
          y = y - 20;
          if (sprite == frames[0])
          {
          changeFrame(1);    
          }
          else 
          {        
          changeFrame(0);
          }
      }
      if (Global.playerY > y)
      {
          direction = "down";
          y = y + 20;
          if (sprite == frames[5])
          {
          changeFrame(4);    
          }
          else 
          {        
          changeFrame(5);
          }
      }
  }
   public long getSpeed()
   {
       return (speed*100000);
   }
 }









