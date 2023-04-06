package com.example.lykasocialmediajava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lykasocialmediajava.Model.Getmaincontext;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class MainActivity extends AppCompatActivity   {

    BottomNavigationView bottomNavigationView;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// initializing
        Getmaincontext.setContext(MainActivity.this);



        bottomNavigationView=findViewById(R.id.bottom_navigation);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();


        // setting default fragment homfragment




        boolean replaceFragment = getIntent().getBooleanExtra("replaceFragment", false);

        if (replaceFragment) {
            Log.e("*","we are dumb");
            Log.e("*","we are dumb");
            FragmentManager fragmentManager =
                    getSupportFragmentManager();

            Bundle bundle = new Bundle();

            bundle.putBoolean("owner",  false);
            bundle.putString("userID",getIntent().getStringExtra("UID"));



            Profilefragment profilefragment = new Profilefragment();
            profilefragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();

            fragmentTransaction.replace
                    (R.id.fragment_contnair, profilefragment).addToBackStack( "tag" ).commit();

        }else{

            FragmentManager fragmentManager =
                    getSupportFragmentManager();
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();

            fragmentTransaction.replace
                    (R.id.fragment_contnair, new Homefragment()).commit();
        }


        // *********all about bottom navigationview and its menu  and fragment*************



        com.google.android.material.badge.BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.favouritemenu);
        badge.setVisible(true);
        badge.setNumber(10);

//        badge.clearNumber();
//        badge.setVisible(false);



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {


               switch (item.getItemId()){


                   case R.id.homemenu: {
                       Log.e("*", "indide case");
                       Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                       FragmentManager fragmentManager =
                               getSupportFragmentManager();
                       FragmentTransaction fragmentTransaction =
                               fragmentManager.beginTransaction();

                       fragmentTransaction.replace
                               (R.id.fragment_contnair, new Homefragment()).commit();
                   }
                       break;

                   case R.id.searchmenu: {
                       Toast.makeText(MainActivity.this, "search", Toast.LENGTH_SHORT).show();
                       FragmentManager fragmentManager =
                               getSupportFragmentManager();
                       FragmentTransaction fragmentTransaction =
                               fragmentManager.beginTransaction();

                       fragmentTransaction.replace
                               (R.id.fragment_contnair, new Searchfragment()).commit();


                   }

                   break;
                   case R.id.favouritemenu: {
                       Toast.makeText(MainActivity.this, "favaourite", Toast.LENGTH_SHORT).show();
                       FragmentManager fragmentManager =
                               getSupportFragmentManager();
                       FragmentTransaction fragmentTransaction =
                               fragmentManager.beginTransaction();

                       fragmentTransaction.replace
                               (R.id.fragment_contnair, new Favouritefragment()).commit();
                   }
break;

                   case R.id.accountmenu: {
                       Toast.makeText(MainActivity.this, "account", Toast.LENGTH_SHORT).show();
                       FragmentManager fragmentManager =
                                getSupportFragmentManager();

                       Bundle bundle = new Bundle();

                       bundle.putBoolean("owner",  true);
                       bundle.putString("userID",firebaseAuth.getUid());



                       Profilefragment profilefragment = new Profilefragment();
                       profilefragment.setArguments(bundle);
                       FragmentTransaction fragmentTransaction =
                               fragmentManager.beginTransaction();

                       fragmentTransaction.replace
                               (R.id.fragment_contnair, profilefragment).addToBackStack( "tag" ).commit();




                   }
                   break;
               }

               return true;
           }
       });






        // setting usermodel
        if(firebaseAuth.getCurrentUser()!=null)
        {

            DocumentReference documentReference=firebaseFirestore.collection("users").document(firebaseAuth.getUid());

            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if(task.isSuccessful())
                    {

                        DocumentSnapshot documentSnapshot=task.getResult();
                        Map<String, Object> details=documentSnapshot.getData();
                       Log.e("*",details.get("email").toString());

                       Usermodel.setUserID(details.get("userID").toString());
                        Usermodel.setUsername(details.get("username").toString());
                        Usermodel.setName(details.get("name").toString());

                        if(details.get("imageurl")!=null){
                            Log.e("*","hii");
                            Usermodel.setImageurl(details.get("imageurl").toString());
                        }
                        if(details.get("description")!=null) {
                            Usermodel.setDesc(details.get("description").toString());
                        }





                    }
                }
            });
        }





    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser()!=null)
        {

            DocumentReference documentReference=firebaseFirestore.collection("users").document(firebaseAuth.getUid());

            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if(task.isSuccessful())
                    {

                        DocumentSnapshot documentSnapshot=task.getResult();
                        Map<String, Object> details=documentSnapshot.getData();
                        Log.e("*",details.get("email").toString());

                        Usermodel.setUserID(details.get("userID").toString());
                        Usermodel.setUsername(details.get("username").toString());
                        Usermodel.setName(details.get("name").toString());

                        if(details.get("imageurl")!=null){
                            Log.e("*","hii");
                            Usermodel.setImageurl(details.get("imageurl").toString());
                        }
                        if(details.get("description")!=null) {
                            Usermodel.setDesc(details.get("description").toString());
                        }





                    }
                }
            });


        }


        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        DocumentReference documentReference=firebaseFirestore.collection("users").document(firebaseAuth.getUid());
        documentReference.update("status","online");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        DocumentReference documentReference=firebaseFirestore.collection("users").document(firebaseAuth.getUid());
        documentReference.update("status","offline");
    }
    public void gotToprofile(String UID){



        FragmentManager fragmentManager =
                getSupportFragmentManager();

        Bundle bundle = new Bundle();

        bundle.putBoolean("owner",  false);
        bundle.putString("userID",UID);



        Profilefragment profilefragment = new Profilefragment();
        profilefragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        fragmentTransaction.replace
                (R.id.fragment_contnair, profilefragment).addToBackStack( "tag" ).commit();






    }


}