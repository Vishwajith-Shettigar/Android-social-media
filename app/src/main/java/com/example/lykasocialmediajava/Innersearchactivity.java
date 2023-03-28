package com.example.lykasocialmediajava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.lykasocialmediajava.Adapters.LikesAdapter;
import com.example.lykasocialmediajava.Adapters.Searchuseradapter;
import com.example.lykasocialmediajava.Model.CommentsModel;
import com.example.lykasocialmediajava.Model.LikesModel;
import com.example.lykasocialmediajava.Model.Searchusermodel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Innersearchactivity extends AppCompatActivity {
    RecyclerView searchinnerrecycler;
    SearchView innersearch;
ArrayList<Searchusermodel> searchusermodelArrayList;
FirebaseFirestore firebaseFirestore;
FirebaseAuth firebaseAuth;

String searchUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_innersearchactivity);
        searchusermodelArrayList=new ArrayList<>();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        innersearch=findViewById(R.id.innersearch);

        searchinnerrecycler=findViewById(R.id.searchinnerrecycler);

        innersearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                searchUsername=newText;
                searchusermodelArrayList.clear();
                getData();
                return false;
            }
        });







    }
    public  void getData()
    {


        firebaseFirestore.collection("users").whereEqualTo("username",searchUsername)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        Log.e("&"," got it");
                        if(task.isSuccessful())
                        {

                            QuerySnapshot querySnapshot=task.getResult();
                            ArrayList<DocumentSnapshot>documentSnapshotArrayList= (ArrayList<DocumentSnapshot>) querySnapshot.getDocuments();
                            Log.e("&"," inside task");
                            CommentsModel commentsModel;
                            Log.e("&",task.getResult().size()+"--  ");
                            for (int i=0;i<task.getResult().size();i++)
                            {
                                Map<String,Object> details=new HashMap<>();
                                DocumentSnapshot documentSnapshot=documentSnapshotArrayList.get(i);
                                details=documentSnapshot.getData();
                                Searchusermodel searchusermodel =new Searchusermodel(
                                        details.get("userID").toString(),
                                        details.get("username").toString(),
                                details.get("name").toString(),
                                details.get("imageurl").toString()




                                );

                               searchusermodelArrayList .add(searchusermodel);

                                setrecyclerview();


                            }



                        }

                    }
                });


    }
    private void setrecyclerview() {
        Searchuseradapter searchuseradapter=new Searchuseradapter(searchusermodelArrayList,getApplicationContext());


        searchinnerrecycler.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        searchinnerrecycler.setLayoutManager(linearLayoutManager);
        searchinnerrecycler.setAdapter(searchuseradapter);
    }
}