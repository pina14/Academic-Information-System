package pt.isel.ls.View.CommandViews;

import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import java.io.IOException;
import java.io.StringWriter;

public abstract class ViewHTML implements View{
    /**
     * Writes the result information into the given writer.
     * @param rt Result object that will give the information to be written into the writer.
     * @param writer Writer where the information will be written.
     * @throws IOException
     */
    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        HtmlPage page = build(rt);
        page.writeTo(writer);
    }

    /**
     * Get the HTML view of this command.
     * @param rt Object that represents the result.
     * @return HTML view of this command.
     */
    protected abstract HtmlPage build(Result rt) throws IOException;
}