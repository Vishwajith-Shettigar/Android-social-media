package com.example.lykasocialmediajava.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lykasocialmediajava.Model.Notimodel;
import com.example.lykasocialmediajava.R;
import com.google.android.exoplayer2.ui.PlayerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotiAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<Notimodel>notimodels;

    public NotiAdapter(Context context, ArrayList<Notimodel> notimodels) {
        this.context = context;
        this.notimodels = notimodels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.searchlayout,parent,false);
        return new viewholder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Notimodel notimodel=notimodels.get(position);
Log.e("*",notimodel.getText()+"---------------"+notimodel.getFromusername());

        ((viewholder)holder).searchname.setText(notimodel.getText());
        ((viewholder)holder).searchusername.setText(notimodel.getFromusername());
        Picasso.get().load(Uri.parse(notimodel.getFromimage())).into(  ((viewholder)holder).userimagesearch);



    }

    @Override
    public int getItemCount() {
        return notimodels.size();

    }
    class viewholder extends RecyclerView.ViewHolder
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
