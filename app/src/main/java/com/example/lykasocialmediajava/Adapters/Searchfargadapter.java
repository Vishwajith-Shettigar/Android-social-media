package com.example.lykasocialmediajava.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lykasocialmediajava.Commentactivity;
import com.example.lykasocialmediajava.Model.PostModel;
import com.example.lykasocialmediajava.Profilefragment;
import com.example.lykasocialmediajava.R;
import com.example.lykasocialmediajava.Searchfragment;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class Searchfargadapter extends RecyclerView.Adapter {

    Context context;
Searchfragment searchfragment=null;
Profilefragment profilefragment=null;
    ArrayList<PostModel>arrayList;

    public Searchfargadapter(Context context, ArrayList<PostModel> arrayList,Searchfragment searchfragment) {
        this.context = context;
        this.arrayList = arrayList;
        this.searchfragment=searchfragment;
    }
    public Searchfargadapter(Context context, ArrayList<PostModel> arrayList,Profilefragment profilefragment) {
        this.context = context;
        this.arrayList = arrayList;
        this.profilefragment=profilefragment;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.explorelayout,parent,false);

        return  new viewholder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        PostModel postModel=arrayList.get(position);

        if((postModel.getPimage().equals("null"))) {
            ((viewholder) holder).imageView.setVisibility(View.GONE);
            ((viewholder) holder).videoplayer.setVisibility(View.GONE);

        }else{

            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            float dpHeight = displayMetrics.heightPixels ;
            float dpWidth = (displayMetrics.widthPixels/2)-20 ;

            if(postModel.getPimage().contains("mp4"))
            {
                MediaController mediaController = new MediaController(context);

                ((viewholder) holder).imageView.setVisibility(View.GONE);

                ExoPlayer player = new ExoPlayer.Builder(context).build();
                ((viewholder)holder).videoplayer.setPlayer(player);
//                ((viewholder)holder).videoplayer.setMinimumWidth((int) (dpWidth/2));
                ((viewholder)holder).videoplayer.requestLayout();
//                ((viewholder)holder).videoplayer.getLayoutParams().width = (int) (dpWidth);

                MediaItem mediaItem = MediaItem.fromUri(Uri.parse(postModel.getPimage()));

                player.addMediaItem(mediaItem);
// Prepare exoplayer
                player.prepare();
// Play media when it is ready



            }else {

                Log.e("*",postModel.getPimage());
                ((viewholder) holder).videoplayer.setVisibility(View.GONE);
//                ((viewholder)holder).imageView.setMinimumWidth((int) (dpWidth/2));
                ((viewholder)holder).imageView.requestLayout();
//                ((viewholder)holder).imageView.getLayoutParams().width = (int) (dpWidth);

                Picasso.get().load(postModel.getPimage()).into(((viewholder) holder).imageView);

            }

        }

        ((viewholder)holder).exployerelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
Log.e("*","hiii-0=-0-=0");
if(searchfragment!=null){
    searchfragment.goToExplore();
} else if (profilefragment!=null) {

profilefragment.goToPostAc();

}


            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public  class  viewholder extends  RecyclerView.ViewHolder
    {

ImageView imageView;
        PlayerView videoplayer;
        RelativeLayout exployerelative;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imaegeviewsearch);
            videoplayer=itemView.findViewById(R.id.videoplayer);
            exployerelative=itemView.findViewById(R.id.exployerelative);




        }
    }
}
