package pt.isel.ls.Model.Results.AdditionalCommandsResults;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.OptionsViews.ViewHTMLOptions;
import pt.isel.ls.View.CommandViews.OptionsViews.ViewJSONOptions;
import pt.isel.ls.View.CommandViews.OptionsViews.ViewPlainTextOptions;

public class OptionsResult extends Result{
    private CustomList<Entity> commands = new CustomList<>();

    public OptionsResult(CustomList<Entity> commands) {
        super(new ViewHTMLOptions(), new ViewPlainTextOptions(), new ViewJSONOptions());
        this.commands = commands;
    }

    public CustomList<Entity> getCommands() {
        return commands;
    }
}