package logisticsapp.utils.helpers;

import logisticsapp.core.exceptions.ElementNotFoundException;
import logisticsapp.core.exceptions.InvalidUserInputException;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationHelperTests {

    @Test
    public void validateValueInRange_ShouldPass_WhenValueIsWithinRange() {
        assertDoesNotThrow(() ->
                ValidationHelper.validateValueInRange(5.0, 1.0, 10.0, "Out of range!"));
    }

    @Test
    public void validateValueInRange_ShouldThrow_WhenValueIsTooLow() {
        InvalidUserInputException ex = assertThrows(
                InvalidUserInputException.class,
                () -> ValidationHelper.validateValueInRange(0.5, 1.0, 10.0, "Out of range!")
        );
        assertEquals("Out of range!", ex.getMessage());
    }

    @Test
    public void validateValueInRange_ShouldThrow_WhenValueIsTooHigh() {
        InvalidUserInputException ex = assertThrows(
                InvalidUserInputException.class,
                () -> ValidationHelper.validateValueInRange(15.0, 1.0, 10.0, "Out of range!")
        );
        assertEquals("Out of range!", ex.getMessage());
    }

    @Test
    public void validateStringLength_ShouldPass_WhenLengthIsWithinRange() {
        assertDoesNotThrow(() ->
                ValidationHelper.validateStringLength("hello", 2, 10, "Invalid length"));
    }

    @Test
    public void validateStringLength_ShouldThrow_WhenTooShort() {
        InvalidUserInputException ex = assertThrows(
                InvalidUserInputException.class,
                () -> ValidationHelper.validateStringLength("a", 2, 5, "Invalid length")
        );
        assertEquals("Invalid length", ex.getMessage());
    }

    @Test
    public void validateStringLength_ShouldThrow_WhenTooLong() {
        InvalidUserInputException ex = assertThrows(
                InvalidUserInputException.class,
                () -> ValidationHelper.validateStringLength("longerthanexpected", 2, 5, "Invalid length")
        );
        assertEquals("Invalid length", ex.getMessage());
    }

    @Test
    public void validateArgumentsCount_ShouldPass_WhenSizeMatches() {
        assertDoesNotThrow(() ->
                ValidationHelper.validateArgumentsCount(List.of("arg1", "arg2"), 2));
    }

    @Test
    public void validateArgumentsCount_ShouldThrow_WhenTooFew() {
        InvalidUserInputException ex = assertThrows(
                InvalidUserInputException.class,
                () -> ValidationHelper.validateArgumentsCount(List.of("onlyOne"), 2)
        );
        assertEquals("Invalid number of arguments. Expected: 2, Received: 1", ex.getMessage());
    }

    @Test
    public void validateArgumentsCount_ShouldThrow_WhenTooMany() {
        InvalidUserInputException ex = assertThrows(
                InvalidUserInputException.class,
                () -> ValidationHelper.validateArgumentsCount(List.of("1", "2", "3"), 2)
        );
        assertEquals("Invalid number of arguments. Expected: 2, Received: 3", ex.getMessage());
    }

    @Test
    public void validateMinimumArgumentsCount_ShouldPass_WhenEnoughArguments() {
        assertDoesNotThrow(() ->
                ValidationHelper.validateMinimumArgumentsCount(List.of("1", "2", "3"), 2));
    }

    @Test
    public void validateMinimumArgumentsCount_ShouldThrow_WhenNotEnough() {
        InvalidUserInputException ex = assertThrows(
                InvalidUserInputException.class,
                () -> ValidationHelper.validateMinimumArgumentsCount(List.of("onlyOne"), 2)
        );
        assertEquals("Invalid number of arguments. Expected minimum: 2, Received: 1", ex.getMessage());
    }

    @Test
    public void validateNotNull_ShouldPass_WhenObjectIsNotNull() {
        assertDoesNotThrow(() ->
                ValidationHelper.validateNotNull("not null", "Object is null"));
    }

    @Test
    public void validateNotNull_ShouldThrow_WhenNull() {
        ElementNotFoundException ex = assertThrows(
                ElementNotFoundException.class,
                () -> ValidationHelper.validateNotNull(null, "Object is null")
        );
        assertEquals("Object is null", ex.getMessage());
    }

    @Test
    public void validateNotEmpty_ShouldPass_WhenListIsNotEmpty() {
        assertDoesNotThrow(() ->
                ValidationHelper.validateNotEmpty(List.of("item"), "List is empty"));
    }

    @Test
    public void validateNotEmpty_ShouldThrow_WhenListIsEmpty() {
        ElementNotFoundException ex = assertThrows(
                ElementNotFoundException.class,
                () -> ValidationHelper.validateNotEmpty(Collections.emptyList(), "List is empty")
        );
        assertEquals("List is empty", ex.getMessage());
    }

    @Test
    public void validateNotEmpty_ShouldThrow_WhenListIsNull() {
        ElementNotFoundException ex = assertThrows(
                ElementNotFoundException.class,
                () -> ValidationHelper.validateNotEmpty(null, "List is null or empty")
        );
        assertEquals("List is null or empty", ex.getMessage());
    }
}