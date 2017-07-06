package pt.isel.ls.Model.Mappers;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.CourseClass;
import pt.isel.ls.Model.Entities.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CoursesClasses implements Mapper{

    @Override
    public CustomList<Entity> getData(ResultSet rs) throws SQLException {
        /* Initiate variable. */
        CustomList<Entity> coursesClasses = new CustomList<>();

        /* Get table content. */
        while(rs.next()){
            CourseClass toAdd = new CourseClass(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));
            coursesClasses.add(toAdd);
        }
        return coursesClasses;
    }
}