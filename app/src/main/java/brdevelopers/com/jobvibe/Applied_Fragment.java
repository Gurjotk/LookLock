package brdevelopers.com.jobvibe;


import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Applied_Fragment extends Fragment {
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;

    ArrayList<EnityInternshipViewJob> enityInternshipViewJobArrayList;


    private TextView TV_applybtn;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_applied_,container,false);
      //  TV_applybtn=(TextView)view.findViewById(R.id.TV_applybtn);
//        TV_applybtn.setVisibility(view.GONE);
        recyclerView=(RecyclerView)view.findViewById(R.id.RV_jobApplied);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").child(SaveLoginUser.user.id).child("ApplyJobs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //
                enityInternshipViewJobArrayList=new ArrayList<>();
//                enityInternshipViewJobArrayList.add(new EnityInternshipViewJob("React Developer","Kolkata","Now Mexian"));

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Model_View_Job user = snapshot.getValue(Model_View_Job.class);
                    user.id = snapshot.getKey();
                    Log.d("mytag", user.companyName);
                    enityInternshipViewJobArrayList.add(new EnityInternshipViewJob(user.jobTitle,user.city,user.companyName,user.id,"RootName","InternName"));
                }

                //  recyclerView.setAdapter(new CategoryAdapter(categories, HomeFragment.this));
                CustomAdapterInternshipViewJob customAdapter= new CustomAdapterInternshipViewJob(enityInternshipViewJobArrayList,getActivity());
                recyclerView.setAdapter(customAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Failed to load Items", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }






}
