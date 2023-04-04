package com.example.lykasocialmediajava.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lykasocialmediajava.Model.Messages;
import com.example.lykasocialmediajava.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

public class Globalmessagesadapter extends RecyclerView.Adapter {

FirebaseFirestore firebaseFirestore;

    Context context;
    ArrayList<Messages>messagesArrayList;

    int SENDERITEM=1;
    int RECEIVERITEM=2;


    public Globalmessagesadapter(Context context, ArrayList<Messages> messagesArrayList) {
        this.context = context;
        this.messagesArrayList = messagesArrayList;
        firebaseFirestore =FirebaseFirestore.getInstance();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==SENDERITEM)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.globalsenderchatlayout,parent,false);




            return new SenderViewholder(view);

        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.globalreceiverchatlayout,parent,false);




            return new ReceiverViewholder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        Messages messages=messagesArrayList.get(position);
        if(holder.getClass()==SenderViewholder.class)
        {
            ((SenderViewholder) holder).message.setText(messages.getMessage());
            ((SenderViewholder) holder).time.setText(messages.getCurrenttime());

        }
        else{

            ((ReceiverViewholder) holder).message.setText(messages.getMessage());
            ((ReceiverViewholder) holder).time.setText(messages.getCurrenttime());
        }

        Log.e("*","99999999999999999999"+ messages.getSenderUID());


firebaseFirestore.collection("users").whereEqualTo("userID",messages.getSenderUID())
        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
if(task.isSuccessful())
{
    Log.e("*","99999999999999999999-->"+ task.getResult().size());
    QuerySnapshot querySnapshot=task.getResult();
    Map<String,Object> details=querySnapshot.getDocuments().get(0).getData();
    if(holder.getClass()==SenderViewholder.class)
    {
        ((SenderViewholder) holder).username.setText(details.get("username").toString());
        Picasso.get().load(details.get("imageurl").toString()).into( ((SenderViewholder) holder).imageView);


    }
    else{

        ((ReceiverViewholder) holder).username.setText(details.get("username").toString());
        Picasso.get().load(details.get("imageurl").toString()).into( ((ReceiverViewholder) holder).imageView);

    }

}
            }
        });


    }


    @Override
    public int getItemViewType(int position) {
        Messages messages=messagesArrayList.get(position);


        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSenderUID()))
        {
            return SENDERITEM;


        }
        else{

            return  RECEIVERITEM;
        }
    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }



    class SenderViewholder extends  RecyclerView.ViewHolder{


        TextView message,time,username;
        ImageView imageView;

        public SenderViewholder(@NonNull View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.sendermessage);
            time=itemView.findViewById(R.id.timesendrmessage);
            username=itemView.findViewById(R.id.namesender);
            imageView=itemView.findViewById(R.id.imageglobal);
        }
    }

    class ReceiverViewholder extends  RecyclerView.ViewHolder{


        TextView message,time,username;

        ImageView imageView;


        public ReceiverViewholder(@NonNull View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.sendermessage);
            time=itemView.findViewById(R.id.timesendrmessage);

            username=itemView.findViewById(R.id.namesender);
            imageView=itemView.findViewById(R.id.imageglobal);




        }
    }
}
