package com.example.lykasocialmediajava;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lykasocialmediajava.Adapters.PostAdapter;
import com.example.lykasocialmediajava.Model.PostModel;

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

        PostAdapter postAdapter=new PostAdapter(arrayList,getActivity());





        postfragrecyclerview.setAdapter(postAdapter);




        return  view;
    }
}