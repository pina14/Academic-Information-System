package pt.isel.ls.View.ViewTypes.JSON;

import java.io.IOException;
import java.io.Writer;

public class JSONString implements JSONValue{
    private String name;
    private String value;

    public JSONString(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "\"" + name + "\"" + " : " + "\"" + value + "\"";
    }

    /**
     * Write this JSONString information to a given writer.
     * @param w writer that will be written with this JSONString information.
     * @throws IOException
     */
    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(toString());
    }
}