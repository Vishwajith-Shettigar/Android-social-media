package com.example.lykasocialmediajava;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profilefragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profilefragment extends Fragment {

    MaterialButton editbtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("*","profle");
        View view= inflater.inflate(R.layout.fragment_profilefragment, container, false);

        editbtn=view.findViewById(R.id.editbtn);


        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),Editprofileactivity.class)

                );
            }
        });



        return  view;
    }
}