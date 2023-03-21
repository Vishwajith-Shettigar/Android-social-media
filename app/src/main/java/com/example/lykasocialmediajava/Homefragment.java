package com.example.lykasocialmediajava;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lykasocialmediajava.Adapters.PostAdapter;
import com.example.lykasocialmediajava.Model.PostModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Homefragment extends Fragment {
Toolbar toolbar;
    Button btn;
    public  String usernamelol,userimagelol;
    ImageView chaticon,newposticon;
    RecyclerView homefragmentrecyclerview;
    ArrayList<PostModel>postModelArrayList;
    PostAdapter postAdapter;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.homefragment,
                container, false);
        chaticon=view.findViewById(R.id.chaticon);
        newposticon=view.findViewById(R.id.newposticon);
        homefragmentrecyclerview=view.findViewById(R.id.homefragmentrecyclerview);
firebaseAuth=FirebaseAuth.getInstance();
firebaseFirestore=FirebaseFirestore.getInstance();
postModelArrayList=new ArrayList<>();

fetchPosts();
//        PostModel postModel=new PostModel("dfewfew","ger","https://firebasestorage.googleapis.com/v0/b/lykasocialmedia.appspot.com/o/EWERiSotLYed6BaFMLAtPcO0oc73%2Fprofilepicture%2Fprofilepic?alt=media&token=cbcefffe-a3ce-4fa5-9265-952899929560","dwqdwqd",
//
//                "dwqddqw","hiiii",
//
//               "910290"
//
//        );
//        postModelArrayList.add(postModel);
        Log.e("*", String.valueOf(postModelArrayList.size()));
        homefragmentrecyclerview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        homefragmentrecyclerview.setLayoutManager(linearLayoutManager);
         postAdapter=new PostAdapter(postModelArrayList,getActivity());
homefragmentrecyclerview.setAdapter(postAdapter);



        newposticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),Newpostactivity.class));
            }
        });


        chaticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),Chatactivity.class));
            }
        });



        return view;
    }


    public  void   fetchPosts()
    {

        CollectionReference postcollection= firebaseFirestore.collection("posts");
        postcollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful())
                {
               QuerySnapshot queryDocumentSnapshots=task.getResult();

                  for (int i=0;i<task.getResult().size();i++)
                  {

                      String postusername,postuserimage;
                      Map<String, Object> doc   =(queryDocumentSnapshots.getDocuments().get(i)).getData();


                      Query query=firebaseFirestore.collection("users").whereEqualTo("userID",doc.get("userID").toString());
                      query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                          @Override
                          public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){


QuerySnapshot querySnapshot=task.getResult();
                            List<DocumentSnapshot> documentSnapshots=querySnapshot.getDocuments();
                            DocumentSnapshot documentSnapshot1=documentSnapshots.get(0);

                            Map<String,Object> details=new HashMap<>();
                                   details =documentSnapshot1.getData();
                                   Log.e("*",details.get("username").toString());
                                   usernamelol=details.get("username").toString();
                                   userimagelol=details.get("imageurl").toString();



                            Log.e("*",doc.get("posttext").toString() +" ininna");
                            PostModel postModel=new PostModel(doc.get("userID").toString(),usernamelol,"https://firebasestorage.googleapis.com/v0/b/lykasocialmedia.appspot.com/o/EWERiSotLYed6BaFMLAtPcO0oc73%2Fprofilepicture%2Fprofilepic?alt=media&token=cbcefffe-a3ce-4fa5-9265-952899929560",doc.get("post_id").toString(),

                                    doc.get("postimage").toString(),doc.get("posttext").toString(),

                                    doc.get("timetsamp").toString()

                            );

                            postModelArrayList.add(postModel);
                            Log.e("*", String.valueOf(postModelArrayList.size()));
                            userimagelol="";
                            usernamelol="";
                            postAdapter.notifyDataSetChanged();

                        }
                          }
                      });








                  }



                }
            }
        });

    }
}
