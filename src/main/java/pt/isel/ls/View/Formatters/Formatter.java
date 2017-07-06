package pt.isel.ls.View.Formatters;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.View.ViewTypes.Writable;

public interface Formatter {
    Writable formatEntity(Entity entity);
    Writable formatEntities(CustomList<Entity> entities);
}