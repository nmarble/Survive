package survive;


import java.awt.Graphics;

public abstract class Hud
{
  protected int x;
  protected int y;
  protected int imageSize;
  protected String type;
  protected double movementSpeed;
  protected Sprite sprite;
  
  public Hud(String ref, int x, int y, String type, int imageSize)
  {
    sprite = SpriteStore.get().getSprite(ref);
    this.x = x;
    this.y = y;
    this.imageSize = imageSize;
    this.type = type;
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
  public int getImageSize()
  {
    return imageSize;
  }
 
  public void changeFrame(int frameNumber) {}
  
  public String getType()
  {
    return type;
  }
}









