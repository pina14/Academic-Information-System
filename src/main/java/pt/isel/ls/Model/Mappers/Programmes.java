package pt.isel.ls.Model.Mappers;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.Programme;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Programmes implements Mapper {

    @Override
    public CustomList<Entity> getData(ResultSet rs) throws SQLException {
        /* Initiate variable. */
        CustomList<Entity> programmes = new CustomList<>();

        /* Get table content. */
        while(rs.next()){
            Programme toAdd = new Programme(rs.getString(1), rs.getString(2), rs.getInt(3));
            programmes.add(toAdd);
        }
        return programmes;
    }
}