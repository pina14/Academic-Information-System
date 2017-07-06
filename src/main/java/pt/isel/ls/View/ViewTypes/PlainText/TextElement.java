package pt.isel.ls.View.ViewTypes.PlainText;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomPair;
import pt.isel.ls.View.ViewTypes.Writable;

import java.io.IOException;
import java.io.Writer;

public class TextElement implements Writable{
    private CustomList<CustomPair<String, String>> data;

    public TextElement(CustomList<CustomPair<String, String>> data) {
        this.data = data;
    }

    /**
     * Add a new entry of information about this text element.
     * @param toAdd CustomPair<String, String> that represents the information to add to this text element.
     */
    public void addAttribute(CustomPair<String, String> toAdd) {
        data.add(toAdd);
    }

    @Override
    public String toString() {
        /* Initiate String. */
        String info = "";

        /* For each element in data custom list adds it to the string. */
        for (int i = 0; i < data.size(); i++) {
            info += data.get(i).getKey() + " : ";
            info += data.get(i).getValue() + "\n";
        }

        info += "\n";
        return info;
    }

    /**
     * Write this TextElement information to a given writer.
     * @param w writer that will be written with this TextElement information.
     * @throws IOException
     */
    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(toString());
    }
}