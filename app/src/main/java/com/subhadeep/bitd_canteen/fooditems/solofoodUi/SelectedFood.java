package com.subhadeep.bitd_canteen.fooditems.solofoodUi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.subhadeep.bitd_canteen.MainScreen;
import com.subhadeep.bitd_canteen.R;
import com.subhadeep.bitd_canteen.fooditems.fooddata.FoodDetails;
import com.subhadeep.bitd_canteen.fooditems.fooddata.StorageClass;


import java.util.ArrayList;


public class SelectedFood extends AppCompatActivity {

    private StorageClass foodData;
    private int index;
    private TextView foodQuantity;
    private int dummyQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selected_food);
        Intent dataRetrieve = getIntent();
        FoodDetails specificFood = (FoodDetails) dataRetrieve.getSerializableExtra("myObject");

        dummyQuantity = 1;  // varaible to store the Quantity Displayed ...

        foodData = new StorageClass();  // giving memory to storageClass object ;
        try {
            index = searchForFood(specificFood);
            if (index >= 0){
                /*
                Food Related Details Below ...
                 */

                ImageView foodImage = (ImageView) findViewById(R.id.selectedimage);
                TextView foodName = (TextView) findViewById(R.id.selectedfood);
                TextView foodPrice = (TextView) findViewById(R.id.selectedprice);

                foodImage.setImageResource(foodData.getCatalogData().get(index).getFoodImage());
                foodName.setText(foodData.getCatalogData().get(index).getFoodName());
                foodPrice.setText(Integer.toString(foodData.getCatalogData().get(index).getPrice()));

                /*
                Food Related Details Above ...
                 */

                /*
                 Quantity Related Buttons below ...
                 */
                foodQuantity = (TextView) findViewById(R.id.selectednoofitems);
                foodQuantity.setText(Integer.toString(dummyQuantity));

                /*
                Quantity Related Buttons Above ...
                 */
            }
            else {
                throw new IllegalArgumentException("SELECTED_FOOD_NOT_IN_DATA");
            }
        }
        catch (IllegalArgumentException e){
            e.getMessage();
        }


    }

    public void increaseQuantity(View view){
        dummyQuantity++;
        foodQuantity.setText(Integer.toString(dummyQuantity));
    }
    public void decreaseQuantity(View view){
        if (dummyQuantity>1){
            dummyQuantity--;
            foodQuantity.setText(Integer.toString(dummyQuantity));
        }
        else {
            Toast.makeText(this,"Quantity should atleast be one", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    Invoked Add to Cart button is Pressed and Then It Returns to Main Display and
    Showing The Items Added In Cart ...
     */
    public void addToCart(View view){
        Intent intent = new Intent(this,MainScreen.class);
        if (isContain(foodData.getFoodCart())){
            Log.i("ERROR","CONATINS ");

            if (foodQuantity.getText().toString().equals(Integer.toString(foodData.getCatalogData().get(index).getFoodQuantity()))){
                Toast.makeText(this,"Food is Already In cart", Toast.LENGTH_SHORT).show();
            }
            else {
                foodData.getCatalogData().get(index).setFoodQuantity(dummyQuantity);
                intent.putExtra("From","SelectedFood");

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
        else {
            foodData.getCatalogData().get(index).setFoodQuantity(dummyQuantity);
            foodData.getFoodCart().add(foodData.getCatalogData().get(index));

            intent.putExtra("From","SelectedFood");

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
    /*
    Method to check Whether Food is Already In Food Cart Or Not ...
     */
    private boolean isContain(ArrayList<FoodDetails> checker){
        Log.i("SIZE", Integer.toString(checker.size()));
        for (int i=0;i<checker.size();i++){
            if (foodData.getCatalogData().get(index).getFoodName().equals(checker.get(i).getFoodName())){
                return true;
            }
        }
        return false;
    }


    /*
    Below Method finds the Selected Food By Using The Linear Search Algorithm ...
     */

    private int searchForFood(FoodDetails specificFood){
        for (int i=0;i<foodData.getCatalogData().size();i++){
            if (specificFood.getFoodName().equals(foodData.getCatalogData().get(i).getFoodName())){
                return i;
            }
        }
        return -1;
    }



}
