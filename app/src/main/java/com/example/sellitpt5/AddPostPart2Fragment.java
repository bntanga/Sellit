package com.example.sellitpt5;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddPostPart2Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private static final String TAG = "AddPostPart2Fragment";

    private TextInputEditText phone_number_inp, location_inp,description_inp ;
    private View current_frag_p2;
    private LinearLayout submit_button;
    private FlexboxLayout flexboxLayout;
    private RecyclerView tags_recycler_view;


    //
    public ArrayList<String> selected_tags = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private PostDataSchema post_data;

    public AddPostPart2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddPostPart2Fragment.
     */
    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            post_data = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        current_frag_p2 =  inflater.inflate(R.layout.fragment_add_post_part2, container, false);
        phone_number_inp = current_frag_p2.findViewById(R.id.phone_number_part2);
        location_inp = current_frag_p2.findViewById(R.id.poster_location_part2);
        description_inp = current_frag_p2.findViewById(R.id.item_description_part2);
        submit_button = current_frag_p2.findViewById(R.id.submit_part2);
        tags_recycler_view = current_frag_p2.findViewById(R.id.tags_recycler_view);
        DataStorage.getTags(new DataStorage.GetTagsCallBack() {
            @Override
            public void get_tags_call_back(ArrayList<String> the_tags) {
                getTagsCallBack(the_tags);
            }
        });
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitCurrentData();
            }
        });


        return current_frag_p2;

    }

    public class TagSelector implements TagsAdapter.TagsClickListener{

        @Override
        public void tagsClickListener(String tag) {
            selected_tags.add(tag);
        }
    }
    public void getTagsCallBack(final ArrayList<String> the_tags){
        tags_recycler_view.setAdapter( new TagsAdapter(the_tags, getActivity(), new TagSelector(), TAG ) );
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getActivity());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        tags_recycler_view.setLayoutManager(layoutManager);


    }
    private void submitCurrentData(){

        String phone_number = phone_number_inp.getText().toString();
        String location = location_inp.getText().toString();
        String description = description_inp.getText().toString();

        MainActivity parentActivity = (MainActivity) getActivity();
        parentActivity.addIntermediateData("phone_number", phone_number);
        parentActivity.addIntermediateData("location", location);
        parentActivity.addIntermediateData("description", description);
        parentActivity.addIntermediateData("tags", selected_tags.toString());
        parentActivity.submitPost();

    };
}
