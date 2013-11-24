package survive;

import java.awt.Graphics;
import java.util.Objects;

/**
 * Something that has X,Y coordinates and can be drawn,
 */
public abstract class Drawable {

  private Coords coords;
  private Sprite sprite;

  protected Drawable(final Coords coords, final Sprite sprite) {
    this.coords = Objects.requireNonNull(coords);
    this.sprite = Objects.requireNonNull(sprite);
  }

  public void draw(final Graphics g, final Coords offset) {
    sprite.draw(g, coords.getX() - offset.getX(), coords.getY() - offset.getY());
  }

  public Coords getCoords() {
    return coords;
  }

  protected void setCoords(final Coords coords) {
    this.coords = Objects.requireNonNull(coords);
  }

  protected Sprite getSprite()
  {
    return sprite;
  }

  protected void setSprite(final Sprite sprite) {
    this.sprite = Objects.requireNonNull(sprite);
  }
}
