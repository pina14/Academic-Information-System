package pt.isel.ls.View.CommandViews.OptionsViews;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.EntityCommand;
import pt.isel.ls.Model.Results.AdditionalCommandsResults.OptionsResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.View;
import pt.isel.ls.View.ViewTypes.PlainText.TextElementCreator;
import pt.isel.ls.View.ViewTypes.PlainText.TextTable;

import java.io.IOException;
import java.io.StringWriter;

public class ViewPlainTextOptions implements View {

    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the list containing the options. */
        CustomList<Entity> options = ((OptionsResult)rt).getCommands();
        /* Create a table and add the created text elements. */
        TextTable elements = new TextTable();
        for (int i = 0; i < options.size(); i++) {
            /* Get current option. */
            EntityCommand cmd = (EntityCommand)options.get(i);
            /* Create the text element and add it to the text table. */
            elements.addElement(new TextElementCreator<EntityCommand>()
                    .withCol("Template", command -> command.getTEMPLATE())
                    .withCol("Description", command -> command.getDESCRIPTION())
                    .build(cmd)
            );
        }

        /* Print the result to the writer. */
        elements.writeTo(writer);
    }
}