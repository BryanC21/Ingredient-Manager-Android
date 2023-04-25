package com.sjsu.hackathon.ingredient_manager.data.handler;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sjsu.hackathon.ingredient_manager.data.model.Recipe;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "IngredientDB";
    private static final int DB_VERSION = 3;
    private static final String TABLE_NAME = "recipes";
    private static final String ID_COL = "id";
    private static final String DATA_COL = "data";

    public RecipeHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATA_COL + " TEXT)";
        db.execSQL(query);
        db.close();
    }

    public void addNewData(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DATA_COL, recipe.getJson().toString());

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public void removeData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[]{Integer.toString(id)});
        db.close();
    }

    public ArrayList<Recipe> getAll() {
        ArrayList<Recipe> dataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    dataList.add(new Recipe(new JSONObject(cursor.getString(1)), cursor.getInt(0)));
                } catch (JSONException e) {
                    Log.e("JSON parse error", e.getMessage());
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return dataList;
    }

    public Recipe get(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Recipe recipe = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id = ?",  new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            do {
                try {
                    recipe = new Recipe(new JSONObject(cursor.getString(1)), cursor.getInt(0));
                } catch (JSONException e) {
                    Log.e("JSON parse error", e.getMessage());
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return recipe;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        db.close();
    }
}
