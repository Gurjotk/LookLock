package brdevelopers.com.jobvibe;


import android.app.DownloadManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Edit_EducationFragment extends Fragment implements View.OnClickListener{

    private EditText schoolBoard,schoolSchool,schoolYear,schoolpercent;
    private EditText universityBoard,universityUniversity,universityYear,universityPercentage;
    private EditText experineceDesignation,experinecPeriod,experineceSkills;
    private TextView tv_btnnext;
    private ProgressBar progressBar;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_edit__education,container,false);
        schoolBoard=view.findViewById(R.id.ET_12board);
        schoolSchool=view.findViewById(R.id.ET_12school);
        schoolYear=view.findViewById(R.id.ET_12yoc);
        schoolpercent=view.findViewById(R.id.ET_12per);

        universityBoard=view.findViewById(R.id.ET_10board);
        universityUniversity=view.findViewById(R.id.ET_10school);
        universityYear=view.findViewById(R.id.ET_10yoc);
        universityPercentage=view.findViewById(R.id.ET_10per);

        experineceDesignation=view.findViewById(R.id.ET_Designation);
        experinecPeriod=view.findViewById(R.id.ET_Period);
        experineceSkills=view.findViewById(R.id.pp_skills);


        tv_btnnext=view.findViewById(R.id.TV_btnnext);
        tv_btnnext.setOnClickListener(this);




        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").child(SaveLoginUser.user.id).child("QualificationDetails").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //
                Model_education_details value = dataSnapshot.getValue(Model_education_details.class);
                //Log.d("testtag", "Value is: " + value.companyName);
                String dbavlue=String.valueOf(value);

                if(dbavlue.equals("null")){
                    Log.d("tesfassttag", "in false");
                    return;
                }
                schoolBoard.setText(value.schoolBoard);
                schoolSchool.setText(value.schoolSchool);
                schoolYear.setText(value.schoolYear);
                schoolpercent.setText(value.schoolpercent);

                universityBoard.setText(value.universityBoard);
                universityUniversity.setText(value.universityUniversity);
                universityYear.setText(value.universityYear);
                universityPercentage.setText(value.universityPercentage);

                experineceDesignation.setText(value.experineceDesignation);
                experinecPeriod.setText(value.experinecPeriod);
                experineceSkills.setText(value.experineceSkills);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }








    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.TV_btnnext)
        {


            String s_board=schoolBoard.getText().toString();
            String s_school=schoolSchool.getText().toString();
            String s_year=schoolYear.getText().toString();
            String s_per=schoolpercent.getText().toString();

            String u_borad=universityBoard.getText().toString();
            String u_university=universityUniversity.getText().toString();
            String u_year=universityYear.getText().toString();
            String u_per=universityPercentage.getText().toString();

            String e_exper=experineceDesignation.getText().toString();
            String e_period=experinecPeriod.getText().toString();
            String e_skills=experineceSkills.getText().toString();


            final Model_education_details user = new Model_education_details();
            user.schoolBoard = s_board;
            user.schoolSchool = s_school;
            user.schoolYear = s_year;
            user.schoolpercent = s_per;

            user.universityBoard=u_borad;
            user.universityUniversity=u_university;
            user.universityYear=u_year;
            user.universityPercentage=u_per;

            user.experineceDesignation=e_exper;
            user.experinecPeriod=e_period;
            user.experineceSkills=e_skills;

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            DatabaseReference users = databaseReference.child("Users").child(SaveLoginUser.user.id).child("QualificationDetails");
            //String newUserKey = users.push().getKey();
            //user.id = newUserKey;
            databaseReference.child("Users").child(SaveLoginUser.user.id).child("QualificationDetails").setValue(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });




        }

    }


}
