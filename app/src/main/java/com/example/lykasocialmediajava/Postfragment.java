package com.example.lykasocialmediajava;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lykasocialmediajava.Adapters.PostAdapter;
import com.example.lykasocialmediajava.Model.PostModel;

import java.util.ArrayList;


public class Postfragment extends Fragment {


RecyclerView postfragrecyclerview;
Bundle bundle;
ArrayList<PostModel> arrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_postfragment, container, false);

        postfragrecyclerview=view.findViewById(R.id.postfragrecyclerview);

        bundle = getArguments();

arrayList=new ArrayList<>();

        arrayList=bundle.getParcelableArrayList("arraylist");


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        postfragrecyclerview.setLayoutManager(linearLayoutManager);

        PostAdapter postAdapter=new PostAdapter(arrayList,getActivity());





        postfragrecyclerview.setAdapter(postAdapter);




        return  view;
    }
}