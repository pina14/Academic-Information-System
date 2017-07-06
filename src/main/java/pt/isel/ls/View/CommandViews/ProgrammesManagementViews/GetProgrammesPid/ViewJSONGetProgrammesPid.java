package pt.isel.ls.View.CommandViews.ProgrammesManagementViews.GetProgrammesPid;

import pt.isel.ls.Model.Entities.Programme;
import pt.isel.ls.Model.Results.ProgrammeManagementResults.GetProgrammesPidResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.View;
import pt.isel.ls.View.Formatters.JSON.Programmes.JSONProgrammesFormatter;

import java.io.IOException;
import java.io.StringWriter;

public class ViewJSONGetProgrammesPid implements View {
    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the JSON programmes formatter. */
        JSONProgrammesFormatter jsonFormatter = new JSONProgrammesFormatter();
        /* Get the programme. */
        Programme programme  = ((GetProgrammesPidResult)rt).getProgramme();
        /* Prints the programme to the writer. */
        jsonFormatter.formatEntity(programme).writeTo(writer);
    }
}