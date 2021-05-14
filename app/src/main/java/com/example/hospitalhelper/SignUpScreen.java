package com.example.hospitalhelper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;

public class SignUpScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    // DatePicker
    DatePickerDialog datePickerDialog;

    //Button
    Button birthday_pick_button;
    Spinner bloodgroup_button;

    ImageView BackButton;
    Button ProfilePhotoChooser;
    ImageView ProfilePhotoSet;
    Uri uri;


    EditText FirstName, LastName, Email, MobileNo, Password;
    RadioGroup GenderGroup;
    RadioButton GenderButton;
    CheckBox TermCondition;
    Button Registration;
    ProgressDialog mprogress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        // Name-Specifier
        FirstName = findViewById(R.id.signup_firstname_edittext);
        LastName = findViewById(R.id.signup_lastname_edittext);
        Email = findViewById(R.id.signup_email_edittext);
        MobileNo = findViewById(R.id.signup_mobile_edittext);
        Password = findViewById(R.id.signup_password_edittext);

        GenderGroup = findViewById(R.id.signup_genderbutton_group);

        TermCondition = findViewById(R.id.termcondition_check);
        Registration = findViewById(R.id.Register_button);
        BackButton = findViewById(R.id.back_button);
        ProfilePhotoChooser = findViewById(R.id.profile_photo_chooser);
        ProfilePhotoSet = findViewById(R.id.profile_photo_set);
        mprogress = new ProgressDialog(this);

        birthday_pick_button = findViewById(R.id.signup_birthday_pick_button);
        bloodgroup_button = findViewById(R.id.signup_bloodgroup_button);


        initDatePicker();
        birthday_pick_button.setText(getTodayDate());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bloodgroup, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodgroup_button.setAdapter(adapter);
        bloodgroup_button.setOnItemSelectedListener(this);


        // Action Here
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ProfilePhotoChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(SignUpScreen.this);
            }
        });

        Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterNewUser();
            }
        });


    }

    // Date Picker Set Function's

    private String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = makeDateString(dayOfMonth, month, year);
                birthday_pick_button.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return getMonthFormate(month) + " " + dayOfMonth + " " + year;
    }

    private String getMonthFormate(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";

        // default should never happer
        return "JAN";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    // Drop down set function's
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // Back Button & Back Action
    public void onBackPressed() {
        Intent i = new Intent(SignUpScreen.this, LogInScreen.class);
        startActivity(i);
        finish();
    }

    // Croping Photo set in Profile-Setter ImageView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                uri = result.getUri();
                ProfilePhotoSet.setImageURI(uri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    // Uploading Data & Register Data With Validation
    private void RegisterNewUser() {

        // Gender GetData Which Select
        int GenderSelector = GenderGroup.getCheckedRadioButtonId();
        GenderButton = (RadioButton) findViewById(GenderSelector);

        if (Validate()) {

        } else {

        }
    }

    private boolean Validate() {
        Boolean result = false;

        String firstname = FirstName.getText().toString().trim();
        String lastname = LastName.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String mobileno = MobileNo.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if (firstname.length() == 0){
            Toast.makeText(this,"Please Enter First Name", Toast.LENGTH_SHORT).show();
        }
        else if (lastname.length() == 0 ){
            Toast.makeText(this,"Please Enter Last Name", Toast.LENGTH_SHORT).show();
        }
        else if (email.length() == 0 ){
            Toast.makeText(this,"Please Enter Email ID", Toast.LENGTH_SHORT).show();
        }
        else if (mobileno.length() == 0 && mobileno.length() == 1 && mobileno.length() == 2 && mobileno.length() == 3 && mobileno.length() == 4 && mobileno.length() == 5 && mobileno.length() == 6 && mobileno.length() == 7 && mobileno.length() == 8 && mobileno.length() == 9 ){
            Toast.makeText(this,"Please Enter Valid Mobile No", Toast.LENGTH_SHORT).show();
        }
        else if (password.length() == 0 ){
            Toast.makeText(this,"Please Enter Password", Toast.LENGTH_SHORT).show();
        }






        return false;
    }
}