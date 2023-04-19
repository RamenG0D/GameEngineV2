package Level;

import Renders.Screen;

public abstract class Tile {
    public static final Tile[] tiles = new Tile[256];
    protected boolean solid;
    protected byte id;
    //
    public Tile(int id, boolean isSolid, int levelColour) {
        this.id = (byte) id;
        if (tiles[id] != null) throw new RuntimeException("Duplicate tile id on " + id);
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
    public abstract void update();
    //
    public abstract void render(Screen screen, Level level, int x, int y);
}
