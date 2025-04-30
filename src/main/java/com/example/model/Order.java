package com.example.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

import com.example.model.RestaurantEnums.OrderStatus;

public class Order {
    private final SimpleIntegerProperty id;
    private final ObservableList<MenuItem> items;
    private final ObjectProperty<OrderStatus> status;
    private final int tableNumber;

    public Order(int id, List<MenuItem> items, int tableNumber) {
        this.id = new SimpleIntegerProperty(id);
        this.items = FXCollections.observableArrayList(items);
        this.status = new SimpleObjectProperty<>(OrderStatus.RECEIVED);
        this.tableNumber = tableNumber;
    }

    // Getters
    public int getId() { return id.get(); }
    public ObservableList<MenuItem> getItems() { return items; }
    public OrderStatus getStatus() { return status.get(); }
    public int getTableNumber() { return tableNumber; }

    // Property getters
    public SimpleIntegerProperty idProperty() { return id; }
    public ObjectProperty<OrderStatus> statusProperty() { return status; }

    // Setter
    public void setStatus(OrderStatus status) {
        this.status.set(status);
    }
}