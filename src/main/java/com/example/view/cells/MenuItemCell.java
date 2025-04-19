package com.example.view.cells;

import com.example.model.MenuItem;

import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class MenuItemCell extends ListCell<MenuItem> {
    @Override
    protected void updateItem(MenuItem item, boolean empty) {
        super.updateItem(item, empty);
        
        if (empty || item == null) {
            setGraphic(null);
        } else {
            HBox container = new HBox(10);
            
            Text nameText = new Text(item.getName());
            nameText.setStyle("-fx-font-weight: bold;");
            
            Text priceText = new Text(String.format("$%.2f", item.getPrice()));
            priceText.setStyle("-fx-fill: #2e8b57; -fx-font-weight: bold;");
            
            Text descriptionText = new Text(item.getDescription());
            descriptionText.setStyle("-fx-fill: #666; -fx-font-size: 0.9em;");
            
            TextFlow textFlow = new TextFlow(nameText, new Text(" - "), priceText, 
                new Text("\n"), descriptionText);
            
            container.getChildren().add(textFlow);
            setGraphic(container);
        }
    }
}