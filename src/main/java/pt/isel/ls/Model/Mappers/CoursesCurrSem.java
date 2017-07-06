package pt.isel.ls.Model.Mappers;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.CourseSemCurr;
import pt.isel.ls.Model.Entities.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CoursesCurrSem implements Mapper {
    @Override
    public CustomList<Entity> getData(ResultSet rs) throws SQLException {
        /* Initiate variable. */
        CustomList<Entity> courseSemCurr = new CustomList<>();

        /* Get table content. */
        while(rs.next()){
            CourseSemCurr toAdd = new CourseSemCurr(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
            courseSemCurr.add(toAdd);
        }
        return courseSemCurr;
    }
}