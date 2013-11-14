package survive;

import java.awt.Graphics;

public abstract class MiddleLayer
{
  protected double x;
  protected double y;
  protected String type;
  protected double movementSpeed;
  protected Sprite sprite;
  protected int modifiedX;
  protected int modifiedY;
  
  public MiddleLayer(String ref, int x, int y, String type)
  {
    sprite = SpriteStore.get().getSprite(ref);
    this.x = x;
    this.y = y;
    this.type = type;
  
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
  
  public boolean collideWithPlayer()
  {
    double xM = x + getModifiedX();
    double yM = y + getModifiedY();
    
    boolean collision = false;
    if ((xM == Global.playerX) && (yM == Global.playerY)) {
      collision = true;
    }
    return collision;
  }
  
  public abstract void interact();
  public abstract boolean passable();
  public abstract int getModifiedX();
  public abstract int getModifiedY();
}









