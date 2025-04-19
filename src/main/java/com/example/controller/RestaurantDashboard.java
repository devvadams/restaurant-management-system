package com.example.controller;

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
import com.example.view.cells.MenuItemCell;
import com.example.view.cells.StatusCell;

public class RestaurantDashboard extends Application {

    private ObservableList<Table> tables = FXCollections.observableArrayList();
    private ObservableList<Order> orders = FXCollections.observableArrayList();
    private ObservableList<MenuItem> menuItems = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        initializeSampleData();
        
        // 1. Create Tabbed Interface
        TabPane tabPane = new TabPane();
        
        // 2. Tables Tab
        Tab tablesTab = new Tab("Tables", createTablesGrid());
        tablesTab.setClosable(false);
        
        // 3. Orders Tab
        Tab ordersTab = new Tab("Orders", createOrdersTable());
        ordersTab.setClosable(false);
        
        // 4. Menu Tab
        Tab menuTab = new Tab("Menu", createMenuView());
        menuTab.setClosable(false);

        tabPane.getTabs().addAll(tablesTab, ordersTab, menuTab);

        Scene scene = new Scene(tabPane, 800, 600);
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
        menuItems.addAll(
            new MenuItem("Bruschetta", "Toasted bread with tomatoes, garlic and basil", 8.99, RestaurantEnums.MenuCategory.APPETIZER),
            new MenuItem("Calamari", "Fried squid with marinara sauce", 12.99, RestaurantEnums.MenuCategory.APPETIZER),
            new MenuItem("Steak", "12oz ribeye with mashed potatoes", 24.99, RestaurantEnums.MenuCategory.MAIN_COURSE),
            new MenuItem("Pasta Carbonara", "Spaghetti with creamy egg sauce", 16.99, RestaurantEnums.MenuCategory.MAIN_COURSE),
            new MenuItem("Tiramisu", "Classic Italian dessert", 7.99, RestaurantEnums.MenuCategory.DESSERT),
            new MenuItem("Soda", "Various flavors", 2.99, RestaurantEnums.MenuCategory.BEVERAGE),
            new MenuItem("Wine", "House red or white", 8.99, RestaurantEnums.MenuCategory.BEVERAGE)
        );

        // Create sample orders
        orders.addAll(
            new Order(1001, Arrays.asList(menuItems.get(2), menuItems.get(4)), 3),
            new Order(1002, Arrays.asList(menuItems.get(3), menuItems.get(5)), 5),
            new Order(1003, Arrays.asList(menuItems.get(0), menuItems.get(1), menuItems.get(6)), 2)
        );
        orders.get(0).setStatus(RestaurantEnums.OrderStatus.PREPARING);
        orders.get(1).setStatus(RestaurantEnums.OrderStatus.READY);
    }

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

    private TableView<Order> createOrdersTable() {
        TableView<Order> table = new TableView<>();
        
        TableColumn<Order, Integer> idCol = new TableColumn<>("Order #");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        TableColumn<Order, Integer> tableCol = new TableColumn<>("Table #");
        tableCol.setCellValueFactory(cell -> 
            new SimpleObjectProperty<>(cell.getValue().getTableNumber()));
        
        TableColumn<Order, String> itemsCol = new TableColumn<>("Items");
        itemsCol.setCellValueFactory(cell -> 
            new SimpleStringProperty(cell.getValue().getItems().stream()
                .map(MenuItem::getName)
                .reduce((a, b) -> a + ", " + b)
                .orElse("")));
        
        TableColumn<Order, RestaurantEnums.OrderStatus> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setCellFactory(col -> new StatusCell<>());
        
        table.getColumns().addAll(idCol, tableCol, itemsCol, statusCol);
        table.setItems(orders);
        
        return table;
    }

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