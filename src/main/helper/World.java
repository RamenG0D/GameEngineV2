package helper;

import java.util.List;
import java.util.ArrayList;

public class World { // this class should only STORE DATA about the WORLD!
    private List<Entity> entities = new ArrayList<>();
    //
    public World() {
        //
    }
    //
    public Entity getEntity(Object entity) {
        return entities.get(entities.indexOf(entity));
    }
    //
    public void add(Entity entity) {
        entities.add(entity);
    }
    //
    public void remove(Entity entity) {
        entities.remove(entity);
    }
}
