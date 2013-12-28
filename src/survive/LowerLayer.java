package survive;

public abstract class LowerLayer extends Drawable implements java.io.Serializable{

    protected int type;

    public LowerLayer(final Coords coords, int type) {
        super(coords, SpriteStore.get().getSprite("sprites/null.png"));
        this.type = type;
    }

    public int getType() {
        return type;
    }
    public void changeFrame(int frameNumber) {}
    public int nextFrame() {return 0;}
    public abstract boolean passable();
}
