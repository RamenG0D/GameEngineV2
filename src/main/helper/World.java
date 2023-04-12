package helper;

import java.util.List;
import java.util.ArrayList;

public class World { // this class should only STORE DATA about the WORLD!
    private List<Entity2D> entities = new ArrayList<>();
    /// responsible for entity management
    public World() {}
    /// get entities based on their type
    public Entity2D getEntity(Entity2D entity) {
        return entities.get(entities.indexOf(entity));
    }
    //
    public void add(Entity2D entity) {
        entities.add(entity);
    }
    //
    public void remove(Entity2D entity) {
        entities.remove(entity);
    }
}
