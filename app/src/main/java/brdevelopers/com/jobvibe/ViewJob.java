package brdevelopers.com.jobvibe;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewJob extends AppCompatActivity {
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;

    ArrayList<EnityInternshipViewJob> enityInternshipViewJobArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job);

        Intent startingIntent = getIntent();
        String InternName = startingIntent.getStringExtra("InternName");
       // Toast.makeText(ViewJob.this, InternName, Toast.LENGTH_SHORT).show();

        recyclerView=(RecyclerView)findViewById(R.id.RV_IntenshipViewJob);
        layoutManager=new LinearLayoutManager( this);
        recyclerView.setLayoutManager(layoutManager);
       // prepare_news();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Jobs").child("Internship").child(InternName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
       //
                enityInternshipViewJobArrayList=new ArrayList<>();
//                enityInternshipViewJobArrayList.add(new EnityInternshipViewJob("React Developer","Kolkata","Now Mexian"));

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Model_View_Job user = snapshot.getValue(Model_View_Job.class);
                    user.id = snapshot.getKey();
                    Log.d("mytag", user.companyName);
                    enityInternshipViewJobArrayList.add(new EnityInternshipViewJob(user.jobTitle,user.city,user.companyName));
                }

              //  recyclerView.setAdapter(new CategoryAdapter(categories, HomeFragment.this));
                CustomAdapterInternshipViewJob customAdapter= new CustomAdapterInternshipViewJob(enityInternshipViewJobArrayList,ViewJob.this);
                recyclerView.setAdapter(customAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewJob.this, "Failed to load categories", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public  void prepare_news(){

        enityInternshipViewJobArrayList=new ArrayList<>();
        enityInternshipViewJobArrayList.add(new EnityInternshipViewJob("React Developer","Kolkata","Now Mexian"));
        enityInternshipViewJobArrayList.add(new EnityInternshipViewJob("Native Developer","INDIA","India Flag "));
        enityInternshipViewJobArrayList.add(new EnityInternshipViewJob("Android Develper","CANADA","International"));
        enityInternshipViewJobArrayList.add(new EnityInternshipViewJob("Expo Developer","USA","Now Mexian"));
        enityInternshipViewJobArrayList.add(new EnityInternshipViewJob("Ios Developer","INDIA","India Flag"));
        enityInternshipViewJobArrayList.add(new EnityInternshipViewJob("Ionic Developer","CANADA","International"));


        CustomAdapterInternshipViewJob customAdapter= new CustomAdapterInternshipViewJob(enityInternshipViewJobArrayList,ViewJob.this);

        recyclerView.setAdapter(customAdapter);
    }
}
