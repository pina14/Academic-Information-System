package pt.isel.ls.Model.Results;

import pt.isel.ls.Model.CustomExceptions.HeadersMismatchException;
import pt.isel.ls.Model.DataStructures.CustomMap;
import pt.isel.ls.View.CommandViews.View;

import java.io.IOException;
import java.io.StringWriter;

public abstract class Result {
    protected View view;
    private CustomMap<String, View> viewOptions = new CustomMap<>();

    public Result() {
    }

    public Result(View htmlView, View plainTextView, View jsonView) {
        this.view = htmlView;
        this.viewOptions.put("text/html", htmlView);
        this.viewOptions.put("text/plain", plainTextView);
        this.viewOptions.put("application/json", jsonView);
    }

    /**
     * Writes this result information in its format to the give writer.
     * @param writer Writer object that will be written with this result information.
     * @throws IOException
     */
    public void write(StringWriter writer) throws IOException {
        view.writeResult(this, writer);
    }

    /**
     * Set this result its current view.
     * @param viewType String that identifies the view to set.
     * @throws HeadersMismatchException
     */
    public void setView(String viewType) throws HeadersMismatchException {
        if(viewType == null) return;
        View aux = viewOptions.get(viewType);
        if(aux == null) throw new HeadersMismatchException(" The headers aren't in the correct form ");
        view = aux;
    }
}