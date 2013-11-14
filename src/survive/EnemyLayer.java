package survive;

import java.awt.Graphics;


public abstract class EnemyLayer
{
  protected double x;
  protected double y;
  protected String type;
  protected String direction;
  protected double movementSpeed;
  protected Sprite sprite;
  
  public EnemyLayer(String ref, int x, int y, String type, String direction)
  {
    sprite = SpriteStore.get().getSprite(ref);
    this.x = x;
    this.y = y;
    this.type = type;
    this.direction = direction;
  }
  
  public boolean collideWithPlayer(int playerX, int playerY)
  {
    boolean collision = false;
    if ((x == playerX) && (y == playerY)) {
      collision = true;
    }
    return collision;
  }
 
  public void moveLeft(double movementSpeed)
  {
    x += movementSpeed;
  }
  
  public void moveRight(double movementSpeed)
  {
    x -= movementSpeed;
  }
  
  public void moveUp(double movementSpeed)
  {
    y += movementSpeed;
  }
  
  public void moveDown(double movementSpeed)
  {
    y -= movementSpeed;
  }
  
  public void draw(Graphics g)
  {
    sprite.draw(g, (int)x, (int)y);
  }
  
  public int getX()
  {
    return (int)x;
  }
  
  public int getY()
  {
    return (int)y;
  }
  
  public void changeFrame(int frameNumber) {}
  
  public String getType()
  {
    return type;
  }
  
  public int getModifiedX()
  {
    int modifiedX = 0;
    return modifiedX;
  }
  
  public int getModifiedY()
  {
    int modifiedY = 0;
    return modifiedY;
  }
  public boolean collideWithPlayer()
  {

    boolean collision = false;
    if ((x == Global.playerX) && (y == Global.playerY)) {
      collision = true;
    }
    return collision;
  }
  public boolean collideWithObject(int objectX, int objectY)
  {
  
    boolean collision = false;
    if ((x == objectX) && (y == objectY))
    {
      collision = true;
    }
    return collision;
  }
  public void moveBack()
  {
      switch (direction)
      {
          case "up":
              y = y + 20;
              break;
          case "down":
              y = y - 20;
              break;
          case "left":
              x = x + 20;
              break;
          case "right":
              x = x - 20;
              break;
      }
  }
  
  public abstract void interact();
  public abstract boolean passable();
  public abstract void moveToPlayer();
  public abstract long getSpeed();
}









