package logisticsapp.commands.saveload;

import logisticsapp.commands.contracts.Command;
import logisticsapp.core.services.HistoryService;
import logisticsapp.utils.helpers.ValidationHelper;

import java.util.List;

public class SaveHistoryCommand implements Command {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;
    private static final String FILE_PATH = "./data/history.json";
    private static final String HISTORY_SAVED_MESSAGE = "History saved successfully.";

    private final HistoryService historyService;

    public SaveHistoryCommand(HistoryService historyService) {
        this.historyService = historyService;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        historyService.save(FILE_PATH);

        return HISTORY_SAVED_MESSAGE;
    }
}
