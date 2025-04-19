package com.example.model;

import javafx.scene.paint.Color;

public enum RestaurantEnums {
    ;
    
    public enum TableStatus {
        AVAILABLE("Available", Color.GREEN),
        OCCUPIED("Occupied", Color.RED),
        RESERVED("Reserved", Color.ORANGE),
        CLEANING("Cleaning", Color.YELLOW);

        private final String displayName;
        private final Color color;

        TableStatus(String displayName, Color color) {
            this.displayName = displayName;
            this.color = color;
        }

        public String getDisplayName() {
            return displayName;
        }

        public Color getColor() {
            return color;
        }
    }

    public enum MenuCategory {
        APPETIZER("Appetizers", "🍤"),
        MAIN_COURSE("Main Courses", "🍲"),
        DESSERT("Desserts", "🍰"),
        BEVERAGE("Beverages", "🍹");

        private final String displayName;
        private final String emoji;

        MenuCategory(String displayName, String emoji) {
            this.displayName = displayName;
            this.emoji = emoji;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getEmoji() {
            return emoji;
        }
    }

    public enum OrderStatus {
        RECEIVED(1, Color.LIGHTGRAY),
        PREPARING(2, Color.ORANGE),
        READY(3, Color.BLUE),
        DELIVERED(4, Color.GREEN),
        PAID(5, Color.DARKGREEN);

        private final int priority;
        private final Color color;

        OrderStatus(int priority, Color color) {
            this.priority = priority;
            this.color = color;
        }

        public int getPriority() {
            return priority;
        }

        public Color getColor() {
            return color;
        }
    }
}