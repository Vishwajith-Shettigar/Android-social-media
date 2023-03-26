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

import kotlinx.coroutines.channels.Receive;


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

        PostAdapter postAdapter=new PostAdapter(arrayList,getActivity());





        explorerecyclerview.setAdapter(postAdapter);




        return  view;
    }




}