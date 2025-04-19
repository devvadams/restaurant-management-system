package com.example.model;

public class Table {
    private final int tableNumber;
    private RestaurantEnums.TableStatus status;
    private Order currentOrder;

    public Table(int tableNumber, RestaurantEnums.TableStatus status) {
        this.tableNumber = tableNumber;
        this.status = status;
    }

    // Getters and setters
    public int getTableNumber() {
        return tableNumber;
    }

    public RestaurantEnums.TableStatus getStatus() {
        return status;
    }

    public void setStatus(RestaurantEnums.TableStatus status) {
        this.status = status;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }
}