package survive;

public abstract class Hud extends Drawable {
  protected int imageSize;
  protected String type;
  protected double movementSpeed;

  public Hud(String ref, final Coords coords, String type, int imageSize) {
    super(coords, SpriteStore.get().getSprite(ref));
    this.imageSize = imageSize;
    this.type = type;
  }

  public int getImageSize() {
    return imageSize;
  }

  public void changeFrame(int frameNumber) {
  }

  public String getType() {
    return type;
  }
}
