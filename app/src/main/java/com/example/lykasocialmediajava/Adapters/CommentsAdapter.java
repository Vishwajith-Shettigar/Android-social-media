package com.example.lykasocialmediajava.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lykasocialmediajava.Commentactivity;
import com.example.lykasocialmediajava.Model.CommentsModel;
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

public class CommentsAdapter extends RecyclerView.Adapter {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

private Context context;
ArrayList<CommentsModel>commentsArrayList;
Commentactivity commentactivity;


    public CommentsAdapter(Context context, ArrayList<CommentsModel> commentsArrayList,Commentactivity commentactivity) {
        this.context = context;
        this.commentsArrayList = commentsArrayList;
        this.commentactivity=commentactivity;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.commentslayout,parent,false);

       return  new viewmodel(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        CommentsModel commentsModel=commentsArrayList.get(position);
//        ((viewmodel)holder).commentusername.setText(commentsModel.get());

        ((viewmodel)holder).comnettext.setText(commentsModel.getComText());
//        Picasso.get().load(Uri.parse(commentsModel.get ).into(((viewmodel)holder).commentimage));


firebaseAuth=FirebaseAuth.getInstance();
firebaseFirestore=FirebaseFirestore.getInstance();

        Query query=firebaseFirestore.collection("users").whereEqualTo("userID",commentsModel.getUserID());
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {


                if(task.isSuccessful()) {


                    QuerySnapshot querySnapshot = task.getResult();
                    List<DocumentSnapshot> documentSnapshots = querySnapshot.getDocuments();
                    DocumentSnapshot documentSnapshot1 = documentSnapshots.get(0);

                    Map<String, Object> details = new HashMap<>();
                    details = documentSnapshot1.getData();

                    ((viewmodel)holder).commentusername.setText(details.get("username").toString());

Log.e("*","afdapter inside"+ details.get("username").toString());

     Picasso.get().load( Uri.parse(details.get("imageurl").toString())).into(((viewmodel)holder).commentimage);


                }

                }
        });
        ((viewmodel)holder).commentimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentactivity.gotToprofile(commentsModel.getUserID());
            }
        });



        ((viewmodel)holder).comntrelative.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {



Log.e("*",firebaseAuth.getUid().equals(commentsModel.getPostUserID())+"");
                if(firebaseAuth.getUid().equals(commentsModel.getUserID()) || firebaseAuth.getUid().equals(commentsModel.getPostUserID()) )
                {
                    ((Commentactivity)context).deleteComment(commentsModel.getComID());


                }
                return  true;
            }


        });

    }



    @Override
    public int getItemCount() {
        return commentsArrayList.size();
    }

    public  class  viewmodel extends RecyclerView.ViewHolder{

        ImageView commentimage;
TextView commentusername;
TextView comnettext;
RelativeLayout comntrelative;

        public viewmodel(@NonNull View itemView) {
            super(itemView);
            commentimage=itemView.findViewById(R.id.commentimage);
            commentusername=itemView.findViewById(R.id.commentusername);
            comnettext=itemView.findViewById(R.id.comnettext);
            comntrelative=itemView.findViewById(R.id.comntrelative);



        }
    }
}
