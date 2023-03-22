package com.example.lykasocialmediajava.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lykasocialmediajava.Commentactivity;
import com.example.lykasocialmediajava.Model.PostModel;
import com.example.lykasocialmediajava.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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


       if((postModel.getPimage().equals("null"))) {
           ((viewholder) holder).postimage.setVisibility(View.GONE);
           ((viewholder) holder).postvideo.setVisibility(View.GONE);

       }else{

           if(postModel.getPimage().contains("mp4"))
           {
               MediaController mediaController = new MediaController(context);

               ((viewholder) holder).postimage.setVisibility(View.GONE);

               ExoPlayer player = new ExoPlayer.Builder(context).build();
               ((viewholder)holder).postvideo.setPlayer(player);
               MediaItem mediaItem = MediaItem.fromUri(Uri.parse(postModel.getPimage()));

               player.addMediaItem(mediaItem);
// Prepare exoplayer
               player.prepare();
// Play media when it is ready
               player.setPlayWhenReady(true);


           }else {

Log.e("*",postModel.getPimage());
               ((viewholder) holder).postvideo.setVisibility(View.GONE);
               Picasso.get().load(postModel.getPimage()).into(((viewholder) holder).postimage);

           }

       }

    if(postModel.getPtext().equals("null"))
    {
        ((viewholder) holder).posttext.setVisibility(View.GONE);

    }else {
        ((viewholder) holder).posttext.setText(postModel.getPtext());
    }


        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        Query query=firebaseFirestore.collection("Likes").whereEqualTo("postID",postModel.getPid()).whereEqualTo("userID",firebaseAuth.getUid());
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    Log.e("*","likes"+task.getResult().size()+"");
                    if(task.getResult().size()==1)
                    {
                        ((viewholder)holder). liked =true;
                        setLikes(holder);
                    }


                }
            }
        });


        // set likes count

        setLikescount(holder,postModel);

// commnet btn click

        ((viewholder)holder).commentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Commentactivity.class);
                intent.putExtra("postID",postModel.getPid());

                context.startActivity(intent);
            }
        });



        // when user likes or dislikes

        ((viewholder)holder).likebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(((viewholder)holder). liked ==true) {
                    firebaseFirestore.collection("Likes").document(postModel.getPid()+""+firebaseAuth.getUid()).delete();
                    ((viewholder)holder). liked =false;
                    ((viewholder)holder).nolikes=  (Integer.parseInt(((viewholder)holder).nolikes)-1)+"";
                    setLikes(holder);
                }
                else{

                    CollectionReference likescollectionReference = firebaseFirestore.collection("Likes");

                    Map<String,String > likesdetails=new HashMap<>();
                    likesdetails.put("userID",firebaseAuth.getUid());
                    likesdetails.put("postID",postModel.getPid());




                    likescollectionReference.document(postModel.getPid()+""+firebaseAuth.getUid()).set(likesdetails);


                    ((viewholder)holder). liked =true;
                    ((viewholder)holder).nolikes=  (Integer.parseInt(((viewholder)holder).nolikes)+1)+"";
                    setLikes(holder);


                }
            }
        });


    }

public  void setLikes(RecyclerView.ViewHolder holder)
{

    if(((viewholder)holder). liked ==true)
    {
        Log.e("*","ye bjai liked he 2");
        ((viewholder)holder).likecount.setText(   ((viewholder)holder).nolikes);

        ((viewholder)holder).likebtn.setImageResource(R.drawable.likedicon);
    }
    else{
        ((viewholder)holder).likecount.setText(   ((viewholder)holder).nolikes);

        ((viewholder)holder).likebtn.setImageResource(R.drawable.favourite_unfilled);

    }

}
public  void setLikescount(RecyclerView.ViewHolder holder,PostModel postModel)
{
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    Query query=firebaseFirestore.collection("Likes").whereEqualTo("postID",postModel.getPid());
    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if(task.isSuccessful())
            {
                Log.e("*","likes"+task.getResult().size()+"");

                ((viewholder)holder).nolikes=  task.getResult().size()+"";
                ((viewholder)holder).likecount.setText(   ((viewholder)holder).nolikes);


            }
        }
    });


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
        PlayerView postvideo;

        String nolikes;
boolean liked;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            userdp=itemView.findViewById(R.id.userdp);
            username=itemView.findViewById(R.id.postusername);
            threedot=itemView.findViewById(R.id.threedot);
            posttext=itemView.findViewById(R.id.posttext);
            postvideo=itemView.findViewById(R.id.postvideo);
            postimage=itemView.findViewById(R.id.postimage);
            likebtn=itemView.findViewById(R.id.likebtn);
            commentbtn=itemView.findViewById(R.id.commentbtn);
            likecount=itemView.findViewById(R.id.likescount);
            comcount=itemView.findViewById(R.id.comcount);
            liked=false;
nolikes="";

        }
    }
}
