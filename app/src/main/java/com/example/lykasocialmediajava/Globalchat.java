package com.example.lykasocialmediajava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lykasocialmediajava.Adapters.Globalmessagesadapter;
import com.example.lykasocialmediajava.Adapters.MessagesAdapter;
import com.example.lykasocialmediajava.Model.Globalmessages;
import com.example.lykasocialmediajava.Model.Messages;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Globalchat extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText messageedittext;
    ImageView sendbutton,userimage,globalbackbtn;
    Toolbar toolbar;
    TextView username,userstatus;
    String enteredmessage;
    Intent intent;
    String recievername,sendername,senderUID;
    String convID;


    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    String senderroom,receiverroom;



    ArrayList<Globalmessages> messagesArrayList;
    String currenttime;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

Globalmessagesadapter globalmessagesadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_globalchat);

        messageedittext=findViewById(R.id.message);
        sendbutton=findViewById(R.id.sendbutn);
        globalbackbtn=findViewById(R.id.globalbackbtn);


        recyclerView=findViewById(R.id.globalrecyclerview);


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        calendar=Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("hh:mm a");

        messagesArrayList=new ArrayList<>();

globalmessagesadapter =new Globalmessagesadapter(this,messagesArrayList,Globalchat.this);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(globalmessagesadapter);

        globalbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteredmessage=messageedittext.getText().toString();
                if(enteredmessage.isEmpty())
                {

                }else{
                    Date date=new Date();
                    currenttime=simpleDateFormat.format(calendar.getTime());


                    Globalmessages messages=new Globalmessages(enteredmessage,firebaseAuth.getUid(), date.getTime(),currenttime,"");

                    firebaseDatabase=FirebaseDatabase.getInstance();
                    firebaseDatabase.getReference().child("Globalchats")


                            .push().setValue(messages).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            });

                    messageedittext.setText(null);


                }
            }
        });



        DatabaseReference databaseReference=firebaseDatabase.getReference().child("Globalchats");





        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren() ){
                    Log.e("*","---------iii0--------");
                    Log.e("*","---------iii--------"+snapshot1.getKey());
                    Globalmessages messages=snapshot1.getValue(Globalmessages.class);
                    messages.setDockey(snapshot1.getKey());
                    messagesArrayList.add(messages);

                    globalmessagesadapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void gotToprofile(String UID){



        Intent intent = new Intent(Globalchat.this, MainActivity.class);
        intent.putExtra("replaceFragment", true);
        intent.putExtra("UID",UID);
        startActivity(intent);





    }
}