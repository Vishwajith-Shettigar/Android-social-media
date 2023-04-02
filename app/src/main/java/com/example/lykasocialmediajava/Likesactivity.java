package com.example.lykasocialmediajava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.lykasocialmediajava.Adapters.CommentsAdapter;
import com.example.lykasocialmediajava.Adapters.LikesAdapter;
import com.example.lykasocialmediajava.Model.CommentsModel;
import com.example.lykasocialmediajava.Model.LikesModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Likesactivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    String post_ID;
    ArrayList<LikesModel> Likesarraylist;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likesactivity);
        ArrayList<LikesModel> Likesarraylist;
        Intent intent = getIntent();
        post_ID=intent.getStringExtra("postID");
        recyclerView=findViewById(R.id.likesrecyclerview);
        getLikes();


    }

    public void getLikes()

    {
        firebaseFirestore=FirebaseFirestore.getInstance();

      Likesarraylist=new ArrayList<>();
        Likesarraylist.clear();
        recyclerView.clearFocus();
        Query query=firebaseFirestore.collection("Likes").whereEqualTo("postID",post_ID);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    QuerySnapshot querySnapshot=task.getResult();
                    ArrayList<DocumentSnapshot>documentSnapshotArrayList= (ArrayList<DocumentSnapshot>) querySnapshot.getDocuments();

                    CommentsModel commentsModel;
                    Log.e("*",task.getResult().size()+"");
                    for (int i=0;i<task.getResult().size();i++)
                    {
                        Map<String,Object> details=new HashMap<>();
                        DocumentSnapshot documentSnapshot=documentSnapshotArrayList.get(i);
                        details=documentSnapshot.getData();
                        LikesModel likesModel=new LikesModel(
                                details.get("postID").toString(),
                                details.get("userID").toString()



                        );

                        Likesarraylist.add(likesModel);




                    }

                    setrecyclerview();

                }
            }


        });

    }
    private void setrecyclerview() {
        LikesAdapter likesAdapter=new LikesAdapter(getApplicationContext(),Likesarraylist);


        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(likesAdapter);
    }

}