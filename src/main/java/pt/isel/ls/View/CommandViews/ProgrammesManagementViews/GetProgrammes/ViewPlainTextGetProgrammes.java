package pt.isel.ls.View.CommandViews.ProgrammesManagementViews.GetProgrammes;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.ProgrammeManagementResults.GetProgrammesResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.View;
import pt.isel.ls.View.Formatters.PlainText.Programmes.PlainTextProgrammesFormatter;

import java.io.IOException;
import java.io.StringWriter;

public class ViewPlainTextGetProgrammes implements View {
    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the plain text programmes formatter. */
        PlainTextProgrammesFormatter textFormatter = new PlainTextProgrammesFormatter();
        /* Get the programmes. */
        CustomList<Entity> programmes = ((GetProgrammesResult) rt).getProgrammes();
        /* Prints each programme to the writer. */
        textFormatter.formatEntities(programmes).writeTo(writer);
    }
}