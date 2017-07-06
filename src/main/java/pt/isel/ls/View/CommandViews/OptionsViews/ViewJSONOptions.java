package pt.isel.ls.View.CommandViews.OptionsViews;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.EntityCommand;
import pt.isel.ls.Model.Results.AdditionalCommandsResults.OptionsResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.View;
import pt.isel.ls.View.ViewTypes.JSON.JSONArray;
import pt.isel.ls.View.ViewTypes.JSON.JSONObject;
import pt.isel.ls.View.ViewTypes.JSON.JSONString;

import java.io.IOException;
import java.io.StringWriter;

public class ViewJSONOptions implements View {

    @Override
    public void writeResult(Result rt, StringWriter writer) throws IOException {
        /* Get the list containing the options. */
        CustomList<Entity> options = ((OptionsResult)rt).getCommands();

        /* Create the JSONArray adding the JSONObjects that represents each option,
         * the JSONObject must contain the option fields and its values. */
        JSONArray json = new JSONArray();
        for (int i = 0; i < options.size(); i++) {
            /* Create the JSONObject with the options fields and its values. */
            JSONObject jsonCurr = new JSONObject();
            /* Get the current option. */
            EntityCommand command = (EntityCommand)options.get(i);
            /* Add the fields and its values to the JSONObject. */
            jsonCurr.withField(new JSONString("Description", command.getDESCRIPTION()))
                    .withField(new JSONString("Template", command.getTEMPLATE()));
            /* Add the JSONObject to the JSONArray. */
            json.addField(jsonCurr);
        }

        /* Print the result to the writer. */
        json.writeTo(writer);
    }
}