package pt.isel.ls.Model.Mappers;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Class;
import pt.isel.ls.Model.Entities.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper that knows how to map a result set from SQL corresponding entity into new Classes.
 */
public class Classes implements Mapper {
    @Override
    public CustomList<Entity> getData(ResultSet rs) throws SQLException {
        /* Initiate variable. */
        CustomList<Entity> classes = new CustomList<>();

        /* Get table content. */
        while(rs.next()){
            Class toAdd = new Class(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4));
            classes.add(toAdd);
        }
        return classes;
    }
}