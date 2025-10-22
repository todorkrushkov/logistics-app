package logisticsapp.utils.helpers;

import logisticsapp.core.exceptions.InvalidUserInputException;

import static java.lang.String.format;

public class ParsingHelper {

    private static final String INVALID_NUMBER_FIELD_ERR = "Invalid value for %s. Should be a number.";
    private static final String INVALID_BOOLEAN_FIELD_ERR = "Invalid value for %s. Should be one of 'true' or 'false'.";

    public static double tryParseDouble(String valueToParse, String parameterName) {
        try {
            return Double.parseDouble(valueToParse);
        } catch (NumberFormatException e) {
            throw new InvalidUserInputException(format(INVALID_NUMBER_FIELD_ERR, parameterName));
        }
    }

    public static int tryParseInteger(String valueToParse, String parameterName) {
        try {
            return Integer.parseInt(valueToParse);
        } catch (NumberFormatException e) {
            throw new InvalidUserInputException(format(INVALID_NUMBER_FIELD_ERR, parameterName));
        }
    }

    public static boolean tryParseBoolean(String valueToParse, String parameterName) {
        if (!valueToParse.equalsIgnoreCase("true") &&
            !valueToParse.equalsIgnoreCase("false")) {
            throw new InvalidUserInputException(format(INVALID_BOOLEAN_FIELD_ERR, parameterName));
        }

        return Boolean.parseBoolean(valueToParse);
    }

    public static <E extends Enum<E>> E tryParseEnum(String valueToParse, Class<E> type, String errorMessage) {
        try {
            return Enum.valueOf(type, valueToParse.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(format(errorMessage, valueToParse));
        }
    }
}