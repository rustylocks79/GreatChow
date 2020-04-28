package edu.psu.bjx2020.greatchow.db;

import java.util.Arrays;

public class Initializer {
    public static void addRecipes() {
        FirestoreGC firestoreGC = FirestoreGC.getInstance();

        Recipe superHam = new Recipe();
        superHam.setName("Super Ham");
        superHam.setOwnerID(firestoreGC.getOwnerID());
        superHam.setPathToImage(null);
        superHam.setNutritionalInfo("6,600 calories\nAll protein");
        superHam.setDiet(Recipe.NEITHER);
        superHam.setIngredients(Arrays.asList(
                "84 Cubic Litres of Chicken",
                "Elmer's Wood Glue"));
        superHam.setSteps(Arrays.asList(
                "Heat glue on stove at medium-high until low boil",
                "Add chicken and stir until coated",
                "Let simmer on low for 4 hours",
                "Serve and enjoy"));

        Recipe boiledStuff = new Recipe();
        boiledStuff.setName("Boiled Stuff-n-Things");
        boiledStuff.setOwnerID(firestoreGC.getOwnerID());
        boiledStuff.setPathToImage(null);
        boiledStuff.setNutritionalInfo("??? calories");
        boiledStuff.setDiet(Recipe.VEGETARIAN);
        boiledStuff.setIngredients(Arrays.asList(
                "Literally anything",
                "Whatever else you want"));
        boiledStuff.setSteps(Arrays.asList(
                "Heat a bunch of water to boil",
                "Put stuff into bowl",
                "Wait until stuff is boiled",
                "Serve and enjoy"));

        Recipe stickersNGlue = new Recipe();
        stickersNGlue.setName("Stickers & Glue");
        stickersNGlue.setOwnerID(firestoreGC.getOwnerID());
        stickersNGlue.setPathToImage(null);
        stickersNGlue.setNutritionalInfo("0 calories");
        stickersNGlue.setDiet(Recipe.VEGAN);
        stickersNGlue.setIngredients(Arrays.asList(
                "$20 worth of stickers from Walmart",
                "16 grams Loctite Ultra Gel Control Super Glue"));
        stickersNGlue.setSteps(Arrays.asList(
                "Pour glue into plastic bowl",
                "Carefully remove stickers from sheets and mix into glue",
                "Heat in microwave on high for 2 minutes",
                "Serve and enjoy"));

        firestoreGC.addRecipes(superHam, boiledStuff, stickersNGlue);
    }
}
