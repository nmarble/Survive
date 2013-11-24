package survive;

public abstract class LowerLayer extends Drawable {

    protected String type;

    public LowerLayer(String ref, final Coords coords, String type) {
        super(coords, SpriteStore.get().getSprite(ref));
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public abstract boolean passable();
}
