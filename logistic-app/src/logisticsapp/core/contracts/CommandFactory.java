package logisticsapp.core.contracts;

import logisticsapp.commands.contracts.Command;

public interface CommandFactory {

    Command createCommandFromCommandName(String commandTypeAsString);

}