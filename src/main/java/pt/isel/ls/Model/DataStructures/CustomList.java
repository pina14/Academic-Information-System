package pt.isel.ls.Model.DataStructures;

import pt.isel.ls.Model.Validators.ErrorValidator;

import java.util.ArrayList;

public class CustomList <T> {
    private ArrayList<T> genericList = new ArrayList<>();

    public void add(T value){
        if(value != null)
            genericList.add(value);
    }

    public T get(int idx){
        return genericList.get(idx);
    }

    public int size(){
        return genericList.size();
    }

    /**
     * Get the element on the index idx in the boolean form if possible.
     * @param idx Index.
     * @return The element in the index idx in boolean form.
     */
    public Boolean getBoolean(int idx) {
        String valueString =  genericList.get(idx).toString();
        if(!isBoolean(valueString))
            return null;
        return Boolean.valueOf(valueString);
    }

    /**
     *  Get the element on the index idx in the boolean form if possible, adding errors if its not possible.
     * @param idx Index.
     * @param errorValidator Object that will contain this validation errors.
     * @param errorID  Error ID to be added to the container.
     * @return  The element in the index idx in boolean form.
     */
    public Boolean getVerifiedBoolean(int idx, ErrorValidator errorValidator, String errorID){
        String valueString =  genericList.get(idx).toString();
        if(valueString.equals("")){
            errorValidator.addError(errorID, "Must be filled.");
            return null;
        }
        Boolean value = getBoolean(idx);
        if(value == null)
            errorValidator.addError(errorID, "Must be a Boolean.");
        return value;
    }

    /**
     * Get the element on the index idx in the Integer form if possible.
     * @param idx Index.
     * @return The element in the index idx in Integer form.
     */
    public Integer getInt(int idx) {
        String valueString =  genericList.get(idx).toString();
        if(!isNumeric(valueString))
            return null;
        return Integer.parseInt(valueString);
    }

    /**
     *  Get the element on the index idx in the Integer form if possible, adding errors if its not possible.
     * @param idx Index.
     * @param errorValidator Object that will contain this validation errors.
     * @param errorID  Error ID to be added to the container.
     * @return  The element in the index idx in Integer form.
     */
    public Integer getVerifiedInt(int idx, ErrorValidator errorValidator, String errorID) {
        String valueString =  genericList.get(idx).toString();
        if(valueString.equals("")) {
            errorValidator.addError(errorID, "Must be filled.");
            return null;
        }
        Integer value = getInt(idx);
        if(value == null)
            errorValidator.addError(errorID, "Must be an Integer.");
        return value;
    }

    /**
     * Get the element on the index idx in the String form if possible.
     * @param idx Index.
     * @return The element in the index idx in String form.
     */
    public String getString(int idx) {
        String valueString = genericList.get(idx).toString();
        if(isNumeric(valueString) || isBoolean(valueString))
            return null;
        return valueString;
    }

    /**
     *  Get the element on the index idx in the String form if possible, adding errors if its not possible.
     * @param idx Index.
     * @param errorValidator Object that will contain this validation errors.
     * @param errorID  Error ID to be added to the container.
     * @return  The element in the index idx in String form.
     */
    public String getVerifiedString(int idx, ErrorValidator errorValidator, String errorID) {
        String valueString =  genericList.get(idx).toString();
        if(valueString.equals("")){
            errorValidator.addError(errorID, "Must be filled.");
            return null;
        }

        String value = getString(idx);
        if(value == null)
            errorValidator.addError(errorID, "Must be a text.");
        return value;
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