package brdevelopers.com.jobvibe;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddJob extends AppCompatActivity implements TextWatcher,View.OnClickListener, AdapterView.OnItemSelectedListener {
    private TextView tv_addJob, tv_Cancel;
    public String InternValue,TypeValue;
    private EditText category,city,jobTitle,designation,description,salary,companyName,website,type;
    String[] InternShip = { "Graphic Designer", "Android Developer", "Web Developer", "Technician", "Accountant"};
    String[] TypeData={"Internship","Job Category"};
    List<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);

        tv_addJob = findViewById(R.id.TV_addJob);
        tv_Cancel = findViewById(R.id.cancel);
        tv_addJob.setOnClickListener(this);
        tv_Cancel.setOnClickListener(this);

       // category = findViewById(R.id.et_category);
        city= findViewById(R.id.et_city);
        jobTitle = findViewById(R.id.et_jobtitle);
        designation= findViewById(R.id.et_designation);
        description= findViewById(R.id.et_description);
        salary= findViewById(R.id.et_salary);
        companyName= findViewById(R.id.et_companyName);
        website= findViewById(R.id.et_website);



        //Getting the instance of Spinner and applying OnItemSelectedListener on it






        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spinType = (Spinner) findViewById(R.id.et_type);
        spinType.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aaType = new ArrayAdapter(this,android.R.layout.simple_spinner_item,TypeData);
        aaType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinType.setAdapter(aaType);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.TV_addJob){

            //String categoryValue = category.getText().toString();
            String cityValue = city.getText().toString();
            String jobTitleValue = jobTitle.getText().toString();

            String designationValue = designation.getText().toString();
            String descriptionValue = description.getText().toString();
            String salaryValue = salary.getText().toString();
            String companyNameValue = companyName.getText().toString();
            String websiteValue = website.getText().toString();


            if ( TextUtils.isEmpty(cityValue)||TextUtils.isEmpty(jobTitleValue)||TextUtils.isEmpty(designationValue)||TextUtils.isEmpty(descriptionValue)||TextUtils.isEmpty(salaryValue)||TextUtils.isEmpty(companyNameValue)||TextUtils.isEmpty(websiteValue)) {
                Toast.makeText(AddJob.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                return;
            }



            final Model_Job job = new Model_Job();
            job.category = InternValue;
            job.city = cityValue;
            job.jobTitle = jobTitleValue;
            job.desgnation = designationValue;
            job.description = descriptionValue;
            job.salary = salaryValue;
            job.companyName = companyNameValue;
            job.website = websiteValue;
            job.type=TypeValue;

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            DatabaseReference users = databaseReference.child("Jobs").child(TypeValue).child(InternValue);
            String newUserKey = users.push().getKey();
            job.id = newUserKey;
            databaseReference.child("Jobs").child(TypeValue).child(InternValue).child(newUserKey).setValue(job)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AddJob.this, "Job created successfully", Toast.LENGTH_SHORT).show();
                            // SaporiItalianoApplication.user = user;
                            Intent intent = new Intent(AddJob.this, Login.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddJob.this, "Failed to create new account", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
        else if(v.getId() == R.id.cancel){
            Intent intent = new Intent(AddJob.this, Login.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spin = (Spinner)adapterView;
        Spinner spin2 = (Spinner)adapterView;
        if(spin.getId() == R.id.et_category) {
            InternValue = list.get(i).toString();

        }
         if(spin2.getId() == R.id.et_type){
         TypeValue=TypeData[i].toString();
         if(TypeValue.equals("Internship")){

               list.clear();
             list.add("Graphic Designer");
             list.add("Android Developer");
             list.add("Web Developer");
             list.add("Technician");
             list.add("Accountant");

             Spinner spincat = (Spinner) findViewById(R.id.et_category);
             spincat.setOnItemSelectedListener(this);
             //Creating the ArrayAdapter instance having the country list
             ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,list);
             aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
             //Setting the ArrayAdapter data on the Spinner
             spincat.setAdapter(aa);
         }
         else if(TypeValue.equals("Job Category")){

             list.clear();
             list.add("General Labour");
             list.add("Graphic Designer");
             list.add("Content Writter");
             list.add("Cleaning");
             list.add("Hospitality");
             list.add("Transportation");
             list.add("Marketing");
             list.add("Customer Service");
             Spinner spincat = (Spinner) findViewById(R.id.et_category);
             spincat.setOnItemSelectedListener(this);
             //Creating the ArrayAdapter instance having the country list
             ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,list);
             aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
             //Setting the ArrayAdapter data on the Spinner
             spincat.setAdapter(aa);
         }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
