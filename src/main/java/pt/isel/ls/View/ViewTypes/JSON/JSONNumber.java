package pt.isel.ls.View.ViewTypes.JSON;

import java.io.IOException;
import java.io.Writer;

public class JSONNumber implements JSONValue{
    private String name;
    private int value;

    public JSONNumber(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "\"" + name + "\"" + " : " + value;
    }

    /**
     * Write this JSONNumber information to a given writer.
     * @param w writer that will be written with this JSONNumber information.
     * @throws IOException
     */
    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(toString());
    }
}