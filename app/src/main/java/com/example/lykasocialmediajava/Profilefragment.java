package com.example.lykasocialmediajava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.guieffect.qual.UI;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class Profilefragment extends Fragment {

    MaterialButton editbtn;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String UID;
    ArrayList<String>Followings=new ArrayList<>();
    ArrayList<String>Followers=new ArrayList<>();
    boolean Owner= true;
TextView usernameprofile,followeesno,followingsno,postnumber;
TextView profilesecnamefullname;
TextView profilebio;
ImageView userprofilepic;
MaterialButton followbtn,messagebtn;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("*","profle");
        View view= inflater.inflate(R.layout.fragment_profilefragment, container, false);
Bundle bundle;
        Owner= true;
        bundle = getArguments();
        Owner=bundle.getBoolean("owner");
        UID=bundle.getString("userID");


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        editbtn=view.findViewById(R.id.editbtn);
        messagebtn=view.findViewById(R.id.messagebtn);
followeesno=view.findViewById(R.id.followeesno);
followingsno=view.findViewById(R.id.followingno);

        postnumber=view.findViewById(R.id.postnumber);
        usernameprofile=view.findViewById(R.id.usernameprofile);
            profilesecnamefullname=view.findViewById(R.id.profilesecnamefullname);
            profilebio=view.findViewById(R.id.profilebio);
            userprofilepic=view.findViewById(R.id.userprofilepic);

followbtn=view.findViewById(R.id.followbtn);



            getData();


        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),Editprofileactivity.class)

                );
            }
        });


        followingsno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Followingactivity.class);
                intent.putExtra("Following",Followings);
                Log.e("@",Followings.size()+"----------------");
                startActivity(intent);
            }
        });

        followeesno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Followersactivity.class);
                intent.putExtra("Follower",Followers);
                Log.e("@",Followers.size()+"----------------");
                startActivity(intent);

            }
        });

followbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        firebaseFirestore.collection("Following").whereEqualTo("id",FirebaseAuth.getInstance().getUid()+""+UID)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            if(task.getResult().size()!=0)
                            {
                                followbtn.setText("Follow");
                                firebaseFirestore.collection("Following").document(FirebaseAuth.getInstance().getUid()+""+UID)
                                        .delete();
                                firebaseFirestore.collection("Follower").document(UID+""+FirebaseAuth.getInstance().getUid())
                                        .delete();


                            }
                            else{

                                Map<String,Object>detaila=new HashMap<>();
                                detaila.put("ownerID",firebaseAuth.getUid());
                                detaila.put("followingID",UID);
                                detaila.put("id",FirebaseAuth.getInstance().getUid()+""+UID);


                                followbtn.setText("Unfollow");
                                firebaseFirestore.collection("Following").document(FirebaseAuth.getInstance().getUid()+""+UID)
                                        .set(detaila);


                                Map<String,Object>details2=new HashMap<>();
                                details2.put("ownerID",UID);
                                details2.put("followerID",firebaseAuth.getUid());
                                details2.put("id",UID+""+FirebaseAuth.getInstance().getUid());



                                firebaseFirestore.collection("Follower").document(UID+""+FirebaseAuth.getInstance().getUid())
                                        .set(details2);

                            }
                        }
                    }
                });





    }
});


        return  view;
    }

    void getData() {


        if(Owner==true){
Log.e("*",Owner+ "hello---");
            editbtn.setVisibility(View.VISIBLE);
            followbtn.setVisibility(View.GONE);
            messagebtn.setVisibility(View.GONE);
            usernameprofile.setText(Usermodel.getUsername());
            profilesecnamefullname.setText(Usermodel.getName());
            profilebio.setText(Usermodel.getDesc());
            Picasso.get().load(Uri.parse(Usermodel.getImageurl())).into(userprofilepic);

        }
        else
        {


firebaseFirestore.collection("Following").whereEqualTo("id",FirebaseAuth.getInstance().getUid()+""+UID)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().size()!=0)
                    {
                        followbtn.setText("Unfollow");
                    }
                }
            }
        });



            Log.e("*",Owner+ "oin elsoe---");
            editbtn.setVisibility(View.GONE);
            followbtn.setVisibility(View.VISIBLE);
            messagebtn.setVisibility(View.VISIBLE);
            firebaseFirestore.collection("users").document(UID)
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {

                                DocumentSnapshot documentSnapshot = task.getResult();
                                Map<String, Object> details = new HashMap<>();
                                details = documentSnapshot.getData();

                                usernameprofile.setText(details.get("username").toString());
                                profilesecnamefullname.setText(details.get("name").toString());
                                profilebio.setText(details.get("description").toString());
                                Picasso.get().load(Uri.parse(details.get("imageurl").toString())).into(userprofilepic);


                            }
                        }
                    });
        }

        firebaseFirestore.collection("Follower").whereEqualTo("ownerID",UID)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
        followeesno.setText(task.getResult().size()+"");




                                QuerySnapshot querySnapshot=task.getResult();
                                ArrayList<DocumentSnapshot> documentSnapshot= (ArrayList<DocumentSnapshot>) querySnapshot.getDocuments();
                                for(int i=0;i<task.getResult().size();i++)
                                {

                                    DocumentSnapshot documentSnapshot1=documentSnapshot.get(i);
                                    Followers.add((String) documentSnapshot1.getData().get("followerID"));




                                }






                        }
                    }
                });

        firebaseFirestore.collection("Following").whereEqualTo("ownerID",UID)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            followingsno.setText(task.getResult().size()+"");

                            QuerySnapshot querySnapshot=task.getResult();
                            ArrayList<DocumentSnapshot> documentSnapshot= (ArrayList<DocumentSnapshot>) querySnapshot.getDocuments();
                        for(int i=0;i<task.getResult().size();i++)
                        {

DocumentSnapshot documentSnapshot1=documentSnapshot.get(i);
Followings.add((String) documentSnapshot1.getData().get("followingID"));




                        }




                        }
                    }
                });

        firebaseFirestore.collection("posts").whereEqualTo("userID",UID)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            postnumber.setText(task.getResult().size()+"");


                        }
                    }
                });

    }



}