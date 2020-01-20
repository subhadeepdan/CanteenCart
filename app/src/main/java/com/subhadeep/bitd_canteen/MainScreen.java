package com.subhadeep.bitd_canteen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.subhadeep.bitd_canteen.fooditems.fooddata.StorageClass;
import com.subhadeep.bitd_canteen.fooditems.menuUi.ItemsFragment;
import com.subhadeep.bitd_canteen.plate.PlateFragment;
import com.subhadeep.bitd_canteen.profile.ProfileFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainScreen extends AppCompatActivity {

    // double tapping feature
    boolean backPressed ;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

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
                    startActivity(new Intent(MainScreen.this, LoginActivity.class));
                    finish();
                }
                else{
                    String name = user.getEmail();
                    //Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();
                }
            }
        };

        StorageClass foodStorage = new StorageClass();
        Intent intent = getIntent();
        /*if (intent != null && intent.getExtras().getString("From").equals("SelectedFood")){
            View view = findViewById(R.id.plate);
            plateButton(view);
        }*/
        //else {
            /*
        setting data on creation of app
        */  foodStorage.setCatalogData();      //setting the data

            View view = findViewById(R.id.home);
            homeButton(view);
        //}
        backPressed = false;
    }


    /*
    *  handling the back press button
    *  below
    */


    @Override
    public void onBackPressed() {
        Fragment checking = getSupportFragmentManager().findFragmentByTag("Main_Menu");
        if (checking!=null && checking.isVisible()){
            if (backPressed){
                super.onBackPressed();
            }
            else {
                backPressed = true;
                Toast.makeText(this,"Press Again To Exit", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            ItemsFragment itemsFragment = new ItemsFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, itemsFragment,"Main_Menu");

            /*FragmentManager fm = getSupportFragmentManager();
            fm.popBackStack();*/

            ft.commit();
            Log.i("fragchecking ", Integer.toString(getFragmentManager().getBackStackEntryCount()));

        }
    }

    /*
    when home button is pressed below method executes ...
     */
    public void homeButton(View view) {
        //buttonHome.setTextColor(Color.parseColor("#0000ff"));
        backPressed = false;
        Fragment checking = getSupportFragmentManager().findFragmentByTag("Main_Menu");
        if (checking==null || !checking.isVisible()){

            //Toast.makeText(this,"Menu Pressed",Toast.LENGTH_SHORT).show();
            ItemsFragment itemsFragment = new ItemsFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, itemsFragment,"Main_Menu");
            ft.commit();
            /*
            checking the pop buttomns
            */

            //ft.addToBackStack("Main_Menu");

            //getSupportFragmentManager().popBackStack();

            Log.i("fragchecking ", Integer.toString(getFragmentManager().getBackStackEntryCount()));
        }
    }
    /*
    when PLATE button is pressed below method executes ...
     */
    public void plateButton(View view) {
        backPressed = false;
        Fragment checking = getSupportFragmentManager().findFragmentByTag("PLATE_VIEW");
        if (checking==null || !checking.isVisible()) {

            //Toast.makeText(this,"Plate Pressed",Toast.LENGTH_SHORT).show();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            PlateFragment plateFragment = new PlateFragment();
            ft.replace(R.id.container, plateFragment, "PLATE_VIEW");


            /*FragmentManager fm = getSupportFragmentManager();
            fm.popBackStack();*/
            ft.commit();
            Log.i("fragchecking ", Integer.toString(getFragmentManager().getBackStackEntryCount()));
        }
    }

    public void profileButton(View view) {
        backPressed = false;
        Fragment checking = getSupportFragmentManager().findFragmentByTag("PROFILE_VIEW");
        if (checking==null || !checking.isVisible()) {

            //Toast.makeText(this,"Profile Pressed",Toast.LENGTH_SHORT).show();
            ProfileFragment profileFragment = new ProfileFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, profileFragment, "PROFILE_VIEW");


            /*FragmentManager fm = getSupportFragmentManager();
            fm.popBackStack();*/
            ft.commit();

            Log.i("fragchecking ", Integer.toString(getFragmentManager().getBackStackEntryCount()));
        }
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
                Intent intent = new Intent(MainScreen.this,MyOrders.class);
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
}
