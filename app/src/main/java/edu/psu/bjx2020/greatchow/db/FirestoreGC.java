package edu.psu.bjx2020.greatchow.db;

import android.graphics.Bitmap;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FirestoreGC {
    public interface OnDownloadImage {
         void onCompete(File file);
    }

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

    /**
     * Stores recipe in the database.
     * @param recipe the recipe to be stored.
     */
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

    /**
     * Stores recipes in the database.
     * @param recipes the recipes to be stored in the database.
     */
    public void addRecipes(Recipe ... recipes) {
        db.runTransaction(transaction -> {
            for (Recipe recipe : recipes) {
                db.collection("recipes").add(recipe);
            }
            return null;
        })
                .addOnSuccessListener(aVoid -> Log.d(TAG, "add recipes to database. " ))
                .addOnFailureListener((@NonNull Exception e) -> Log.e(TAG, "failed to add recipe to database ", e));
    }

    /**
     * Adds a scheduled recipe to the database.
     * @param recipe the scheduled recipe to be added to the database.
     */
    public void addScheduledRecipe(ScheduledRecipe recipe) {
        db.collection("schedule").add(recipe)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "add scheduled recipe to database: " + documentReference ))
                .addOnFailureListener((@NonNull Exception e) -> Log.e(TAG, "failed to add recipe to database ", e));
    }

    /**
     * Gets all the recipe in the database that meet the diet requirement
     * @param diet the minimum diet requirement.
     * @param onCompleteListener The listener that will be called when the query is complete.
     */
    public void getAllRecipes(int diet, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        db.collection("recipes")
                .whereGreaterThanOrEqualTo("diet", diet)
                .orderBy("diet")
                .orderBy("name")
                .get().addOnCompleteListener(onCompleteListener);
    }

    public void getAllMyRecipes(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        db.collection("recipes")
                .whereEqualTo("ownerID", user.getUid())
                .get().addOnCompleteListener(onCompleteListener);
    }

    public void getRecipes(String name, int diet, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        db.collection("recipes")
                .whereEqualTo("name", name)
                .whereGreaterThanOrEqualTo("diet", diet)
                .get().addOnCompleteListener(onCompleteListener);
    }

    public void getScheduledRecipes(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        db.collection("schedule")
                .whereEqualTo("ownerID", user.getUid())
                .get().addOnCompleteListener(onCompleteListener);
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
        return storageRef.getPath();
    }

    public void getImageFromStorage(String location, OnDownloadImage onDownloadImage) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(location);
        File localFile = null;
        try {
            localFile = File.createTempFile("location", "jpg");
        } catch (IOException e) {
            Log.e(TAG, "could not create temp file ", e);
        }
        File finalLocalFile = localFile;
        storageReference.getFile(localFile).addOnSuccessListener(taskSnapshot -> onDownloadImage.onCompete(finalLocalFile))
            .addOnFailureListener(exception -> Log.e(TAG, "failed to download image", exception));

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
