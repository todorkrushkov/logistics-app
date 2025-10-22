package logisticsapp.commands.show;

import logisticsapp.commands.contracts.Command;
import logisticsapp.commands.enums.TransferType;
import logisticsapp.core.contracts.repositories.CustomerRepository;
import logisticsapp.core.services.PrintModelInfoService;
import logisticsapp.models.contracts.Customer;
import logisticsapp.utils.helpers.ParsingHelper;
import logisticsapp.utils.helpers.ValidationHelper;

import java.util.List;

public class ShowCustomerInfoCommand implements Command {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private static final String TRANSFER_TYPE_ERR = "Transfer type must be RECEIVE or SENT.";

    private final CustomerRepository customerRepository;
    private final PrintModelInfoService printModelInfoService;

    private Customer customer;
    private TransferType transferType;

    public ShowCustomerInfoCommand (PrintModelInfoService printModelInfoService, CustomerRepository customerRepository) {
        this.printModelInfoService = printModelInfoService;
        this.customerRepository = customerRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        parseParameters(parameters);

        return printModelInfoService.printCustomerInfo(customer, transferType);
    }

    private void parseParameters(List<String> parameters) {
        String phoneNumber = parameters.get(0);

        customer = customerRepository.findCustomerByPhone(phoneNumber);

        transferType = ParsingHelper.tryParseEnum(parameters.get(1), TransferType.class, TRANSFER_TYPE_ERR);
    }
}