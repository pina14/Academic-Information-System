package pt.isel.ls.View.CommandViews.ProgrammesManagementViews.GetProgrammes;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.ProgrammeManagementResults.GetProgrammesResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.View;
import pt.isel.ls.View.Formatters.JSON.Programmes.JSONProgrammesFormatter;

import java.io.IOException;
import java.io.StringWriter;

public class ViewJSONGetProgrammes implements View {
    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the JSON programmes formatter. */
        JSONProgrammesFormatter jsonFormatter = new JSONProgrammesFormatter();
        /* Get the programmes. */
        CustomList<Entity> programmes = ((GetProgrammesResult) rt).getProgrammes();
        /* Prints each programme to the writer. */
        jsonFormatter.formatEntities(programmes).writeTo(writer);
    }
}