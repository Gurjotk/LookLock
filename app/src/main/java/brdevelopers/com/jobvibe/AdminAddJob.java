package brdevelopers.com.jobvibe;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AdminAddJob extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView tv_addJob, tv_Cancel;
    private Spinner spincat;
    public String InternValue,TypeValue;
    private EditText category,city,jobTitle,designation,description,salary,companyName,website,type;
    String[] InternShip = { "Graphic Designer", "Android Developer", "Web Developer", "Technician", "Accountant"};
    String[] TypeData={"Internship","Job Category"};
    List<String> list = new ArrayList<String>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
String jobId;
String JobCategoryValue;
String AdminPostedJobId;
    private OnFragmentInteractionListener mListener;

    public AdminAddJob() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminAddJob.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminAddJob newInstance(String param1, String param2) {
        AdminAddJob fragment = new AdminAddJob();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_admin_add_job, container, false);
        // Inflate the layout for this fragment
        tv_addJob = view.findViewById(R.id.TV_addJob);
       // tv_Cancel = view.findViewById(R.id.cancel);
        tv_addJob.setOnClickListener(this);
//        tv_Cancel.setOnClickListener(this);

        // category = findViewById(R.id.et_category);
        city= view.findViewById(R.id.et_city);
        jobTitle = view.findViewById(R.id.et_jobtitle);
        designation= view.findViewById(R.id.et_designation);
        description= view.findViewById(R.id.et_description);
        salary= view.findViewById(R.id.et_salary);
        companyName= view.findViewById(R.id.et_companyName);
        website= view.findViewById(R.id.et_website);

        salary.setText("$ ");

        //Getting the instance of Spinner and applying OnItemSelectedListener on it






        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spinType = (Spinner) view.findViewById(R.id.et_type);
        spinType.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aaType = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,TypeData);
        aaType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinType.setAdapter(aaType);


         spincat = (Spinner)view.findViewById(R.id.et_category);
        spincat.setOnItemSelectedListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
                Toast.makeText(getActivity(), "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                return;
            }


            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
           String  dateTime = simpleDateFormat.format(calendar.getTime());

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
            job.AdiminId=SaveLoginUser.user.id;
            job.datetime=dateTime;
            job.savedjob="No";

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            DatabaseReference users = databaseReference.child("Jobs").child(TypeValue).child(InternValue);
            String newUserKey = users.push().getKey();
            job.id = newUserKey;
            jobId=newUserKey;
            databaseReference.child("Jobs").child(TypeValue).child(InternValue).child(newUserKey).setValue(job)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                       //     Toast.makeText(getActivity(), "Job created successfully", Toast.LENGTH_SHORT).show();




                            addJobtoAdmin();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Failed to create new account", Toast.LENGTH_SHORT).show();
                        }
                    });

        }



    }

    public void addJobtoAdmin(){

        String cityValue = city.getText().toString();
        String jobTitleValue = jobTitle.getText().toString();

        String designationValue = designation.getText().toString();
        String descriptionValue = description.getText().toString();
        String salaryValue = salary.getText().toString();
        String companyNameValue = companyName.getText().toString();
        String websiteValue = website.getText().toString();

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
        job.AdiminId=SaveLoginUser.user.id;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference users = databaseReference.child("Users").child(SaveLoginUser.user.id).child("PostedJobs");
        String newUserKey = users.push().getKey();
        AdminPostedJobId=newUserKey;
        job.id = jobId;
        databaseReference.child("Users").child(SaveLoginUser.user.id).child("PostedJobs").child(newUserKey).setValue(job)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        addPostedIdtoJob();
                       // Toast.makeText(getActivity(), "Job created successfully", Toast.LENGTH_SHORT).show();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed to create new account", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void addPostedIdtoJob(){


        HashMap<String, Object> result = new HashMap<>();
        result.put("AdminPostedJobId",AdminPostedJobId );



        //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        //DatabaseReference users = databaseReference.child("Users").child(SaveLoginUser.user.id).child("password");

        FirebaseDatabase.getInstance().getReference().child("Jobs").child(TypeValue).child(InternValue).child(jobId).updateChildren(result)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                       // Toast.makeText(getActivity(), "Job Created Successfully", Toast.LENGTH_SHORT).show();

                        Gson gson = new GsonBuilder().setLenient().create();
                        OkHttpClient client = new OkHttpClient();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://jobvibe-63850.web.app/")
                                .client(client)
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();
                        ApiInterface service = retrofit.create(ApiInterface.class);
                        Call<PushNotification> call = service.loginUser(InternValue);
                        call.enqueue(new Callback<PushNotification>() {
                            @Override
                            public void onResponse(Call<PushNotification> call, Response<PushNotification> response) {

                                if (response.body() != null && response.isSuccessful()) {

                                    Toast.makeText(getActivity(), response.body().getStatus(), Toast.LENGTH_SHORT).show();

                                }
                                else
                                {
                                    Toast.makeText(getActivity(), "No Response from Server!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<PushNotification> call, Throwable t) {
                                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed !!", Toast.LENGTH_SHORT).show();
                    }
                });
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


                //Creating the ArrayAdapter instance having the country list
                ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,list);
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
               // Spinner spincat = (Spinner) view.findViewById(R.id.et_category);
                //spincat.setOnItemSelectedListener(this);
                //Creating the ArrayAdapter instance having the country list
                ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,list);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Setting the ArrayAdapter data on the Spinner
                spincat.setAdapter(aa);
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
