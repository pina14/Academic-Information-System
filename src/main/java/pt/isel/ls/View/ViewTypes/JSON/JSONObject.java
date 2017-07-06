package pt.isel.ls.View.ViewTypes.JSON;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class JSONObject implements JSONValue {
    private String name;
    private ArrayList<JSONValue> fields = new ArrayList<>();

    public JSONObject(){

    }

    public JSONObject(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name == null ? buildString() : "\"" + name + "\"" + " : " + buildString() + "\n";
    }

    /**
     * Add a new JSONObject to the JSONArray.
     * @param value JSONValue to be added to this JSONArray.
     */
    public JSONObject withField(JSONValue value){
        fields.add(value);
        return this;
    }

    /**
     * Run through all it's fields and forms the String containing all fields value.
     * @return String that represents this JSONObject information
     */
    private String buildString() {
        /* Initiate String. */
        String toPrint = "{\n";

        /* For each element this JSONObject contains add it's toString to this array information String.*/
        for (JSONValue value : fields) {
            toPrint += value.toString() + ",\n";
        }

        /* Correct String. */
        toPrint = toPrint.substring(0, toPrint.length() - 2);
        toPrint += "\n}" ;
        return toPrint;
    }

    /**
     * Write this JSONObject information to a given writer.
     * @param w writer that will be written with this JSONObject information.
     * @throws IOException
     */
    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(toString());
    }
}