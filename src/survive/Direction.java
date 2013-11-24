package survive;

import java.util.Random;

/**
 * The four possible directions.
 */
public enum Direction
{

  UP(0, -20), DOWN(0, 20), LEFT(-20, 0), RIGHT(20, 0);

  private final int xOffset;
  private final int yOffset;

  private Direction(final int xOffset, final int yOffset)
  {
    this.xOffset = xOffset;
    this.yOffset = yOffset;
  }

  /**
   * Calculates the coordinates if the given coordinates were moved in this direction.
   *
   * @param reference The coordinates to move from.
   * @return The moved coordinates.
   */
  public Coords getCoordsFrom(final Coords reference)
  {
    return new Coords(reference.getX() + xOffset, reference.getY() + yOffset);
  }

  public Direction getOpposite()
  {
    for (final Direction other : values()) {
      if (this.xOffset == -other.xOffset && this.yOffset == -other.yOffset) {
        return other;
      }
    }
    throw new Error("No opposite found for " + this);
  }

  public static Direction getRandom()
  {
    return values()[new Random().nextInt(values().length)];
  }
}
