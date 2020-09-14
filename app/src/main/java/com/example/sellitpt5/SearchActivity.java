package com.example.sellitpt5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.DialogTitle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SearchAdapter.SearchClickListeners{

    private static final String TAG = "SavedPostsActivity";
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_posts);
        recyclerView = (RecyclerView) findViewById(R.id.search_results_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




    }



    @Override
    public void viewSearchPostClickListener(PostDataSchemaWithId post_info) {

    }
}
