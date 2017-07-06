package pt.isel.ls.View.ViewTypes.html;

import org.apache.commons.lang.StringEscapeUtils;
import pt.isel.ls.View.ViewTypes.Writable;

import java.io.IOException;
import java.io.Writer;

public class HtmlText implements Writable {
    private final String _text;
    
    public HtmlText(String text) {
        _text = text;
    }

    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(StringEscapeUtils.escapeHtml(_text));        
    }
}