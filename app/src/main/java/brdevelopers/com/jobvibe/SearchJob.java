package brdevelopers.com.jobvibe;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchJob extends AppCompatActivity {
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    ImageView seachbtn,IV_backjobsearchJob;
    EditText searchtext;
    String flag="false";
    CustomAdapterInternshipViewJob customAdapter;
    ArrayList<EnityInternshipViewJob> enityInternshipViewJobArrayList;
    ArrayList<EnityInternshipViewJob> SearchArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_job);
        IV_backjobsearchJob=(ImageView)findViewById(R.id.IV_backjobsearchJob);
        recyclerView=(RecyclerView)findViewById(R.id.RV_searchJob);
        layoutManager=new LinearLayoutManager( this);
        recyclerView.setLayoutManager(layoutManager);
        IV_backjobsearchJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        seachbtn=findViewById(R.id.seachbtn);
        searchtext=findViewById(R.id.searchtext);
        seachbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String searchvalue=searchtext.getText().toString();
                 int count=0;
                 flag="false";
                    if(!TextUtils.isEmpty(searchvalue)) {
                        SearchArrayList=new ArrayList<>();
                        for(int i=0;i<enityInternshipViewJobArrayList.size();i++) {
                            if (enityInternshipViewJobArrayList.get(i).JobTitle.toLowerCase().contains(searchvalue.trim().toLowerCase())) {
                                //  Log.d("hhh", "onClick: "+enityInternshipViewJobArrayList.get(i).JobTitle);
                                String filterTitle = enityInternshipViewJobArrayList.get(i).JobTitle;
                                String FilterCity = enityInternshipViewJobArrayList.get(i).Location;
                                String FilterCmpanyName = enityInternshipViewJobArrayList.get(i).CompanyName;
                                String FilterDate = enityInternshipViewJobArrayList.get(i).dateTime;
                                String SaveData = enityInternshipViewJobArrayList.get(i).savedJob;
                                String FilterUserType = enityInternshipViewJobArrayList.get(i).rootName;
                                String FilterInternType = enityInternshipViewJobArrayList.get(i).interName;
                                String FilterId = enityInternshipViewJobArrayList.get(i).id;
                              //  Log.d("sfsfdsfs", "onClick: "+enityInternshipViewJobArrayList.get(i).interName);

                                flag="true";

                                SearchArrayList.add(new EnityInternshipViewJob(filterTitle, FilterCity, FilterCmpanyName,FilterId, FilterUserType, FilterInternType, FilterDate, SaveData,"SHOWBOTH"));
                                customAdapter = new CustomAdapterInternshipViewJob(SearchArrayList, SearchJob.this);
                                recyclerView.setAdapter(customAdapter);
                                count++;

                            }

                        }

                        if(flag.equals("false")){
                            SearchArrayList.clear();
                            customAdapter = new CustomAdapterInternshipViewJob(SearchArrayList, SearchJob.this);
                            recyclerView.setAdapter(customAdapter);
                            Toast.makeText(SearchJob.this, "Job Not Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(SearchJob.this, "blank search", Toast.LENGTH_SHORT).show();
                    }


            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Jobs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //
                enityInternshipViewJobArrayList=new ArrayList<>();
//                enityInternshipViewJobArrayList.add(new EnityInternshipViewJob("React Developer","Kolkata","Now Mexian"));

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    for (DataSnapshot jobType : snapshot.getChildren()) {
                        for (DataSnapshot jobCategory : jobType.getChildren()) {
                      Model_View_Job user = jobCategory.getValue(Model_View_Job.class);
                    user.id = jobCategory.getKey();
                    Log.d("mytag", user.companyName);
                    enityInternshipViewJobArrayList.add(new EnityInternshipViewJob(user.jobTitle,user.city,user.companyName,user.id,user.type,user.category,user.datetime,"SAVE","SHOWBOTH"));


                        }
                    }

                }

                //  recyclerView.setAdapter(new CategoryAdapter(categories, HomeFragment.this));
                customAdapter= new CustomAdapterInternshipViewJob(enityInternshipViewJobArrayList,SearchJob.this);
                recyclerView.setAdapter(customAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SearchJob.this, "Failed to load categories", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
