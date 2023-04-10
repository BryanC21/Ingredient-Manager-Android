package com.sjsu.hackathon.ingredient_manager.data.model;

import java.sql.Time;
import java.util.Date;

public class Ingredient {
    private String id;
    private String name;
    private double quantity;
    private String locationId;
    private Date expirationTime;
    private Date createTime;
    private String img;
    private String notes;
    private String categoryId;
    private String unitId;

    private String userId;

    public Ingredient() {}

    public Ingredient(String name, double quantity, String img, String notes, Date expirationTime,
                      Date createTime, String locationId, String categoryId, String unitId,
                      String userId) {
        this.name = name;
        this.quantity = quantity;
        this.locationId = locationId;
        this.expirationTime = expirationTime;
        this.createTime = createTime;
        this.img = img;
        this.notes = notes;
        this.categoryId = categoryId;
        this.unitId = unitId;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return name + " " + quantity + " " + expirationTime;
    }
}
