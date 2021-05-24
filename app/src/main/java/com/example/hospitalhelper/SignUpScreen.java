package com.example.hospitalhelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.hospitalhelper.Data_Holder.NewUserHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.karumi.dexter.Dexter.withActivity;

public class SignUpScreen extends AppCompatActivity{

    ImageView BackButton;
    Button Gallary;
    final static int IMAGE_CODE = 1;
    Bitmap bitmap;
    CircleImageView ProfilePhoto;
    Button Register;

    EditText FirstName,LastName,Emails,MobileNo,BirthDate,Passwords;

    RadioGroup GenderButtonGroup;
    RadioButton GenderButton;

    Uri resultUri;
    FirebaseFirestore clouddatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        BackButton = (ImageView) findViewById(R.id.back_button);
        Gallary = (Button) findViewById(R.id.profile_photo_chooser);
        ProfilePhoto = (CircleImageView) findViewById(R.id.profile_photo_set);
        FirstName = (EditText) findViewById(R.id.signup_firstname_edittext);
        LastName = (EditText) findViewById(R.id.signup_lastname_edittext);
        Emails = (EditText) findViewById(R.id.signup_email_edittext);
        MobileNo = (EditText) findViewById(R.id.signup_mobile_edittext);
        BirthDate = (EditText) findViewById(R.id.signup_birthday_edittext);
        Passwords = (EditText) findViewById(R.id.signup_password_edittext);
        Register = (Button) findViewById(R.id.Register_button);

        GenderButtonGroup = (RadioGroup) findViewById(R.id.signup_genderbutton_group);


        // Action Here...
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Select Profile Photo"),1);*/
                Dexter.withContext(SignUpScreen.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Profile Photo"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadDataOnFirebase();
            }
        });
    }

    public void onBackPressed(){
        Intent i = new Intent(SignUpScreen.this,LogInScreen.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CODE && resultCode == RESULT_OK && data != null){
            resultUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(resultUri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                ProfilePhoto.setImageBitmap(bitmap);
            }catch (Exception ex){

            }
        }
    }

    private void UploadDataOnFirebase() {
        if (Validate()) {

            int genderselecter = GenderButtonGroup.getCheckedRadioButtonId();
            GenderButton = (RadioButton) findViewById(genderselecter);

            final String Firstname = FirstName.getText().toString().trim();
            final String Lastname = LastName.getText().toString().trim();
            final String Emailid = Emails.getText().toString().trim();
            final String Mobileno = MobileNo.getText().toString().trim();
            final String Genderbutton = GenderButton.getText().toString();
            final String Birthdate = BirthDate.getText().toString().trim();
            final String Password = Passwords.getText().toString().trim();

            ProgressDialog dialog1 = new ProgressDialog(SignUpScreen.this);
            dialog1.setMessage("Please wait check in Server" +
                    " You are already exist or not!");
            dialog1.show();

            Query checkuser = FirebaseDatabase.getInstance().getReference("Patients").child(Mobileno);
            checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){

                        MobileNo.setError("such user exist!");
                        MobileNo.requestFocus();
                        dialog1.dismiss();

                    }
                    else{

                        mAuth = FirebaseAuth.getInstance();

                        mAuth.createUserWithEmailAndPassword(Emailid,Password)
                                .addOnCompleteListener(SignUpScreen.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful())
                                        {
                                            ProgressDialog dialog = new ProgressDialog(SignUpScreen.this);
                                            dialog.setTitle("File Uploader");
                                            dialog.show();

                                            if (resultUri != null) {

                                                FirebaseStorage storage = FirebaseStorage.getInstance();
                                                StorageReference Uploader = storage.getReference().child("Profile_Photo/" + resultUri.getLastPathSegment());

                                                Uploader.putFile(resultUri)
                                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                Uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                    @Override
                                                                    public void onSuccess(Uri uri) {

                                                                        // Storedata in Realtime Database
                                                                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                                                                        DatabaseReference root = db.getReference("Patients");

                                                                        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                                        // final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                                                        NewUserHelper newUserHelper = new NewUserHelper(Firstname, Lastname, Emailid, Mobileno, Genderbutton, Birthdate, Password, uri.toString(),user);
                                                                        root.child(Mobileno).setValue(newUserHelper);


                                                                        Toast.makeText(SignUpScreen.this, "Registration Successfull", Toast.LENGTH_LONG).show();

                                                                        Intent i = new Intent(SignUpScreen.this, LogInScreen.class);
                                                                        startActivity(i);
                                                                        finish();
                                                                    }
                                                                });
                                                            }
                                                        })
                                                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                                                float percentage = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                                                                dialog.setMessage("Uploading " + (int) percentage + " %");
                                                            }
                                                        });
                                            } else {
                                                dialog.dismiss();
                                                dialog1.dismiss();
                                                Toast.makeText(SignUpScreen.this, "Please Select Image", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }
                                });
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private boolean Validate() {
        Boolean result = false;

        String firstname = FirstName.getText().toString().trim();
        String lastname = LastName.getText().toString().trim();
        String email = Emails.getText().toString().trim();
        String mobileno = MobileNo.getText().toString().trim();
        String birthdate = BirthDate.getText().toString().trim();
        String password = Passwords.getText().toString().trim();

        if (firstname.length() == 0){
            Toast.makeText(this,"Please Enter First Name", Toast.LENGTH_SHORT).show();
        }
        else if (lastname.length() == 0 ){
            Toast.makeText(this,"Please Enter Last Name", Toast.LENGTH_SHORT).show();
        }
        else if (!validateEmailAddress(Emails)){
            Toast.makeText(this,"Please Enter Email ID", Toast.LENGTH_SHORT).show();
        }
        else if (mobileno.length() == 0 || mobileno.length() == 1 || mobileno.length() == 2 || mobileno.length() == 3 || mobileno.length() == 4 || mobileno.length() == 5 || mobileno.length() == 6 || mobileno.length() == 7 || mobileno.length() == 8 || mobileno.length() == 9 ){
            Toast.makeText(this,"Please Enter Valid Mobile No", Toast.LENGTH_SHORT).show();
        }
        else if (GenderButtonGroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(getApplicationContext(), "Select Gender", Toast.LENGTH_SHORT).show();
        }
        else if (birthdate.length() == 0){
            Toast.makeText(this,"Please Enter Birthdate", Toast.LENGTH_SHORT).show();
        }
        else if (password.length() == 0 ){
            Toast.makeText(this,"Please Enter Password", Toast.LENGTH_SHORT).show();
        }
        else {
            result = true;
        }
        return result;
    }

    private boolean validateEmailAddress(EditText email) {
        String emailInput = Emails.getText().toString();

        if(!emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches())
        {
            return true;
        }
        else {
            return  false;
        }
    }
}