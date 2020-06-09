package brdevelopers.com.jobvibe;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewJob extends AppCompatActivity implements SearchView.OnQueryTextListener {
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    SearchView editsearch;
    CustomAdapterInternshipViewJob customAdapter;
    ArrayList<EnityInternshipViewJob> enityInternshipViewJobArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job);

        Intent startingIntent = getIntent();
        final String InternName = startingIntent.getStringExtra("InternName");
        final String RootName = startingIntent.getStringExtra("RootName");
       // Toast.makeText(ViewJob.this, InternName, Toast.LENGTH_SHORT).show();
        editsearch = (SearchView) findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);
        recyclerView=(RecyclerView)findViewById(R.id.RV_IntenshipViewJob);
        layoutManager=new LinearLayoutManager( this);
        recyclerView.setLayoutManager(layoutManager);
       // prepare_news();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Jobs").child(RootName).child(InternName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
       //
                enityInternshipViewJobArrayList=new ArrayList<>();
//                enityInternshipViewJobArrayList.add(new EnityInternshipViewJob("React Developer","Kolkata","Now Mexian"));

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Model_View_Job user = snapshot.getValue(Model_View_Job.class);
                    user.id = snapshot.getKey();
                    Log.d("mytag", user.companyName);
                    enityInternshipViewJobArrayList.add(new EnityInternshipViewJob(user.jobTitle,user.city,user.companyName,user.id,RootName,InternName));
                }

              //  recyclerView.setAdapter(new CategoryAdapter(categories, HomeFragment.this));
                 customAdapter= new CustomAdapterInternshipViewJob(enityInternshipViewJobArrayList,ViewJob.this);
                recyclerView.setAdapter(customAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewJob.this, "Failed to load categories", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String text = s;
        customAdapter.filter(text);
        return false;
    }
}
