package com.example.hospitalhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hospitalhelper.Data_Holder.DoctorAppoimentDataHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class Doctor_Detail extends AppCompatActivity {

    String DoctorImage;
    String DoctorName,DoctorQualification,DoctorType,DoctorTime;

    Button bookDoctor;
    ImageView backButton;

    DatePickerDialog datePickerDialog;
    Button DoctorBook_pick_button;
    String DoctorBookDate;

    ImageView doctorImage;
    TextView doctorName;
    TextView doctorQualification;
    TextView doctorType;
    TextView doctorTime;

    String FirstName,LastrName,MobileNo,BirthDate,url;
    ProgressDialog mprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        DoctorImage = i.getStringExtra("DoctorImage");
        DoctorName = i.getStringExtra("DoctorName");
        DoctorQualification = i.getStringExtra("DoctorQualification");
        DoctorType = i.getStringExtra("DoctorType");
        DoctorTime = i.getStringExtra("DoctorTime");

        setContentView(R.layout.activity_doctor__detail);

        doctorImage = findViewById(R.id.doctor_images);
        doctorName = findViewById(R.id.doctorname_txt);
        doctorQualification = findViewById(R.id.doctor_qualification_txt);
        doctorType = findViewById(R.id.doctor_type_txt);
        doctorTime = findViewById(R.id.doctor_time_txt);
        bookDoctor = findViewById(R.id.bookofdoctor);
        backButton = findViewById(R.id.back_button);
        ProgressDialog dialog = new ProgressDialog(Doctor_Detail.this);


        // Date Picker set in Activity
        DoctorBook_pick_button = findViewById(R.id.bookdoctor_pick_button);
        initDatePicker();
        DoctorBook_pick_button.setText(getTodayDate());

        // set Data for Textviews
        Picasso.get().load(DoctorImage).into(doctorImage);
        doctorName.setText(DoctorName);
        doctorQualification.setText(DoctorQualification);
        doctorType.setText(DoctorType);
        doctorTime.setText(DoctorTime);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        bookDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFatchAndUploadData();
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
        return makeDateString(day,month,year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = makeDateString(dayOfMonth,month,year);
                DoctorBook_pick_button.setText(date);
                DoctorBookDate = date;
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
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

    private void FirebaseFatchAndUploadData() {
        //UserID
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //FirebaseDatabase Connection
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        DatabaseReference childR = reference.child("ProfileEditData").child(userid);

        childR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Fatch User ALL Data
                FirstName = String.valueOf(snapshot.child("firstname").getValue());
                LastrName = String.valueOf(snapshot.child("lastname").getValue());
                MobileNo = String.valueOf(snapshot.child("mobileno").getValue());
                BirthDate = String.valueOf(snapshot.child("birthdate").getValue());
                //set profile in circleImageview
                url = snapshot.child("profileimg").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Store in Realtime Database #RootName : DoctorAppoimentDatail

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference root = db.getReference("DoctorAppoimentDatail");

        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DoctorAppoimentDataHolder appoimentDataHolder = new DoctorAppoimentDataHolder(FirstName,LastrName,MobileNo,BirthDate,url,user,DoctorBookDate,DoctorName,DoctorQualification,DoctorType,DoctorTime,DoctorImage);
        root.child(user).setValue(appoimentDataHolder);

    }
    public void onBackPressed(){
        Intent i = new Intent(Doctor_Detail.this,Doctor.class);
        startActivity(i);
        finish();
    }
}