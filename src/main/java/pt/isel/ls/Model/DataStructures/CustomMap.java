package pt.isel.ls.Model.DataStructures;

import java.util.HashMap;

public class CustomMap <K, V> {
    private HashMap<K, V> hashmap = new HashMap<>();


    public void put(K key, V value){
        hashmap.put(key, value);
    }

    public V get(K key){
        return hashmap.get(key);
    }

    public boolean containsKey(K key){
        return hashmap.containsKey(key);
    }

    public int size() {
        return hashmap.size();
    }

    /**
     * Get the element with the key key in Integer form if possible.
     * @param key Key.
     * @return Integer form of the element.
     */
    public Integer getInt(K key) {
        V value = hashmap.get(key);

        if(value == null)
            return null;

        String valueString =  value.toString();
        if(!isNumeric(valueString))
            return null;

        return Integer.parseInt(valueString);
    }

    /**
     * Get the element with the key key in String form if possible.
     * @param key Key.
     * @return String form of the element.
     */
    public String getString(K key) {
        V value = hashmap.get(key);

        if(value == null)
            return null;

        String valueString = value.toString();
        if(isNumeric(valueString) || isBoolean(valueString) || valueString.equals(""))
            return null;

        return valueString;
    }

    /**
     * Verify if String passed in parameters can be an Integer.
     * @param number String to be verified
     * @return True if the number is a valid number.
     */
    public boolean isNumeric(String number) {
        if(number.equals(""))
            return false;

        for (int i = 0; i < number.length(); i++) {
            if(number.charAt(i) < '0' || number.charAt(i) > '9')
                return false;
        }
        return true;
    }

    /**
     * Verify if String passed in parameter can be a boolean.
     * @param bool String to be verified
     * @return  True if the number is a valid boolean.
     */
    public boolean isBoolean(String bool) {
        if(bool.equals(""))
            return false;
        return bool.equalsIgnoreCase("true") || bool.equalsIgnoreCase("false");
    }

}