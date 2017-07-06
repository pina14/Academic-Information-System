package pt.isel.ls.Model.Mappers;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Teachers implements Mapper {

    @Override
    public CustomList<Entity> getData(ResultSet rs) throws SQLException {
        /* Initiate variable. */
        CustomList<Entity> teachers = new CustomList<>();

        /* Get table content. */
        while(rs.next()){
            Teacher toAdd = new Teacher(rs.getInt(1), rs.getString(2), rs.getString(3));
            teachers.add(toAdd);
        }
        return teachers;
    }
}