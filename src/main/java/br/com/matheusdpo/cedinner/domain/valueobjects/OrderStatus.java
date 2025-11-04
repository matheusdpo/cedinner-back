package br.com.matheusdpo.cedinner.domain.valueobjects;

public enum OrderStatus {
    PENDING("Pending"),
    CONFIRMED("Confirmed"),
    PREPARING("Preparing"),
    READY_FOR_DELIVERY("Ready for Delivery"),
    OUT_FOR_DELIVERY("Out for Delivery"),
    DELIVERED("Delivered"),
    CANCELLED("Cancelled");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFinalStatus() {
        return this == DELIVERED || this == CANCELLED;
    }

    public boolean canTransitionTo(OrderStatus newStatus) {
        switch (this) {
            case PENDING:
                return newStatus == CONFIRMED || newStatus == CANCELLED;
            case CONFIRMED:
                return newStatus == PREPARING || newStatus == CANCELLED;
            case PREPARING:
                return newStatus == READY_FOR_DELIVERY || newStatus == CANCELLED;
            case READY_FOR_DELIVERY:
                return newStatus == OUT_FOR_DELIVERY || newStatus == CANCELLED;
            case OUT_FOR_DELIVERY:
                return newStatus == DELIVERED || newStatus == CANCELLED;
            case DELIVERED:
            case CANCELLED:
                return false;
            default:
                return false;
        }
    }
}
