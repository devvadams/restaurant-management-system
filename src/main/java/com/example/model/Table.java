package com.example.model;

import com.example.model.RestaurantEnums.TableStatus;

public class Table {
    private final int tableNumber;
    private final TableStatus status;
    private Order currentOrder;

    public Table(int tableNumber, TableStatus status) {
        this.tableNumber = tableNumber;
        this.status = status;
    }

    public int getTableNumber() { return tableNumber; }
    public TableStatus getStatus() { return status; }
    public Order getCurrentOrder() { return currentOrder; }
    public void setCurrentOrder(Order order) { this.currentOrder = order; }
}