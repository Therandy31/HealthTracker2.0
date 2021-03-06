package com.zachschulze.healthtracker20.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zachschulze.healthtracker20.models.FoodItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zach on 3/29/2015.
 */
public class FoodItemDataSource {
    private Context mContext;
    private HealthTrackerSQLiteHelper mHealthTrackerSQliteHelper;

    public FoodItemDataSource(Context context) {
        mContext = context;
        mHealthTrackerSQliteHelper = new HealthTrackerSQLiteHelper(context);
    }

    public void addFoodItem(FoodItem fooditem) {
        ContentValues values = new ContentValues();
        values.put(HealthTrackerSQLiteHelper.COLUMN_FOODNAME, fooditem.getFoodName());
        values.put(HealthTrackerSQLiteHelper.COLUMN_CALORIES, fooditem.getCalories());
        values.put(HealthTrackerSQLiteHelper.COLUMN_SERVINGSIZE, fooditem.getServingSize());
        values.put(HealthTrackerSQLiteHelper.COLUMN_SERVINGUNIT, fooditem.getServingUnit());

        SQLiteDatabase db = mHealthTrackerSQliteHelper.getWritableDatabase();

        db.insert(HealthTrackerSQLiteHelper.TABLE_FOODITEMS, null, values);
        db.close();
    }

    public boolean deleteFoodItem(String foodname) {
        boolean result = false;

        String query = "Select * FROM" + HealthTrackerSQLiteHelper.TABLE_FOODITEMS + " WHERE" + HealthTrackerSQLiteHelper.COLUMN_FOODNAME + " = \"" + foodname + "\"";

        SQLiteDatabase db = mHealthTrackerSQliteHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        FoodItem fooditem = new FoodItem();

        if (cursor.moveToFirst()) {
            fooditem.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(HealthTrackerSQLiteHelper.TABLE_FOODITEMS, HealthTrackerSQLiteHelper.COLUMN_ID + " = ?",
                    new String[] {
                            String.valueOf(fooditem.getID())
                    });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public List<FoodItem> getAllFoodItems() {
        List<FoodItem> foodItems = new ArrayList<FoodItem>();
        String selectQuery = "SELECT * FROM " + HealthTrackerSQLiteHelper.TABLE_FOODITEMS;

        SQLiteDatabase db = mHealthTrackerSQliteHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                FoodItem fooditem = new FoodItem();
                fooditem.setID(c.getInt(c.getColumnIndex(HealthTrackerSQLiteHelper.COLUMN_ID)));
                fooditem.setFoodName(c.getString(c.getColumnIndex(HealthTrackerSQLiteHelper.COLUMN_FOODNAME)));
                fooditem.setCalories(c.getInt(c.getColumnIndex(HealthTrackerSQLiteHelper.COLUMN_CALORIES)));
                fooditem.setServingSize(c.getInt(c.getColumnIndex(HealthTrackerSQLiteHelper.COLUMN_SERVINGSIZE)));
                fooditem.setServingUnit(c.getString(c.getColumnIndex(HealthTrackerSQLiteHelper.COLUMN_SERVINGUNIT)));

                foodItems.add(fooditem);
            } while (c.moveToNext());
        }
        return foodItems;
    }
}
