package com.subhadeep.bitd_canteen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.subhadeep.bitd_canteen.fooditems.fooddata.FoodDetails;
import com.subhadeep.bitd_canteen.fooditems.fooddata.StorageClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class InvoiceActivity extends AppCompatActivity {
    TextView invoiceItemname, invoiceQuantity, invoicePrice, invoiceAmount, invoiceTotalamount, invoiceUsername, invoiceDatetime;
    String itemname="",quantity="",price="",amount="";
    int totalamount = 0;
    private FirebaseAuth auth;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        ArrayList<FoodDetails> storage = (ArrayList<FoodDetails>) getIntent().getSerializableExtra("storage");


        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if(user!=null){
            String userEmail = user.getEmail();
            username   = userEmail.substring(0, userEmail.lastIndexOf("@"));
        }


        //get current date and time
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        //Toast.makeText(getContext(), formattedDate, Toast.LENGTH_SHORT).show();

        invoiceItemname = findViewById(R.id.invoiceItemname);
        invoiceQuantity = findViewById(R.id.invoiceQuantity);
        invoicePrice = findViewById(R.id.invoicePrice);
        invoiceAmount = findViewById(R.id.invoiceAmount);
        invoiceTotalamount = findViewById(R.id.amountTextview);
        invoiceUsername = findViewById(R.id.invoiceUsername);
        invoiceDatetime = findViewById(R.id.invoiceDatetime);

        for(int i=0;i<storage.size();i++){
            int am = 0;
            FoodDetails foodItem = storage.get(i);
            itemname = itemname+foodItem.getFoodName()+"\n\n";
            quantity = quantity+String.valueOf(foodItem.getFoodQuantity())+"\n\n";
            price = price+String.valueOf(foodItem.getPrice())+"\n\n";
            am = am + foodItem.getFoodQuantity()*foodItem.getPrice();
            amount = amount + String.valueOf(am) + "\n\n";
            totalamount = totalamount + foodItem.getFoodQuantity()*foodItem.getPrice();
            //Toast.makeText(getContext(),String.valueOf(amount),Toast.LENGTH_LONG).show();
        }

        invoiceItemname.setText(itemname);
        invoiceQuantity.setText(quantity);
        invoicePrice.setText(price);
        invoiceAmount.setText(amount);
        invoiceTotalamount.setText("Sub Total : Rs. "+String.valueOf(totalamount));
        invoiceUsername.setText("Bill To : "+username);
        invoiceDatetime.setText("Invoice Date and Time : "+formattedDate);

        new StorageClass().getFoodCart().clear();
        /*for(int i=0;i<storage.size();i++)
            new StorageClass().getFoodCart().remove(i);*/


    }
}
