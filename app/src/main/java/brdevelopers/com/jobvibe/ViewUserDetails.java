package brdevelopers.com.jobvibe;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewUserDetails extends AppCompatActivity {

    private TextView TV_name,TV_email,TV_mobileValue,TV_experienceSkillsValue,TV_universitynameValue,TV_experienceDesignationValue;
   private ImageView imageViewprofile,IV_back_arrow;
   String resumeUrl;
   private TextView ViewUserResume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_details);
        TV_name=(TextView)findViewById(R.id.TV_name);
        TV_email=(TextView)findViewById(R.id.TV_email);
        ViewUserResume=(TextView)findViewById(R.id.ViewUserResume);
        TV_mobileValue=(TextView)findViewById(R.id.TV_mobileValue);
        TV_universitynameValue=(TextView)findViewById(R.id.TV_universitynameValue);
        TV_experienceDesignationValue=(TextView)findViewById(R.id.TV_experienceDesignationValue);
        TV_experienceSkillsValue=(TextView)findViewById(R.id.TV_experienceSkillsValue);
        imageViewprofile=(ImageView)findViewById(R.id.imageViewprofile);
        IV_back_arrow=(ImageView)findViewById(R.id.IV_back_arrow);

        ViewUserResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!resumeUrl.equals("null")) {

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(resumeUrl), "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Intent newIntent = Intent.createChooser(intent, "Open File");
                    try {
                        startActivity(newIntent);
                    } catch (ActivityNotFoundException e) {
                        // Instruct the user to install a PDF reader here, or something
                    }
                }
                else{
                    Toast.makeText(ViewUserDetails.this, "Please Upload the Resume First", Toast.LENGTH_SHORT).show();
                }
            }
        });


        IV_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Intent startingIntent = getIntent();
        final String UserId = startingIntent.getStringExtra("UserId");
     //   Toast.makeText(ViewUserDetails.this,UserId , Toast.LENGTH_SHORT).show();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //
                Model_User value = dataSnapshot.getValue(Model_User.class);


                TV_name.setText(value.name);

                 TV_email.setText(value.email);
                 TV_mobileValue.setText(value.mobile);
                resumeUrl=value.Resume;
                String imguri=value.profileImage;

                String CheckImageValue=String.valueOf(imguri);

                if(!CheckImageValue.equals("null")) {
                    Glide.with(ViewUserDetails.this).load(imguri)

                            .into(imageViewprofile);
                }

                Model_education_details education = dataSnapshot.child("QualificationDetails").getValue(Model_education_details.class);

                Model_personal_details personal = dataSnapshot.child("PersonalDetails").getValue(Model_personal_details.class);



                if (personal.degree!="null") {
    TV_universitynameValue.setText(personal.degree);
}
                if (personal.fieldStudy!="null") {
                    TV_experienceDesignationValue.setText(personal.fieldStudy);
                }
                if (education.experineceSkills!="null") {
                    TV_experienceSkillsValue.setText(education.experineceSkills);
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewUserDetails.this, "Failed to load categories", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
