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
    this.sprite = SpriteStore.get().getSprite(ref);
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
    this.x += movementSpeed;
  }
  
  public void moveRight(double movementSpeed)
  {
    this.x -= movementSpeed;
  }
  
  public void moveUp(double movementSpeed)
  {
    this.y += movementSpeed;
  }
  
  public void moveDown(double movementSpeed)
  {
    this.y -= movementSpeed;
  }
  
  public void draw(Graphics g)
  {
    this.sprite.draw(g, (int)this.x, (int)this.y);
  }
  
  public int getX()
  {
    return (int)this.x;
  }
  
  public int getY()
  {
    return (int)this.y;
  }
  
  public void changeFrame(int frameNumber) {}
  
  public String getType()
  {
    return this.type;
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









