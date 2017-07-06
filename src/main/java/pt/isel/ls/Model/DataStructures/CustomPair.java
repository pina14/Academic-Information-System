package pt.isel.ls.Model.DataStructures;

public class CustomPair<k,v> {
    private k key;
    private v value;

    public CustomPair(k key, v value) {
        this.key = key;
        this.value = value;
    }

    public k getKey() {
        return key;
    }

    public v getValue() {
        return value;
    }
}