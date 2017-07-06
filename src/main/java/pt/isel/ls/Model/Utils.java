package pt.isel.ls.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Model.CustomExceptions.FormatMismatchException;
import pt.isel.ls.Model.CustomExceptions.HeadersMismatchException;
import pt.isel.ls.Model.DataStructures.CustomList;
import pt.isel.ls.Model.DataStructures.CustomMap;

public class Utils {
    public static CustomMap<String, String> semMap = new CustomMap<>();
    private static final Logger _logger = LoggerFactory.getLogger(Utils.class);
    static {
        semMap.put("summer", "v");
        semMap.put("winter", "i");
    }

    /**
     * Remove the spaces of the command requested to reduce users mistakes
     * @param strings Various strings.
     * @return CustomList<String> that contains all strings that weren't empty strings.
     * @throws FormatMismatchException
     */
    public static CustomList<String> filterSpaces(String[] strings) throws FormatMismatchException {
        _logger.info("Beginning to filter the spaces in the input of the user.");

        CustomList<String> ret = new CustomList<>();
        for (String component : strings) {
            if (!component.equals("")) {
                ret.add(component);
                if (ret.size() > 4)
                    throw new FormatMismatchException(component);
            }
        }
        if (ret.size() < 2)
            throw new FormatMismatchException(ret.toString());

        _logger.info("Finishing with success to remove the spaces in the user input.");
        return ret;
    }

    /**
     * Decode the string headers and puts it in a hash map containing the "key" for example accept and "value" as text/html.
     * @param headers String that is composed by a sequence of "name-value" pairs, where each pair is separated by the '|' character
     * @return HashMap with the parameters in their correct form.
     */
    public static CustomMap<String, String> organizeHeaders(String headers) throws HeadersMismatchException {
        _logger.info("Beginning to organize the headers in the input of the user.");
        /* If its empty or null then do nothing. */
        if (headers == null || headers.equals(""))
            return new CustomMap<>();
        /* Initiate variables. */
        String[] aux, separatedHeaders = headers.split("\\|");
        String key, value;
        CustomMap<String, String> hashmap = new CustomMap<>();

        /* For each header resultant from the split on character '|' insert in the map its key and value. */
        for (int i = 0; i < separatedHeaders.length; i++) {
            aux = separatedHeaders[i].split(":");
            if (aux.length != 2)
                throw new HeadersMismatchException();
            key = aux[0];
            value = aux[1];
            hashmap.put(key, value);
        }

        _logger.info("Finishing with success to organize the headers in the user input.");
        return hashmap;
    }

    /**
     * Decodes the string parameters and puts it in a hash map containing the "key" for example name and "value" as Manel.
     * @param parameters String  that is a sequence of "name=value" pairs, separated by '&'.
     * @return HashMap with the parameters in their correct form.
     */
    public static CustomMap<String, CustomList<String>> organizeParameters(String parameters) {
        _logger.info("Beginning to organize the parameters in the input of the user.");
         /* If its empty or null then do nothing. */
        if (parameters == null || parameters.equals(""))
            return null;
         /* Initiate variables. */
        String[] aux;
        String key;
        CustomList<String> value;
        parameters = parameters.replace("+", " ");
        String[] separatedParams = parameters.split("&");
        CustomMap<String, CustomList<String>> hashmap = new CustomMap<>();

        /* For each parameter separated previously by the character '&' adds the map. */
        for (int i = 0; i < separatedParams.length; i++) {
            aux = separatedParams[i].split("=");
            if (aux.length != 2)
                continue;
            key = aux[0];
            /* If the parameter key already exists in the map adds its value to its custom list. */
            if(hashmap.containsKey(key)) {
                CustomList<String> accumValue = hashmap.get(key);
                accumValue.add(aux[1]);
                continue;
            }
            value = new CustomList<>();
            value.add(aux[1]);
            hashmap.put(key, value);
        }

        _logger.info("Finishing with success to organize the parameters of the command in the user input.");
        return hashmap;
    }
}