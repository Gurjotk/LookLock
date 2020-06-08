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


import org.w3c.dom.Text;

import java.net.URL;
import java.util.ArrayList;

public class CustomAdapterInternshipViewJob extends RecyclerView.Adapter<CustomAdapterInternshipViewJob.MyViewHolder>{

    ArrayList<EnityInternshipViewJob> enityInternshipViewJobArrayList;
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
        TextView TV_applybtn;
        MyViewHolder (View itemview)
        {
            super(itemview);
            this.tv_JobTitle=(TextView)itemview.findViewById(R.id.TV_jobtitle);
            this.tv_location=(TextView)itemview.findViewById(R.id.TV_companyname);
            this.tv_companyName=(TextView)itemview.findViewById(R.id.TV_location);
           // this.TV_applybtn=(TextView)itemview.findViewById(R.id.TV_applybtn);

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
//TextView TV_applybtn=holder.TV_applybtn;


        tv_JobTitle.setText(enityInternshipViewJobArrayList.get(position).JobTitle+"");
        tv_location.setText(enityInternshipViewJobArrayList.get(position).Location+"");
        tv_companyName.setText(enityInternshipViewJobArrayList.get(position).CompanyName+"");

        holder.tv_JobTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String a = enityInternshipViewJobArrayList.get(position).getRootName();
              String b = enityInternshipViewJobArrayList.get(position).getInternName();
              String c = enityInternshipViewJobArrayList.get(position).getId();
              Intent profile = new Intent(context,Apply_Job.class);

            profile.putExtra("RootName",a);
                profile.putExtra("InternName",b);
                profile.putExtra("Id",c);

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
