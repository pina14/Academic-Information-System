package pt.isel.ls.Model.Mappers;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.Student;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Students implements Mapper {

    @Override
    public CustomList<Entity> getData(ResultSet rs) throws SQLException {
        /* Initiate variable. */
        CustomList<Entity> students = new CustomList<>();

        /* Get table content. */
        while(rs.next()){
            Student toAdd = new Student(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            students.add(toAdd);
        }
        return students;
    }
}