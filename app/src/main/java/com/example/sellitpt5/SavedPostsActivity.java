package com.example.sellitpt5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.DialogTitle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class SavedPostsActivity extends AppCompatActivity implements DataStorage.GetSavedPostsContentCallBack, FeedAdapter.AllPostClickListeners{

    private static final String TAG = "SavedPostsActivity";
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_posts);
        recyclerView = (RecyclerView) findViewById(R.id.saved_posts_container_account);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DataStorage.getUserSavedPostsContent(this);



    }

    @Override
    public void get_saved_posts_content_callback(ArrayList<PostDataSchemaWithId> the_posts) {
        Log.i(TAG, "call back called with posts" + the_posts.toString());
        FeedAdapter feedAdapter = new FeedAdapter(100,this, this, the_posts, TAG);
        recyclerView.setAdapter(feedAdapter);
    }

    @Override
    public void viewPostClickListener(PostDataSchemaWithId post_info) {

    }

    @Override
    public void savePostClickListener(String id) {

    }
}
