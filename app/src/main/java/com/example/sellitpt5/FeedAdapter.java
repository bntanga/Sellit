package com.example.sellitpt5;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder>{
    private static final String TAG = "FeedAdapter";
    AllPostClickListeners listeners;
    int num_items_to_display;
    Context context;
    ArrayList<PostDataSchemaWithId> retrieved_posts;
    String from_where;


    public interface AllPostClickListeners {
        void viewPostClickListener(PostDataSchemaWithId post_info);
        void savePostClickListener(String id);
    };

    public FeedAdapter(int num_items_to_display, Context context, AllPostClickListeners listeners, ArrayList<PostDataSchemaWithId> retrieved_posts , String from_where){
        this.num_items_to_display = num_items_to_display;
        this.context = context;
        this.listeners = listeners;
        this.retrieved_posts = retrieved_posts;
        //From where is used to make slight modifications to the feed card depending on what component is calling it.
        this.from_where = from_where;
    }
    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.feed_card;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        FeedViewHolder viewHolder = new FeedViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return retrieved_posts.size();
    }


    public class FeedViewHolder extends  RecyclerView.ViewHolder {

        public TextView item_name;
        public TextView item_price;
        public ImageView item_image;
        public View item_view;
        public View delete_post_button;
        public LinearLayout save_post;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name_feed_card);
            item_price = itemView.findViewById(R.id.item_price_feed_card);
            item_image = itemView.findViewById(R.id.image_source_feed_card);
            delete_post_button = itemView.findViewById(R.id.delete_post_layout);
            save_post = itemView.findViewById(R.id.save_post_button);
            if (from_where == "FeedFragment"){
                delete_post_button.setVisibility(View.GONE);
            }
            if (from_where == "SavedPostsActivity"){
                delete_post_button.setVisibility(View.GONE);
            }

            this.item_view = itemView;


        }


        public void bind(final int position){
            PostDataSchema current_view = retrieved_posts.get(position);
            item_name.setText(current_view.getProduct_name());
            item_price.setText("K " + current_view.getProduct_price());
            item_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listeners.viewPostClickListener( retrieved_posts.get(position));
                }
            });

            save_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "supposed to save post");
                    listeners.savePostClickListener(retrieved_posts.get(position).getProduct_id());


                }
            });



            Picasso.get().load(current_view.image_uri).fit().centerCrop().into(item_image);


        }
    }
}