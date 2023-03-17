package com.example.lykasocialmediajava;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;



public class PagerAdapter extends FragmentPagerAdapter {

    int tabcount;
    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {

            case 0:
                Log.e("*","home fragment");
                return  new Profilefragment();

            case 1:
                return  new Searchfragment();


            case 2:
                return  new Favouritefragment();

            case 3:
                return  new Profilefragment();





        }
        return  new Homefragment();
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
