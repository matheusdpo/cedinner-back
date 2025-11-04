package br.com.matheusdpo.cedinner.infrastructure.persistence.entities;

public enum OrderStatusJpa {
    PENDING,
    CONFIRMED,
    PREPARING,
    READY_FOR_DELIVERY,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED
}
