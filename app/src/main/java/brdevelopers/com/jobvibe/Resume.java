package brdevelopers.com.jobvibe;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class Resume extends AppCompatActivity {

    private final int REQUEST_CODE_DOC=999;
    private TextView uploadresume,viewResume;
    private ImageView IV_back_reume;
    public StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        uploadresume=(TextView) findViewById(R.id.uploadresume);
        viewResume=(TextView)findViewById(R.id.viewResume);
        IV_back_reume=(ImageView)findViewById(R.id.IV_back_reume);
        IV_back_reume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(Resume.this, Home.class);
                startActivity(homeIntent);
                finish();
            }
        });



        viewResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Users").child(SaveLoginUser.user.id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //
                        Model_User value = dataSnapshot.getValue(Model_User.class);

                              String resume= String.valueOf(value.Resume);

                              if(!resume.equals("null")) {

                                  Intent intent = new Intent(Intent.ACTION_VIEW);
                                  intent.setDataAndType(Uri.parse(resume), "application/pdf");
                                  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                  Intent newIntent = Intent.createChooser(intent, "Open File");
                                  try {
                                      startActivity(newIntent);
                                  } catch (ActivityNotFoundException e) {
                                      // Instruct the user to install a PDF reader here, or something
                                  }
                              }
                              else{
                                  Toast.makeText(Resume.this, "Please Upload the Resume First", Toast.LENGTH_SHORT).show();
                              }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Resume.this, "Failed to load categories", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
        mStorageRef = FirebaseStorage.getInstance().getReference();
        uploadresume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDocument();
            }
        });

    }


    private void getDocument()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/msword,application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
        startActivityForResult(intent, REQUEST_CODE_DOC);
    }



    @Override
    protected void onActivityResult(int req, int result, Intent data)
    {
        // TODO Auto-generated method stub
        super.onActivityResult(req, result, data);
        if (result == RESULT_OK)
        {
            Uri fileuri = data.getData();




            String imgname=SaveLoginUser.user.name;
            final StorageReference riversRef = mStorageRef.child("resume/"+imgname+".pdf");

            riversRef.putFile(fileuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //Get a URL to the uploaded content
                            //Uri downloadUrl = taskSnapshot.getDownloadURL();
                            riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    final Uri downloadUrl = uri;
                                    SaveLoginUser.user.profileImage=uri.toString();
                                    // Log.d("firebaseUrl", String.valueOf(downloadUrl));
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                    DatabaseReference users = databaseReference.child("Users").child(SaveLoginUser.user.id).child("Resume");

                                    databaseReference.child("Users").child(SaveLoginUser.user.id).child("Resume").setValue(downloadUrl.toString())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(Resume.this, " Uploaded", Toast.LENGTH_SHORT).show();

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(Resume.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });
        }
    }


}