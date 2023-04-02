package com.example.lykasocialmediajava.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lykasocialmediajava.Chatactivity;
import com.example.lykasocialmediajava.Model.Chatmodel;
import com.example.lykasocialmediajava.Model.Searchusermodel;
import com.example.lykasocialmediajava.R;
import com.example.lykasocialmediajava.Specificchat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Chatadapter extends RecyclerView.Adapter {
FirebaseFirestore firebaseFirestore;

    ArrayList<Chatmodel>chatmodelArrayList;
    Context context;

    public Chatadapter(ArrayList<Chatmodel> chatmodels, Context context) {
        this.chatmodelArrayList = chatmodels;
        this.context = context;
        firebaseFirestore=FirebaseFirestore.getInstance();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.searchlayout,parent,false);

        return  new viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Chatmodel chatmodel=chatmodelArrayList.get(position);
String searchID;
        if(Objects.equals(chatmodel.getFromUID(), FirebaseAuth.getInstance().getUid()))
        {
            searchID=chatmodel.getToUID();

        }else{

            searchID=chatmodel.getFromUID();
        }
        final String[] username = new String[1];
        final String[] image = new String[1];
        final String[] status = new String[1];


        firebaseFirestore.collection("users").whereEqualTo("userID",searchID)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            QuerySnapshot querySnapshot=task.getResult();
                            DocumentSnapshot documentSnapshot=querySnapshot.getDocuments().get(0);
                            Map<String,Object> details=new HashMap<>();
                            details=documentSnapshot.getData();
                                    Picasso.get().load(Uri.parse(details.get("imageurl").toString())).into(((viewholder)holder).userimagesearch);
//
        ((viewholder)holder).searchusername.setText(details.get("username").toString());
        username[0] =details.get("username").toString();


//
        ((viewholder)holder).searchname.setText(details.get("name").toString());
        image[0] =details.get("imageurl").toString();

status[0] =details.get("status").toString();

                        }
                    }
                });



        ((viewholder)holder).searchla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Specificchat.class);
                intent.putExtra("convID",chatmodel.getConvID());
                intent.putExtra("username", username[0]);
                intent.putExtra("image", image[0]);
                intent.putExtra("status",status[0]);

                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return chatmodelArrayList.size();
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
