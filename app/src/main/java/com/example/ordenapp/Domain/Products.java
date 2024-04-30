package com.example.ordenapp.Domain;

import java.io.Serializable;

public class Products implements Serializable {
    private int CategoryId;
    private String Description;
    private boolean BestProduct;
    private int Id;
    private double Price;
    private String ImagePath;
    private double Star;
    private String Title;
    private int numberInCart;

    public Products() {
    }

    @Override
    public String toString() {
        return Title;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isBestProduct() {
        return BestProduct;
    }

    public void setBestProduct(boolean bestProduct) {
        BestProduct = bestProduct;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public double getStar() {
        return Star;
    }

    public void setStar(double star) {
        Star = star;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }
}
