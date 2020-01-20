package com.subhadeep.bitd_canteen.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.subhadeep.bitd_canteen.R;


import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    FloatingActionButton changeimage;
    ImageView imageView;
    private static int RESULT_LOAD_IMAGE = 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        changeimage = (FloatingActionButton) view.findViewById(R.id.changeimage);
        imageView = (ImageView) view.findViewById(R.id.profileimg);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        changeimage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("Its Working ", "belive me");
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("Its Working ", "belive me");
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Log.i("Its Working ", "belive me");


            Uri selectedImage = data.getData();


            /*String[] filePathColumn = {MediaStore.Images.Media.DATA};


            Log.i("Its Working ", "belive me");

            Cursor cursor = managedQuery(selectedImage,
                    filePathColumn, null, null, null);

            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();*/


            //imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            imageView.setImageURI(selectedImage);

        }
    }
}
