package pt.isel.ls.Model.Results;

import java.io.IOException;
import java.io.StringWriter;

public class NullResult extends Result {
    @Override
    public void write(StringWriter writer) throws IOException {
    }

    @Override
    public void setView(String viewType) {
    }
}
