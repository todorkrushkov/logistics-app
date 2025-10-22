package logisticsapp.utils.helpers;

import logisticsapp.core.exceptions.InvalidUserInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParsingHelperTests {

    enum SampleEnum {
        FIRST, SECOND, THIRD
    }

    @Test
    public void tryParseDouble_ShouldReturnDouble_WhenValid() {
        double result = ParsingHelper.tryParseDouble("3.14", "radius");
        assertEquals(3.14, result);
    }

    @Test
    public void tryParseDouble_ShouldThrow_WhenInvalid() {
        InvalidUserInputException ex = assertThrows(
                InvalidUserInputException.class,
                () -> ParsingHelper.tryParseDouble("pi", "radius")
        );
        assertEquals("Invalid value for radius. Should be a number.", ex.getMessage());
    }

    @Test
    public void tryParseInteger_ShouldReturnInt_WhenValid() {
        int result = ParsingHelper.tryParseInteger("42", "age");
        assertEquals(42, result);
    }

    @Test
    public void tryParseInteger_ShouldThrow_WhenInvalid() {
        InvalidUserInputException ex = assertThrows(
                InvalidUserInputException.class,
                () -> ParsingHelper.tryParseInteger("forty-two", "age")
        );
        assertEquals("Invalid value for age. Should be a number.", ex.getMessage());
    }

    @Test
    public void tryParseBoolean_ShouldReturnTrue_WhenInputIsTrue() {
        assertTrue(ParsingHelper.tryParseBoolean("true", "flag"));
        assertTrue(ParsingHelper.tryParseBoolean("TrUe", "flag"));
    }

    @Test
    public void tryParseBoolean_ShouldReturnFalse_WhenInputIsFalse() {
        assertFalse(ParsingHelper.tryParseBoolean("false", "flag"));
        assertFalse(ParsingHelper.tryParseBoolean("FaLsE", "flag"));
    }

    @Test
    public void tryParseBoolean_ShouldThrow_WhenInvalid() {
        InvalidUserInputException ex = assertThrows(
                InvalidUserInputException.class,
                () -> ParsingHelper.tryParseBoolean("maybe", "flag")
        );
        assertEquals("Invalid value for flag. Should be one of 'true' or 'false'.", ex.getMessage());
    }

    @Test
    public void tryParseEnum_ShouldReturnCorrectEnum_WhenValid() {
        SampleEnum result = ParsingHelper.tryParseEnum("first", SampleEnum.class, "Invalid enum: %s");
        assertEquals(SampleEnum.FIRST, result);
    }

    @Test
    public void tryParseEnum_ShouldThrow_WhenInvalid() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> ParsingHelper.tryParseEnum("unknown", SampleEnum.class, "Invalid enum: %s")
        );
        assertEquals("Invalid enum: unknown", ex.getMessage());
    }
}
