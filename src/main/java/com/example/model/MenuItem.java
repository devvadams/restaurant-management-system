package com.example.model;

import com.example.model.RestaurantEnums.MenuCategory;

public class MenuItem {
    private final String name;
    private final String description;
    private final double price;
    private final MenuCategory category;
    private boolean isVegetarian;
    private boolean isSpicy;

    public MenuItem(String name, String description, double price, MenuCategory category) {
        this(name, description, price, category, false, false);
    }

    private MenuItem(String name, String description, double price, 
                   MenuCategory category, boolean isVegetarian, boolean isSpicy) {
        if (price <= 0) throw new IllegalArgumentException("Price must be positive");
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.isVegetarian = isVegetarian;
        this.isSpicy = isSpicy;
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public MenuCategory getCategory() { return category; }
    public boolean isVegetarian() { return isVegetarian; }
    public boolean isSpicy() { return isSpicy; }

    // Setters
    public void setVegetarian(boolean vegetarian) { isVegetarian = vegetarian; }
    public void setSpicy(boolean spicy) { isSpicy = spicy; }

    // Factory methods
    public static MenuItem createVegetarian(String name, String description, 
                                          double price, MenuCategory category) {
        MenuItem item = new MenuItem(name, description, price, category);
        item.setVegetarian(true);
        return item;
    }

    public static MenuItem createSpicy(String name, String description, 
                                     double price, MenuCategory category) {
        MenuItem item = new MenuItem(name, description, price, category);
        item.setSpicy(true);
        return item;
    }
}