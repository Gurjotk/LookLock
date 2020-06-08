package brdevelopers.com.jobvibe;

import android.app.DatePickerDialog;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by End User on 27-03-2018.
 */

public class Profile_personal extends Fragment implements View.OnClickListener, View.OnFocusChangeListener, TextWatcher {

    private EditText et_email, et_dob,et_mobile,et_name,et_address,et_pincode,et_city,editText1;
    RadioButton radio_male,radio_female;
    private ImageView iv_dob;
    private TextView tv_btnnext,tv_age;
    private Spinner degree,fos;
    private ProgressBar progressBar;
    public View rootView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        rootView=inflater.inflate(R.layout.profile_personal,container,false);

       /* Profile.tv_personal.setBackgroundColor(Color.rgb(255,87,34));
        Profile.tv_personal.setTextColor(Color.rgb(255, 255, 255));
        Profile.tv_education.setBackgroundColor(Color.rgb(255, 255, 255));
        Profile.tv_education.setTextColor(Color.rgb(0,0,0));*/

        //Binding view to references
        et_email= rootView.findViewById(R.id.personali_email);
        et_name= rootView.findViewById(R.id.ET_name);
        et_mobile=rootView.findViewById(R.id.ET_mobie);
        et_address=rootView.findViewById(R.id.ET_addressperonal);
        et_pincode= rootView.findViewById(R.id.ET_pincode);
        et_city=rootView.findViewById(R.id.ET_currentcity);
        et_dob=rootView.findViewById(R.id.ET_dob);
        radio_male=rootView.findViewById(R.id.Radio_male);
        radio_female=rootView.findViewById(R.id.Radio_female);
        degree=rootView.findViewById(R.id.Spinner_degree);
        fos=rootView.findViewById(R.id.Spinner_fos);
        iv_dob=rootView.findViewById(R.id.IV_dob);
        tv_btnnext=rootView.findViewById(R.id.TV_btnnext);
        tv_age=rootView.findViewById(R.id.TV_age);
        progressBar=rootView.findViewById(R.id.progressbar);

        editText1.findViewById(R.id.editText1);

        editText1.setText("dfgdf");

        tv_btnnext.setOnClickListener(this);    //button next
        et_dob.setOnClickListener(this);        //Edit Text dob click
        iv_dob.setOnClickListener(this);        //Image View dob click
        et_dob.setOnFocusChangeListener(this);  //Edit Text dob on focus
        et_mobile.addTextChangedListener(this);  //Edit Text mobile on textchange
        //Fetching data for degree from web service
        et_address.setText("dfgdf");


        return rootView;
    }


    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.TV_btnnext)
        {



        }
        else if(v.getId()==R.id.ET_dob)
        {
            //Calling dateOfBirth on click on edittext
            dateOfBirth();
        }
        else if(v.getId()==R.id.IV_dob)
        {
            //Calling dateOfBirth on click on calendar ImageView
            dateOfBirth();
        }

    }



    private void dateOfBirth() {

        Calendar c=Calendar.getInstance();
        final int dd,mm,yy;
        dd=c.get(Calendar.DAY_OF_MONTH);
        mm=c.get(Calendar.MONTH);
        yy=c.get(Calendar.YEAR);

        DatePickerDialog dpd=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                et_dob.setText(dayOfMonth+"/"+month+"/"+year);
                calculateAge(dd,mm,yy,dayOfMonth,month,year); //calling calculateage to show it on textview age
            }
        },dd,mm,yy);

        dpd.updateDate(1980,0,1);
        dpd.show();
    }

    //On focus on edittext
    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if(v.getId()==R.id.ET_dob && hasFocus)
        {
            dateOfBirth();
        }

    }

// calculating age from datetimepicker and show it on textview age

    public void calculateAge(int cdd,int cmm,int cyy,int bdd,int bmm,int byy)
    {
        cmm++;
        bmm++;
        if(cdd<bdd)
            cmm=cmm-1;
        if(cmm<bmm)
            cyy=cyy-1;

        int age=cyy-byy;

        tv_age.setText("Age : "+age);
        tv_age.setVisibility(View.VISIBLE);

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    //On Degree Spinner Item selected


    @Override
    public void onStart(){
        super.onStart();
        et_email.setText(SaveLoginUser.user.email);
        et_address.setText("dfgdf");
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        et_email.setText(SaveLoginUser.user.email);
        et_address.setText("dfgdf");
    }

    @Override
    public void onResume() {
        super.onResume();
        et_email.setText(SaveLoginUser.user.email);
        et_address.setText("dfgdf");
    }





}
