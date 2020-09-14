package com.example.sellitpt5;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.DialogTitle;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.net.URI;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class AddPostFragment extends Fragment {

    private static final String TAG = "AddPostFragment";
    //all the views
    public int UploadImageIntent = 1;
    View this_fragment_view;
    TextInputEditText name_of_product, price_of_product;
    LinearLayout continue_button,  upload_image;
    Uri image_path;
    ImageView image_view;
    TextView no_image;


    //Strings to avoid hard coding
    public AddPostFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final MainActivity parentActivity = (MainActivity) getActivity();
        this_fragment_view = inflater.inflate(R.layout.fragment_add_post, container, false);
        continue_button = this_fragment_view.findViewById(R.id.next_page_add_info_button);
        upload_image = this_fragment_view.findViewById(R.id.upload_image_button);
        image_view = this_fragment_view.findViewById(R.id.post_image);
        no_image = this_fragment_view.findViewById(R.id.no_image_uploaded_text);
        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, UploadImageIntent);

            }
        });
        continue_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_next_input();
            }
        });
        return this_fragment_view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UploadImageIntent && resultCode == Activity.RESULT_OK) {

            image_path = data.getData();
            Picasso.get().load(image_path).fit().centerCrop().into(image_view);
            no_image.setVisibility(View.GONE);
            image_view.setVisibility(View.VISIBLE);


        }

    }

    private void go_to_next_input(){
        Log.i(TAG, "on click invoked");
        name_of_product = this_fragment_view.findViewById(R.id.product_name_input);
        price_of_product = this_fragment_view.findViewById(R.id.product_price_input);
        String product_name = name_of_product.getText().toString();
        String product_price = price_of_product.getText().toString();
        Log.i(TAG, " PRODUCT NAME " + product_name);
        Log.i(TAG, "PRODUCT PRICE " + product_price);
        if (product_name == null || product_price == null ){
            Log.i(TAG, "why you happening");
            return;
        }
        MainActivity parentActivity = (MainActivity) getActivity();
        parentActivity.addIntermediateData("product_name", product_name);
        parentActivity.addIntermediateData("product_price", product_price);
        parentActivity.addIntermediateData("image_path", image_path.toString());
        //remove this information. For testing purposes only
        parentActivity.goToPart2AddPostFragment();

    };


}
