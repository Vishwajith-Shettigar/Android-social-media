package com.example.lykasocialmediajava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.lykasocialmediajava.Adapters.Searchuseradapter;
import com.example.lykasocialmediajava.Model.CommentsModel;
import com.example.lykasocialmediajava.Model.Getmaincontext;
import com.example.lykasocialmediajava.Model.Searchusermodel;
import com.example.lykasocialmediajava.Model.userApi;
import com.example.lykasocialmediajava.Model.userApiInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.guieffect.qual.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Innersearchactivity extends AppCompatActivity {
    RecyclerView searchinnerrecycler;
    SearchView innersearch;
ArrayList<Searchusermodel> searchusermodelArrayList;
FirebaseFirestore firebaseFirestore;
FirebaseAuth firebaseAuth;
    userApi userapi;
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

        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient= new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://192.168.43.214:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();


         userapi = retrofit.create(com.example.lykasocialmediajava.Model.userApi.class);

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
        Log.e("$","get data");

        Call<List<Searchusermodel>> call=userapi.apigetUsers(searchUsername);

          call.enqueue(new Callback<List<Searchusermodel>>() {
              @Override
              public void onResponse(Call<List<Searchusermodel>> call, Response<List<Searchusermodel>> response) {
                  if(!response.isSuccessful())
                  {
                      Log.e("#","not success");
                      return;
                  }

                  Log.e("#","success");

                  searchusermodelArrayList= (ArrayList<Searchusermodel>) response.body();
setrecyclerview();

                  List<Searchusermodel>se=response.body();


              }

              @Override
              public void onFailure(Call<List<Searchusermodel>> call, Throwable t) {
Log.e("#","fail"+ t.toString());

              }
          });
//
//




//        firebaseFirestore.collection("users").whereEqualTo("username",searchUsername)
//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                        Log.e("&"," got it");
//                        if(task.isSuccessful())
//                        {
//
//                            QuerySnapshot querySnapshot=task.getResult();
//                            ArrayList<DocumentSnapshot>documentSnapshotArrayList= (ArrayList<DocumentSnapshot>) querySnapshot.getDocuments();
//                            Log.e("&"," inside task");
//                            CommentsModel commentsModel;
//                            Log.e("&",task.getResult().size()+"--  ");
//                            for (int i=0;i<task.getResult().size();i++)
//                            {
//                                Map<String,Object> details=new HashMap<>();
//                                DocumentSnapshot documentSnapshot=documentSnapshotArrayList.get(i);
//                                details=documentSnapshot.getData();
//                                Searchusermodel searchusermodel =new Searchusermodel(
//                                        details.get("userID").toString(),
//                                        details.get("username").toString(),
//                                        details.get("name").toString(),
//                                        details.get("imageurl").toString()
//
//
//
//
//                                );
//
//                                searchusermodelArrayList .add(searchusermodel);
//
//                                setrecyclerview();
//
//
//                            }
//
//
//
//                        }
//
//                    }
//                });

    }
    private void setrecyclerview() {
        Searchuseradapter searchuseradapter=new Searchuseradapter(searchusermodelArrayList,this,Innersearchactivity.this);


        searchinnerrecycler.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        searchinnerrecycler.setLayoutManager(linearLayoutManager);
        searchinnerrecycler.setAdapter(searchuseradapter);
    }
    public void gotToprofile(String UID){



        Intent intent = new Intent(Innersearchactivity.this, MainActivity.class);
        intent.putExtra("replaceFragment", true);
        intent.putExtra("UID",UID);
        startActivity(intent);





    }


}