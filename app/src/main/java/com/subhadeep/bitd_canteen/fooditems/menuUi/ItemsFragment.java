package com.subhadeep.bitd_canteen.fooditems.menuUi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.subhadeep.bitd_canteen.R;
import com.subhadeep.bitd_canteen.fooditems.fooddata.FoodDetails;
import com.subhadeep.bitd_canteen.fooditems.fooddata.StorageClass;
import com.subhadeep.bitd_canteen.fooditems.solofoodUi.SelectedFood;


public class ItemsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_menu, container, false);

        final GridView gridview = (GridView) view.findViewById(R.id.gridview);

        StorageClass foodStorage = new StorageClass();


        gridview.setAdapter(new FoodAdapter(getContext(),foodStorage.getCatalogData()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                FoodDetails food = (FoodDetails) gridview.getItemAtPosition(position);
                Intent activity = new Intent(getContext(),SelectedFood.class);

               // Bundle bundle = new Bundle();

                activity.putExtra("myObject", food);

                startActivity(activity);
            }
        });
        return view;
    }
}
