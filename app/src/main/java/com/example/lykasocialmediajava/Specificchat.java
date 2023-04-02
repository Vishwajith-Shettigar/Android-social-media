package com.example.lykasocialmediajava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lykasocialmediajava.Adapters.MessagesAdapter;
import com.example.lykasocialmediajava.Model.Messages;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Specificchat extends AppCompatActivity {

    EditText messageedittext;
    ImageView sendbutton,userimage;
    Toolbar toolbar;
    TextView username,userstatus;
    String enteredmessage;
    Intent intent;
    String recievername,sendername,senderUID;
    String convID;


    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    String senderroom,receiverroom;
    RecyclerView recyclerView;
    MessagesAdapter messagesAdapter;

    ArrayList<Messages>messagesArrayList;
    String currenttime;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
String receiverimage,receiverstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specificchat);


messageedittext=findViewById(R.id.message);
sendbutton=findViewById(R.id.sendbutn);
toolbar=findViewById(R.id.toolbarspecificchat);
username=findViewById(R.id.usernamesc);
userimage=findViewById(R.id.imagesc);
recyclerView=findViewById(R.id.specificchatrecyclerview);

userstatus=findViewById(R.id.statussc);
intent=getIntent();
setSupportActionBar(toolbar);

firebaseAuth=FirebaseAuth.getInstance();
firebaseDatabase=FirebaseDatabase.getInstance();
calendar=Calendar.getInstance();
simpleDateFormat=new SimpleDateFormat("hh:mm a");


senderUID=firebaseAuth.getUid();
convID=getIntent().getStringExtra("convID");
recievername=getIntent().getStringExtra("username");
receiverimage=getIntent().getStringExtra("image");
receiverstatus=getIntent().getStringExtra("status");

username.setText(recievername);
if(!receiverimage.isEmpty())
        Picasso.get().load(receiverimage).into(userimage);
else{


}
userstatus.setText(receiverstatus);

//senderroom=senderUID+receiverUID;
//receiverroom=receiverUID+senderUID;
//
//
//
//
//
messagesArrayList=new ArrayList<>();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        messagesAdapter= new MessagesAdapter(this,messagesArrayList);
        recyclerView.setAdapter(messagesAdapter);

//
//
sendbutton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        enteredmessage=messageedittext.getText().toString();
        if(enteredmessage.isEmpty())
        {

        }else{
                Date date=new Date();
                currenttime=simpleDateFormat.format(calendar.getTime());


                Messages messages=new Messages(enteredmessage,firebaseAuth.getUid(), date.getTime(),currenttime);

                firebaseDatabase=FirebaseDatabase.getInstance();
                firebaseDatabase.getReference().child("chats")

                        .child(convID)
                        .push().setValue(messages).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });

    messageedittext.setText(null);


        }
    }
});
//
        DatabaseReference databaseReference=firebaseDatabase.getReference().child("chats")
                .child(convID);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren() ){


                    Messages messages=snapshot1.getValue(Messages.class);
                    messagesArrayList.add(messages);

                    messagesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();



//        messagesAdapter.notifyDataSetChanged();
        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        DocumentReference documentReference=firebaseFirestore.collection("users").document(firebaseAuth.getUid());
        documentReference.update("status","online");
    }

    @Override
    public void onStop() {

        if(messagesAdapter!=null)
            messagesAdapter.notifyDataSetChanged();
        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        DocumentReference documentReference=firebaseFirestore.collection("users").document(firebaseAuth.getUid());
        documentReference.update("status","offline");

        super.onStop();
    }
}