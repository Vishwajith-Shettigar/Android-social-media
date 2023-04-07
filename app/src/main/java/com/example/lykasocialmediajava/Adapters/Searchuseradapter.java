package com.example.lykasocialmediajava.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lykasocialmediajava.Followersactivity;
import com.example.lykasocialmediajava.Followingactivity;
import com.example.lykasocialmediajava.Innersearchactivity;
import com.example.lykasocialmediajava.Model.Searchusermodel;
import com.example.lykasocialmediajava.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Searchuseradapter extends RecyclerView.Adapter {

    ArrayList<Searchusermodel>Arraysearchusermodels;
    Context context;
    Innersearchactivity innersearchactivity=null;
    Followersactivity followersactivity=null;
    Followingactivity followingactivity=null;

    public Searchuseradapter(ArrayList<Searchusermodel> searchusermodels, Context context, Innersearchactivity innersearchactivity) {
        this.Arraysearchusermodels = searchusermodels;
        this.context = context;
        this.innersearchactivity=innersearchactivity;
    }
    public Searchuseradapter(ArrayList<Searchusermodel> searchusermodels, Context context, Followersactivity followersactivity) {
        this.Arraysearchusermodels = searchusermodels;
        this.context = context;
        this.followersactivity=followersactivity;
    }
    public Searchuseradapter(ArrayList<Searchusermodel> searchusermodels, Context context, Followingactivity followingactivity) {
        this.Arraysearchusermodels = searchusermodels;
        this.context = context;
        this.followingactivity=followingactivity;
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

        ((viewholder)holder).searchla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(innersearchactivity!=null)
                innersearchactivity.gotToprofile(searchuser.getUserID());
                else if (followingactivity!=null) {
                 followingactivity.gotToprofile(searchuser.getUserID());

                } else if (followersactivity!=null) {

                    followersactivity.gotToprofile(searchuser.getUserID());
                }
            }

        });





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
RelativeLayout searchla;



        public viewholder(@NonNull View itemView) {
            super(itemView);

            userimagesearch=itemView.findViewById(R.id.userimagesearch);
            searchusername =itemView.findViewById(R.id.searchusername);
            searchname=itemView.findViewById(R.id.searchname);
            searchla=itemView.findViewById(R.id.searchla);



        }
    }
}
