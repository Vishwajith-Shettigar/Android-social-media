package com.example.lykasocialmediajava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lykasocialmediajava.Adapters.CommentsAdapter;
import com.example.lykasocialmediajava.Adapters.PostAdapter;
import com.example.lykasocialmediajava.Model.CommentsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Commentactivity extends AppCompatActivity {
EditText comtext;
FirebaseAuth firebaseAuth;
FirebaseFirestore firebaseFirestore;

String post_ID,POST_USERID;
ArrayList<CommentsModel>commentsModelArrayList;
String comIDDelete="";
RecyclerView recyclerView;
    CommentsAdapter commentsAdapter;
    ImageView deletecmt;

ImageView comsend,commnectbackbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentactivity);
        commnectbackbtn=findViewById(R.id.commnectbackbtn);

        recyclerView=findViewById(R.id.commentrecycler);
deletecmt=findViewById(R.id.deletecmt);
firebaseAuth=FirebaseAuth.getInstance();
firebaseFirestore=FirebaseFirestore.getInstance();
commentsModelArrayList=new ArrayList<>();


        comtext=findViewById(R.id.comnttext);
        comsend=findViewById(R.id.sendbutn);
        Intent intent = getIntent();
       post_ID=intent.getStringExtra("postID");
       POST_USERID=intent.getStringExtra("postUserID");


        recyclerView.setHasFixedSize(true);
        getComments();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
         commentsAdapter=new CommentsAdapter(this,commentsModelArrayList,Commentactivity.this);
        recyclerView.setAdapter(commentsAdapter);





        commnectbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
comsend.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        if(comtext.getText().toString().toString().isEmpty()){

            Toast.makeText(Commentactivity.this, "Enter comment", Toast.LENGTH_SHORT).show();
        }else {

            CollectionReference commnetcollectionReference = firebaseFirestore.collection("Comments");

            Map<String, String> comdetails = new HashMap<>();
            comdetails.put("userID", firebaseAuth.getUid());
            comdetails.put("postID",post_ID );
            comdetails.put("postUserID",POST_USERID);
String comid=Math.random()+""+Math.random()+Math.random();
comdetails.put("comID",comid);
            comdetails.put("commenttext", comtext.getText().toString());


            commnetcollectionReference.document(comid).set(comdetails);

            comtext.setText("");

            getComments();
            commentsAdapter.notifyDataSetChanged();

        }

    }
});


    }

    public void getComments()

    {
        commentsModelArrayList.clear();
        recyclerView.clearFocus();
        Query query=firebaseFirestore.collection("Comments").whereEqualTo("postID",post_ID);
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
                        CommentsModel commentsModel1=new CommentsModel(details.get("comID").toString(),

                                details.get("userID").toString(),
                                details.get("commenttext").toString(),

                                details.get("postID").toString(),
                                details.get("postUserID").toString()
                        );

                        commentsModelArrayList.add(commentsModel1);
                        commentsAdapter.notifyDataSetChanged();



                    }

                    setrecyclerview();

                }
            }


        });
        deletecmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseFirestore.collection("Comments").document(comIDDelete).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                getComments();
                                setrecyclerview();
                            }
                        });
                comIDDelete="";
                deletecmt.setVisibility(View.GONE);
                Toast.makeText(Commentactivity.this, "dleted", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public  void  deleteComment(String commmentID)
    {

        comIDDelete=commmentID;
        deletecmt.setVisibility(View.VISIBLE);



    }
    private void setrecyclerview() {
        CommentsAdapter commentsAdapter=new CommentsAdapter(this,commentsModelArrayList,Commentactivity.this);
        recyclerView.setAdapter(commentsAdapter);
    }

    public void gotToprofile(String UID){



        Intent intent = new Intent(Commentactivity.this, MainActivity.class);
        intent.putExtra("replaceFragment", true);
        intent.putExtra("UID",UID);
        startActivity(intent);





    }

}