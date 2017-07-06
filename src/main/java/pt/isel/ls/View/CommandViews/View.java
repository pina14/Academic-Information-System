package pt.isel.ls.View.CommandViews;

import pt.isel.ls.Model.Results.Result;

import java.io.IOException;
import java.io.StringWriter;

public interface View {
   /**
    * Writes the result information into the given writer.
    * @param rt Result object that will give the information to be written into the writer.
    * @param writer Writer where the information will be written.
    * @throws IOException
    */
   void writeResult(Result rt, StringWriter writer) throws IOException;
}