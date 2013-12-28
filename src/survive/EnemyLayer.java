package survive;

import java.awt.Graphics;


public abstract class EnemyLayer extends Drawable
{

  protected Direction direction;
  protected double movementSpeed;
  protected int type;
  public EnemyLayer(String ref, final Coords coords, final Direction direction, int enemyType)
  {
    super(coords, SpriteStore.get().getSprite(ref));
    this.direction = direction;
    type = enemyType;
  }
  
  public void changeFrame(int frameNumber) {}
  
  public int getType()
  {
    return type;
  }
  public int getRotation()
  {
      int rot = 0;
      switch (direction) {
          case UP:
              rot = 0;
              break;
          case DOWN:
              rot = 180;
              break;
          case RIGHT:
              rot = 90;
              break;
          case LEFT:
              rot = 270;
              break;
      }
      return rot;
  }
  public abstract boolean interact();
  public abstract boolean passable();
  public abstract void changeDirection(Direction d, boolean attack);
  public abstract long getSpeed();
  public abstract int getLastX();
  public abstract int getLastY();
  public abstract void setLast(int lX, int lY);
  public abstract int getLife();
  public abstract void setLife(int newLife);
  public abstract int getSTR();
  public abstract void setPassable(boolean newPass);
  
}









