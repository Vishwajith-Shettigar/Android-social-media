package com.example.lykasocialmediajava.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lykasocialmediajava.Likesactivity;
import com.example.lykasocialmediajava.Model.LikesModel;
import com.example.lykasocialmediajava.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LikesAdapter extends RecyclerView.Adapter {
    Context context;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ArrayList<LikesModel>likesArraylist;
Likesactivity likesactivity;
    public LikesAdapter(Context context, ArrayList<LikesModel> likesArraylist, Likesactivity likesactivity) {
        this.context = context;
        this.likesArraylist = likesArraylist;
        this.likesactivity=likesactivity;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view= LayoutInflater.from(context).inflate(R.layout.likeslayout,parent,false);

     return  new viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        LikesModel likesModel=likesArraylist.get(position);

   String userid=likesModel.getUserID();

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();

        Query query=firebaseFirestore.collection("users").whereEqualTo("userID",userid);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {


                if(task.isSuccessful()) {


                    QuerySnapshot querySnapshot = task.getResult();
                    List<DocumentSnapshot> documentSnapshots = querySnapshot.getDocuments();
                    DocumentSnapshot documentSnapshot1 = documentSnapshots.get(0);

                    Map<String, Object> details = new HashMap<>();
                    details = documentSnapshot1.getData();

                    ((viewholder)holder).username.setText(details.get("username").toString());
                    ((viewholder)holder).name.setText(details.get("name").toString());


                    Log.e("*","afdapter inside"+ details.get("username").toString());

                    Picasso.get().load( Uri.parse(details.get("imageurl").toString())).into(((viewholder)holder).imageView);


                }

            }
        });


        ((viewholder)holder).imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likesactivity.gotToprofile(likesModel.getUserID());
            }
        });

    }

    @Override
    public int getItemCount() {

        return likesArraylist.size();
    }
    public  class  viewholder extends RecyclerView.ViewHolder
    {

ImageView imageView;
TextView username;
TextView name;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.likesuserimage);
            username=itemView.findViewById(R.id.likesusername);
            name=itemView.findViewById(R.id.likesname);



        }
    }
}
