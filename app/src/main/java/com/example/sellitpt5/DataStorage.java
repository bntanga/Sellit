package com.example.sellitpt5;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class DataStorage {
    private static final String TAG = "DataStorage";
    private static int postNum = 0;
    private static FirebaseFirestore data_store  = FirebaseFirestore.getInstance();
    private static CollectionReference user_data_collection = FirebaseFirestore.getInstance().collection("Users");
    private static StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    public static FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private static String post_collection = "Posts";
    private static String tags_collection = "Tags";
    private static String tags_document = "Tags_document";
    private static String all_tags = "all_tags";


    private static String current_user_name = "";
    private static String current_user_id = "";
    private static String current_user_phone = "";

    public static String getCurrent_user_name() {
//        if (current_user_name == "")
//        {
//            Query query = user_data_collection.whereEqualTo("username",currentUser.getUid() );
//
//        }
    return current_user_name;
    }


    public static void setCurrent_user_name(String current_user_name) {
        DataStorage.current_user_name = current_user_name;
    }

    public static String getCurrent_user_id() {
        return currentUser.getUid();
    }



    public static String getCurrent_user_phone() {
        return current_user_phone;
    }

    public static void setCurrent_user_phone(String current_user_phone) {
        DataStorage.current_user_phone = current_user_phone;
    }

    public static CollectionReference getUser_data_collection() {
        return user_data_collection;
    }

    public static void setUser_data_collection(CollectionReference user_data_collection) {
        DataStorage.user_data_collection = user_data_collection;
    }

    public static StorageReference getmStorageRef() {
        return mStorageRef;
    }

    public interface GetPostCallBack{
        public void call_back(ArrayList<PostDataSchemaWithId> retrieved_posts);
    }
    public interface GetTagsCallBack{
        public void get_tags_call_back(ArrayList<String> the_tags);
    }

    public interface GetSavedPostsCallBack{
        public void get_saved_posts_call_back(ArrayList<String> the_posts);
    }
    public interface GetSavedPostsContentCallBack{
        public void get_saved_posts_content_callback(ArrayList<PostDataSchemaWithId> the_posts);
    }

    public static int getPostNum() {
        return postNum;
    }

    public static void setPostNum(int postNum) {
        DataStorage.postNum = postNum;
    }

    public static FirebaseFirestore getData_store() {
        return data_store;
    }

    public static void setData_store(FirebaseFirestore data_store) {
        DataStorage.data_store = data_store;
    }

    public static String getPost_collection() {
        return post_collection;
    }

    public static void setPost_collection(String post_collection) {
        DataStorage.post_collection = post_collection;
    }
    public static void actually_put_tags(final ArrayList<String> tags_input){
        HashMap <String, ArrayList<String>> tags_map = new HashMap<>();
        tags_map.put(all_tags, tags_input);
        data_store.collection(tags_collection).document(tags_document).set(tags_map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "Success!!!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,e.toString() );
            }
        });

    };

    public static ArrayList<String> parse_db_response(String response){
        String stripped = response.substring(1, response.length()-1);
        String[] ret_tags = stripped.split(", ");
        return new ArrayList<>(Arrays.asList(ret_tags));

    }
    //Method should be removed in final app
    public static void put_tags(final ArrayList<String> tags_input){
        Log.i(TAG, "method invoked");
        data_store.collection(tags_collection).document(tags_document).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        ArrayList res = documentSnapshot.toObject(ArrayList.class);
                        //Doing some weird things to update data
                        Object res = documentSnapshot.get(all_tags);
                        if (res == null){
                            actually_put_tags(tags_input);
                            return;
                        };
                        ArrayList<String> tags_array = parse_db_response(res.toString());
                        for (String tag_3: tags_input){
                            if (!tags_array.contains(tag_3)) {
                                tags_array.add(tag_3);
                            }
                        }
                        actually_put_tags(tags_array);
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, e.toString());
            }
        });
    };

    public static void getTags (final GetTagsCallBack tags_call_back){
        data_store.collection(tags_collection).document(tags_document).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Object res = documentSnapshot.get(all_tags);
                        if (res == null){
                            return;
                        };
                        ArrayList<String> tags_array = parse_db_response(res.toString());
                        tags_call_back.get_tags_call_back(tags_array);
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, e.toString());
            }
        });
    }


    public static void getUserSavedPostsId(final GetSavedPostsCallBack getSavedPostsCallBack){
        user_data_collection.document(getCurrent_user_id()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ArrayList<String> res = (ArrayList<String>) documentSnapshot.get("saved_posts");
                        if (res.size() == 0){
                            getSavedPostsCallBack.get_saved_posts_call_back( new ArrayList<String>());
                            return;
                        };
                        ArrayList<String> saved_posts_array = parse_db_response(res.toString());
                        getSavedPostsCallBack.get_saved_posts_call_back(saved_posts_array);
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, e.toString());
            }
        });

    }

    public static ArrayList<PostDataSchemaWithId> all_saved_posts = new ArrayList<PostDataSchemaWithId>();

    public static void getLessThan10Posts(ArrayList<String> post_ids, final GetSavedPostsContentCallBack getSavedPostsContentCallBack,
                                          final boolean morePostsComing){
        Log.i(TAG, "less than 10 thingy invoked");
        //hack because of error
//        post_ids.add("akdkfkfk");
        data_store.collection(post_collection).whereIn( FieldPath.documentId(), post_ids).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document:  queryDocumentSnapshots) {
                    PostDataSchemaWithId single_post = document.toObject(PostDataSchemaWithId.class);
                    single_post.setProduct_id(document.getId());
                    all_saved_posts.add(single_post);
                    Log.i(TAG, "All saved posts is " + all_saved_posts);

                }
                getSavedPostsContentCallBack.get_saved_posts_content_callback(all_saved_posts);
                if (!morePostsComing){
                    all_saved_posts = new ArrayList<PostDataSchemaWithId>();
                };


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, "Failed to get posts");
            }
        });



    }
    public static void getUserSavedPostsContent(final GetSavedPostsContentCallBack getSavedPostsContentCallBack){
        getUserSavedPostsId(new GetSavedPostsCallBack() {
            @Override
            public void get_saved_posts_call_back(ArrayList<String> the_posts) {

                ArrayList<String> temp10 = new ArrayList<String>();
                int counter = 0;
                for (String postId: the_posts){
                    temp10.add(postId);
                    counter++;
                    if (counter == 10){
                        getLessThan10Posts(temp10, getSavedPostsContentCallBack, true);
                        counter = 0;
                        temp10 = new ArrayList<String>();
                    }
                }
                getLessThan10Posts(temp10, getSavedPostsContentCallBack, false);

            }
        });


    }

    public static void setUserSavedPostsLiterally(ArrayList<String> the_posts){
        user_data_collection.document(getCurrent_user_id()).update("saved_posts", the_posts).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i(TAG, "added saved posts");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, "Failed to add tags");
            }
        });
    }

    public static void setUserSavedPosts(final String post_id){
        getUserSavedPostsId(new GetSavedPostsCallBack() {
            @Override
            public void get_saved_posts_call_back(ArrayList<String> the_posts) {
                Log.i(TAG ,"these are the posts");
                Boolean contains_id = the_posts.contains(post_id);
                if (contains_id){
                    the_posts.remove(post_id);
                }
                else {
                    the_posts.add(post_id);
                }
                setUserSavedPostsLiterally(the_posts);



            }
        });

    }

    public static void getAllPosts(final GetPostCallBack getPostCallBack){

        Log.i(TAG, "GET All POSTs invoked");
        data_store.collection(post_collection)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<PostDataSchemaWithId> retrieved_posts = new ArrayList<PostDataSchemaWithId>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PostDataSchemaWithId single_post = document.toObject(PostDataSchemaWithId.class);
                                single_post.setProduct_id(document.getId());
                                retrieved_posts.add(single_post);
                            }
                            getPostCallBack.call_back(retrieved_posts);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


    }

    public static void getUserPosts(final GetPostCallBack getPostCallBack, String user_id){

        Log.i(TAG, "GET All POSTs invoked");
        data_store.collection(post_collection).whereEqualTo("user_id", user_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<PostDataSchemaWithId> retrieved_posts = new ArrayList<PostDataSchemaWithId>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                PostDataSchemaWithId current_post = document.toObject(PostDataSchemaWithId.class);
                                current_post.setProduct_id(document.getId());
                                retrieved_posts.add(current_post);

                            }
                            getPostCallBack.call_back(retrieved_posts);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


    };


}
