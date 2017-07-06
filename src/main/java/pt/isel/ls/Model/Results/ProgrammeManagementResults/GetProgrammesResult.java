package pt.isel.ls.Model.Results.ProgrammeManagementResults;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.ProgrammesManagementViews.GetProgrammes.ViewHTMLGetProgrammes;
import pt.isel.ls.View.CommandViews.ProgrammesManagementViews.GetProgrammes.ViewJSONGetProgrammes;
import pt.isel.ls.View.CommandViews.ProgrammesManagementViews.GetProgrammes.ViewPlainTextGetProgrammes;

public class GetProgrammesResult extends Result {
    private CustomList<Entity> programmes;
    private int skip, top, numberRows;

    public GetProgrammesResult(CustomList<Entity> programmes, int skip, int top, int numberRows) {
        super(new ViewHTMLGetProgrammes(), new ViewPlainTextGetProgrammes(), new ViewJSONGetProgrammes());
        this.programmes = programmes;
        this.skip = skip;
        this.top = top;
        this.numberRows = numberRows;
    }

    public CustomList<Entity> getProgrammes() {
        return programmes;
    }

    public int getSkip() {
        return skip;
    }

    public int getTop() {
        return top;
    }

    public int getNumberRows() {
        return numberRows;
    }
}