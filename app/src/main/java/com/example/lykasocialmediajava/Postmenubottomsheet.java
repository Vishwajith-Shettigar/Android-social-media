package com.example.lykasocialmediajava;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Postmenubottomsheet extends BottomSheetDialogFragment {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    final  String POST_ID;

    RelativeLayout bookmark,edit,hidelike,hidecomt,delete;
    public Postmenubottomsheet(String post_id) {


        POST_ID = post_id;
    }

    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.postmenubottomsheet,
                container, false);
        bookmark=v.findViewById(R.id.bookmarkbtn);
        edit=v.findViewById(R.id.editbtn);
        hidelike=v.findViewById(R.id.hidelikebtn);
        hidecomt=v.findViewById(R.id.hidecomtbtn);

firebaseFirestore=FirebaseFirestore.getInstance();
firebaseAuth=FirebaseAuth.getInstance();
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,String>details=new HashMap<>();
                details.put("userID",firebaseAuth.getUid());
                details.put("postID",POST_ID);
                firebaseFirestore.collection("bookmark").document(firebaseAuth.getUid()+""+POST_ID)
                        .set(details);

                dismiss();

            }
        });



        return  v;



    }
}
