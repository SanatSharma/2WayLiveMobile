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
import com.mobile.rrqquiz.utility.SharedPrefManager;
import com.mobile.rrqquiz.utility.Utility;

import androidx.appcompat.app.AppCompatActivity;

import static com.mobile.rrqquiz.utility.ConstantUtil.Registration_number;
import static com.mobile.rrqquiz.utility.SharedPrefManager.KEY_PIN;
import static com.mobile.rrqquiz.utility.SharedPrefManager.KEY_REGISTRATION_ID;
import static com.mobile.rrqquiz.utility.SharedPrefManager.KEY_STUDENT_NAME;
import static com.mobile.rrqquiz.utility.URLs.URL_REGISTER;

public class UserSetPinActivity extends AppCompatActivity implements APIResponseInterface, OnKeyboardVisibilityListener {

    private com.google.android.material.textfield.TextInputEditText pin_no_edit_text, pin_repeat_edit_text;
    private Button submitbtn;
    private LinearLayout progressbar;
    private String registration_no, pin;
    private TextView titletxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_set_pin);
        progressbar = findViewById(R.id.progressbar);
        titletxt = findViewById(R.id.titletxt);
        Intent intent = getIntent();
        registration_no = intent.getStringExtra(Registration_number);
        pin_no_edit_text = findViewById(R.id.pin_no_edit_text);
        pin_repeat_edit_text = findViewById(R.id.pin_repeat_edit_text);
        submitbtn = findViewById(R.id.submitbtn);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pin_no_edit_text.getText().toString().isEmpty() &&
                        !pin_repeat_edit_text.getText().toString().isEmpty()) {
                    if (pin_no_edit_text.getText().toString().length() == 4 &&
                            pin_repeat_edit_text.getText().toString().length() == 4) {
                        if (pin_no_edit_text.getText().toString().equals(pin_repeat_edit_text.getText().toString())) {
                            progressbar.setVisibility(View.VISIBLE);
                            pin = pin_no_edit_text.getText().toString();
                            String _pin = Utility.hashedPassword(pin);
                            new APIRequestResponse(UserSetPinActivity.this,
                                    URL_REGISTER + "?StudentRegNo=" + registration_no + "&Password=" + _pin)
                                    .setUserPINPostRequest();
                        } else {
                            Toast.makeText(UserSetPinActivity.this, "Pin did'nt matched. Please re-enter again", Toast.LENGTH_LONG).show();

                        }

                    } else {
                        Toast.makeText(UserSetPinActivity.this, "Pin should have 4 digit", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(UserSetPinActivity.this, "Field could not be empty", Toast.LENGTH_LONG).show();
                }

            }
        });
        Utility.setKeyboardVisibilityListener(this, this);
    }

    @Override
    public void apiResponse(Object _response) {
        progressbar.setVisibility(View.GONE);
        String response = (String) _response;
        if (response != null && !response.isEmpty()) {
            SharedPrefManager.getInstance(UserSetPinActivity.this)
                    .setUserData(KEY_REGISTRATION_ID, registration_no);
            SharedPrefManager.getInstance(UserSetPinActivity.this)
                    .setUserData(KEY_STUDENT_NAME, response);
            SharedPrefManager.getInstance(UserSetPinActivity.this)
                    .setUserData(KEY_PIN, pin);
            Intent intent = new Intent(UserSetPinActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Registration confirmed", Toast.LENGTH_LONG).show();
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
