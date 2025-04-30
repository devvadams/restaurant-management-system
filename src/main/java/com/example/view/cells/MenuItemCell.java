package com.example.view.cells;

import com.example.model.MenuItem;
import javafx.scene.control.ListCell;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MenuItemCell extends ListCell<MenuItem> {
    @Override
    protected void updateItem(MenuItem item, boolean empty) {
        super.updateItem(item, empty);
        
        if (empty || item == null) {
            setGraphic(null);
            setText(null);
        } else {
            VBox container = new VBox(5);
            Label nameLabel = new Label(item.getName());
            nameLabel.setStyle("-fx-font-weight: bold;");
            
            Label descLabel = new Label(item.getDescription());
            descLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 0.9em;");
            
            HBox bottomRow = new HBox(10);
            Label priceLabel = new Label(String.format("$%.2f", item.getPrice()));
            priceLabel.setStyle("-fx-text-fill: #2e8b57; -fx-font-weight: bold;");
            
            HBox tags = new HBox(5);
            if (item.isVegetarian()) {
                Label vegLabel = new Label("🌱 Vegetarian");
                vegLabel.setStyle("-fx-text-fill: #2e8b57; -fx-font-size: 0.8em;");
                tags.getChildren().add(vegLabel);
            }
            if (item.isSpicy()) {
                Label spicyLabel = new Label("🌶️ Spicy");
                spicyLabel.setStyle("-fx-text-fill: #d32f2f; -fx-font-size: 0.8em;");
                tags.getChildren().add(spicyLabel);
            }
            
            bottomRow.getChildren().addAll(priceLabel, tags);
            container.getChildren().addAll(nameLabel, descLabel, bottomRow);
            setGraphic(container);
            setText(null); // Clear text to only show graphic
        }
    }
}