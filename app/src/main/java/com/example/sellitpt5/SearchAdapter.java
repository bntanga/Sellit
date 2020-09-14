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


class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{
    private static final String TAG = "FeedAdapter";
    SearchClickListeners listeners;
    int num_items_to_display;
    Context context;
    ArrayList<PostDataSchemaWithId> retrieved_posts;
    String from_where;


    public interface SearchClickListeners {
        void viewSearchPostClickListener(PostDataSchemaWithId post_info);
    };

    public SearchAdapter(int num_items_to_display, Context context, SearchClickListeners listeners, ArrayList<PostDataSchemaWithId> retrieved_posts){
        this.num_items_to_display = num_items_to_display;
        this.context = context;
        this.listeners = listeners;
        this.retrieved_posts = retrieved_posts;
        //From where is used to make slight modifications to the feed card depending on what component is calling it.
    }
    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.search_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        SearchViewHolder viewHolder = new SearchViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return retrieved_posts.size();
    }


    public class SearchViewHolder extends  RecyclerView.ViewHolder {

        public TextView item_name;
        public TextView item_price, item_poster;

        public ImageView item_image;
        public View item_view;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.post_name_search);
            item_price = itemView.findViewById(R.id.item_price_search);
            item_image = itemView.findViewById(R.id.item_image_search);
            item_poster = itemView.findViewById(R.id.poster_search);
            this.item_view = itemView;


        }


        public void bind(final int position){
            PostDataSchema current_view = retrieved_posts.get(position);
            item_name.setText(current_view.getProduct_name());
            item_price.setText("K " + current_view.getProduct_price());
            item_poster.setText(current_view.getUser_name());
            item_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listeners.viewSearchPostClickListener( retrieved_posts.get(position));
                }
            });

            Picasso.get().load(current_view.image_uri).fit().centerCrop().into(item_image);


        }
    }
}