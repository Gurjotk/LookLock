package brdevelopers.com.jobvibe;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.microedition.khronos.egl.EGLDisplay;

public class EditActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tv_personal,tv_education,tv_name,tv_email;
    ImageView profileimg;
    private final int REQUEST_CODE_GALLERY=999;
    ImageView iv_back;
    public String emailh=Home.canemail;
    public String name=Home.name;
    public String degree=Home.getdegree;
    public String fos=Home.getfos;
    public StorageReference mStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        tv_personal=findViewById(R.id.TV_editPersonal);
        tv_education=findViewById(R.id.TV_editEducation);
        iv_back=findViewById(R.id.IV_back_arrow);
        profileimg=findViewById(R.id.imageView);
        tv_name=findViewById(R.id.TV_name);
        tv_email=findViewById(R.id.TV_email);

        tv_name.setText(SaveLoginUser.user.name);
        tv_email.setText(SaveLoginUser.user.email);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(EditActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);

            }
        });

        tv_personal.setOnClickListener(this);
        tv_education.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_personal.setBackgroundColor(Color.rgb(255,87,34));
        tv_personal.setTextColor(Color.rgb(255, 255, 255));
        loadFragmentEdit(new Edit_PersonalFragment());

        loadProfilePic();

    }

    private void loadProfilePic() {

        DBManager db=new DBManager(this);
        byte[] byteimg=db.getImage(Home.canemail);
        if(byteimg!=null){
            Bitmap bitimg= BitmapFactory.decodeByteArray(byteimg, 0, byteimg.length);
            try{
                profileimg.setImageBitmap(bitimg);
            }
            catch (Exception ex)
            {
                Log.d("logcheck","exception "+ex);
            }
        }
    }

    //Permission menu for access gallery or camera
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==REQUEST_CODE_GALLERY){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){

                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_GALLERY);
            }
            else{
                Toast.makeText(this, "You don't have permission to access file!.", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //Setting image to image View after permission granted
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==REQUEST_CODE_GALLERY && resultCode==RESULT_OK && data!=null) {

            Uri uri = data.getData();

            Log.d("imagetag", String.valueOf(uri));


            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);


            String imgname=SaveLoginUser.user.name;
            final StorageReference riversRef = mStorageRef.child("images/"+imgname+".jpg");

            riversRef.putFile(uri)
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
                                    DatabaseReference users = databaseReference.child("Users").child(SaveLoginUser.user.id).child("profileImage");

                                    databaseReference.child("Users").child(SaveLoginUser.user.id).child("profileImage").setValue(downloadUrl.toString())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(EditActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(EditActivity.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
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
             profileimg.setImageBitmap(bitmap);

            //addImgToDb(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }








    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.TV_editPersonal)
        {
            tv_personal.setBackgroundColor(Color.rgb(255,87,34));
            tv_personal.setTextColor(Color.rgb(255, 255, 255));
            tv_education.setBackgroundColor(Color.rgb(255, 255, 255));
            tv_education.setTextColor(Color.rgb(0,0,0));
            loadFragmentEdit(new Edit_PersonalFragment());

        }
        else if(view.getId()==R.id.TV_editEducation)
        {
            tv_education.setBackgroundColor(Color.rgb(255,87,34));
            tv_education.setTextColor(Color.rgb(255, 255, 255));
            tv_personal.setBackgroundColor(Color.rgb(255, 255, 255));
            tv_personal.setTextColor(Color.rgb(0,0,0));
            loadFragmentEdit(new Edit_EducationFragment());

        }
        else if(view.getId()==R.id.IV_back_arrow){
            Intent intent=new Intent(EditActivity.this, Home.class);
            intent.putExtra("emailid",emailh);
            intent.putExtra("name",name);
            intent.putExtra("getdegree",degree);
            intent.putExtra("getfos",fos);
            startActivity(intent);
            overridePendingTransition(R.anim.topttobottom,R.anim.bottomtotop);
            finish();
        }
    }

    private void loadFragmentEdit(Fragment fragment) {
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.FL_edit,fragment);
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(EditActivity.this,Home.class);
        intent.putExtra("emailid",emailh);
        intent.putExtra("name",name);
        intent.putExtra("getdegree",degree);
        intent.putExtra("getfos",fos);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.topttobottom,R.anim.bottomtotop);
    }
}