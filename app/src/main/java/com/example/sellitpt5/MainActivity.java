package com.example.sellitpt5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, FeedAdapter.AllPostClickListeners {
    private static final String TAG = "MainActivity";
    BottomNavigationView bottomNavigationView;
    // information for the feed post fragment to hold
    //not to be confused with variable postdataschema
    PostDataSchema post_info_frag_stuff;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home_icon);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, feedFragment).commit();






    }
    // all the fragments
    FeedFragment feedFragment = new FeedFragment();
    AddPostFragment addPostFragment = new AddPostFragment();
    AccountFragment accountFragment = new AccountFragment();
    PostInfoFragment postInfoFragment = new PostInfoFragment();
    AddPostPart2Fragment addPostPart2Fragment = new AddPostPart2Fragment();
    EditAddressFragment editAddressFragment = new EditAddressFragment();


    // all the add post inputs stuff
    //not to be confused with post_info_frag stuff
    private PostDataSchema postDataSchema = new PostDataSchema();
    Uri image_uri;
    String image_download_uri;




    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        ActionBar actionBar = getSupportActionBar();
            actionBar.setBackgroundDrawable(new ColorDrawable(0xFFE5E5E5));
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        switch (item.getItemId()){

            case R.id.home_icon:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, feedFragment).commit();
                getSupportActionBar().hide();
                ArrayList<String> sample_tags  = new ArrayList<String>();
                sample_tags.add("Room equipment");
                sample_tags.add("Accessories");
                sample_tags.add("Phones");
                sample_tags.add("Books");
                sample_tags.add("Food");
                Log.i(TAG, "GOING TO CALL");
                DataStorage.put_tags(sample_tags);
                Log.i(TAG, "done calling method");
                break;
            case R.id.add_icon:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, addPostFragment).commit();
                actionBar.setCustomView(R.layout.tool_bar_addpost);
                actionBar.show();
                break;
            case R.id.profile_icon:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, accountFragment).commit();
                actionBar.setCustomView(R.layout.tool_bar_account);
                actionBar.show();
                break; }
        return true;
    }


    @Override
    public void viewPostClickListener(PostDataSchemaWithId post_info ) {
        post_info_frag_stuff = post_info;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, postInfoFragment).commit();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFFE5E5E5));

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);


        actionBar.setCustomView(R.layout.toolbar);
        actionBar.show();
        ImageView back_icon = findViewById(R.id.back_to_home);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView.setSelectedItemId(R.id.home_icon);


            }
        });


    }

    @Override
    public void savePostClickListener(String id) {
        DataStorage.setUserSavedPosts(id);
    }

    public void changeToEditInfoFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,editAddressFragment).commit();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFFE5E5E5));

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);


        actionBar.setCustomView(R.layout.toolbar_edit_info);
        actionBar.show();

    }

    //takes user to next fragment when next button is clicked on the add post fragment
    public void goToPart2AddPostFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, addPostPart2Fragment).commit();
    }

    public void addIntermediateData(String key, String value){
        switch (key) {

            case "description":
                postDataSchema.setDescription(value);
                break;
            case "location":
                postDataSchema.setLocation(value);
                break;
            case "product_name":
                postDataSchema.setProduct_name(value);
                break;
            case "product_price":
                postDataSchema.setProduct_price(value);
                break;
            case "phone_number":
                postDataSchema.setPhone_number(value);
                break;
            case "image_path":
                image_uri = Uri.parse(value);
                Log.i(TAG, "am I receiving right URI" + image_uri.toString());
                break;
            case "tags":
                // dont know why I can't pass generic type in java function
                postDataSchema.setTags(DataStorage.parse_db_response(value));
                break;
            default:
                Log.i(TAG, "Invalid tag");

        };
    };

    public String GetCurrentDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyyy");
        Date date = new Date();
        return formatter.format(date).toString();

    }

    public void UploadDataWithImageUri( String image_new_uri){

        //After first call back has finished, set new data

        postDataSchema.setImage_uri(image_new_uri);
        postDataSchema.setUser_id(DataStorage.getCurrent_user_id());
        postDataSchema.setUser_name(DataStorage.getCurrent_user_name());
        postDataSchema.setDate(GetCurrentDate());
        DataStorage.getData_store().collection(DataStorage.getPost_collection())
                .add(postDataSchema)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        int new_num_posts = DataStorage.getPostNum();
                        DataStorage.setPostNum(new_num_posts+1);
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    };
    public void submitPost(){

        final StorageReference postPhotos = DataStorage.getmStorageRef().child("PostPhotos").child("User1").child(image_uri.getLastPathSegment());
        postPhotos.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(MainActivity.this, "picture added", Toast.LENGTH_LONG).show();
                Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
                while (!firebaseUri.isComplete()){}
                Uri final_thing = firebaseUri.getResult();
                Log.i(TAG, "string URI " + final_thing);
                UploadDataWithImageUri(final_thing.toString());
                bottomNavigationView.setSelectedItemId(R.id.home_icon);





            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, "this is the error " + e.toString());
            }
        });
    }
}