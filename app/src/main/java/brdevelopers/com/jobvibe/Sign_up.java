package brdevelopers.com.jobvibe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by End User on 27-03-2018.
 */

public class Sign_up extends AppCompatActivity implements TextWatcher, View.OnClickListener {
    private TextView tv_btnlogin, tv_SignUp;
    private EditText name,mobile,email,password,confirmpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        tv_btnlogin = findViewById(R.id.backtologin);
        tv_SignUp = findViewById(R.id.TV_signbutton);
        tv_btnlogin.setOnClickListener(this);
        tv_SignUp.setOnClickListener(this);



        name = findViewById(R.id.ET_name);
        mobile= findViewById(R.id.ET_mobile);
        email = findViewById(R.id.ET_email);
        password= findViewById(R.id.password);
        confirmpassword= findViewById(R.id.ET_cpassword);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.backtologin) {
            Intent profile = new Intent(Sign_up.this, Login.class);
            startActivity(profile);
            finish();
        }
        else if(v.getId() == R.id.TV_signbutton){

            String NameValue = name.getText().toString();
            String MobileValue = mobile.getText().toString();
            String EmialValue = email.getText().toString();

            String passwordValue = password.getText().toString();
            String confirmPasswordValue = confirmpassword.getText().toString();

            if (TextUtils.isEmpty(NameValue) || TextUtils.isEmpty(MobileValue)||TextUtils.isEmpty(EmialValue)||TextUtils.isEmpty(passwordValue)||TextUtils.isEmpty(confirmPasswordValue)) {
                Toast.makeText(Sign_up.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                return;
            }

            //String passwordValue = password.getText().toString();
            //String confirmPasswordValue = confirmpassword.getText().toString();
            if (!passwordValue.equals(confirmPasswordValue)) {
                Toast.makeText(Sign_up.this, "password and confirm password are not same", Toast.LENGTH_SHORT).show();
                return;
            }

            final Model_User user = new Model_User();
            user.name = NameValue;
            user.mobile = MobileValue;
            user.email = EmialValue;
            user.password = passwordValue;

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            DatabaseReference users = databaseReference.child("Users");
            String newUserKey = users.push().getKey();
            user.id = newUserKey;
            databaseReference.child("Users").child(newUserKey).setValue(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Sign_up.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                            // SaporiItalianoApplication.user = user;
                            Intent intent = new Intent(Sign_up.this, Login.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Sign_up.this, "Failed to create new account", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }
}


