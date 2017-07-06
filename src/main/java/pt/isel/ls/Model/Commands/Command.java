package pt.isel.ls.Model.Commands;

import pt.isel.ls.Model.CustomExceptions.InvalidTypeException;
import pt.isel.ls.Model.CustomExceptions.NotExistInformationException;
import pt.isel.ls.Model.CustomExceptions.ParametersException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.Result;

import java.sql.Connection;
import java.sql.SQLException;

public interface Command {
    CustomList<Entity> commands = new CustomList<>();
    Result execute(CustomMap<String, String> pathParameters, CustomMap<String, CustomList<String>> parameters, Connection conn) throws ParametersException, SQLException, NotExistInformationException, InvalidTypeException;
}