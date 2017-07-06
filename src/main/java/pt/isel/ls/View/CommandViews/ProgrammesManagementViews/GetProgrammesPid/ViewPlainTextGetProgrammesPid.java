package pt.isel.ls.View.CommandViews.ProgrammesManagementViews.GetProgrammesPid;

import pt.isel.ls.Model.Entities.Programme;
import pt.isel.ls.Model.Results.ProgrammeManagementResults.GetProgrammesPidResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.View;
import pt.isel.ls.View.Formatters.PlainText.Programmes.PlainTextProgrammesFormatter;

import java.io.IOException;
import java.io.StringWriter;

public class ViewPlainTextGetProgrammesPid implements View {
    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the plain text programmes formatter. */
        PlainTextProgrammesFormatter textFormatter = new PlainTextProgrammesFormatter();
        /* Get the programme. */
        Programme programme  = ((GetProgrammesPidResult)rt).getProgramme();
        /* Prints the programme to the writer. */
        textFormatter.formatEntity(programme).writeTo(writer);
    }
}