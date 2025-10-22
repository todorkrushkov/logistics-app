package logisticsapp.commands;

import logisticsapp.commands.contracts.Command;
import logisticsapp.core.contracts.repositories.DeliveryPackageRepository;
import logisticsapp.core.enums.State;
import logisticsapp.core.services.ChangeStateService;
import logisticsapp.models.contracts.DeliveryPackage;
import logisticsapp.utils.helpers.ParsingHelper;
import logisticsapp.utils.helpers.ValidationHelper;

import java.util.List;

import static java.lang.String.format;

public class ChangeStateCommand implements Command {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    public static final String INVALID_STATE_ERR = "Invalid state";
    public static final String DELIVERY_PACKAGE_ID_PARAMETER = "Delivery Package ID";
    public static final String CHANGE_STATE_MESSAGE = "State of Delivery Package %d changed to %s";

    private final ChangeStateService changeStateService;

    private State state;
    private int deliveryPackageId;

    public ChangeStateCommand(ChangeStateService changeStateService) {
        this.changeStateService = changeStateService;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        parseParameters(parameters);

        DeliveryPackage deliveryPackage = changeStateService.changeState(deliveryPackageId, state);

        return format(CHANGE_STATE_MESSAGE, deliveryPackageId, deliveryPackage.getState());
    }

    private void parseParameters(List<String> parameters) {
        deliveryPackageId = ParsingHelper.tryParseInteger(parameters.get(0), DELIVERY_PACKAGE_ID_PARAMETER);
        state = ParsingHelper.tryParseEnum(parameters.get(1), State.class, INVALID_STATE_ERR);
    }
}
