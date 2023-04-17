package Level;

import Renders.Screen;

public abstract class Tile {
    public static final Tile[] tiles = new Tile[256];
    protected boolean solid;
    private int levelColour;
    protected byte id;
    //
    public Tile(int id, boolean isSolid, int levelColour) {
        this.id = (byte) id;
        if (tiles[id] != null) throw new RuntimeException("Duplicate tile id on " + id);
        this.levelColour = levelColour;
        this.solid = isSolid;
        tiles[id] = this;
    }
    //
    public byte getId() {
        return id;
    }
    //
    public boolean isSolid() {
        return solid;
    }
    //
    public int getLevelColour() {
        return levelColour;
    }
    //
    public abstract void tick();
    //
    public abstract void render(Screen screen, Level level, int x, int y);
}
