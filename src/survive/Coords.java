package survive;

import java.util.Objects;

/**
 * Holds integer X,Y coordinates for entities.
 */
public class Coords implements java.io.Serializable{

    private final int x;
    private final int y;

    public Coords(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        try {
        return x;
        }
        catch (Exception ex) {
            return 0;
        }
    }

    public int getY() {
        try {
        return y;
        }
        catch (Exception ex) {
            return 0;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Coords other = (Coords) obj;
        return this.x == other.x
                && this.y == other.y;
    }

}
