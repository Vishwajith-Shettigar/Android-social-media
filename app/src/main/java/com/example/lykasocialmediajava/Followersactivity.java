package com.example.lykasocialmediajava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.lykasocialmediajava.Adapters.Searchuseradapter;
import com.example.lykasocialmediajava.Model.CommentsModel;
import com.example.lykasocialmediajava.Model.Searchusermodel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Followersactivity extends AppCompatActivity {
    RecyclerView followerrecycler;


    ArrayList<Searchusermodel> searchusermodelArrayList;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    ArrayList<String>followers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followersactivity);
        followerrecycler=findViewById(R.id.followerrecycler);



        followers=new ArrayList<>();
        searchusermodelArrayList=new ArrayList<>();
        Intent intent= getIntent();
        firebaseFirestore=FirebaseFirestore.getInstance();
        followers= (ArrayList<String>) intent.getSerializableExtra("Follower");

        Log.e("@",followers.size()+"--- in following-------------");
        followerrecycler=findViewById(R.id.followerrecycler);
        getData();


    }

    public  void getData()
    {



        for(int i=0;i<followers.size();i++) {

            firebaseFirestore.collection("users").whereEqualTo("userID",followers.get(i) )
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            Log.e("&", " got it");
                            if (task.isSuccessful()) {

                                QuerySnapshot querySnapshot = task.getResult();
                                ArrayList<DocumentSnapshot> documentSnapshotArrayList = (ArrayList<DocumentSnapshot>) querySnapshot.getDocuments();
                                Log.e("&", " inside task");
                                CommentsModel commentsModel;
                                Log.e("&", task.getResult().size() + "--  ");
                                for (int i = 0; i < task.getResult().size(); i++) {
                                    Map<String, Object> details = new HashMap<>();
                                    DocumentSnapshot documentSnapshot = documentSnapshotArrayList.get(i);
                                    details = documentSnapshot.getData();
                                    Searchusermodel searchusermodel = new Searchusermodel(
                                            details.get("userID").toString(),
                                            details.get("username").toString(),
                                            details.get("name").toString(),
                                            details.get("imageurl").toString()


                                    );

                                    searchusermodelArrayList.add(searchusermodel);

                                    setrecyclerview();


                                }


                            }

                        }
                    });
        }


    }
    private void setrecyclerview() {
        Searchuseradapter searchuseradapter=new Searchuseradapter(searchusermodelArrayList,getApplicationContext());


        followerrecycler.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        followerrecycler.setLayoutManager(linearLayoutManager);
        followerrecycler.setAdapter(searchuseradapter);
    }



}