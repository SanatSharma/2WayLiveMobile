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
import com.mobile.rrqquiz.appInterface.OnKeyboardVisibilityListener;
import com.mobile.rrqquiz.utility.Utility;

import androidx.appcompat.app.AppCompatActivity;

import static com.mobile.rrqquiz.utility.ConstantUtil.Mobile_number;
import static com.mobile.rrqquiz.utility.ConstantUtil.Registration_number;
import static com.mobile.rrqquiz.utility.URLs.URL_SENDOTP;

public class RegistrationActivity extends AppCompatActivity implements APIResponseInterface, OnKeyboardVisibilityListener {

    private Button continuebtn;
    private com.google.android.material.textfield.TextInputEditText registration_no_edit_text, mobileno_edit_text;
    private LinearLayout progressbar;
    private TextView titletxt;
    private String registrationNo, mobileNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        registration_no_edit_text = findViewById(R.id.registration_no_edit_text);
        mobileno_edit_text = findViewById(R.id.mobileno_edit_text);
        titletxt = findViewById(R.id.titletxt);
        progressbar = findViewById(R.id.progressbar);
        continuebtn = findViewById(R.id.continuebtn);
        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbar.setVisibility(View.VISIBLE);
                registrationNo = registration_no_edit_text.getText().toString();
                mobileNo = mobileno_edit_text.getText().toString();
                new APIRequestResponse(RegistrationActivity.this,
                        URL_SENDOTP + "?StudentRegNo=" + registrationNo + "&Mobile=" + mobileNo)
                        .generateConfirmOTPpostRequest();

            }
        });
        Utility.setKeyboardVisibilityListener(this, this);

    }


    @Override
    public void apiResponse(Object response) {
        progressbar.setVisibility(View.GONE);
        if ((Boolean) response) {
            Intent intent = new Intent(RegistrationActivity.this, OTPVerificationActivity.class);
            intent.putExtra(Registration_number, registrationNo);
            intent.putExtra(Mobile_number, mobileNo);
            startActivity(intent);
            finish();
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
    public void onVisibilityChanged(boolean visible) {
        if (visible) {
            titletxt.setVisibility(View.GONE);

        } else {
            titletxt.setVisibility(View.VISIBLE);

        }

    }
}
