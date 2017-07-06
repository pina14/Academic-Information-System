package pt.isel.ls.View.CommandViews;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Error.CustomError;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.Model.Results.ResultError;

import java.io.IOException;
import java.io.StringWriter;

public class DefaultPostView implements View {

    /**
     * Writes in normal text all the errors in the ResultError received in parameters into the writer.
     * @param rt ResultError object that will give all the errors to be written into the writer.
     * @param writer Writer object that will be written the result information.
     * @throws IOException
     */
    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the result error. */
        ResultError resultError = (ResultError) rt;

        /* Get the errors. */
        CustomList<CustomError> errors = resultError.getErrors();
        String result = "";
        CustomError curr;

        /* For each error writes its id and description. */
        for (int i = 0; i < errors.size(); i++) {
            curr = errors.get(i);
            result += curr.getId() + " : " + curr.getDescription() + "\n";
        }
        /* Write the information into the writer. */
        writer.write(result);
    }
}