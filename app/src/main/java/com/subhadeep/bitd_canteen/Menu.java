package com.subhadeep.bitd_canteen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class Menu extends AppCompatActivity {
    private ListView dataListView;
    private EditText itemText,priceText;
    private Button addButton;
    private Button deleteButton;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef;
    String ab[] = {"abc","def","ghi"};
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayList<String> listPrices = new ArrayList<String>();
    ArrayList<String> listKeys = new ArrayList<String>();

    //ArrayAdapter<String> adapter;
    MyAdapter adapter;
    private Boolean searchMode = false;
    private Boolean itemSelected = false;
    private int selectedPosition = 0;
    String sem,usertype,type;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        dbRef = database.getReference("menu/");


        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(Menu.this, LoginActivity.class));
                    finish();
                }
            }
        };


        dataListView = (ListView) findViewById(R.id.dataListView);
        itemText = (EditText) findViewById(R.id.itemText);
        priceText = findViewById(R.id.priceText);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        addButton = findViewById(R.id.addButton);

        dataListView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            //Person person = listPerson.get(i);
            AlertDialog.Builder buildernew = new AlertDialog.Builder(this);
                    buildernew.setTitle("Delete ");
                    buildernew.setMessage("Do you want to delete the selected record?");
                    buildernew.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dbRef.child(listKeys.get(selectedPosition)).removeValue();
                        }
                    });
                    buildernew.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    buildernew.create().show();
            return true;
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(v);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(v);
            }
        });
        /*adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice,
                listItems);*/
        adapter = new MyAdapter(Menu.this,listItems,listPrices);
        dataListView.setAdapter(adapter);
        //Toast.makeText(ClassAssignment_Faculty.this,listItems[0],Toast.LENGTH_LONG).show();
        dataListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        /*dataListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {
                        selectedPosition = position;
                        itemSelected = true;
                        deleteButton.setEnabled(true);
                    }
                });*/

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
                listItems.add((String) dataSnapshot.child("itemname").getValue());
                listPrices.add((String) dataSnapshot.child("price").getValue());
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
                    listItems.remove(index);
                    listKeys.remove(index);
                    adapter.notifyDataSetChanged();
                }
            }

        };
        dbRef.addChildEventListener(childListener);
    }

    public void addItem(View view) {

        String item = itemText.getText().toString();
        String price = priceText.getText().toString();
        String key = dbRef.push().getKey();

        itemText.setText("");
        priceText.setText("");
        dbRef.child(key).child("itemname").setValue(item);
        dbRef.child(key).child("price").setValue(price);

        adapter.notifyDataSetChanged();
    }

    public void deleteItem(View view) {
        dataListView.setItemChecked(selectedPosition, false);
        dbRef.child(listKeys.get(selectedPosition)).removeValue();
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
                signOut();
                Toast.makeText(this,"Signout",Toast.LENGTH_SHORT).show();
                break;


            case R.id.item2:
                finishAffinity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //sign out method
    public void signOut() {
        auth.signOut();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }


    class MyAdapter extends ArrayAdapter {
        ArrayList<String> titleArray,priceArray;

        public MyAdapter(Context c, ArrayList<String> listItems, ArrayList<String> listPrices) {
            super(c, R.layout.menu_food_item, R.id.foodname,listItems);
            this.priceArray = listPrices;
            this.titleArray = listItems;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.menu_food_item, parent, false);
            TextView fname = (TextView) v.findViewById(R.id.foodname);
            TextView price = (TextView) v.findViewById(R.id.foodprice);
            fname.setText(titleArray.get(position));
            price.setText(priceArray.get(position));
            return v;

        }
    }
}

