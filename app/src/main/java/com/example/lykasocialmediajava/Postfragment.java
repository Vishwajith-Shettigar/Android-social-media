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
import android.widget.TextView;

import com.example.lykasocialmediajava.Adapters.PostAdapter;
import com.example.lykasocialmediajava.Model.PostModel;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class Postfragment extends Fragment {


RecyclerView postfragrecyclerview;
Bundle bundle;
TextView toolbartext;

ArrayList<PostModel> arrayList;
boolean isBookmark=false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_postfragment, container, false);

        postfragrecyclerview=view.findViewById(R.id.postfragrecyclerview);

        bundle = getArguments();

arrayList=new ArrayList<>();

        arrayList=bundle.getParcelableArrayList("arraylist");
        isBookmark=bundle.getBoolean("isBookmark");


        toolbartext=view.findViewById(R.id.toolbartext);


        if(isBookmark)
        {
            toolbartext.setText("Bookmark");
        }
        else{
            toolbartext.setText("Post");

        }
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        postfragrecyclerview.setLayoutManager(linearLayoutManager);

        PostAdapter postAdapter=new PostAdapter(arrayList,getActivity(),Postfragment.this);





        postfragrecyclerview.setAdapter(postAdapter);




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