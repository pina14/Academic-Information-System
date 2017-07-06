package pt.isel.ls.View.ViewTypes.PlainText;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomPair;
import pt.isel.ls.Model.Entities.Entity;

import java.util.function.Function;

/**
 * Create a list of Pair<String1, String2> where String2 is the result of corresponding function.
 * @param <T>
 */
public class TextElementCreator<T>{
    private CustomList<CustomPair<String, Function>> functionsList;

    public TextElementCreator(){
        functionsList = new CustomList<>();
    }

    /**
     * Add a new pair with the name representing the column and the function that tells how to retrieve the value.
     * @param colName Column name that represents the information entry to add.
     * @param function Function that gives the value to this entry.
     * @return The same TextElementCreator.
     */
    public TextElementCreator<T> withCol(String colName, Function<T, String> function){
        functionsList.add(new CustomPair<>(colName, function));
        return this;
    }

    /**
     * Get the text element with values of functions that will come from the application of corresponding function over entity t.
     * @return Text element which properties were prepared before.
     */
    public TextElement build(Entity t){
        TextElement listData = new TextElement(new CustomList<>());
        Function f;
        for (int i = 0; i < functionsList.size(); i++) {
            CustomPair<String, Function> p = functionsList.get(i);
            f = p.getValue();
            CustomPair<String, String> curCol = new CustomPair(p.getKey(), f.apply(t));
            listData.addAttribute(curCol);
        }
        return listData;
    }
}