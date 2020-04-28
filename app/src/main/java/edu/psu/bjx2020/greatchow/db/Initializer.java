package edu.psu.bjx2020.greatchow.db;

import java.util.Arrays;

public class Initializer {
    public static void addRecipes() {
        FirestoreGC firestoreGC = FirestoreGC.getInstance();

        Recipe scrambledEggs = new Recipe();
        scrambledEggs.setName("Scrambled Eggs");
        scrambledEggs.setOwnerID(firestoreGC.getOwnerID());
        scrambledEggs.setPathToImage(null);
        scrambledEggs.setNutritionalInfo("__ calories");
        scrambledEggs.setDiet(Recipe.VEGETARIAN);
        scrambledEggs.setIngredients(Arrays.asList(
                "4 large EGGS",
                "1/4 cup milk",
                "pinch salt",
                "pinch pepper",
                "2 tsp. butter"));
        scrambledEggs.setSteps(Arrays.asList(
                "BEAT eggs, milk, salt and pepper in bowl.",
                "HEAT butter in large nonstick skillet over medium heat until hot.",
                "POUR in egg mixture. As eggs begin to set, gently",
                "PULL the eggs across the pan with a spatula, forming large soft curds.",
                "CONTINUE cooking—pulling, lifting and folding eggs—until thickened and no visible liquid egg remains. Do not stir constantly. ",
                "REMOVE from heat.",
                "REMOVE from heat."));

        Recipe macAndCheese = new Recipe();
        macAndCheese.setName("Macaroni and Cheese");
        macAndCheese.setOwnerID(firestoreGC.getOwnerID());
        macAndCheese.setPathToImage(null);
        macAndCheese.setNutritionalInfo("__ calories");
        macAndCheese.setDiet(Recipe.VEGETARIAN);
        macAndCheese.setIngredients(Arrays.asList(
                "Macaronies",
                "Cheese"));
        macAndCheese.setSteps(Arrays.asList(
                "Make mac",
                "Make Cheese",
                "Cook"));


        firestoreGC.addRecipes(scrambledEggs, macAndCheese);
    }

    //        firestoreGC.addRecipes(
//                new Recipe("Scrambled Eggs", currentUser.getUid(), true, false),
//                new Recipe("Macaroni and Cheese", currentUser.getUid(), true, false));
//
//        //show all vegetarian recipes;
//        firestoreGC.getAllRecipes(true, false, task -> {
//            if (task.isSuccessful()) {
//                for (QueryDocumentSnapshot document : task.getResult()) {
//                    Recipe recipe = document.toObject(Recipe.class);
//                    Log.d(TAG, document.getId() + " => " + recipe.toString());
//                }
//            } else {
//                Log.e(TAG, "Error getting documents: ", task.getException());
//            }
//        });
//
//        //update a recipe
//        firestoreGC.getRecipes("Scrambled Eggs", false, false, task -> {
//            if (task.isSuccessful()) {
//                Recipe recipe = task.getResult().getDocuments().get(0).toObject(Recipe.class);
//                recipe.setName("Bacon and Eggs");
//                firestoreGC.updateRecipe(task.getResult().getDocuments().get(0).getReference(), recipe);
//            } else {
//                Log.e(TAG, "Could not find scrambled eggs", task.getException());
//            }
//        });
}
