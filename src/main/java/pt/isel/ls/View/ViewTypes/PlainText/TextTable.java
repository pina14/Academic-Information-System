package pt.isel.ls.View.ViewTypes.PlainText;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.View.ViewTypes.Writable;

import java.io.IOException;
import java.io.Writer;

public class TextTable implements Writable {
    private CustomList<TextElement> elements = new CustomList<>();

    public void addElement(TextElement element){
        this.elements.add(element);
    }

    /**
     * Write this TextTable information to a given writer.
     * @param w writer that will be written with this TextTable information.
     * @throws IOException
     */
    @Override
    public void writeTo(Writer w) throws IOException {
        /* For each textElement this table contains writes it information to the writer. */
        for (int i = 0; i < elements.size(); i++) {
            elements.get(i).writeTo(w);
        }
    }
}