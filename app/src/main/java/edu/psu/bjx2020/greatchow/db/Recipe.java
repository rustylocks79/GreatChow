package edu.psu.bjx2020.greatchow.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Serializable {
    public static final int NONE = 0;
    public static final int VEGETARIAN = 1;
    public static final int VEGAN = 2;

    private String name;
    private String ownerID;
    private String pathToImage;
    private String nutritionalInfo;
    private int diet;
    private List<String> ingredients = new ArrayList<>();
    private List<String> steps = new ArrayList<>();

    public Recipe() {

    }

    public Recipe(String name, String ownerID, String pathToImage, String nutritionalInfo, int diet, List<String> ingredients, List<String> steps) {
        this.name = name;
        this.ownerID = ownerID;
        this.pathToImage = pathToImage;
        this.nutritionalInfo = nutritionalInfo;
        this.diet = diet;
        this.ingredients = ingredients;
        this.steps = steps;
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

    public String getPathToImage() {
        return pathToImage;
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }

    public String getNutritionalInfo() {
        return nutritionalInfo;
    }

    public void setNutritionalInfo(String nutritionalInfo) {
        this.nutritionalInfo = nutritionalInfo;
    }

    public int getDiet() {
        return diet;
    }

    public void setDiet(int diet) {
        this.diet = diet;
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
                ", pathToImage='" + pathToImage + '\'' +
                ", nutritionalInfo='" + nutritionalInfo + '\'' +
                ", diet=" + diet +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                '}';
    }
}
