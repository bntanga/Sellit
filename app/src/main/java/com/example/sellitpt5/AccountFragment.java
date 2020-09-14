package com.example.sellitpt5;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.card.MaterialCardView;

import javax.xml.parsers.SAXParser;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    LinearLayout notifications, your_posts, saved_posts, edit_info;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View account_layout = inflater.inflate(R.layout.fragment_account, container, false);
        notifications = account_layout.findViewById(R.id.notifications_card_account);
        your_posts = account_layout.findViewById(R.id.posts_card_account);
        saved_posts = account_layout.findViewById(R.id.saved_posts_card_account);
        edit_info = account_layout.findViewById(R.id.edit_info_card_account);
        final MainActivity parentActivity = (MainActivity) getActivity();
        your_posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getActivity(), UserPostsActivity.class));
            }
        });
        edit_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.changeToEditInfoFragment();
            }
        });
        saved_posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getActivity(), SavedPostsActivity.class));

            }
        });
        return account_layout;

    }



    private void user_posts(){


    };
}
