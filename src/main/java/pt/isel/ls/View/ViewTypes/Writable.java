package pt.isel.ls.View.ViewTypes;

import java.io.IOException;
import java.io.Writer;

public interface Writable {
    void writeTo(Writer w) throws IOException;
}