package pt.isel.ls.Model.Mappers;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Course;
import pt.isel.ls.Model.Entities.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Courses implements Mapper {
    @Override
    public CustomList<Entity> getData(ResultSet rs) throws SQLException {
        /* Initiate variable. */
        CustomList<Entity> courses = new CustomList<>();

        /* Get table content. */
        while(rs.next()){
            Course toAdd = new Course(rs.getString(1), rs.getString(2), rs.getInt(3));
            courses.add(toAdd);
        }
        return courses;
    }
}