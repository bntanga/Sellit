package com.example.sellitpt5;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;

import java.util.ArrayList;

public class UserPostsActivity extends AppCompatActivity implements DataStorage.GetPostCallBack, FeedAdapter.AllPostClickListeners {
    private RecyclerView recyclerView;
    private static final String TAG = "UserPostsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_posts);

        recyclerView = (RecyclerView) findViewById(R.id.user_posts_container_account);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        this.getSupportActionBar().hide();
        DataStorage.getUserPosts(this, DataStorage.getCurrent_user_id());

    }


    @Override
    public void call_back(ArrayList<PostDataSchemaWithId> retrieved_posts) {
        Log.i(TAG, "this is what's found " + retrieved_posts.toString());
        FeedAdapter feedAdapter = new FeedAdapter(100,this, this, retrieved_posts, TAG);
        recyclerView.setAdapter(feedAdapter);

    }




    @Override
    public void viewPostClickListener(PostDataSchemaWithId post_info) {

    }

    @Override
    public void savePostClickListener(String id) {

    }
}
