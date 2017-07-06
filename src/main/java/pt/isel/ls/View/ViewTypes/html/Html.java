package pt.isel.ls.View.ViewTypes.html;

import pt.isel.ls.View.ViewTypes.Writable;

public class Html {
    public static Writable text(String s) {
        return new HtmlText(s);
    }

    public static Writable intText(int i) {
        return new HtmlText(String.valueOf(i));
    }

    public static Writable h1(Writable... c) {
        return new HtmlElem("h1",c);
    }

    public static Writable h2(Writable... c) {
        return new HtmlElem("h2",c);
    }

    public static Writable h3(Writable... c) {
        return new HtmlElem("h3",c);
    }

    public static HtmlElem form(String method, String url, Writable... c) {
        return new HtmlElem("form",c)
                .withAttr("method", method)
                .withAttr("action", url);
    }

    public static HtmlElem label(String to, String text) {
        return new HtmlElem("label", new HtmlText(text))
                .withAttr("for", to);
    }

    public static HtmlElem textInput(String name) {
        return new HtmlElem("input")
                .withAttr("type", "text")
                .withAttr("name", name);
    }

    public static HtmlElem p(Writable... c) {
        return new HtmlElem("p",c);
    }

    public static HtmlElem table(Writable... c){
        return new HtmlElem("table", c);
    }

    public static HtmlElem th(Writable... c) {
        return new HtmlElem("th",c);
    }

    public static HtmlElem tr(Writable... c) {
        return new HtmlElem("tr",c);
    }

    public static HtmlElem td(Writable... c) {
        return new HtmlElem("td",c);
    }

    public static HtmlElem ul(Writable... c) {
        return new HtmlElem("ul",c);
    }

    public static HtmlElem li(Writable...c) {
        return new HtmlElem("li",c);
    }

    public static HtmlElem a(String href, String t) {
        return new HtmlElem("a", text(t))
                .withAttr("href", href);
    }

    public static HtmlElem aWithText(String href, String linkedText, String text) {
        return li(text(text), a(href,linkedText));
    }

    public static HtmlElem button(String value, String type, String buttonText) {
        return new HtmlElem("button", text(buttonText))
                .withAttr("type", type)
                .withAttr("value", value);
    }

    public static HtmlElem checkBox(String value, String type, String text, String name) {
        return new HtmlElem("input", text(text))
                .withAttr("type", type)
                .withAttr("value", value)
                .withAttr("name", name);
    }
}