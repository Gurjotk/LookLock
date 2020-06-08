package brdevelopers.com.jobvibe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class JobSuccess extends AppCompatActivity {

    private TextView TV_okay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_success);
        TV_okay=findViewById(R.id.TV_okay);

        TV_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JobSuccess.this, Home.class);
                startActivity(intent);
                finish();
            }
        });



    }
}
