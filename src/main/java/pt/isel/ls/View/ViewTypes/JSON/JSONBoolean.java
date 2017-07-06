package pt.isel.ls.View.ViewTypes.JSON;

import java.io.IOException;
import java.io.Writer;

public class JSONBoolean implements JSONValue {
    private String name;
    private boolean value;

    public JSONBoolean(String name, boolean value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
            return "\"" + name + "\"" + " : " +  String.valueOf(value);
    }

    /**
     * Write this JSONBoolean information to a given writer.
     * @param w writer that will be written with this JSONBoolean information.
     * @throws IOException
     */
    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(toString());
    }
}