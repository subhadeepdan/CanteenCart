package com.subhadeep.bitd_canteen.plate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.subhadeep.bitd_canteen.InvoiceActivity;
import com.subhadeep.bitd_canteen.LoginActivity;
import com.subhadeep.bitd_canteen.MainScreen;
import com.subhadeep.bitd_canteen.MyOrders;
import com.subhadeep.bitd_canteen.R;
import com.subhadeep.bitd_canteen.fooditems.fooddata.FoodDetails;
import com.subhadeep.bitd_canteen.fooditems.fooddata.StorageClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PlateFragment extends Fragment  {
    ListView listview;
    Button placeOrder;

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    String username;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_plate, container, false);

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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference("orders/" + username );


        placeOrder = view.findViewById(R.id.placeorder);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<FoodDetails> storage = new StorageClass().getFoodCart();
                String orderItems = "";
                int amount=0;
                for(int i=0;i<storage.size();i++){
                    FoodDetails foodItem = storage.get(i);
                    orderItems = orderItems+foodItem.getFoodQuantity()+" X "+foodItem.getFoodName()+" ,";
                    amount = amount + foodItem.getFoodQuantity()*foodItem.getPrice();
                    //Toast.makeText(getContext(),String.valueOf(amount),Toast.LENGTH_LONG).show();
                }
                String key = dbRef.push().getKey();
                dbRef.child(key).child("orderItems").setValue(orderItems);
                dbRef.child(key).child("amount").setValue(String.valueOf(amount));
                dbRef.child(key).child("orderedOn").setValue(formattedDate);

                Intent intent = new Intent(getContext(),InvoiceActivity.class);
                intent.putExtra("storage",storage);
                /*for(int i=0;i<storage.size();i++)
                    new StorageClass().getFoodCart().remove(i);*/
                startActivity(intent);
            }
        });

        listview = (ListView) view.findViewById(R.id.custom_ListView);
        listview.setAdapter(new PlateAdapter(getContext(),new StorageClass().getFoodCart()));

        FloatingActionButton addItems = (FloatingActionButton) view.findViewById(R.id.addItems);
        listview.setEmptyView(view.findViewById(R.id.empty_view));
        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Add Some Items", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });

        return view;
    }



}
