package com.zachschulze.healthtracker20.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zachschulze.healthtracker20.models.FoodItem;
import com.zachschulze.healthtracker20.R;

import java.util.List;

/**
 * Created by Zach on 3/28/2015.
 */
public class FoodItemListAdapter extends BaseAdapter{
    private List<FoodItem> foodItems;
    private Context context;

    public FoodItemListAdapter(Context context, List<FoodItem> foodItems) {
        this.context = context;
        this.foodItems = foodItems;
    }

    @Override
    public int getCount() {
        return foodItems.size();
    }

    @Override
    public Object getItem(int i) {
        return foodItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return foodItems.indexOf(getItem(i));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.tab_foods, null);
        }

        TextView textView = (TextView)convertView.findViewById(R.id.foodInfo);
        FoodItem foodItem = this.foodItems.get(position);
        String foodName = foodItem.getFoodName();
        int calories = foodItem.getCalories();
        int servingSize = foodItem.getServingSize();
        String servingUnit = foodItem.getServingUnit();
        textView.setText(foodName + " (" + calories + " calories, serving size - " + servingSize + " " + servingUnit + ")");

        return convertView;
    }
}