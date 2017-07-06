package pt.isel.ls.Model.Mappers;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper {
  /**
   * Iterate over the result set given, creating new entities and mapping them with SQL information.
   * @param rs ResultSet that contain DataBase information within the query result.
   * @return CustomList<Entity> that will contain the elements resultant of the query.
   * @throws SQLException
   */
  CustomList<Entity> getData(ResultSet rs) throws SQLException;
}