package com.example.lykasocialmediajava;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class Postmenubottomsheet extends BottomSheetDialogFragment {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    final  String POST_ID;
    final String UID;
    String postUrl,text;

    boolean islikehidealready=false;
    boolean iscomthidealready=false;
    FirebaseStorage firebaseStorage;
TextView liketext,comttext;

    RelativeLayout bookmark,edit,hidelike,hidecomt,delete;
    public Postmenubottomsheet(String uID,String post_id,String postUrl,String text, boolean hidelike,boolean hidecomt) {


        POST_ID = post_id;
        UID=uID;
      this. postUrl=postUrl;
      this.text=text;
      this.islikehidealready=hidelike;
      this.iscomthidealready=hidecomt;
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
delete=v.findViewById(R.id.deletebtn);

liketext=v.findViewById(R.id.liketext);
comttext=v.findViewById(R.id.comttext);

firebaseFirestore=FirebaseFirestore.getInstance();
firebaseAuth=FirebaseAuth.getInstance();
firebaseStorage=FirebaseStorage.getInstance();

if(!(UID.equals(firebaseAuth.getUid())))
{
edit.setVisibility(View.GONE);
hidecomt.setVisibility(View.GONE);
hidelike.setVisibility(View.GONE);
delete.setVisibility(View.GONE);


}


if(islikehidealready)
{
  liketext.setText("Unhide like count");

}
if(iscomthidealready)
{
    comttext.setText("Unhide comments");
}


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



edit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(UID.equals(firebaseAuth.getUid()))
        {
            Intent intent=new Intent(getActivity(),Newpostactivity.class);
            intent.putExtra("isEdit","true");
            intent.putExtra("postUrl",postUrl);
            intent.putExtra("text",text);
            intent.putExtra("postID",POST_ID);
            startActivity(intent);


        }
    }
});

hidelike.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
boolean settf=false;
        if(islikehidealready)
        {
            settf=false;
        }
        else{
            settf=true;
        }

        firebaseFirestore.collection("posts").document(POST_ID)
                .update("hideLike",settf);
        dismiss();

    }
});

        hidecomt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean settf=false;
                if(iscomthidealready){

                    settf=false;
                }
                else{
                    settf=true;

                }

                firebaseFirestore.collection("posts").document(POST_ID)
                        .update("hidecomt",settf);
                dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseFirestore.collection("posts").document(POST_ID)
                        .delete();
                Log.e("*","dfafr -- "+ postUrl);

                if(!postUrl.equals("null")) {
                    StorageReference storageReference = firebaseStorage.getReferenceFromUrl(postUrl);

                    storageReference.delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.e("*", "deleted succ");
                                }

                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("*", "deleted fail");
                                }
                            });
                }

dismiss();
            }
        });

        return  v;



    }
}
