package pt.isel.ls.Model.Results;

import java.io.IOException;
import java.io.StringWriter;

public class RedirectResult extends Result {
    private String uri;

    public RedirectResult(String uri) {
        this.uri = uri;
    }

    @Override
    public void write(StringWriter writer) throws IOException {
    }

    @Override
    public void setView(String viewType) {
    }

    public String getUri() {
        return uri;
    }
}