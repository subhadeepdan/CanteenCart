package com.subhadeep.bitd_canteen;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MyOrders extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef;
    private FirebaseAuth auth;
    private ListView orderListView;
    Button home;
    String username;
    //TextView emptyText;


    ArrayList<String> listOrderItems = new ArrayList<String>();
    ArrayList<String> listOrderDateandTime = new ArrayList<String>();
    ArrayList<String> listOrderAmount = new ArrayList<String>();
    ArrayList<String> listKeys = new ArrayList<String>();
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        //emptyText = findViewById(R.id.emptyElement);


        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if(user!=null){
            String userEmail = user.getEmail();
            username   = userEmail.substring(0, userEmail.lastIndexOf("@"));
        }

        dbRef = database.getReference("orders/"+ username );

        orderListView = findViewById(R.id.order_ListView);

        adapter = new MyAdapter(MyOrders.this,android.R.layout.simple_list_item_1, listOrderItems,listOrderDateandTime,listOrderAmount);
        orderListView.setAdapter(adapter);

        home = findViewById(R.id.homeButton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MainScreen.class);
                startActivity(i);
            }
        });

        addChildEventListener();

    }

    private void addChildEventListener() {
        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                /*adapter.add(
                        (String) dataSnapshot.child("itemname").getValue()+" "(String) dataSnapshot.child("price").getValue());*/
                //adapter.add(
                //(String) dataSnapshot.child("itemname").getValue());
                listOrderItems.add((String) dataSnapshot.child("orderItems").getValue());
                listOrderDateandTime.add((String) dataSnapshot.child("orderedOn").getValue());
                listOrderAmount.add((String) dataSnapshot.child("amount").getValue());
                adapter.notifyDataSetChanged();

                listKeys.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int index = listKeys.indexOf(key);

                if (index != -1) {
                    listOrderItems.remove(index);
                    listOrderAmount.remove(index);
                    listOrderDateandTime.remove(index);
                    listKeys.remove(index);
                    adapter.notifyDataSetChanged();
                }
            }

        };
        dbRef.addChildEventListener(childListener);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                finish();
                break;

            case R.id.item1:
                Intent intent = new Intent(getApplicationContext(),MyOrders.class);
                startActivity(intent);
                //Toast.makeText(this,"Signout",Toast.LENGTH_SHORT).show();
                break;


            case R.id.item2:
                signOut();
                Toast.makeText(this,"Signout",Toast.LENGTH_SHORT).show();
                break;


            case R.id.item3:
                finishAffinity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void signOut() {
        auth.signOut();
    }


    class MyAdapter extends ArrayAdapter {
        ArrayList<String> OrderItemArray,OrderDateandTimeArray,OrderAmountArray;

        public MyAdapter(Context c, int x,ArrayList<String> listOrderItems, ArrayList<String> listOrderDateandTime,ArrayList<String> listOrderAmount) {
            super(c, R.layout.custom_orderlayout, R.id.itemname,listOrderItems);
            this.OrderItemArray = listOrderItems;
            this.OrderDateandTimeArray = listOrderDateandTime;
            this.OrderAmountArray = listOrderAmount;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            /*if(convertView==null)
                emptyText.setVisibility(View.VISIBLE);
            else
                emptyText.setVisibility(View.INVISIBLE);*/
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.custom_orderlayout, parent, false);
            TextView itemname = (TextView) v.findViewById(R.id.itemname);
            TextView dateandtime = (TextView) v.findViewById(R.id.dateandtime);
            TextView amount = (TextView) v.findViewById(R.id.totalamount);
            itemname.setText(OrderItemArray.get(position));
            dateandtime.setText(OrderDateandTimeArray.get(position));
            amount.setText(OrderAmountArray.get(position));

            /*itemname.setText(OrderItemArray.get(position));
            dateandtime.setText(OrderDateandTimeArray.get(position));
            amount.setText(OrderAmountArray.get(position));*/
            return v;

        }
    }
}
