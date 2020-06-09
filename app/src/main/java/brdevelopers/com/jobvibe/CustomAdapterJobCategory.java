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

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapterJobCategory extends RecyclerView.Adapter<CustomAdapterJobCategory.MyViewHolder>{

    ArrayList<EntityInternship> entityInternshipArrayList;
    Context context;

    public CustomAdapterJobCategory(ArrayList<EntityInternship> entityInternshipArrayList, Context context) {
        this.entityInternshipArrayList = entityInternshipArrayList;
        this.context = context;
    }

    public  static class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv_headlines;
        TextView tv_type;

        MyViewHolder (View itemview)
        {
            super(itemview);

            this.tv_type=(TextView)itemview.findViewById(R.id.TV_companynamecat);
            this.tv_headlines=(TextView)itemview.findViewById(R.id.TV_jobtitlecat);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li= LayoutInflater.from(parent.getContext());
        View view=li.inflate(R.layout.enitity_jobcategory,parent,false);
        MyViewHolder myViewHolder= new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        TextView tv_headlines=holder.tv_headlines;
        TextView tv_type=holder.tv_type;


        tv_headlines.setText(entityInternshipArrayList.get(position).country+"");
        tv_type.setText(entityInternshipArrayList.get(position).companyName+"");

        //============= SET IMAGE FRM URL
        holder.tv_headlines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ctx = entityInternshipArrayList.get(position).country;
                Intent profile = new Intent(context,ViewJob.class);
                profile.putExtra("InternName",ctx);
                profile.putExtra("RootName","Job Category");
                context.startActivity(profile);
            }
        });
       // Glide.with(context).load(entityNewsArrayList.get(position).thumb).into(img_thumb);

    }


    @Override
    public int getItemCount() {
        return entityInternshipArrayList.size();
    }
}

