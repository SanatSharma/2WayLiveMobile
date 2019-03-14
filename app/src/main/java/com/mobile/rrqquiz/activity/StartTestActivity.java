package com.mobile.rrqquiz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.rrqquiz.R;
import com.mobile.rrqquiz.model.RRQDetailModel;

import androidx.appcompat.app.AppCompatActivity;

import static com.mobile.rrqquiz.utility.ConstantUtil.RRQ_Data;

public class StartTestActivity extends AppCompatActivity {

    private TextView studentname, subjectname, rrqid, totalquestion;
    private Button startbtn;
    private boolean isTestStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);
        studentname = findViewById(R.id.studentname);
        subjectname = findViewById(R.id.subjectname);
        rrqid = findViewById(R.id.rrqid);
        totalquestion = findViewById(R.id.totalquestion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        final RRQDetailModel rrqDetailModel = (RRQDetailModel) intent.getSerializableExtra(RRQ_Data);
        if (rrqDetailModel != null) {
            if (rrqDetailModel.getStudentName() != null && !rrqDetailModel.getStudentName().isEmpty()) {
                studentname.setText("Name: " + rrqDetailModel.getStudentName());

            } else {
                studentname.setText("Name: Not Available");
            }
            if (rrqDetailModel.getSubjectName() != null && !rrqDetailModel.getSubjectName().isEmpty()) {
                subjectname.setText("Subject: " + rrqDetailModel.getSubjectName());

            } else {
                subjectname.setText("Subject: Not Available");

            }
            if (rrqDetailModel.getRrqID() != null && !rrqDetailModel.getRrqID().isEmpty()) {
                rrqid.setText("RRQ ID: " + rrqDetailModel.getRrqID());

            } else {
                rrqid.setText("RRQ ID: Not Available");
            }
            if (rrqDetailModel.getNumQuestions() != 0) {
                isTestStart = true;
                totalquestion.setText("Total Questions: " + rrqDetailModel.getNumQuestions());

            } else {
                isTestStart = false;
                totalquestion.setText("Total Questions: Not Available");

            }
        } else {
            isTestStart = false;
            studentname.setText("Name: Not Available");
            subjectname.setText("Subject: Not Available");
            rrqid.setText("RRQID: Not Available");
            totalquestion.setText("Total Questions: Not Available");
        }
        startbtn = findViewById(R.id.startbtn);
        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTestStart) {
                    Intent intent = new Intent(StartTestActivity.this, QuizActivity.class);
                    intent.putExtra(RRQ_Data, rrqDetailModel);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(StartTestActivity.this,
                            "There is 0 Questions, Please contact to Admin to start", Toast.LENGTH_LONG).show();

                }

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
