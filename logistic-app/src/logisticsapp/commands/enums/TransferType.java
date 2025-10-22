package logisticsapp.commands.enums;

import logisticsapp.core.exceptions.InvalidUserInputException;

public enum TransferType {
    RECEIVE, SENT;

    @Override
    public String toString() {
         switch (this) {
             case RECEIVE -> {
                 return "receive";
             }
             case SENT -> {
                 return "sent";
             }
             default -> throw new InvalidUserInputException("");
         }
    }
}
