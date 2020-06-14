package brdevelopers.com.jobvibe;


import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.ConditionVariable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class Edit_PersonalFragment extends Fragment implements View.OnClickListener,View.OnFocusChangeListener,TextWatcher,AdapterView.OnItemSelectedListener  {
    private EditText et_email, et_dob,et_mobile,et_name,et_address,et_pincode,et_city,et_degree,et_fos,ET_Experience;
    private RadioButton radio_male,radio_female;
    private ImageView iv_dob;
    private TextView TV_btnnext_personal_update,tv_age;
    private ProgressBar progressBar;
    public String degreeValue,studyValue;
    private Spinner degree,study;

    String[] country = { "India", "USA", "China", "Japan", "Other"};
    String [] degreevalues =
            {"High School Diploma","AEC","DEC","Bachelor Degree","Master Degree","Doctorate"};

    String [] studyvalue =
            {"Agriculture","Accounting","Business Economics","Business Finance","Communication and Journalism","Computer and Information","Science","Educational Administration and supervision","Engineering","Geography and Cartography","Mechanic and Repair Technologies","Psychology","Dance","Design and Applied Arts"};




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_edit__personal,container,false);
        //Binding view to references
        et_email= view.findViewById(R.id.ET_email_personal);
        et_name= view.findViewById(R.id.ET_name_personal);
        et_mobile=view.findViewById(R.id.ET_mobie);
        et_address=view.findViewById(R.id.ET_address);
        et_pincode= view.findViewById(R.id.ET_pincode);
        et_city=view.findViewById(R.id.ET_currentcity);
        et_dob=view.findViewById(R.id.ET_dob);
        radio_male=view.findViewById(R.id.Radio_male);
        radio_female=view.findViewById(R.id.Radio_female);
        degree=view.findViewById(R.id.spinnerDegreedata);
        study=view.findViewById(R.id.spinnerFielfStudydata);
        et_fos=view.findViewById(R.id.ET_fos);
        iv_dob=view.findViewById(R.id.IV_dob);
        TV_btnnext_personal_update=view.findViewById(R.id.TV_btnnext_personal_update);
        tv_age=view.findViewById(R.id.TV_age);
        ET_Experience=view.findViewById(R.id.ET_Experience);
        progressBar=view.findViewById(R.id.progressbar);

        String email=Home.canemail;



        Spinner spinner = (Spinner) view.findViewById(R.id.spinnerDegreedata);
        spinner.setOnItemSelectedListener(this);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, degreevalues);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);


        Spinner spinner2 = (Spinner) view.findViewById(R.id.spinnerFielfStudydata);
        spinner2.setOnItemSelectedListener(this);
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, studyvalue);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner2.setAdapter(adapter2);





        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").child(SaveLoginUser.user.id).child("PersonalDetails").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //
                Model_personal_details value = dataSnapshot.getValue(Model_personal_details.class);
                //Log.d("testtag", "Value is: " + value.companyName);

                String dbavlue=String.valueOf(value);

                if(dbavlue.equals("null")){
                    Log.d("tesfassttag", "in false");
                    return;
                }
                et_email.setText(value.email);
                et_name.setText(value.name);
                et_mobile.setText(value.mobile);
                et_address.setText(value.address);
                et_pincode.setText(value.pincode);
                et_city.setText(value.currentCity);
                et_dob.setText(value.dateOfBirth);
                radio_male.setChecked(value.male);
                radio_female.setChecked(value.female);
                degree.setSelection(adapter.getPosition(value.degree));
                // et_fos.setText(value.website);
                study.setSelection(adapter2.getPosition(value.fieldStudy));
                ET_Experience.setText(value.Experience);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });

       et_email.setText(SaveLoginUser.user.email);
        et_name.setText(SaveLoginUser.user.name);


      //  Toast.makeText(getActivity(), SaveLoginUser.user.email, Toast.LENGTH_SHORT).show();



        et_dob.setOnClickListener(this);        //Edit Text dob click
        iv_dob.setOnClickListener(this);        //Image View dob click
        et_dob.setOnFocusChangeListener(this);  //Edit Text dob on focus
        et_mobile.addTextChangedListener(this);//Edit Text mobile on textchange


        TV_btnnext_personal_update.setOnClickListener(this);

        return view;

    }






    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.TV_btnnext_personal_update)
        {
           // progressBar.setVisibility(View.VISIBLE);

            String email=et_email.getText().toString();
            String name=et_name.getText().toString();
            String mobile=et_mobile.getText().toString();
            String address=et_address.getText().toString();
            String pincode=et_pincode.getText().toString();
            String city=et_city.getText().toString();
            String dob=et_dob.getText().toString();
            String exper=ET_Experience.getText().toString();
          //  String degree=et_degree.getText().toString();
            //String fos=et_fos.getText().toString();
            boolean bolMale=radio_male.isChecked();
            boolean bolFemale=radio_female.isChecked();

            final Model_personal_details user = new Model_personal_details();
            user.email=email;
            user.name=name;
            user.mobile=mobile;
            user.address=address;
            user.pincode=pincode;
            user.currentCity=city;
            user.dateOfBirth=dob;
            user.male=bolMale;
            user.female=bolFemale;
            user.Experience=exper;
            user.fieldStudy=studyValue;
            user.degree=degreeValue;

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            DatabaseReference users = databaseReference.child("Users").child(SaveLoginUser.user.id).child("PersonalDetails");
           // String newUserKey = users.push().getKey();

            databaseReference.child("Users").child(SaveLoginUser.user.id).child("PersonalDetails").setValue(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Failed to Save", Toast.LENGTH_SHORT).show();
                        }
                    });




        }
        else if(v.getId()==R.id.ET_dob)
        {
            //Calling dateOfBirth on click on edittext
            dateOfBirth();
        }
        else if(v.getId()==R.id.IV_dob)
        {
            //Calling dateOfBirth on click on calendar ImageView
            dateOfBirth();
        }

    }




    private void dateOfBirth() {

        Calendar c=Calendar.getInstance();
        final int dd,mm,yy;
        dd=c.get(Calendar.DAY_OF_MONTH);
        mm=c.get(Calendar.MONTH);
        yy=c.get(Calendar.YEAR);

        DatePickerDialog dpd=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                et_dob.setText(dayOfMonth+"/"+month+"/"+year);
                calculateAge(dd,mm,yy,dayOfMonth,month,year); //calling calculateage to show it on textview age
            }
        },dd,mm,yy);

        dpd.updateDate(1980,0,1);
        dpd.show();
    }

    //On focus on edittext
    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if(v.getId()==R.id.ET_dob && hasFocus)
        {
            dateOfBirth();
        }

    }

// calculating age from datetimepicker and show it on textview age

    public void calculateAge(int cdd,int cmm,int cyy,int bdd,int bmm,int byy)
    {
        cmm++;
        bmm++;
        if(cdd<bdd)
            cmm=cmm-1;
        if(cmm<bmm)
            cyy=cyy-1;

        int age=cyy-byy;

        tv_age.setText("Age : "+age);
        tv_age.setVisibility(View.VISIBLE);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spin = (Spinner)adapterView;
       // Toast.makeText(getActivity(), "outside", Toast.LENGTH_SHORT).show();
        Spinner spin2 = (Spinner)adapterView;
        if(spin.getId() == R.id.spinnerDegreedata) {
            degreeValue = degreevalues[i].toString();
          //  Toast.makeText(getActivity(), "igndg", Toast.LENGTH_SHORT).show();
            //int position=i;
           // degreeValue=position;
        }
        if(spin2.getId()==R.id.spinnerFielfStudydata){
            studyValue = studyvalue[i].toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
