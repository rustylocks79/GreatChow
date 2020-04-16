package edu.psu.bjx2020.greatchow.db;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String name;
    private String ownerID;
    private boolean vegetarian;
    private boolean vegan;
    private List<String> ingredients = new ArrayList<>();
    private List<String> steps = new ArrayList<>();

    public Recipe() {

    }

    public Recipe(String name, String ownerID, boolean vegetarian, boolean vegan) {
        this.name = name;
        this.ownerID = ownerID;
        this.vegetarian = vegetarian;
        this.vegan = vegan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", ownerID='" + ownerID + '\'' +
                ", vegetarian=" + vegetarian +
                ", vegan=" + vegan +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                '}';
    }
}
