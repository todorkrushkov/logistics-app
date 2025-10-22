package logisticsapp.core.enums;

public enum State {
    ACCEPTED,
    ON_ROUTE,
    DELIVERED;

    @Override
    public String toString() {
        switch (this) {
            case ACCEPTED -> {
                return "Accepted";
            }
            case ON_ROUTE -> {
                return "On route";
            }
            case DELIVERED -> {
                return "Delivered";
            }
            default -> throw new IllegalArgumentException();
        }
    }
}
