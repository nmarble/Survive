package survive;

import java.awt.Graphics;

public abstract class Hud
{
  protected double x;
  protected double y;
  protected int imageSize;
  protected String type;
  protected double movementSpeed;
  protected Sprite sprite;
  
  public Hud(String ref, int x, int y, String type, int imageSize)
  {
    this.sprite = SpriteStore.get().getSprite(ref);
    this.x = x;
    this.y = y;
    this.imageSize = imageSize;
    this.type = type;
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
  public int getImageSize()
  {
    return (int)this.imageSize;
  }
  public void changeFrame(int frameNumber) {}
  
  public String getType()
  {
    return this.type;
  }
}









