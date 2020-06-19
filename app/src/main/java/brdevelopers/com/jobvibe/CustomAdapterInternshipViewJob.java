package brdevelopers.com.jobvibe;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomAdapterInternshipViewJob extends RecyclerView.Adapter<CustomAdapterInternshipViewJob.MyViewHolder>{

    ArrayList<EnityInternshipViewJob> enityInternshipViewJobArrayList;
    private ArrayList<EnityInternshipViewJob> arraylist;
    Context context;
    //declare interface


    public CustomAdapterInternshipViewJob(ArrayList<EnityInternshipViewJob> enityInternshipViewJobArrayList, Context context) {
        this.enityInternshipViewJobArrayList = enityInternshipViewJobArrayList;
        this.context = context;
    }



    public  static class MyViewHolder extends RecyclerView.ViewHolder
    {

       // ImageView img_thumb;
        TextView tv_JobTitle;
        TextView tv_location;
        TextView tv_companyName;
        TextView TV_date;
        TextView TV_applybtn;
        ImageView deleteJob;
        MyViewHolder (View itemview)
        {
            super(itemview);
            this.tv_JobTitle=(TextView)itemview.findViewById(R.id.TV_jobtitle);
            this.tv_location=(TextView)itemview.findViewById(R.id.TV_companyname);
            this.tv_companyName=(TextView)itemview.findViewById(R.id.TV_location);
            this.TV_date=(TextView)itemview.findViewById(R.id.TV_date);
            this.deleteJob=(ImageView)itemview.findViewById(R.id.deleteJob);
           // this.TV_applybtn=(TextView)itemview.findViewById(R.id.TV_applybtn);
            if(SaveLoginUser.user.UserType.equals("User")){
                deleteJob.setVisibility(View.GONE);
            }

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li= LayoutInflater.from(parent.getContext());
        View view=li.inflate(R.layout.job_row,parent,false);
        MyViewHolder myViewHolder= new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        TextView tv_JobTitle=holder.tv_JobTitle;
        TextView tv_location=holder.tv_location;
        TextView tv_companyName=holder.tv_companyName;
        TextView TV_date=holder.TV_date;
        ImageView deleteJob=holder.deleteJob;
//TextView TV_applybtn=holder.TV_applybtn;


        tv_JobTitle.setText(enityInternshipViewJobArrayList.get(position).JobTitle+"");
        tv_location.setText(enityInternshipViewJobArrayList.get(position).Location+"");
        tv_companyName.setText(enityInternshipViewJobArrayList.get(position).CompanyName+"");
        TV_date.setText(enityInternshipViewJobArrayList.get(position).dateTime+"");


        holder.deleteJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String RootName = enityInternshipViewJobArrayList.get(position).getRootName();
                String InternName = enityInternshipViewJobArrayList.get(position).getInternName();
                String Id = enityInternshipViewJobArrayList.get(position).getId();

                String JobId=Id;

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Jobs").child(RootName).child(InternName).child(Id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //
                        Model_View_Job value = dataSnapshot.getValue(Model_View_Job.class);

                        String postedJobId=value.AdminPostedJobId;
                        deletePostedJob(postedJobId);
                        deleteMainJob();

                        enityInternshipViewJobArrayList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,enityInternshipViewJobArrayList.size());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(context, "Failed to load categories", Toast.LENGTH_SHORT).show();
                    }
                });


            }


            public void deletePostedJob(String postedJobId){
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                DatabaseReference savedpost = databaseReference.child("Users").child(SaveLoginUser.user.id).child("PostedJobs").child(postedJobId);
                savedpost.removeValue();

            }
           public void deleteMainJob(){
               String RootName = enityInternshipViewJobArrayList.get(position).getRootName();
               String InternName = enityInternshipViewJobArrayList.get(position).getInternName();
               String Id = enityInternshipViewJobArrayList.get(position).getId();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                DatabaseReference savedpost = databaseReference.child("Jobs").child(RootName).child(InternName).child(Id);
                savedpost.removeValue();

            }
        });




        holder.tv_JobTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String a = enityInternshipViewJobArrayList.get(position).getRootName();
              String b = enityInternshipViewJobArrayList.get(position).getInternName();
              String c = enityInternshipViewJobArrayList.get(position).getId();
              String savedId=enityInternshipViewJobArrayList.get(position).getSavedJob();
                String buttonView=enityInternshipViewJobArrayList.get(position).getButtonView();
              Intent profile = new Intent(context,Apply_Job.class);
              //  Log.d("aa", "onClick: "+a);
             //   Log.d("aa", "onClick: "+b);
              //  Log.d("aa", "onClick: "+c);
            profile.putExtra("RootName",a);
                profile.putExtra("InternName",b);
                profile.putExtra("Id",c);
                profile.putExtra("SavedJobId",savedId);
                profile.putExtra("buttonView",buttonView);

                if(a.equals("RootName")){
                    return;
                }
            context.startActivity(profile);

            }
        });

        //============= SET IMAGE FRM URL

       // Glide.with(context).load(entityNewsArrayList.get(position).thumb).into(img_thumb);

    }


    @Override
    public int getItemCount() {
        return enityInternshipViewJobArrayList.size();
    }

}
