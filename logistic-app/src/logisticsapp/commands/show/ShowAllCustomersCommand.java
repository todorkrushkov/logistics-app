package logisticsapp.commands.show;

import logisticsapp.commands.contracts.Command;
import logisticsapp.core.contracts.repositories.CustomerRepository;
import logisticsapp.core.services.PrintModelInfoService;
import logisticsapp.utils.helpers.ValidationHelper;

import java.util.List;

public class ShowAllCustomersCommand implements Command {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;

    private final PrintModelInfoService printModelInfoService;
    private final CustomerRepository customerRepository;

    public ShowAllCustomersCommand(PrintModelInfoService printModelInfoService, CustomerRepository customerRepository) {
        this.printModelInfoService = printModelInfoService;
        this.customerRepository = customerRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        return printModelInfoService.printAllCustomers(customerRepository.getCustomers());
    }
}
