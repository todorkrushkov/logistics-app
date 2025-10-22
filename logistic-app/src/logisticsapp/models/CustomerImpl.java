package logisticsapp.models;

import logisticsapp.models.contracts.Customer;
import logisticsapp.models.contracts.DeliveryPackage;
import logisticsapp.utils.helpers.ValidationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

public class CustomerImpl implements Customer {
    public static final int FIRST_NAME_MIN_LENGTH = 3;
    public static final int FIRST_NAME_MAX_LENGTH = 20;
    private static final String FIRST_NAME_LENGTH_ERR = format(
            "First name should be between %d and %d symbols.",
            FIRST_NAME_MIN_LENGTH,
            FIRST_NAME_MAX_LENGTH);

    public static final int LAST_NAME_MIN_LENGTH = 3;
    public static final int LAST_NAME_MAX_LENGTH = 30;
    private static final String LAST_NAME_LENGTH_ERR = format(
            "Last name should be between %d and %d symbols.",
            LAST_NAME_MIN_LENGTH,
            LAST_NAME_MAX_LENGTH);

    public static final int PHONE_NUM_LENGTH = 10;
    private static final String PHONE_NUM_LENGTH_ERR = format(
            "Phone number must be %d symbols.",
            PHONE_NUM_LENGTH);

    private int id;
    private String firstName;
    private String lastName;
    private String phoneNum;

    private final List<DeliveryPackage> deliveryPackages;

    public CustomerImpl(int id, String firstName, String lastName, String phoneNum) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNum(phoneNum);
        deliveryPackages = new ArrayList<>();
    }

    @Override
    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    private void setFirstName(String firstName) {
        ValidationHelper.validateStringLength(
                firstName,
                FIRST_NAME_MIN_LENGTH,
                FIRST_NAME_MAX_LENGTH,
                FIRST_NAME_LENGTH_ERR);

        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    private void setLastName(String lastName) {
        ValidationHelper.validateStringLength(
                lastName,
                LAST_NAME_MIN_LENGTH,
                LAST_NAME_MAX_LENGTH,
                LAST_NAME_LENGTH_ERR);

        this.lastName = lastName;
    }

    @Override
    public String getPhoneNum() {
        return phoneNum;
    }

    private void setPhoneNum(String phoneNum) {
        if (phoneNum.length() != PHONE_NUM_LENGTH) {
            throw new IllegalArgumentException(PHONE_NUM_LENGTH_ERR);
        }
        this.phoneNum = phoneNum;
    }

    @Override
    public List<DeliveryPackage> getDeliveryPackage() {
        return new ArrayList<>(deliveryPackages);
    }

    @Override
    public void addDeliveryPackage(DeliveryPackage deliveryPackage) {
        deliveryPackages.add(deliveryPackage);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CustomerImpl customer = (CustomerImpl) o;
        return id == customer.id
                && Objects.equals(firstName, customer.firstName)
                && Objects.equals(lastName, customer.lastName)
                && Objects.equals(phoneNum, customer.phoneNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, phoneNum);
    }

    @Override
    public String printInfo() {
        return format(
                "-> Customer %d%n" +
                        "   - Name: %s %s%n" +
                        "   - Phone number: %s%n",
                getId(),
                getFirstName(),
                getLastName(),
                getPhoneNum()
        );
    }
}
