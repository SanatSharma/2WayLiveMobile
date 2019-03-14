package com.mobile.rrqquiz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobile.rrqquiz.R;
import com.mobile.rrqquiz.api.APIRequestResponse;
import com.mobile.rrqquiz.appInterface.APIResponseInterface;

import androidx.appcompat.app.AppCompatActivity;

import static com.mobile.rrqquiz.utility.ConstantUtil.Mobile_number;
import static com.mobile.rrqquiz.utility.ConstantUtil.Registration_number;
import static com.mobile.rrqquiz.utility.URLs.URL_CONFORMOTP;

public class OTPVerificationActivity extends AppCompatActivity implements APIResponseInterface {

    private LinearLayout progressbar;
    private Button confirmbtn;
    private com.google.android.material.textfield.TextInputEditText otp_no_edit_text;
    private String registration_no, mobile_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
        Intent intent = getIntent();
        registration_no = intent.getStringExtra(Registration_number);
        mobile_no = intent.getStringExtra(Mobile_number);
        progressbar = findViewById(R.id.progressbar);
        otp_no_edit_text = findViewById(R.id.otp_no_edit_text);
        confirmbtn = findViewById(R.id.confirmbtn);
        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbar.setVisibility(View.VISIBLE);
                String otp_no = otp_no_edit_text.getText().toString();
                new APIRequestResponse(OTPVerificationActivity.this,
                        URL_CONFORMOTP + "?StudentRegNo=" + registration_no + "&Mobile=" + mobile_no + "&OTP=" + otp_no)
                        .generateConfirmOTPpostRequest();
            }
        });


    }

    @Override
    public void apiResponse(Object response) {
        progressbar.setVisibility(View.GONE);
        if ((Boolean) response) {
            Intent intent = new Intent(OTPVerificationActivity.this, UserSetPinActivity.class);
            intent.putExtra(Registration_number, registration_no);
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
}
