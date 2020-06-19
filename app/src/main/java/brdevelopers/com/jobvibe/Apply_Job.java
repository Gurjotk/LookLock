package brdevelopers.com.jobvibe;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Apply_Job extends AppCompatActivity {

public  TextView jobTitle,companyName,location,salary,Jobdescription,Designation,CompanyWebsite;
public  LinearLayout LL_save,LL_apply;
public Boolean flag=false;
private ImageView IV_back;
public String AdminName,jobpostedid;
public String savedRootname,savedInternName,savedId,savedPostId;

//private ImageView IV_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply__job);

        Intent startingIntent = getIntent();
        final String RootName = startingIntent.getStringExtra("RootName");
        final String InternName = startingIntent.getStringExtra("InternName");
        final String Id = startingIntent.getStringExtra("Id");
        final String loginsavedId=startingIntent.getStringExtra("SavedJobId");
        final String buttonView=startingIntent.getStringExtra("buttonView");



           savedRootname=RootName;
           savedInternName=InternName;
           savedId=Id;
        savedPostId=loginsavedId;

        jobTitle = findViewById(R.id.TV_jobtitle);
        companyName = findViewById(R.id.TV_companyname);
        location=findViewById(R.id.TV_location);
        salary=findViewById(R.id.TV_salary);
        Jobdescription=findViewById(R.id.TV_about);
        Designation=findViewById(R.id.TV_Designation);
        CompanyWebsite=findViewById(R.id.TV_url);
        LL_save=findViewById(R.id.LL_save);
        LL_apply=findViewById(R.id.LL_apply);

        IV_back=findViewById(R.id.IV_back);


        if(SaveLoginUser.user.UserType.equals("Employer")){
            LL_save.setVisibility(View.GONE);
            LL_apply.setVisibility(View.GONE);
        }
        if(buttonView.equals("HIDESAVE")){
            LL_save.setVisibility(View.GONE);
        }
        else if(buttonView.equals("HIDEBOTH")){
            LL_save.setVisibility(View.GONE);
            LL_apply.setVisibility(View.GONE);
        }

        IV_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();

            }
        });


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Jobs").child(RootName).child(InternName).child(Id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //
                Model_View_Job value = dataSnapshot.getValue(Model_View_Job.class);

                jobTitle.setText(value.jobTitle);
                companyName.setText(value.companyName);
                location.setText(value.city);
                salary.setText(value.salary);
                Jobdescription.setText(value.description);
                Designation.setText(value.desgnation);
                CompanyWebsite.setText(value.website);
                AdminName=value.AdiminId;
                jobpostedid=value.AdminPostedJobId;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Apply_Job.this, "Failed to load categories", Toast.LENGTH_SHORT).show();
            }
        });

      /*  IV_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Apply_Job.this, ViewJob.class);
                startActivity(intent);
                finish();
            }
        });*/

        LL_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(Apply_Job.this, "Working", Toast.LENGTH_SHORT).show();

                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Users").child(SaveLoginUser.user.id).child("SavedJobs").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Model_View_Job user = snapshot.getValue(Model_View_Job.class);
                            if(user.id.equals(Id)){
                                Toast.makeText(Apply_Job.this, "You have Already Saved this job", Toast.LENGTH_SHORT).show();
                                flag=true;
                            }


                        }
              if(flag==false){
                  updateSaveStatus();

     final Model_View_Job job = new Model_View_Job();

    job.jobTitle=jobTitle.getText().toString();
    job.companyName=companyName.getText().toString();
    job.city=location.getText().toString();
    job.salary=salary.getText().toString();
    job.description=Jobdescription.getText().toString();
    job.desgnation=Designation.getText().toString();
    job.website=CompanyWebsite.getText().toString();
    job.category=InternName;
    job.type=RootName;
    job.id=Id;
    //  DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference users = databaseReference.child("Users").child(SaveLoginUser.user.id).child("SavedJobs");
    String newUserKey = users.push().getKey();
    job.savejobId=newUserKey;

    databaseReference.child("Users").child(SaveLoginUser.user.id).child("SavedJobs").child(newUserKey).setValue(job)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Apply_Job.this, "Job Saved", Toast.LENGTH_SHORT).show();

                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Apply_Job.this, "Failed to save", Toast.LENGTH_SHORT).show();
                }
            });
}

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Apply_Job.this, "Failed to load categories", Toast.LENGTH_SHORT).show();
                    }
                });







            }
        });

        LL_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Users").child(SaveLoginUser.user.id).child("ApplyJobs").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Model_View_Job user = snapshot.getValue(Model_View_Job.class);
                            if(user.id.equals(Id)){
                                Toast.makeText(Apply_Job.this, "You have Already Applied this job", Toast.LENGTH_SHORT).show();
                                flag=true;
                            }


                        }
                        if(flag==false){

                            final Model_View_Job job = new Model_View_Job();

                            job.jobTitle=jobTitle.getText().toString();
                            job.companyName=companyName.getText().toString();
                            job.city=location.getText().toString();
                            job.salary=salary.getText().toString();
                            job.description=Jobdescription.getText().toString();
                            job.desgnation=Designation.getText().toString();
                            job.website=CompanyWebsite.getText().toString();
                            job.category=InternName;
                            job.type=RootName;
                            job.id=Id;
                            //  DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference users = databaseReference.child("Users").child(SaveLoginUser.user.id).child("ApplyJobs");
                            String newUserKey = users.push().getKey();

                            databaseReference.child("Users").child(SaveLoginUser.user.id).child("ApplyJobs").child(newUserKey).setValue(job)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                           // Toast.makeText(Apply_Job.this, "Job Applied", Toast.LENGTH_SHORT).show();
                                            adminApplyJob();


                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Apply_Job.this, "Failed to jobs", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Apply_Job.this, "Failed to load categories", Toast.LENGTH_SHORT).show();
                    }
                });




            }
        });
    }

    public void updateSaveStatus(){

        HashMap<String, Object> result = new HashMap<>();
        result.put("savedjob","yes" );



        //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        //DatabaseReference users = databaseReference.child("Users").child(SaveLoginUser.user.id).child("password");

        FirebaseDatabase.getInstance().getReference().child("Jobs").child(savedRootname).child(savedInternName).child(savedId).updateChildren(result)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

    }

public void adminApplyJob(){


    final Model_User user =new Model_User();
    user.name=SaveLoginUser.user.name;
    user.email=SaveLoginUser.user.email;
    user.id=SaveLoginUser.user.id;
      DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference users = databaseReference.child("Users").child(AdminName).child("PostedJobs").child(jobpostedid).child("UserAppliedOnJob");

    String newUserKey = users.push().getKey();

    databaseReference.child("Users").child(AdminName).child("PostedJobs").child(jobpostedid).child("UserAppliedOnJob").child(newUserKey).setValue(user)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // Toast.makeText(Apply_Job.this, "Job Applied", Toast.LENGTH_SHORT).show();

                    if(!savedPostId.equals("SAVE")){
                        deleteSavejob();
                    }
                    Intent profile = new Intent(Apply_Job.this, JobSuccess.class);
                    startActivity(profile);
                    finish();

                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Apply_Job.this, "Failed to jobs", Toast.LENGTH_SHORT).show();
                }
            });
}

public void deleteSavejob(){
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference savedpost = databaseReference.child("Users").child(SaveLoginUser.user.id).child("SavedJobs").child(savedPostId);
              savedpost.removeValue();

}

}
