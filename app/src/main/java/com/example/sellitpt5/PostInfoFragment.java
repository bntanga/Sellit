package com.example.sellitpt5;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


public class PostInfoFragment extends Fragment {

    // TODO: Rename and change types of parameters
    private ImageView image_view, back_icon;
    private TextView item_price, date;
    private TextView item_name;
    private TextView item_description;
    private TextView seller_address;
    private TextView seller_phone_number;
    private RecyclerView tags_recycler_view;
    private static final String TAG = "PostInfoFragment";

    public PostInfoFragment() {
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
        View feed_file = inflater.inflate(R.layout.fragment_post_info, container, false);
        image_view = feed_file.findViewById(R.id.pic_post_info);
        item_name = feed_file.findViewById(R.id.item_name_post_info);
        item_price = feed_file.findViewById(R.id.item_price_post_info);
        item_description = feed_file.findViewById(R.id.description_post_info);
        seller_address = feed_file.findViewById(R.id.address_post_info);
        seller_phone_number = feed_file.findViewById(R.id.phone_number_post_info);
        tags_recycler_view = feed_file.findViewById(R.id.tags_recycler_view_post_info);
        date = feed_file.findViewById(R.id.date_post_posted);
        MainActivity parentActivity = (MainActivity) getActivity();
        PostDataSchema info = parentActivity.post_info_frag_stuff;

        date.setText("Posted on " + info.getDate());
        item_name.setText(info.getProduct_name());
        item_price.setText(info.getProduct_price());
        Picasso.get().load(info.getImage_uri()).fit().centerCrop().into(image_view);
        seller_address.setText(info.getDescription());
        seller_phone_number.setText(info.getPhone_number());
        item_description.setText(info.getDescription());

        Log.i(TAG, "these are tags  " + info.getTags().toString());
        tags_recycler_view.setAdapter( new TagsAdapter(info.getTags(), getActivity(), null, TAG ) );
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getActivity());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        tags_recycler_view.setLayoutManager(layoutManager);




        return feed_file;
    }


}
