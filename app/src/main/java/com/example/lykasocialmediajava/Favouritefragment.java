package com.example.lykasocialmediajava;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lykasocialmediajava.Adapters.NotiAdapter;
import com.example.lykasocialmediajava.Adapters.Searchuseradapter;
import com.example.lykasocialmediajava.Model.Notimodel;
import com.example.lykasocialmediajava.Model.PostModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Favouritefragment extends Fragment {


FirebaseAuth firebaseAuth;
FirebaseFirestore firebaseFirestore;
ArrayList<Notimodel> notimodelArrayList;
NotiAdapter notiAdapter;

RecyclerView favouriterecyclerview;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_favouritefragment, container, false);

firebaseAuth=FirebaseAuth.getInstance();
firebaseFirestore=FirebaseFirestore.getInstance();

        favouriterecyclerview=view.findViewById(R.id.favouriterecyclerview);
        notimodelArrayList=new ArrayList<>();

        notiAdapter=new NotiAdapter(getActivity(),notimodelArrayList,Favouritefragment.this);
        getData();
        setrecyclerview();



        return  view;
    }

    public void getData()
    {

        firebaseFirestore.collection("Notification").whereEqualTo("toUserid",firebaseAuth.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful())
                        {

                            for(int i=0;i<task.getResult().size();i++)
                            {

                                QuerySnapshot querySnapshot=task.getResult();
                                DocumentSnapshot documentSnapshot=querySnapshot.getDocuments().get(i);

                                Map<String,Object> details=new HashMap<>();
                                details=documentSnapshot.getData();
                                Notimodel notimodel=new Notimodel(details.get("notiID").toString(),details.get("fromUserid").toString(),
                                        details.get("toUserid").toString(),
                                        details.get("fromUsername").toString(),
                                        details.get("fromImage").toString(),
                                        details.get("text").toString()

                                        );
                                notimodel.setSeen((Boolean) details.get("seen"));

notimodelArrayList.add(notimodel);
                                DocumentReference documentReference=FirebaseFirestore.getInstance().collection("Notification")
                                        .document(details.get("notiID").toString());
                                documentReference.update("seen",true);


                            }


                        }
                        notiAdapter.notifyDataSetChanged();
                    }
                });

    }

    private void setrecyclerview() {
        notiAdapter=new NotiAdapter(getActivity(),notimodelArrayList,Favouritefragment.this);


        favouriterecyclerview.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        favouriterecyclerview.setLayoutManager(linearLayoutManager);
        favouriterecyclerview.setAdapter(notiAdapter);
    }


    public void gotToprofile(String UID){

        Log.e("*","hgiii  +"+ UID);
        FragmentManager fragmentManager =
                (getActivity()). getSupportFragmentManager();

        Bundle bundle = new Bundle();

        bundle.putBoolean("owner",  false);
        bundle.putString("userID",UID);


        Profilefragment profilefragment = new Profilefragment();
        profilefragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        fragmentTransaction.replace
                (R.id.fragment_contnair, profilefragment).addToBackStack( "tag" ).commit();







    }
}