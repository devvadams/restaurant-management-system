package com.example.view.cells;

import com.example.model.RestaurantEnums;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class StatusCell<S, T extends RestaurantEnums.OrderStatus> extends TableCell<S, T> {
    private final Circle statusCircle = new Circle(8);

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        
        if (empty || item == null) {
            setGraphic(null);
            setText(null);
        } else {
            statusCircle.setFill(item.getColor());
            setGraphic(statusCircle);
            setText(item.toString());
            setContentDisplay(ContentDisplay.LEFT);
        }
    }
}