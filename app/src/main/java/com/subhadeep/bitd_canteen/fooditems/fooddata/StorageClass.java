package com.subhadeep.bitd_canteen.fooditems.fooddata;

import com.subhadeep.bitd_canteen.R;

import java.util.ArrayList;

/**
 * Created by rajat on 08-01-2017.
 */

public class StorageClass {

    private static ArrayList<FoodDetails> foodItems ;
    private static ArrayList<FoodDetails> foodCart ;
    public StorageClass(){

    }

    public void setCatalogData(){
        if (foodItems ==null){
            foodItems = new ArrayList<>();

            foodItems.add(new FoodDetails(50,"Burger", R.drawable.burger));
            foodItems.add(new FoodDetails(50,"Vada Pav", R.drawable.vada_pav));
            foodItems.add(new FoodDetails(25,"Aloo Paratha", R.drawable.aloo_paratha));
            foodItems.add(new FoodDetails(30,"Bread Pakora", R.drawable.bread_pakora));
            foodItems.add(new FoodDetails(50,"Cheese Sandwich", R.drawable.cheese_sandwich));
            foodItems.add(new FoodDetails(60,"Chole Bhature", R.drawable.chole_bhature));
            foodItems.add(new FoodDetails(60,"Chole Kulche", R.drawable.chole_kulche));
            foodItems.add(new FoodDetails(50,"Idli Sambar", R.drawable.idli_sambar));
            foodItems.add(new FoodDetails(30,"Khamand", R.drawable.khamand));
            foodItems.add(new FoodDetails(80,"Masala Dosa", R.drawable.masala_dosa));
            foodItems.add(new FoodDetails(30,"Paneer Paratha", R.drawable.paneer_paratha));
            foodItems.add(new FoodDetails(40,"Pav Bhaji", R.drawable.pav_bhaji));
            foodItems.add(new FoodDetails(100,"Pizza", R.drawable.pizza));
            foodItems.add(new FoodDetails(150,"Cheese Pizza", R.drawable.pizza2));
            foodItems.add(new FoodDetails(100,"Rava Dosa", R.drawable.rava_dosa));
            foodItems.add(new FoodDetails(60,"Red Pasta", R.drawable.red_pasta));
            foodItems.add(new FoodDetails(10,"Samosa", R.drawable.samosa));
            foodItems.add(new FoodDetails(15,"Veg. Sandwich", R.drawable.veg_sandwich));
            foodItems.add(new FoodDetails(70,"White pasta", R.drawable.white_pasta));

        }
    }

    public ArrayList<FoodDetails> getCatalogData(){
        return foodItems;
    }

    public ArrayList<FoodDetails> getFoodCart(){
        if (foodCart ==null){
            foodCart = new ArrayList<>();
        }
        return foodCart;
    }

}
