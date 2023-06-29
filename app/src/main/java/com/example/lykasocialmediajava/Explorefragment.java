package com.example.lykasocialmediajava;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lykasocialmediajava.Adapters.PostAdapter;
import com.example.lykasocialmediajava.Model.PostModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;




public class Explorefragment extends Fragment {
ArrayList<PostModel>arrayList;
RecyclerView explorerecyclerview;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_explorefragment, container, false);

        explorerecyclerview=view.findViewById(R.id.explorerecyclerview);

         bundle = getArguments();


arrayList=bundle.getParcelableArrayList("arraylist");


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        explorerecyclerview.setLayoutManager(linearLayoutManager);

        PostAdapter postAdapter=new PostAdapter(arrayList,getActivity(),Explorefragment.this);





        explorerecyclerview.setAdapter(postAdapter);




        return  view;
    }

    public void gotToprofile(String UID){

        Log.e("*","hgiii  +"+ UID);
        FragmentManager fragmentManager =
                (getActivity()). getSupportFragmentManager();

        Bundle bundle = new Bundle();

        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        if(UID.equals(firebaseAuth.getUid())) {
            bundle.putBoolean("owner", true);
        }else{
            bundle.putBoolean("owner", false);

        }
        bundle.putString("userID",UID);


        Profilefragment profilefragment = new Profilefragment();
        profilefragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        fragmentTransaction.replace
                (R.id.fragment_contnair, profilefragment).addToBackStack( "tag" ).commit();







    }


}