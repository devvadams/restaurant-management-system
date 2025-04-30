package com.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.Arrays;

import com.example.model.Order;
import com.example.model.RestaurantEnums;
import com.example.model.Table;
import com.example.model.MenuItem;
import com.example.view.cells.MenuItemCell;
import com.example.view.cells.StatusCell;

public class RestaurantDashboard extends Application {

    private ObservableList<Table> tables = FXCollections.observableArrayList();
    private ObservableList<Order> orders = FXCollections.observableArrayList();
    private ObservableList<MenuItem> menuItems = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        initializeSampleData();
        
        TabPane tabPane = new TabPane();
        
        // Tables Tab
        Tab tablesTab = new Tab("Tables", createTablesGrid());
        tablesTab.setClosable(false);
        
        // Orders Tab
        Tab ordersTab = new Tab("Orders", createOrdersTable());
        ordersTab.setClosable(false);
        
        // Menu Tab (using enhanced view)
        Tab menuTab = new Tab("Menu", createEnhancedMenuView());
        menuTab.setClosable(false);

        tabPane.getTabs().addAll(tablesTab, ordersTab, menuTab);

        Scene scene = new Scene(tabPane, 800, 600);
        // Fixed CSS path (assuming it's in src/main/resources/css/)
        scene.getStylesheets().add(getClass().getResource("restaurant.css").toExternalForm());
        stage.setTitle("Restaurant Management System");
        stage.setScene(scene);
        stage.show();
    }

    

    private void initializeSampleData() {
        // Create tables
        for (int i = 1; i <= 12; i++) {
            tables.add(new Table(i, RestaurantEnums.TableStatus.values()[i % 4]));
        }

        // Create menu items
        MenuItem bruschetta = MenuItem.createVegetarian(
            "Bruschetta", 
            "Toasted bread with tomatoes, garlic and basil", 
            8.99, 
            RestaurantEnums.MenuCategory.APPETIZER
        );

        MenuItem calamari = new MenuItem(
            "Calamari",
            "Fried squid with marinara sauce",
            12.99,
            RestaurantEnums.MenuCategory.APPETIZER
        );

        MenuItem steak = new MenuItem(
            "Steak",
            "12oz ribeye with mashed potatoes",
            24.99,
            RestaurantEnums.MenuCategory.MAIN_COURSE
        );

        MenuItem pasta = MenuItem.createSpicy(
            "Spicy Arrabiata",
            "Pasta with spicy tomato sauce",
            16.99,
            RestaurantEnums.MenuCategory.MAIN_COURSE
        );

        MenuItem tiramisu = MenuItem.createVegetarian(
            "Tiramisu",
            "Classic Italian dessert",
            7.99,
            RestaurantEnums.MenuCategory.DESSERT
        );

        MenuItem soda = MenuItem.createVegetarian(
            "Soda",
            "Various flavors",
            2.99,
            RestaurantEnums.MenuCategory.BEVERAGE
        );

        MenuItem wine = MenuItem.createVegetarian(
            "Wine",
            "House red or white",
            8.99,
            RestaurantEnums.MenuCategory.BEVERAGE
        );

        menuItems.addAll(bruschetta, calamari, steak, pasta, tiramisu, soda, wine);

        // Create sample orders
        orders.addAll(
            new Order(1001, Arrays.asList(steak, tiramisu), 3),
            new Order(1002, Arrays.asList(pasta, soda), 5),
            new Order(1003, Arrays.asList(bruschetta, calamari, wine), 2)
        );
        
        // Set order statuses
        orders.get(0).setStatus(RestaurantEnums.OrderStatus.PREPARING);
        orders.get(1).setStatus(RestaurantEnums.OrderStatus.READY);
        
        // Assign orders to tables
        tables.get(2).setCurrentOrder(orders.get(0));  // Table 3
        tables.get(4).setCurrentOrder(orders.get(1));  // Table 5
        tables.get(1).setCurrentOrder(orders.get(2));  // Table 2
    }

    private FlowPane createEnhancedMenuView() {
        FlowPane flow = new FlowPane();
        flow.setPadding(new Insets(20));
        flow.setHgap(20);
        flow.setVgap(20);
        flow.setPrefWidth(800);

        for (RestaurantEnums.MenuCategory category : RestaurantEnums.MenuCategory.values()) {
            VBox categoryBox = new VBox(10);
            categoryBox.setPrefWidth(350);
            
            Label title = new Label(category.getDisplayName() + " " + category.getEmoji());
            title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 0 0 5 0;");
            
            VBox itemsBox = new VBox(8);
            itemsBox.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 1px; -fx-padding: 10px;");
            
            for (MenuItem item : getMenuItemsByCategory(category)) {
                HBox itemBox = new HBox(10);
                
                VBox details = new VBox(3);
                Label nameLabel = new Label(item.getName());
                nameLabel.setStyle("-fx-font-weight: bold;");
                
                Label descLabel = new Label(item.getDescription());
                descLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 0.9em;");
                
                HBox priceAndTags = new HBox(10);
                Label priceLabel = new Label(String.format("$%.2f", item.getPrice()));
                priceLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2e8b57;");
                
                HBox tags = new HBox(5);
                if (item.isVegetarian()) {
                    Label vegTag = new Label("🌱 Vegetarian");
                    vegTag.setStyle("-fx-font-size: 0.8em; -fx-text-fill: #2e8b57;");
                    tags.getChildren().add(vegTag);
                }
                if (item.isSpicy()) {
                    Label spicyTag = new Label("🌶️ Spicy");
                    spicyTag.setStyle("-fx-font-size: 0.8em; -fx-text-fill: #d32f2f;");
                    tags.getChildren().add(spicyTag);
                }
                
                priceAndTags.getChildren().addAll(priceLabel, tags);
                details.getChildren().addAll(nameLabel, descLabel, priceAndTags);
                
                Button addButton = new Button("Add");
                addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                addButton.setOnAction(e -> addToOrder(item));
                
                itemBox.getChildren().addAll(details, addButton);
                HBox.setHgrow(details, Priority.ALWAYS);
                
                itemsBox.getChildren().add(itemBox);
            }
            
            categoryBox.getChildren().addAll(title, itemsBox);
            flow.getChildren().add(categoryBox);
        }

        return flow;
    }

    private void addToOrder(MenuItem item) {
        System.out.println("Added " + item.getName() + " to order");
        // Implementation to add to current order would go here
    }

    // ... [Other methods remain exactly the same as in your original code]
    // Keep all other methods (createTablesGrid, showTableDetails, etc.) unchanged



    private GridPane createTablesGrid() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(15);
        grid.setVgap(15);

        for (Table table : tables) {
            Button tableBtn = new Button("Table " + table.getTableNumber());
            tableBtn.getStyleClass().add("table-button");
            
            // Add status-specific style
            switch (table.getStatus()) {
                case AVAILABLE:
                    tableBtn.getStyleClass().add("table-button-available");
                    break;
                case OCCUPIED:
                    tableBtn.getStyleClass().add("table-button-occupied");
                    break;
                case RESERVED:
                    tableBtn.getStyleClass().add("table-button-reserved");
                    break;
                case CLEANING:
                    tableBtn.getStyleClass().add("table-button-cleaning");
                    break;
            }
            
            tableBtn.setOnAction(e -> showTableDetails(table));
            grid.add(tableBtn, (table.getTableNumber()-1) % 4, (table.getTableNumber()-1) / 4);
        }

        return grid;
    }

    @SuppressWarnings("unchecked")
    private TableView<Order> createOrdersTable() {
        TableView<Order> tableView = new TableView<>();
        
        // Order ID Column
        TableColumn<Order, Integer> idColumn = new TableColumn<>("Order #");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        // Table Number Column
        TableColumn<Order, Integer> tableColumn = new TableColumn<>("Table #");
        tableColumn.setCellValueFactory(cellData -> 
            new SimpleObjectProperty<>(cellData.getValue().getTableNumber()));
        
        // Items Column
        TableColumn<Order, String> itemsColumn = new TableColumn<>("Items");
        itemsColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() == null || cellData.getValue().getItems() == null) {
                return new SimpleStringProperty("");
            }
            return new SimpleStringProperty(
                cellData.getValue().getItems().stream()
                    .map(MenuItem::getName)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("")
            );
        });
        
        // Status Column
        TableColumn<Order, RestaurantEnums.OrderStatus> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(column -> new StatusCell<>());
        
        tableView.getColumns().addAll(idColumn, tableColumn, itemsColumn, statusColumn);
        tableView.setItems(orders);
        
        return tableView;
    }

    private void showTableDetails(Table table) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Table " + table.getTableNumber());
        alert.setHeaderText("Table Status: " + table.getStatus().getDisplayName());
        
        if (table.getCurrentOrder() != null) {
            alert.setContentText("Current Order: #" + table.getCurrentOrder().getId());
        } else {
            alert.setContentText("No current order");
        }
        
        alert.showAndWait();
    }

   
    @SuppressWarnings("unused")
    private FlowPane createMenuView() {
        FlowPane flow = new FlowPane();
        flow.setPadding(new Insets(20));
        flow.setHgap(20);
        flow.setVgap(20);

        for (RestaurantEnums.MenuCategory category : RestaurantEnums.MenuCategory.values()) {
            VBox categoryBox = new VBox(10);
            Label title = new Label(category.getDisplayName() + " " + category.getEmoji());
            title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            
            ListView<MenuItem> itemList = new ListView<>();
            itemList.setItems(getMenuItemsByCategory(category));
            itemList.setCellFactory(lv -> new MenuItemCell());
            itemList.getStyleClass().add("menu-item-cell");
            
            categoryBox.getChildren().addAll(title, itemList);
            flow.getChildren().add(categoryBox);
        }

        return flow;
    }

    private ObservableList<MenuItem> getMenuItemsByCategory(RestaurantEnums.MenuCategory category) {
        return menuItems.filtered(item -> item.getCategory() == category);
    }

    public static void main(String[] args) {
        launch(args);
    }
}