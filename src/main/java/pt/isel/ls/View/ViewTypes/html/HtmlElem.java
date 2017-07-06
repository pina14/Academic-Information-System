package pt.isel.ls.View.ViewTypes.html;

import pt.isel.ls.View.ViewTypes.Writable;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class HtmlElem implements Writable {
    private final String _name;
    private final Map<String,String> _attrs = new HashMap<String,String>();
    private final List<Writable> _content = new ArrayList<Writable>();

    public HtmlElem(String name, Writable...cs) {
        _name = name;
        _content.addAll(Arrays.asList(cs));
    }

    public final HtmlElem withAttr(String name, String value) {
        _attrs.put(name, value);
        return this;
    }

    public final HtmlElem with(Writable w) {
        _content.add(w);
        return this;
    }
    
    @Override
    public void writeTo(Writer w) throws IOException {
        w.write(String.format("<%s",_name));
        for(Map.Entry<String,String> entry : _attrs.entrySet()) {
            w.write(String.format(" %s='%s'",entry.getKey(), entry.getValue()));
        }
        w.write(">\n");
        for(Writable c : _content) {
            c.writeTo(w);
        }
        w.write(String.format("\n</%s>\n",_name));
    }    
}