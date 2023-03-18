package com.example.lykasocialmediajava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

ImageView chaticon,newposticon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// initializing
        bottomNavigationView=findViewById(R.id.bottom_navigation);


        // setting default fragment homfragment
        FragmentManager fragmentManager =
                getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

            fragmentTransaction.replace
                    (R.id.fragment_contnair, new Homefragment()).commit();





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
                       FragmentTransaction fragmentTransaction =
                               fragmentManager.beginTransaction();

                       fragmentTransaction.replace
                               (R.id.fragment_contnair, new Profilefragment()).commit();
                   }
                   break;
               }

               return true;
           }
       });








    }
}