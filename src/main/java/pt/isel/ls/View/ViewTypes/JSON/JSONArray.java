package pt.isel.ls.View.ViewTypes.JSON;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class JSONArray implements JSONValue {
    private String name;
    private ArrayList<JSONValue> values = new ArrayList<>();

    public JSONArray() {

    }

    @Override
    public String toString() {
        return name == null ? buildString() : "\"" + name + "\"" + " : " + buildString() + ",\n";
    }

    /**
     * Run through all it's fields and forms the String containing all fields value.
     * @return String that represents this JSONArray information
     */
    private String buildString() {
        /* Initiate String. */
        String toPrint = "[\n";

        /* For each element this JSONArray contains add it's toString to this array information String.*/
        for (JSONValue value : values) {
            toPrint += value.toString() + ",\n";
        }

        /* Correct String. */
        toPrint = toPrint.substring(0, toPrint.length() - 2);
        toPrint += "\n]" ;
        return toPrint;
    }

    /**
     * Add a new JSONObject to the JSONArray.
     * @param jsonCurr JSONValue to be added to this JSONArray.
     */
    public void addField(JSONValue jsonCurr) {
        values.add(jsonCurr);
    }

    /**
     * Sets the object type name.
     * @param name String that represents the name of this JSONArray.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Write this JSONArray information to a given writer.
     * @param w writer that will be written with this JSONArray information.
     * @throws IOException
     */
    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(toString());
    }
}