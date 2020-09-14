package com.example.sellitpt5;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagsViewHolder>{
    ArrayList<String> tags;
    Context context;
    TagsClickListener listener;
   String where_from;
    private static final String TAG = "TagsAdapter";


    public interface TagsClickListener{
        void tagsClickListener(String tag);
    };

    public TagsAdapter( ArrayList<String> tags, Context context, TagsClickListener listener, String where_from){
            this.tags = tags;
            this.context = context;
            this.listener = listener;
            this.where_from = where_from;
    }
    @NonNull
    @Override
    public TagsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType ) {
        int layoutIdForListItem = 0;
        if(where_from == "AddPostPart2Fragment") {
            layoutIdForListItem = R.layout.tag_item;
        }
        if (where_from == "PostInfoFragment"){
            layoutIdForListItem = R.layout.tag_item_post_info;
            Log.i(TAG, "entered post info");
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        TagsViewHolder viewHolder = new TagsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TagsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }


    public class TagsViewHolder extends  RecyclerView.ViewHolder {

        private static final String TAG = "TagsViewHolder";

        public TextView tag_view;
        public View item_view;

        public TagsViewHolder(@NonNull View itemView) {
            super(itemView);
            if (where_from == "AddPostPart2Fragment") {
                tag_view = itemView.findViewById(R.id.tag_view);
            }
            if (where_from == "PostInfoFragment"){
                tag_view = itemView.findViewById(R.id.tag_view_post_info);
            }
            this.item_view = itemView;

        }


        public void bind(final int position){
            tag_view.setText(tags.get(position));
            if (where_from== "AddPostPart2Fragment") {
                tag_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "these are tags " + tags);
                        //Change this in future
                        v.setBackgroundColor(0xFF00FF00);
                        listener.tagsClickListener(tags.get(position));
                    }
                });

            }

        }
    }
}
