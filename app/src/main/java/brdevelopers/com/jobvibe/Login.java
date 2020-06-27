package brdevelopers.com.jobvibe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Login extends AppCompatActivity implements TextWatcher, View.OnClickListener {
    private TextView tv_btnloginUser,tv_btnloginAdmin, tv_createnew,user_email,admin_email,forgetpassword,invalidpassword;
   private  TextInputEditText user_password,admin_password;
   private TextInputLayout TIL_passwordlayout,TIL_passwordlayoutadmin;
   String [] UserType={"User","Employer"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        tv_btnloginUser = findViewById(R.id.TV_loginbutton_user);
        tv_btnloginAdmin = findViewById(R.id.TV_loginbutton_admin);
        user_email=findViewById(R.id.user_email);
        user_password=findViewById(R.id.user_password);
        invalidpassword=findViewById(R.id.invalidpassword);
        admin_email=findViewById(R.id.admin_email);
        admin_password=findViewById(R.id.admin_password);
        forgetpassword=findViewById(R.id.forgetpassword);
        tv_createnew = findViewById(R.id.crete_new);
        tv_btnloginUser.setOnClickListener((View.OnClickListener) this);
        tv_btnloginAdmin.setOnClickListener((View.OnClickListener) this);
        tv_createnew.setOnClickListener(this);






        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(getApplicationContext(),ForgetPassword.class);
                startActivity(profile);
                finish();
            }
        });
    }

    public void selectUser(View view) {
        TextView admintext= (TextView)findViewById(R.id.admintoggle);

        TextView adminEmail= (TextView)findViewById(R.id.admin_email);
        TextView admintPassword= (TextView)findViewById(R.id.admin_password);
         TextInputLayout TIL_passwordlayoutadmin=(TextInputLayout)findViewById(R.id.TIL_passwordlayoutadmin);
        TextView adminLoginButton= (TextView)findViewById(R.id.TV_loginbutton_admin);
        adminEmail.setVisibility(View.GONE);
        admintPassword.setVisibility(View.GONE);
        adminLoginButton.setVisibility(View.GONE);
        TIL_passwordlayoutadmin.setVisibility(View.GONE);

        TextView userEmail= (TextView)findViewById(R.id.user_email);
        TextView userPassword= (TextView)findViewById(R.id.user_password);
        TextInputLayout TIL_passwordlayout=(TextInputLayout)findViewById(R.id.TIL_passwordlayout);
        TextView userLoginButton= (TextView)findViewById(R.id.TV_loginbutton_user);
        userEmail.setVisibility(View.VISIBLE);
        userPassword.setVisibility(View.VISIBLE);
        userLoginButton.setVisibility(View.VISIBLE);
        TIL_passwordlayout.setVisibility(View.VISIBLE);


        ((TextView)view).setTextColor(this.getResources().getColor(R.color.white));
        ((TextView)view).setBackgroundColor(this.getResources().getColor(R.color.bluehead));
        admintext.setTextColor(this.getResources().getColor(R.color.black));
        //.setBackgroundColor(this.getResources().getColor(R.color.white));
        //((TextView)view).setTextColor('#000');
        admintext.setBackgroundResource(R.drawable.orangeborder_whiteback);
    }

    public void selectAdmin(View view) {
        TextView userText= (TextView)findViewById(R.id.usertoggle);

        TextView adminEmail= (TextView)findViewById(R.id.admin_email);
        TextView admintPassword= (TextView)findViewById(R.id.admin_password);
        TextInputLayout TIL_passwordlayoutadmin=(TextInputLayout)findViewById(R.id.TIL_passwordlayoutadmin);
        TextView adminLoginButton= (TextView)findViewById(R.id.TV_loginbutton_admin);
        adminEmail.setVisibility(View.VISIBLE);
        admintPassword.setVisibility(View.VISIBLE);
        adminLoginButton.setVisibility(View.VISIBLE);
        TIL_passwordlayoutadmin.setVisibility(View.VISIBLE);

        EditText userEmail= (EditText)findViewById(R.id.user_email);
      // TextInputEditText userPassword= (TextInputEditText)findViewById(R.id.user_password);
       TextInputLayout TIL_passwordlayout=(TextInputLayout)findViewById(R.id.TIL_passwordlayout);
        TextView userLoginButton= (TextView)findViewById(R.id.TV_loginbutton_user);
        userEmail.setVisibility(View.GONE);
      // userPassword.setVisibility(View.GONE);
        userLoginButton.setVisibility(View.GONE);
        TIL_passwordlayout.setVisibility(View.GONE);

        ((TextView)view).setTextColor(this.getResources().getColor(R.color.white));
        ((TextView)view).setBackgroundColor(this.getResources().getColor(R.color.bluehead));
        userText.setTextColor(this.getResources().getColor(R.color.black));
        userText.setBackgroundResource(R.drawable.orangeborder_whiteback);
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

        if (v.getId() == R.id.TV_loginbutton_user) {
            final Float elevation = tv_btnloginUser.getElevation();
            tv_btnloginUser.setElevation(-elevation);

            final String userEmail = user_email.getText().toString();
            final String password = user_password.getText().toString();
            if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(password)) {
                Toast.makeText(Login.this, "UserEmail or Password is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            /*if (userEmail.equals("admin@gmail.com")) {
                Toast.makeText(Login.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                return;
            }*/
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            Query query = databaseReference.child("Users");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Model_User user = snapshot.getValue(Model_User.class);
                        user.id = snapshot.getKey();
                        if (userEmail.equals(user.email) && password.equals(user.password)&& user.UserType.equals("User")) {

                           // Toast.makeText(Login.this, "Successfully logged in as User", Toast.LENGTH_SHORT).show();
                             SaveLoginUser.user=user;
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("email", user.email);
                            editor.putString("id", user.id);
                            editor.putString("mobile", user.mobile);
                            editor.putString("profileImage", user.profileImage);
                            editor.putString("name", user.name);
                            editor.putString("password", user.password);
                            editor.putString("Resume", user.Resume);
                            editor.putString("UserType", user.UserType);
                            editor.apply();
                            Intent profile = new Intent(getApplicationContext(),Home.class);
                            startActivity(profile);
                            finish();
                        }


                    }
                    if (SaveLoginUser.user == null) {
                      //  Toast.makeText(Login.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                        invalidpassword.setVisibility(View.VISIBLE);
                    }



                }



                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Login.this, "Failed to load login details", Toast.LENGTH_SHORT).show();
                }
            });


        }else if(v.getId()==R.id.TV_loginbutton_admin){

            final String userEmail = admin_email.getText().toString();
            final String password = admin_password.getText().toString();
            if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(password)) {
                Toast.makeText(Login.this, "UserEmail or Password is empty", Toast.LENGTH_SHORT).show();
                return;
            }


            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            Query query = databaseReference.child("Users");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Model_User user = snapshot.getValue(Model_User.class);
                        user.id = snapshot.getKey();
                        if (userEmail.equals(user.email) && password.equals(user.password) && user.UserType.equals("Employer")) {
                            SaveLoginUser.user=user;
                           // Toast.makeText(Login.this, "Successfully logged in as Admin", Toast.LENGTH_SHORT).show();
                            Intent profile = new Intent(Login.this, Admin.class);
                            startActivity(profile);
                            finish();
                        }

                    }

                    if (SaveLoginUser.user == null) {
                       // Toast.makeText(Login.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                        invalidpassword.setVisibility(View.VISIBLE);
                    }


                }



                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Login.this, "Failed to load login details", Toast.LENGTH_SHORT).show();
                }
            });


        }

        else if (v.getId() == R.id.crete_new) {
            Intent profile = new Intent(Login.this, Sign_up.class);
            startActivity(profile);
            finish();
        }
    }
}
