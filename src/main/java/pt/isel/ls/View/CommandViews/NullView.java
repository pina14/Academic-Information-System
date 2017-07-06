package pt.isel.ls.View.CommandViews;

import pt.isel.ls.Model.Results.Result;

import java.io.IOException;
import java.io.StringWriter;

public class NullView implements View{
    /**
     * As this object is a NullView is shall do nothing.
     * @param rt Result object that this views belongs to.
     * @param writer Writer that this view shall write to.
     * @throws IOException
     */
    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException { }
}