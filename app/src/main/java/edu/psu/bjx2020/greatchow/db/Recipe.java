package edu.psu.bjx2020.greatchow.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Serializable {
    private String name;
    private String ownerID;
    private String nutritionalInfo;
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

    public String getNutritionalInfo() {
        return nutritionalInfo;
    }

    public void setNutritionalInfo(String nutritionalInfo) {
        this.nutritionalInfo = nutritionalInfo;
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

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
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
