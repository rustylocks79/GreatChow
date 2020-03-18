package edu.psu.bjx2020.greatchow.db;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Recipe.class, Ingredient.class, Contains.class, Account.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public interface DatabaseListener<E> {
        void onReturn(E e);
    }

    public abstract RecipeDAO recipeDAO();
    public abstract ContainsDAO containsDAO();
    public abstract IngredientDAO ingredientDAO();
    public abstract AccountDAO accountDAO();

    private static AppDatabase INSTANCE;

    public static synchronized AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "recipe_database")
                    .addCallback(createJokeDatabaseCallback)
                    .build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback createJokeDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    public static void getRecipe(int id, final DatabaseListener<RecipeWithIngredients> listener) {
        new AsyncTask<Integer, Void, RecipeWithIngredients>() {
            protected RecipeWithIngredients doInBackground(Integer... ids) {
                return INSTANCE.recipeDAO().getById(ids[0]);
            }

            protected void onPostExecute(RecipeWithIngredients recipe) {
                super.onPostExecute(recipe);
                listener.onReturn(recipe);
            }
        }.execute(id);
    }

    public static void getAccount(int id, final DatabaseListener<Account> listener) {
        new AsyncTask<Integer, Void, Account>() {
            protected Account doInBackground(Integer... ids) {
                return INSTANCE.accountDAO().getById(ids[0]);
            }

            protected void onPostExecute(Account account) {
                super.onPostExecute(account);
                listener.onReturn(account);
            }
        }.execute(id);
    }

    public static void insert(Recipe recipe) {
        new AsyncTask<Recipe, Void, Void> () {
            protected Void doInBackground(Recipe... recipes) {
                INSTANCE.recipeDAO().insert(recipes);
                return null;
            }
        }.execute(recipe);
    }

    public static void insert(final Contains contains) {
        new AsyncTask<Contains, Void, Void> () {
            protected Void doInBackground(Contains... containers) {
                INSTANCE.containsDAO().insert(containers);
                return null;
            }
        }.execute(contains);
    }

    public static void insert(final Ingredient ingredient) {
        new AsyncTask<Ingredient, Void, Void> () {
            protected Void doInBackground(Ingredient... ingredients) {
                INSTANCE.ingredientDAO().insert(ingredients);
                return null;
            }
        }.execute(ingredient);
    }

    public static void insert(final Account account) {
        new AsyncTask<Account, Void, Void> () {
            protected Void doInBackground(Account... accounts) {
                INSTANCE.accountDAO().insert(accounts);
                return null;
            }
        }.execute(account);
    }

    public static void deleteRecipe(int recipeID) {
        new AsyncTask<Integer, Void, Void> () {
            protected Void doInBackground(Integer... ids) {
                INSTANCE.recipeDAO().delete(ids[0]);
                return null;
            }
        }.execute(recipeID);
    }

    public static void deleteContains(int recipeID, int ingredientID) {
        new AsyncTask<Integer, Void, Void> () {
            protected Void doInBackground(Integer... ids) {
                INSTANCE.containsDAO().delete(ids[0], ids[1]);
                return null;
            }
        }.execute(recipeID, ingredientID);
    }

    public static void deleteIngredient(int ingredientID) {
        new AsyncTask<Integer, Void, Void> () {
            protected Void doInBackground(Integer... ids) {
                INSTANCE.ingredientDAO().delete(ids[0]);
                return null;
            }
        }.execute(ingredientID);
    }

    public static void deleteAccount(int accountID) {
        new AsyncTask<Integer, Void, Void> () {
            protected Void doInBackground(Integer... ids) {
                INSTANCE.accountDAO().delete(ids[0]);
                return null;
            }
        }.execute(accountID);
    }

    public static void update(Recipe recipe) {
        new AsyncTask<Recipe, Void, Void> () {
            protected Void doInBackground(Recipe... recipes) {
                INSTANCE.recipeDAO().update(recipes);
                return null;
            }
        }.execute(recipe);
    }

    public static void update(Contains contains) {
        new AsyncTask<Contains, Void, Void> () {
            protected Void doInBackground(Contains... contains1) {
                INSTANCE.containsDAO().update(contains1);
                return null;
            }
        }.execute(contains);
    }

    public static void update(Ingredient ingredient) {
        new AsyncTask<Ingredient, Void, Void> () {
            protected Void doInBackground(Ingredient... ingredients) {
                INSTANCE.ingredientDAO().update(ingredients);
                return null;
            }
        }.execute(ingredient);
    }

    public static void update(Account account) {
        new AsyncTask<Account, Void, Void> () {
            protected Void doInBackground(Account... accounts) {
                INSTANCE.accountDAO().update(accounts);
                return null;
            }
        }.execute(account);
    }
}
