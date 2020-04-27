package edu.psu.bjx2020.greatchow.db;

import android.graphics.Bitmap;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class FirestoreGC {
    private static final String TAG = "FirestoreGC";

    private static FirestoreGC INSTANCE;
    private FirebaseFirestore db;
    private FirebaseUser user;

    public static synchronized FirestoreGC getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FirestoreGC();
        }
        return INSTANCE;
    }

    public void onSuccessfulAuthentication() {
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public boolean isAuthenticated() {
        return user != null;
    }

    public void addRecipe(Recipe recipe) {
        db.collection("recipes").add(recipe)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "add recipe to database: " + documentReference );
                })
                .addOnFailureListener((@NonNull Exception e) -> {
                    Log.e(TAG, "failed to add recipe to database ", e);
                }
        );
    }

    public void addRecipes(Recipe ... recipes) {
        db.runTransaction(transaction -> {
            for (Recipe recipe : recipes) {
                db.collection("recipes").add(recipe);
            }
            return null;
        }).addOnSuccessListener(aVoid -> {
            Log.d(TAG, "add recipes to database. " );
        }).addOnFailureListener((@NonNull Exception e) -> {
            Log.e(TAG, "failed to add recipe to database ", e);
        });
    }

    public void getAllRecipes(boolean vegetarian, boolean vegan, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        Query recipes = db.collection("recipes");
        if(vegetarian) {
            recipes = recipes.whereEqualTo("vegetarian", true);
        }
        if(vegan) {
            recipes = recipes.whereEqualTo("vegan", true);
        }
        recipes.orderBy("name").get().addOnCompleteListener(onCompleteListener);
    }

    public void getAllMyRecipes(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        db.collection("recipes").whereEqualTo("ownerID", user.getUid()).get()
            .addOnCompleteListener(onCompleteListener);
    }

    public void getRecipes(String name, boolean vegetarian, boolean vegan, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        Query recipes = db.collection("recipes");
        if(vegetarian) {
            recipes = recipes.whereEqualTo("vegetarian", true);
        }
        if(vegan) {
            recipes = recipes.whereEqualTo("vegan", true);
        }
        recipes.whereEqualTo("name", name).get().addOnCompleteListener(onCompleteListener);
    }

    public String uploadRecipeImage(Bitmap bitmap, String ownerID) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        String path = "images/" + ownerID + "/" + UUID.randomUUID() + ".jpg";
        StorageReference storageRef = storage.getReference().child("images/" + ownerID + "/" + UUID.randomUUID() + ".jpg");
        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask
                .addOnFailureListener(exception -> Log.e(TAG, "Could not upload photo. ", exception))
                .addOnSuccessListener(taskSnapshot -> Log.d(TAG, "uploaded photo"));
        return path;
    }

    public String getOwnerID() {
        return user.getUid();
    }

    public void updateRecipe(DocumentReference reference, Recipe recipe) {
        reference.set(recipe);
    }

    private FirestoreGC() {
        db = FirebaseFirestore.getInstance();
    }
}
