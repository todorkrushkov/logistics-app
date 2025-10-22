package logisticsapp.models.dtos;

import java.util.List;

public class CustomerDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private List<Integer> packageIds;

    public CustomerDTO() {
        // Required no-arg constructor for Jackson
    }

    public CustomerDTO(int id, String firstName, String lastName, String phoneNum, List<Integer> packageIds) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNum(phoneNum);
        setPackageIds(packageIds);
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    private void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public List<Integer> getPackageIds() {
        return packageIds;
    }

    private void setPackageIds(List<Integer> packageIds) {
        this.packageIds = packageIds;
    }
}
