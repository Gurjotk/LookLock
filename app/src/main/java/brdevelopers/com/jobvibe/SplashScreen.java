package brdevelopers.com.jobvibe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {

    private static int TIMMER=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                String emailValue=pref.getString("email", "NOT_EXIST");

                if(emailValue.equals("NOT_EXIST")) {
                    Intent login = new Intent(SplashScreen.this, Login.class);
                    startActivity(login);
                    finish();
                }else
                {
                    Model_User user =new Model_User();
                    user.id=pref.getString("id", "NOT_EXIST");
                    user.UserType=pref.getString("UserType", "NOT_EXIST");
                    user.Resume=pref.getString("Resume", "NOT_EXIST");
                    user.name=pref.getString("name", "NOT_EXIST");
                    user.profileImage=pref.getString("profileImage", "NOT_EXIST");
                    user.mobile=pref.getString("mobile", "NOT_EXIST");
                    user.password=pref.getString("password", "NOT_EXIST");
                    user.email=pref.getString("email", "NOT_EXIST");

                    SaveLoginUser.user=user;


                    Intent homeintent = new Intent(SplashScreen.this, Home.class);
                    startActivity(homeintent);
                    finish();
                }
            }
        },TIMMER);


    }

}
