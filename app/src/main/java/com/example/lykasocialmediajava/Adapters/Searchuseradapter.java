package com.example.lykasocialmediajava.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lykasocialmediajava.Model.Searchusermodel;
import com.example.lykasocialmediajava.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Searchuseradapter extends RecyclerView.Adapter {

    ArrayList<Searchusermodel>Arraysearchusermodels;
    Context context;

    public Searchuseradapter(ArrayList<Searchusermodel> searchusermodels, Context context) {
        this.Arraysearchusermodels = searchusermodels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.searchlayout,parent,false);

        return  new viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Searchusermodel searchuser=Arraysearchusermodels.get(position);

        Picasso.get().load(Uri.parse(searchuser.getUserImage())).into(((viewholder)holder).userimagesearch);

        ((viewholder)holder).searchusername.setText(searchuser.getUsername());

        ((viewholder)holder).searchname.setText(searchuser.getName());


    }

    @Override
    public int getItemCount() {
        return Arraysearchusermodels.size();
    }

    public  class  viewholder extends  RecyclerView.ViewHolder
    {

ImageView userimagesearch;
TextView searchusername;
TextView searchname;


        public viewholder(@NonNull View itemView) {
            super(itemView);

            userimagesearch=itemView.findViewById(R.id.userimagesearch);
            searchusername =itemView.findViewById(R.id.searchusername);
            searchname=itemView.findViewById(R.id.searchname);



        }
    }
}
