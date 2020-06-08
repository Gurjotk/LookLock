package brdevelopers.com.jobvibe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class CustomAdapterInternship extends RecyclerView.Adapter<CustomAdapterInternship.MyViewHolder>{

    ArrayList<EntityInternship> entityInternshipArrayList;
    Context context;

    public CustomAdapterInternship(ArrayList<EntityInternship> entityInternshipArrayList, Context context) {
        this.entityInternshipArrayList = entityInternshipArrayList;
        this.context = context;
    }

    public  static class MyViewHolder extends RecyclerView.ViewHolder
    {


        TextView tv_country;
        TextView tv_headlines;
        Button jobbutton;
        LinearLayout parent;

        MyViewHolder (View itemview)
        {
            super(itemview);

            this.tv_country=(TextView)itemview.findViewById(R.id.tv_country);
            this.parent=itemview.findViewById(R.id.parent);
           this.jobbutton=(Button) itemview.findViewById(R.id.jobbutton);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li= LayoutInflater.from(parent.getContext());
        View view=li.inflate(R.layout.entity_internship,parent,false);
        MyViewHolder myViewHolder= new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        TextView tv_country=holder.tv_country;
       Button jobbutton=holder.jobbutton;
        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        holder.parent.setBackgroundColor(currentColor);

        tv_country.setText(entityInternshipArrayList.get(position).country+"");
      //  tv_headlines.setText(entityInternshipArrayList.get(position).headlines+"");
        holder.jobbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String ctx = entityInternshipArrayList.get(position).country;
                Intent profile = new Intent(context,ViewJob.class);
                profile.putExtra("InternName",ctx);
                profile.putExtra("RootName","Internship");
                context.startActivity(profile);
            }
        });
        //============= SET IMAGE FRM URL

      //  Glide.with(context).load(entityNewsArrayList.get(position).thumb).into(img_thumb);

    }


    @Override
    public int getItemCount() {
        return entityInternshipArrayList.size();
    }



}

