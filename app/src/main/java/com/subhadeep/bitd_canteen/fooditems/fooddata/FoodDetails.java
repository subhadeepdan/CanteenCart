package com.subhadeep.bitd_canteen.fooditems.fooddata;
import java.io.Serializable;

public class FoodDetails implements Serializable {
    private int price;
    private String foodName;
    private int foodImage;
    private int foodQuantity;


    public FoodDetails(int mPrice, String mFoodName, int mFoodImage){
        price = mPrice;
        foodName = mFoodName;
        foodImage = mFoodImage;
        foodQuantity = 0;
    }

    public int getPrice() {
        return price;
    }

    public int getFoodImage() {
        return foodImage;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(int i){
        foodQuantity = i;
    }

}
