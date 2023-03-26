package com.example.lykasocialmediajava;

import static android.widget.GridLayout.VERTICAL;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lykasocialmediajava.Adapters.PostAdapter;
import com.example.lykasocialmediajava.Adapters.Searchfargadapter;
import com.example.lykasocialmediajava.Model.PostModel;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
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


public class Searchfragment extends Fragment {
    ImageView chaticon,newposticon;
EditText search;
FirebaseAuth firebaseAuth;
FirebaseFirestore firebaseFirestore;
    ArrayList<PostModel>postModelArrayList;
Searchfargadapter searchfargadapter;
RecyclerView searchrecyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_searchfragment, container, false);

        search=view.findViewById(R.id.search);
        chaticon=view.findViewById(R.id.chaticon);
        newposticon=view.findViewById(R.id.newposticon);
        searchrecyclerview=view.findViewById(R.id.searchrecyclerview);

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();


        postModelArrayList=new ArrayList<>();

        fetchPosts();

        (searchrecyclerview).setHasFixedSize(true);

//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,1);
FlexboxLayoutManager flexboxLayoutManager=new FlexboxLayoutManager(getActivity());

        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_END);


flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        searchrecyclerview.setLayoutManager(flexboxLayoutManager);
        searchfargadapter=new Searchfargadapter(getActivity(),postModelArrayList,Searchfragment.this);
       searchrecyclerview.setAdapter(searchfargadapter);





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


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),Innersearchactivity.class));


            }
        });

        return  view;
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
String  usernamelol,userimagelol;

                                    QuerySnapshot querySnapshot=task.getResult();
                                    List<DocumentSnapshot> documentSnapshots=querySnapshot.getDocuments();
                                    DocumentSnapshot documentSnapshot1=documentSnapshots.get(0);

                                    Map<String,Object> details=new HashMap<>();
                                    details =documentSnapshot1.getData();
                                    Log.e("*",details.get("username").toString());
                                    usernamelol=details.get("username").toString();
                                    userimagelol=details.get("imageurl").toString();



                                    Log.e("*",doc.get("posttext").toString() +" ininna");
                                    PostModel postModel=new PostModel(doc.get("userID").toString(),usernamelol,userimagelol,doc.get("post_id").toString(),

                                            doc.get("postimage").toString(),doc.get("posttext").toString(),

                                            doc.get("timetsamp").toString(),
                                            (boolean)doc.get("hideLike"),(boolean)doc.get("hidecomt")

                                    );

                                    postModelArrayList.add(postModel);
                                    Log.e("*", String.valueOf(postModelArrayList.size()));
                                    userimagelol="";
                                    usernamelol="";
                                    searchfargadapter.notifyDataSetChanged();

                                }
                            }
                        });








                    }



                }
            }
        });

    }
    public void goToExplore(){

        FragmentManager fragmentManager =
                (getActivity()). getSupportFragmentManager();

        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList("arraylist",  postModelArrayList);

        Explorefragment explorefragment = new Explorefragment();
        explorefragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        fragmentTransaction.replace
                (R.id.fragment_contnair, explorefragment).addToBackStack( "tag" ).commit();







    }
}