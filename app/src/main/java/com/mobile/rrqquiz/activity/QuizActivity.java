package com.mobile.rrqquiz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.rrqquiz.R;
import com.mobile.rrqquiz.api.APIRequestResponse;
import com.mobile.rrqquiz.appInterface.APIResponseInterface;
import com.mobile.rrqquiz.model.RRQDetailModel;

import androidx.appcompat.app.AppCompatActivity;

import static com.mobile.rrqquiz.utility.ConstantUtil.RRQ_Data;
import static com.mobile.rrqquiz.utility.URLs.URL_SENDRRQDETAIL;

public class QuizActivity extends AppCompatActivity implements APIResponseInterface, View.OnClickListener {

    private LinearLayout progressbar;
    private TextView a_option, b_option, c_option, d_option,
            studentname, subjectname, rrqid, question, totalquestion;
    private Button submitbtn, nextbtn;
    private boolean isSubmitButtonClicked;
    private RRQDetailModel rrqDetailModel;
    private int questionCounter = 1;
    private String select = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        progressbar = findViewById(R.id.progressbar);
        a_option = findViewById(R.id.a_option);
        b_option = findViewById(R.id.b_option);
        c_option = findViewById(R.id.c_option);
        d_option = findViewById(R.id.d_option);
        a_option.setOnClickListener(this);
        b_option.setOnClickListener(this);
        c_option.setOnClickListener(this);
        d_option.setOnClickListener(this);
        studentname = findViewById(R.id.studentname);
        subjectname = findViewById(R.id.subjectname);
        rrqid = findViewById(R.id.rrqid);
        totalquestion = findViewById(R.id.totalquestion);
        question = findViewById(R.id.question);
        submitbtn = findViewById(R.id.submitbtn);
        nextbtn = findViewById(R.id.nextbtn);
        submitbtn.setOnClickListener(this);
        nextbtn.setOnClickListener(this);
        Intent intent = getIntent();
        rrqDetailModel = (RRQDetailModel) intent.getSerializableExtra(RRQ_Data);
        if (rrqDetailModel != null) {
            if (rrqDetailModel.getStudentName() != null && !rrqDetailModel.getStudentName().isEmpty()) {
                studentname.setText(rrqDetailModel.getStudentName());

            } else {
                studentname.setVisibility(View.GONE);
            }
            if (rrqDetailModel.getSubjectName() != null && !rrqDetailModel.getSubjectName().isEmpty()) {
                subjectname.setText(rrqDetailModel.getSubjectName());

            } else {
                subjectname.setVisibility(View.GONE);

            }
            if (rrqDetailModel.getRrqID() != null && !rrqDetailModel.getRrqID().isEmpty()) {
                rrqid.setText("RRQID: " + rrqDetailModel.getRrqID());

            } else {
                rrqid.setVisibility(View.GONE);
            }
            if (rrqDetailModel.getNumQuestions() != 0) {
                question.setText("Question: 1");
                totalquestion.setText("Total Questions : " + rrqDetailModel.getNumQuestions());

            } else {
                question.setVisibility(View.GONE);
                totalquestion.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void apiResponse(Object response) {
        progressbar.setVisibility(View.GONE);
        if ((Boolean) response) {
            isSubmitButtonClicked = true;
            Toast.makeText(this, "Response submitted successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Something went wrong, Please try again", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void apiError(Object response) {
        progressbar.setVisibility(View.GONE);
        Toast.makeText(this, "Something went wrong, Please try again", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if (v == a_option) {
            select = "A";
            a_option.setSelected(true);
            b_option.setSelected(false);
            c_option.setSelected(false);
            d_option.setSelected(false);

        } else if (v == b_option) {
            select = "B";
            a_option.setSelected(false);
            b_option.setSelected(true);
            c_option.setSelected(false);
            d_option.setSelected(false);

        } else if (v == c_option) {
            select = "C";
            a_option.setSelected(false);
            b_option.setSelected(false);
            c_option.setSelected(true);
            d_option.setSelected(false);

        } else if (v == d_option) {
            select = "D";
            a_option.setSelected(false);
            b_option.setSelected(false);
            c_option.setSelected(false);
            d_option.setSelected(true);

        } else if (v == submitbtn) {
            if (a_option.isSelected() || b_option.isSelected() || c_option.isSelected()
                    || d_option.isSelected()) {
                progressbar.setVisibility(View.VISIBLE);
                new APIRequestResponse(QuizActivity.this,
                        URL_SENDRRQDETAIL + "?StudentID=" + rrqDetailModel.getStudentID()
                                + "&RRQID=" + rrqDetailModel.getRrqID() + "&QuesNo=" + questionCounter + "&Response=" + select)
                        .generateConfirmOTPpostRequest();
            } else {
                Toast.makeText(this, "Please select any option", Toast.LENGTH_LONG).show();

            }

        } else if (v == nextbtn) {
            if (isSubmitButtonClicked) {
                isSubmitButtonClicked = false;
                questionCounter = questionCounter + 1;
                question.setText("Question: " + questionCounter);
                a_option.setSelected(false);
                b_option.setSelected(false);
                c_option.setSelected(false);
                d_option.setSelected(false);
                if (questionCounter == rrqDetailModel.getNumQuestions()) {
                    nextbtn.setText("END & LOGOUT");

                }
                if (questionCounter > rrqDetailModel.getNumQuestions()) {
                    Intent intent = new Intent(QuizActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            } else {
                Toast.makeText(this, "Please submit response successfully before click next.", Toast.LENGTH_LONG).show();

            }


        }


    }
}
