package com.example.lykasocialmediajava.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lykasocialmediajava.Model.PostModel;
import com.example.lykasocialmediajava.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter {
    FirebaseAuth firebaseAuth;

    ArrayList<PostModel>Postarraylist;

  Context context;

    public PostAdapter(ArrayList<PostModel> postarraylist, Context context) {
        Postarraylist = postarraylist;
        this.context = context;
        Log.e("*","inadapter");

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("*","on create view");
        View view= LayoutInflater.from(context).inflate(R.layout.singlepostlayout,parent,false);
        return  new viewholder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        firebaseAuth=FirebaseAuth.getInstance();
        PostModel postModel=Postarraylist.get(position);
        Picasso.get().load(postModel.getUserimage()).into( ( (viewholder)holder).userdp);
        Log.e("*",postModel.getUsername()+" adapter");
       ( (viewholder)holder).username.setText(postModel.getUsername());

        Picasso.get().load(postModel.getPimage()).into( ( (viewholder)holder).postimage);
        Log.e("*","n bind");
     (   (viewholder)holder).posttext.setText(postModel.getPtext());





    }


    @Override
    public int getItemCount() {
        return Postarraylist.size();
    }

    public  class  viewholder extends RecyclerView.ViewHolder

    {
        ImageView userdp,postimage,likebtn,commentbtn;
        TextView username,likecount,comcount,posttext;
        ImageView threedot;


        public viewholder(@NonNull View itemView) {
            super(itemView);
            userdp=itemView.findViewById(R.id.userdp);
            username=itemView.findViewById(R.id.postusername);
            threedot=itemView.findViewById(R.id.threedot);
            posttext=itemView.findViewById(R.id.posttext);

            postimage=itemView.findViewById(R.id.postimage);
            likebtn=itemView.findViewById(R.id.likebtn);
            commentbtn=itemView.findViewById(R.id.commentbtn);
            likecount=itemView.findViewById(R.id.likescount);
            comcount=itemView.findViewById(R.id.comcount);


        }
    }
}
