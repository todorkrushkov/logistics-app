package logisticsapp.utils.helpers;

import logisticsapp.core.exceptions.ElementNotFoundException;
import logisticsapp.core.exceptions.InvalidUserInputException;

import java.util.List;

import static java.lang.String.format;

public class ValidationHelper {

    public static final String INVALID_NUMBER_OF_ARGUMENTS_ERR = "Invalid number of arguments. Expected: %d, Received: %d";
    public static final String INVALID_MINIMUM_NUMBER_OF_ARGUMENTS_ERR = "Invalid number of arguments. Expected minimum: 2, Received: %d";

    public static void validateValueInRange(double value, double min, double max, String errorMessage) {
        if (value < min || value > max) {
            throw new InvalidUserInputException(errorMessage);
        }
    }

    public static void validateStringLength(String stringToValidate, int minLength, int maxLength, String errorMessage) {
        validateValueInRange(stringToValidate.length(), minLength, maxLength, errorMessage);
    }

    public static void validateArgumentsCount(List<String> list, int expectedArgumentsCount) {
        if (list.size() < expectedArgumentsCount || list.size() > expectedArgumentsCount) {
            throw new InvalidUserInputException(format(INVALID_NUMBER_OF_ARGUMENTS_ERR, expectedArgumentsCount, list.size()));
        }
    }

    public static void validateMinimumArgumentsCount(List<String> list, int minimumExpectedArgumentsCount) {
        if (list.size() < minimumExpectedArgumentsCount) {
            throw new InvalidUserInputException(format(INVALID_MINIMUM_NUMBER_OF_ARGUMENTS_ERR, list.size()));
        }
    }

    public static <T> void validateNotNull(T object, String errorMessage) {
        if (object == null) {
            throw new ElementNotFoundException(errorMessage);
        }
    }

    public static void validateNotEmpty(List<?> list, String errorMessage) {
        if (list == null || list.isEmpty()) {
            throw new ElementNotFoundException(errorMessage);
        }
    }
}