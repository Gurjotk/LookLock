package brdevelopers.com.jobvibe;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapterAdminJobApplied extends RecyclerView.Adapter<CustomAdapterAdminJobApplied.MyViewHolder>{

    ArrayList<EntityAdminJobApplied> entityAdminJobApplied;
    Context context;

    public CustomAdapterAdminJobApplied(ArrayList<EntityAdminJobApplied> entityAdminJobApplied, Context context) {
        this.entityAdminJobApplied = entityAdminJobApplied;
        this.context = context;
    }

    public  static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_JobTitle;
        TextView tv_location;
        TextView tv_companyName;
        TextView TV_applybtn;

        LinearLayout applicants;

        LayoutInflater inflater;

        MyViewHolder (View itemview, LayoutInflater inflater)
        {
            super(itemview);
            this.inflater = inflater;

            this.tv_JobTitle=(TextView)itemview.findViewById(R.id.TV_jobtitle);
            this.tv_location=(TextView)itemview.findViewById(R.id.TV_companyname);
            this.tv_companyName=(TextView)itemview.findViewById(R.id.TV_location);
            applicants = itemview.findViewById(R.id.applicants);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li= LayoutInflater.from(parent.getContext());
        View view=li.inflate(R.layout.adminappliedjobuser,parent,false);
        MyViewHolder myViewHolder= new MyViewHolder(view, li);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        TextView tv_JobTitle=holder.tv_JobTitle;
        TextView tv_location=holder.tv_location;
        TextView tv_companyName=holder.tv_companyName;
//TextView TV_applybtn=holder.TV_applybtn;


        final EntityAdminJobApplied entityAdminJobApplied = this.entityAdminJobApplied.get(position);
        tv_JobTitle.setText(entityAdminJobApplied.JobTitle+"");
        tv_location.setText(entityAdminJobApplied.Location+"");
        tv_companyName.setText(entityAdminJobApplied.CompanyName+"");

        holder.tv_JobTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = entityAdminJobApplied.getRootName();
                String b = entityAdminJobApplied.getInternName();
                String c = entityAdminJobApplied.getId();
                Intent profile = new Intent(context,Apply_Job.class);
                //  Log.d("aa", "onClick: "+a);
                //   Log.d("aa", "onClick: "+b);
                //  Log.d("aa", "onClick: "+c);
                profile.putExtra("RootName",a);
                profile.putExtra("InternName",b);
                profile.putExtra("Id",c);

                if(a.equals("RootName")){
                    return;
                }
                context.startActivity(profile);

            }
        });

        holder.applicants.removeAllViews();
        for (final Model_User user : entityAdminJobApplied.users) {
            View userView = holder.inflater.inflate(R.layout.item_applicant, holder.applicants, false);
            ((TextView) userView.findViewById(R.id.user_name)).setText(user.name);
            ((TextView) userView.findViewById(R.id.user_email)).setText(user.email);
            userView.findViewById(R.id.user_name).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("pp", "onClick: "+user.name);
                    Intent userDetails = new Intent(context, ViewUserDetails.class);
                    String UserId=user.id;
                    userDetails.putExtra("UserId",UserId);
                    context.startActivity(userDetails);

                }
            });
            holder.applicants.addView(userView);
        }

    }


    @Override
    public int getItemCount() {
        return entityAdminJobApplied.size();
    }
}

