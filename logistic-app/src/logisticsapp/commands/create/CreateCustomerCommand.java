package logisticsapp.commands.create;

import logisticsapp.commands.CommandsConstants;
import logisticsapp.commands.contracts.Command;
import logisticsapp.core.services.CreateModelService;
import logisticsapp.models.contracts.Customer;
import logisticsapp.utils.helpers.ValidationHelper;

import java.util.List;

import static java.lang.String.format;

public class CreateCustomerCommand implements Command {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;
    private static final String ITEM = "Customer";

    private final CreateModelService createModelService;

    private String firstName;
    private String lastName;
    private String phoneNum;

    public CreateCustomerCommand(CreateModelService createModelService) {
        this.createModelService = createModelService;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        parseParameters(parameters);

        Customer createdCustomer = createModelService.createCustomer(firstName, lastName, phoneNum);

        return format(CommandsConstants.ITEM_CREATED_MESSAGE, ITEM, createdCustomer.getId());
    }

    private void parseParameters(List<String> parameters) {
        firstName = parameters.get(0);
        lastName = parameters.get(1);
        phoneNum = parameters.get(2);
    }
}
