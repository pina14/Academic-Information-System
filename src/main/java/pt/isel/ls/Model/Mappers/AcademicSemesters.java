package pt.isel.ls.Model.Mappers;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.AcademicSemester;
import pt.isel.ls.Model.Entities.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AcademicSemesters implements Mapper {
    @Override
    public CustomList<Entity> getData(ResultSet rs) throws SQLException {
        /* Initiate variable. */
        CustomList<Entity> semesters = new CustomList<>();

        /* Get table content. */
        while(rs.next()){
            AcademicSemester toAdd = new AcademicSemester(rs.getInt(1), rs.getString(2));
            semesters.add(toAdd);
        }
        return semesters;
    }
}