package com.example.lykasocialmediajava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.lykasocialmediajava.Adapters.Chatadapter;
import com.example.lykasocialmediajava.Adapters.CommentsAdapter;
import com.example.lykasocialmediajava.Model.Chatmodel;
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

public class Chatactivity extends AppCompatActivity {
FirebaseAuth firebaseAuth;
FirebaseFirestore firebaseFirestore;
ArrayList<Chatmodel> chatmodels;
Chatadapter chatadapter;

ImageView globalchatbtn;

    RecyclerView chatsrecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatactivity);
        chatsrecycler=findViewById(R.id.chatsrecycler);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        chatmodels=new ArrayList<>();
        chatadapter=new Chatadapter(chatmodels,this);
        globalchatbtn=findViewById(R.id.globalchatbtn);
        globalchatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Chatactivity.this,Globalchat.class));
            }
        });

Log.e("*","n chats" );
        getData();




        setrecyclerview();





    }
    public  void getData()
    {

        firebaseFirestore.collection("chats").whereEqualTo("fromUID",firebaseAuth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){

                            QuerySnapshot querySnapshot=task.getResult();
                            ArrayList<DocumentSnapshot>documentSnapshots= (ArrayList<DocumentSnapshot>) querySnapshot.getDocuments();
                            for(int i=0;i<task.getResult().size();i++)
                            {

                                DocumentSnapshot documentSnapshot=documentSnapshots.get(i);
                                Map<String,Object> details=new HashMap<>();
                                details=documentSnapshot.getData();
                                Chatmodel chatmodel=new Chatmodel(details.get("fromUID").toString(),
                                        details.get("toUID").toString(),
                                        details.get("convID").toString()
                                        );
                                chatmodels.add(chatmodel);
                                chatadapter.notifyDataSetChanged();



                            }

                        }
                    }
                });


        firebaseFirestore.collection("chats").whereEqualTo("toUID",firebaseAuth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){

                            QuerySnapshot querySnapshot=task.getResult();
                            ArrayList<DocumentSnapshot>documentSnapshots= (ArrayList<DocumentSnapshot>) querySnapshot.getDocuments();
                            for(int i=0;i<task.getResult().size();i++)
                            {

                                DocumentSnapshot documentSnapshot=documentSnapshots.get(i);
                                Map<String,Object> details=new HashMap<>();
                                details=documentSnapshot.getData();
                                Chatmodel chatmodel=new Chatmodel(details.get("fromUID").toString(),
                                        details.get("toUID").toString(),
                                        details.get("convID").toString()
                                );
                                chatmodels.add(chatmodel);
                                chatadapter.notifyDataSetChanged();



                            }

                        }
                    }
                });
        setrecyclerview();

    }
    private void setrecyclerview() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        chatsrecycler.setLayoutManager(linearLayoutManager);

        chatadapter=new Chatadapter(chatmodels,this);
        chatsrecycler.setAdapter(chatadapter);
    }


}