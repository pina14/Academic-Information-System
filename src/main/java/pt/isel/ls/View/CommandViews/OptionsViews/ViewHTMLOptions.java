package pt.isel.ls.View.CommandViews.OptionsViews;

import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.Entities.Entity;
import pt.isel.ls.Model.Entities.EntityCommand;
import pt.isel.ls.Model.Results.AdditionalCommandsResults.OptionsResult;
import pt.isel.ls.Model.Results.Result;
import pt.isel.ls.View.CommandViews.ViewHTML;
import pt.isel.ls.View.ViewTypes.Writable;
import pt.isel.ls.View.ViewTypes.html.HtmlElem;
import pt.isel.ls.View.ViewTypes.html.HtmlPage;

import static pt.isel.ls.View.ViewTypes.html.Html.h1;
import static pt.isel.ls.View.ViewTypes.html.Html.text;

public class ViewHTMLOptions extends ViewHTML {

    @Override
    protected HtmlPage build(Result rt) {
        /* Get the list of options. */
        CustomList<Entity> options = ((OptionsResult)rt).getCommands();
        /* Initiate variable. */
        String title = "Options";
        /* Build HTML page. */
        return new HtmlPage(
                title,
                h1(text(title)),
                optionsContent(options)
        );
    }

    /**
     * Get the HTML form of options.
     * @param options CustomList<Entity> that represents the options.
     * @return Writable object that will contain its form of representation in HTML.
     */
    private Writable optionsContent(CustomList<Entity> options) {
        /* Create the HTML table. */
        HtmlElem table = new HtmlElem("table");
        table.withAttr("Border","1");

        /* Define columns name and insert it to the table. */
        HtmlElem trHeaders = new HtmlElem("tr"), trData;
        trHeaders.with(new HtmlElem("th", text("Command Template")))
                .with(new HtmlElem("th", text("Command Description")));
        table.with(trHeaders);
        EntityCommand command;

        /* For each option in the options list insert it to the table. */
        for (int i = 0; i < options.size(); i++) {
            /* Get current option. */
            command = (EntityCommand)options.get(i);

            /* Create the table row that will contain the representation of the option. */
            trData = new HtmlElem("tr");
            trData.with(new HtmlElem("td", text(command.getTEMPLATE())))
                  .with(new HtmlElem("td", text(command.getDESCRIPTION())));
            /* Add the current row to the table. */
            table.with(trData);
        }

        return table;
    }
}