package com.sjsu.hackathon.ingredient_manager.data.model;

public class Unit {
    private String id;
    private String name;

    public Unit() {
    }

    public Unit(String name) {
        this.name = name;
    }

    public Unit(String name, String id) {
        this.name = name;
        this.id = id;
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

    @Override
    public String toString() {
        return name;
    }
}
