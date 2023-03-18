package com.example.lykasocialmediajava;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Searchfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Searchfragment extends Fragment {
    ImageView chaticon,newposticon;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_searchfragment, container, false);

        chaticon=view.findViewById(R.id.chaticon);
        newposticon=view.findViewById(R.id.newposticon);
        newposticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),Newpostactivity.class));
            }
        });


        chaticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),Chatactivity.class));
            }
        });

        return  view;
    }
}