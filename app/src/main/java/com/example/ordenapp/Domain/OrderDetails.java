package com.example.ordenapp.Domain;
public class OrderDetails {
    private int Id;
    private String UserID;
    private int ItemsQuantity;
    private String ShippingAddress;
    private String Status;
    private double Total;
    private String DateTime;

    public OrderDetails() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public int getItemsQuantity() {
        return ItemsQuantity;
    }

    public void setItemsQuantity(int itemsQuantity) {
        ItemsQuantity = itemsQuantity;
    }

    public String getShippingAddress() {
        return ShippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        ShippingAddress = shippingAddress;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        this.DateTime = dateTime;
    }
}

