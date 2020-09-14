package com.example.sellitpt5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment implements DataStorage.GetPostCallBack{
    private static final String TAG = "FeedFragment";
    View feed_file;
    private RecyclerView recyclerView;
    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        feed_file = inflater.inflate(R.layout.feed_fragment, container, false);
        recyclerView = (RecyclerView) feed_file.findViewById(R.id.feed_container);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        MainActivity parentActivity = (MainActivity) getActivity();

        DataStorage.getAllPosts(this);


//        Log.i(TAG, "this is feed news" + feed_news.toString());
        return feed_file;
    }


    @Override
    public void call_back(ArrayList<PostDataSchemaWithId> retrieved_posts) {
        Log.i(TAG, "this is what's found " + retrieved_posts.toString());
        MainActivity parentActivity = (MainActivity) getActivity();
        FeedAdapter feedAdapter = new FeedAdapter(100, parentActivity, parentActivity, retrieved_posts, TAG);
        recyclerView.setAdapter(feedAdapter);

    }
}
