package com.example.lawrence.fju_post;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class FragmentSetting extends AppCompatActivity {

    private static  final String TAG = "FireLog";

    private FirebaseFirestore mFirestore;



    private static final int PICK_IMAGE_REQUEST =1;
    private Button mUpload;
    private Button mChooseImage;
    private ImageView image;
    private ProgressBar mProgressBar;

    private Uri mImageUri;

    private StorageReference mStorageRef;

    private StorageTask mUploadTask;
    private StorageTask uploadTask;

    private FirebaseAuth firebaseAuth;
    private String user_ID;
    private String pictureURL;
    private String currentPicture;
    private Button logout;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        final TextView user_name = (TextView) findViewById(R.id.userName);
        final TextView ldap = (TextView) findViewById(R.id.textAccountNum);
        final TextView setting_page_title=findViewById(R.id.textPersonnalPage);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        final StorageReference storageRef = storage.getReference();
        //get userID
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        user_ID = user.getUid();
        final String[] data = new String[3];



        mFirestore.collection("UserData").whereEqualTo("user_ID",user_ID).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d(TAG,"Error" + e.getMessage());
                }
                for(DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){
                        data[0] = doc.getDocument().getString("name");
                        data[1] = doc.getDocument().getString("ldap");
                        data[2] = doc.getDocument().getString("picture");

                        Log.d(TAG,"Name : " + data[0]);

                    }
                }
                user_name.setText(data[0]);
                ldap.setText(data[1]);
                setting_page_title.setText(data[0]);
            }
        });




        firebaseAuth=FirebaseAuth.getInstance();
        logout =(Button)findViewById(R.id.buttonSignOut);

        mChooseImage=findViewById(R.id.button2);
        mUpload = findViewById(R.id.button7);
        image=findViewById(R.id.userPicture);
        mProgressBar=findViewById(R.id.progress_bar);

        mStorageRef= FirebaseStorage.getInstance().getReference("uploads");

        mChooseImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openFileChooser();
            }
        });

        mUpload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){



                if(mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(FragmentSetting.this, "Upload in progress",Toast.LENGTH_SHORT).show();
                }
                uploadFile();





                mFirestore.collection("UserData").document(user_ID)
                        .update(
                                "picture", pictureURL
                        );



            }
        });


        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(FragmentSetting.this,LoginActivity.class));
            }
        });


    }
    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(image);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime= MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadFile(){


        FirebaseStorage storage = FirebaseStorage.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        final StorageReference storageRef = storage.getReference();





        if(mImageUri != null){
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    +"."+getFileExtension(mImageUri));
            uploadTask=fileReference.putFile(mImageUri);


            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            },500);
                            Toast.makeText(FragmentSetting.this,"Upload successful",Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FragmentSetting.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int)progress);
                        }
                    });

            Task<Uri> urlTask = mUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        pictureURL=downloadUri.toString();
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });


        }else{
            Toast.makeText(this,"No file selected", Toast.LENGTH_SHORT).show();
        }


    }





}