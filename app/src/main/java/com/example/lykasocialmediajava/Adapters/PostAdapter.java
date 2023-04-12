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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lykasocialmediajava.Commentactivity;
import com.example.lykasocialmediajava.Explorefragment;
import com.example.lykasocialmediajava.Homefragment;
import com.example.lykasocialmediajava.Likesactivity;
import com.example.lykasocialmediajava.Model.PostModel;
import com.example.lykasocialmediajava.Postfragment;
import com.example.lykasocialmediajava.Postmenubottomsheet;
import com.example.lykasocialmediajava.R;
import com.example.lykasocialmediajava.Usermodel;
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

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PostAdapter extends RecyclerView.Adapter {
    FirebaseAuth firebaseAuth;

    ArrayList<PostModel>Postarraylist;

  Context context;
  Homefragment homefragment=null;
  Explorefragment explorefragment=null;
  Postfragment postfragment=null;

    public PostAdapter(ArrayList<PostModel> postarraylist, Context context,Homefragment homefragment) {
        Postarraylist = postarraylist;
        this.context = context;
        Log.e("*","inadapter");
    this.homefragment    =homefragment;

    }
    public PostAdapter(ArrayList<PostModel> postarraylist, Context context,Explorefragment explorefragment) {
        Postarraylist = postarraylist;
        this.context = context;
        Log.e("*","inadapter");
        this.explorefragment    =explorefragment;

    }
    public PostAdapter(ArrayList<PostModel> postarraylist, Context context,Postfragment postfragment) {
        Postarraylist = postarraylist;
        this.context = context;
        Log.e("*","inadapter");
        this.postfragment    =postfragment;

    }
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


        DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Date cDate;
        Log.e("#",postModel.getTtime());

        try {
             cDate = df.parse(postModel.getTtime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
        String ago = prettyTime.format(cDate);


        ((viewholder) holder).time.setText(ago);
        // set ishide is comt

        if(postModel.gethideLike())
        {

            ((viewholder) holder).likecount.setVisibility(View.GONE);

        }
        if(postModel.gethideComt())
        {

            ((viewholder) holder).comcount.setVisibility(View.GONE);
            ((viewholder) holder).commentbtn.setEnabled(false);

        }

        if(!postModel.isAnony()) {
            Picasso.get().load(postModel.getUserimage()).into(((viewholder) holder).userdp);
            Log.e("*", postModel.getUsername() + " adapter");
            ((viewholder) holder).username.setText(postModel.getUsername());

        }else{

            ((viewholder) holder).username.setText("Anonymous");
            ( (viewholder)holder).userdp.setEnabled(false);
        }
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


        // liked user details btn

        ((viewholder)holder).likecount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Likesactivity.class);
                intent.putExtra("postID",postModel.getPid());

                context.startActivity(intent);
            }
        });


// commnet btn click

        ((viewholder)holder).commentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Commentactivity.class);
                intent.putExtra("postID",postModel.getPid());
                intent.putExtra("postUserID",postModel.getUid());


                context.startActivity(intent);
            }
        });



        //set commnets count


        FirebaseFirestore firebaseFirestore1=FirebaseFirestore.getInstance();
        Query query1=firebaseFirestore.collection("Comments").whereEqualTo("postID",postModel.getPid());
        query1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    Log.e("*","commnets"+task.getResult().size()+"");
                    ((viewholder)holder).comcount.setText(task.getResult().size()+"");


                }
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

                    CollectionReference notiref=firebaseFirestore.collection("Notification");

                    Map<String,Object>detailsnoti=new HashMap<>();

                    detailsnoti.put("toUserid",postModel.getUid());

                    detailsnoti.put("fromUserid",firebaseAuth.getUid());
                    detailsnoti.put("fromUsername",Usermodel.getUsername());
                    detailsnoti.put("fromImage",Usermodel.getImageurl());

                    detailsnoti.put("text",Usermodel.getUsername() +" Liked your post");
                    detailsnoti.put("seen",false);
                    detailsnoti.put("notiID",postModel.getUid()+""+postModel.getPid()+"");


notiref.document(postModel.getUid()+""+postModel.getPid()+"").set(detailsnoti);



                }
            }
        });

        ( (viewholder)holder).userdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(homefragment!=null) {
                    homefragment.gotToprofile(postModel.getUid());
                }
                else if (explorefragment!=null) {

                    explorefragment.gotToprofile(postModel.getUid());
                }
                else if(postfragment!=null)
                {
                    postfragment.gotToprofile(postModel.getUid());
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

    ((viewholder)(holder)).threedot.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Postmenubottomsheet postmenubottomsheet=new Postmenubottomsheet(postModel.getUid(),postModel.getPid(),
                    postModel.getPimage(),postModel.getPtext(),postModel.gethideLike(),postModel.gethideComt()
                    );
            postmenubottomsheet.show(((AppCompatActivity)context).getSupportFragmentManager(), "lol");

        }
    });


}

    @Override
    public int getItemCount() {
        return Postarraylist.size();
    }

    public static class  viewholder extends RecyclerView.ViewHolder

    {
        ImageView userdp,postimage,likebtn,commentbtn;
        TextView username,likecount,comcount,posttext,time;
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
            time=itemView.findViewById(R.id.time);

            liked=false;
nolikes="";

        }
    }
}
