package survive;

import java.awt.Graphics;

public abstract class MiddleLayer
{
  protected double x;
  protected double y;
  protected String type;
  protected double movementSpeed;
  protected Sprite sprite;
  
  public MiddleLayer(String ref, int x, int y, String type)
  {
    sprite = SpriteStore.get().getSprite(ref);
    this.x = x;
    this.y = y;
    this.type = type;
  }
  
  public boolean collideWith(int x, int y, int playerX, int playerY)
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
  public abstract void interact();
  
}









