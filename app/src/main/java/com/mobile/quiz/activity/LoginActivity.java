package com.mobile.quiz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.quiz.R;
import com.mobile.quiz.api.APIRequestResponse;
import com.mobile.quiz.appInterface.APIResponseInterface;
import com.mobile.quiz.appInterface.OnKeyboardVisibilityListener;
import com.mobile.quiz.model.RRQDetailModel;
import com.mobile.quiz.utility.SharedPrefManager;
import com.mobile.quiz.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

import static com.mobile.quiz.utility.ConstantUtil.RRQ_Data;
import static com.mobile.quiz.utility.SharedPrefManager.KEY_REGISTRATION_ID;
import static com.mobile.quiz.utility.SharedPrefManager.KEY_STUDENT_NAME;
import static com.mobile.quiz.utility.URLs.URL_GETRRQDETAIL;

public class LoginActivity extends AppCompatActivity implements APIResponseInterface, OnKeyboardVisibilityListener {

    private com.google.android.material.textfield.TextInputEditText rrq_id_edit_text;
    private Button loginbtn;
    private LinearLayout progressbar, main_ly;
    private TextView studentname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        studentname = findViewById(R.id.studentname);
        progressbar = findViewById(R.id.progressbar);
        main_ly = findViewById(R.id.main_ly);
        rrq_id_edit_text = findViewById(R.id.rrq_id_edit_text);
        loginbtn = findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbar.setVisibility(View.VISIBLE);
                String registration_id = SharedPrefManager.
                        getInstance(LoginActivity.this).getUserData(KEY_REGISTRATION_ID);
                String rrqID = rrq_id_edit_text.getText().toString();
                new APIRequestResponse(LoginActivity.this,
                        URL_GETRRQDETAIL + registration_id + "/" + rrqID)
                        .getRRQGETRequest();

            }
        });
        studentname.setText(SharedPrefManager.
                getInstance(LoginActivity.this).getUserData(KEY_STUDENT_NAME).replaceAll("^\"|\"$", ""));
        Utility.setKeyboardVisibilityListener(this, this);

    }

    @Override
    public void apiResponse(Object response) {
        progressbar.setVisibility(View.GONE);
        RRQDetailModel rrqDetailModel = new RRQDetailModel();
        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            JSONObject student = jsonObject.getJSONObject("Student");
            rrqDetailModel.setRrqID(jsonObject.getString("RRQID"));
            rrqDetailModel.setSubjectName(jsonObject.getString("SubjectName"));
            rrqDetailModel.setNumQuestions(jsonObject.getInt("NumQuestions"));
            rrqDetailModel.setStudentID(student.getString("StudentID"));
            rrqDetailModel.setStudentName(student.getString("StudentName"));
            rrqDetailModel.setStudentImageURL(student.getString("StudentImageURL"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(LoginActivity.this, StartTestActivity.class);
        intent.putExtra(RRQ_Data, rrqDetailModel);
        startActivity(intent);
        finish();

    }

    @Override
    public void apiError(Object response) {
        progressbar.setVisibility(View.GONE);
        Toast.makeText(this, "Something went wrong, Please try again", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onVisibilityChanged(boolean visible) {
        if (visible) {
            main_ly.setVisibility(View.GONE);

        } else {
            main_ly.setVisibility(View.VISIBLE);

        }
    }
}
