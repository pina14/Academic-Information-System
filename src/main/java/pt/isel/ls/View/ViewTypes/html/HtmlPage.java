package pt.isel.ls.View.ViewTypes.html;

import pt.isel.ls.View.ViewTypes.Writable;

import java.io.IOException;
import java.io.Writer;

public class HtmlPage implements Writable{
    private final HtmlElem _html;
    private final HtmlElem _body = new HtmlElem("body");

    public HtmlPage(String title, Writable...cs) {
        _html = new HtmlElem("html",
                new HtmlElem("head", new HtmlElem("Title", new HtmlText(title))),
                _body);

        for(Writable c : cs)
            with(c);
    }

    public final HtmlPage with(Writable w) {
        _body.with(w);
        return this;
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        _html.writeTo(w);
    }
}