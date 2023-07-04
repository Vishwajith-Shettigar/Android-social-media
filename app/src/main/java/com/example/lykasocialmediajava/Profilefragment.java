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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.lykasocialmediajava.Adapters.PostAdapter;
import com.example.lykasocialmediajava.Adapters.Searchfargadapter;
import com.example.lykasocialmediajava.Model.PostModel;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.guieffect.qual.UI;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Profilefragment extends Fragment {

    MaterialButton editbtn;
ArrayList<PostModel>postModelArrayList;

    FirebaseAuth firebaseAuth;
    Map<String,Object>postdetails;
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
    RecyclerView profilepostrecyclerview;

Searchfargadapter searchfargadapter;
ImageView profilebookmarkbtn,profilenewposticon;

    ArrayList<PostModel>bookmarkpost;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("*","profle");
        View view= inflater.inflate(R.layout.fragment_profilefragment, container, false);
Bundle bundle;
        Owner= true;
         bookmarkpost=new ArrayList<>();

        postModelArrayList=new ArrayList<>();

        bundle = getArguments();
        Owner=bundle.getBoolean("owner");
        UID=bundle.getString("userID");

        profilepostrecyclerview=view.findViewById(R.id.profilepostrecyclerview);

        profilenewposticon=view.findViewById(R.id.profilenewposticon);


        profilebookmarkbtn=view.findViewById(R.id.profilebookmarkbtn);

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

        profilenewposticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),Newpostactivity.class).putExtra("isEdit","false"));

            }
        });

            getData();
            getBookmarkData();



        (profilepostrecyclerview).setHasFixedSize(true);

//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,1);



        profilepostrecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        searchfargadapter=new Searchfargadapter(getActivity(),postModelArrayList,Profilefragment.this);
        profilepostrecyclerview.setAdapter(searchfargadapter);


        getPosts();


        profilebookmarkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

Log.e("*","bookmark size---"+ bookmarkpost.size());
if(bookmarkpost.size()!=0) {
    FragmentManager fragmentManager =
            (getActivity()).getSupportFragmentManager();

    Bundle bundle = new Bundle();

    bundle.putParcelableArrayList("arraylist", bookmarkpost);
    bundle.putBoolean("isBookmark",true);


    Postfragment postfragment = new Postfragment();
    postfragment.setArguments(bundle);
    FragmentTransaction fragmentTransaction =
            fragmentManager.beginTransaction();

    fragmentTransaction.replace
            (R.id.fragment_contnair, postfragment).addToBackStack("tag").commit();


}



            }
        });


        messagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseFirestore.collection("chats").whereEqualTo("convID",UID+""+firebaseAuth.getUid())
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    if(task.getResult().size()==0)
                                    {


                                        Map<String,Object> details=new HashMap<>();
                                        details.put("fromUID",firebaseAuth.getUid());
                                        details.put("toUID",UID);
                                        details.put("convID",firebaseAuth.getUid()+""+UID);

                                        firebaseFirestore.collection("chats").document(firebaseAuth.getUid()+""+UID)
                                                .set(details);
                                    }
                                }
                            }
                        });




                Intent intent=new Intent(getActivity(),Chatactivity.class);
                startActivity(intent);

            }
        });


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



                                CollectionReference notiref=firebaseFirestore.collection("Notification");

                                Map<String,Object>detailsnoti=new HashMap<>();

                                detailsnoti.put("toUserid",UID);

                                detailsnoti.put("fromUserid",firebaseAuth.getUid());
                                detailsnoti.put("fromUsername",Usermodel.getUsername());
                                detailsnoti.put("fromImage",Usermodel.getImageurl());

                                detailsnoti.put("text",Usermodel.getUsername() +" followed you");
                                detailsnoti.put("seen",false);
                                detailsnoti.put("notiID",firebaseAuth.getUid()+""+UID+"");
                                notiref.document(firebaseAuth.getUid()+""+UID+"").set(detailsnoti);


                            }
                        }
                    }
                });





    }
});


        return  view;
    }

    public void getBookmarkData() {

  bookmarkpost=new ArrayList<>();


  FirebaseFirestore.getInstance().collection("bookmark").whereEqualTo("userID",firebaseAuth.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful())
                        {

                            long n=task.getResult().size();
                            ArrayList<DocumentSnapshot> documentSnapshots= (ArrayList<DocumentSnapshot>) task.getResult()
                                    .getDocuments();

                            for (long i=0;i<n;i++)
                            {
                                DocumentSnapshot documentSnapshot= (DocumentSnapshot) documentSnapshots.get((int) i);
                                Map<String,Object> bookmark=documentSnapshot.getData();
                                Log.e("*","bookmark ---"+bookmark.get("postID").toString());

                                FirebaseFirestore.getInstance().collection("posts").whereEqualTo("post_id",bookmark.get("postID").toString())

                                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                                if(task.isSuccessful())
                                                {
                                                   postdetails=new HashMap<>();
try{
 postdetails=task.getResult().getDocuments().get(0).getData();
    FirebaseFirestore.getInstance().collection("users").whereEqualTo("userID",postdetails.get("userID").toString())
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {


                    if(task.isSuccessful())
                    {

                        Map<String,Object>user=task.getResult().getDocuments().get(0).getData();

                        PostModel postModel=new PostModel(user.get("userID").toString(),user.get("username").toString(),user.get("imageurl").toString(),postdetails.get("post_id").toString(),

                                postdetails.get("postimage").toString(),postdetails.get("posttext").toString(),

                                postdetails.get("timetsamp").toString(),
                                (boolean)postdetails.get("hideLike"),(boolean)postdetails.get("hidecomt"),
                                (boolean)postdetails.get("isAnony")


                        );

                        bookmarkpost.add(postModel);
                        Log.e("*","hiiiiiiii--------------- booikmark"+postModel.getPtext());
                        Log.e("*","hiiiiiiii--------------- booikmark-->"+ bookmarkpost.size());

                    }

                }
            });

}
catch (Exception e)
{

}




                                                }
                                            }
                                        });




                            }


                        }
                    }
                });

    }

    public void getPosts()
    {
        {

           firebaseFirestore.collection("posts")
                    .whereEqualTo("userID",UID)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                                    String    usernamelol=details.get("username").toString();
                                        String userimagelol=details.get("imageurl").toString();



                                        Log.e("*",doc.get("posttext").toString() +" ininna");
                                        PostModel postModel=new PostModel(doc.get("userID").toString(),usernamelol,userimagelol,doc.get("post_id").toString(),

                                                doc.get("postimage").toString(),doc.get("posttext").toString(),

                                                doc.get("timetsamp").toString(),
                                                (boolean)doc.get("hideLike"),(boolean)doc.get("hidecomt"),
                                                (boolean)doc.get("isAnony")


                                        );

                                        if(!Owner && postModel.isAnony()==true )
                                        {

                                        }
                                        else {

                                            postModelArrayList.add(postModel);
                                            Log.e("*", String.valueOf(postModelArrayList.size()));
                                            userimagelol = "";
                                            usernamelol = "";
                                            searchfargadapter.notifyDataSetChanged();
                                        }

                                    }
                                }
                            });








                        }



                    }
                }
            });

        }

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
            if(Usermodel.getDesc().equals("null")) {


                profilebio.setText("");
            }
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
                                if(details.get("description").toString().equals("null"))
                                {
                                    profilebio.setText("");

                                }
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

    public void goToPostAc() {

        FragmentManager fragmentManager =
                (getActivity()).getSupportFragmentManager();

        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList("arraylist", postModelArrayList);
        bundle.putBoolean("isBookmark", false);

        Postfragment postfragment = new Postfragment();
        postfragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        fragmentTransaction.replace
                (R.id.fragment_contnair, postfragment).addToBackStack("tag").commit();


    }

}