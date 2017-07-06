package pt.isel.ls.Model.Results.ProgrammeManagementResults;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.Programme;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.ProgrammesManagementViews.GetProgrammesPid.ViewHTMLGetProgrammesPid;
import pt.isel.ls.View.CommandViews.ProgrammesManagementViews.GetProgrammesPid.ViewJSONGetProgrammesPid;
import pt.isel.ls.View.CommandViews.ProgrammesManagementViews.GetProgrammesPid.ViewPlainTextGetProgrammesPid;

public class GetProgrammesPidResult extends Result {
    private Programme programme;
    private CustomList<Entity> programmeCourses;
    private String pid;

    public GetProgrammesPidResult(Programme programme, CustomList<Entity> programmeCourses, String pid) {
        super(new ViewHTMLGetProgrammesPid(), new ViewPlainTextGetProgrammesPid(), new ViewJSONGetProgrammesPid());
        this.programme = programme;
        this.programmeCourses = programmeCourses;
        this.pid = pid;
    }

    public Programme getProgramme() {
        return programme;
    }

    public CustomList<Entity> getProgrammeCourses() {
        return programmeCourses;
    }

    public String getPid() {
        return pid;
    }
}