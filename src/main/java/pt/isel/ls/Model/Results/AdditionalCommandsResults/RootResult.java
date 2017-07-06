package pt.isel.ls.Model.Results.AdditionalCommandsResults;

import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.RootViews.ViewHTMLTextRoot;

public class RootResult extends Result {
    public RootResult(){
        super(new ViewHTMLTextRoot(), null, null);
    }
}