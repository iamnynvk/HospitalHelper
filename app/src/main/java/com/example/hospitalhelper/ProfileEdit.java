package com.example.hospitalhelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hospitalhelper.Data_Holder.BloodRequestUser;
import com.example.hospitalhelper.Data_Holder.EditProfileData;
import com.example.hospitalhelper.Data_Holder.NewUserHelper;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
import com.squareup.picasso.Picasso;

import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileEdit extends AppCompatActivity {

    Button photo_chooser,Save;
    ImageView backtohome;
    CircleImageView ImageSetView;
    EditText edit_firstname,edit_lastname,edit_mobileno,edit_birthdate;

    final static int IMAGE_CODE = 1;
    Bitmap bitmap;

    Uri uri;
    boolean result = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        edit_firstname = findViewById(R.id.editprofile_firstname_edittext);
        edit_lastname = findViewById(R.id.editprofile_lastname_edittext);
        edit_mobileno = findViewById(R.id.editprofile_mobile_edittext);
        edit_birthdate = findViewById(R.id.editprofile_birthday_edittext);

        backtohome = (ImageView) findViewById(R.id.back_button_editprofile);
        photo_chooser = (Button) findViewById(R.id.eiditprofile_photo_chooser);
        ImageSetView = (CircleImageView) findViewById(R.id.editprofile_photo_set);
        Save = (Button) findViewById(R.id.editprofile_button);

        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        showDataEditProfile();

        photo_chooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(ProfileEdit.this)
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

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadOnFire();
            }
        });
    }

    private void showDataEditProfile() {
        //UserID
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //FirebaseDatabase Connection
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        DatabaseReference childR = reference.child("ProfileEditData").child(userid);

        childR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Set the User name in TextView
                String FirstName = String.valueOf(snapshot.child("firstname").getValue());
                edit_firstname.setText(FirstName);

                String LastrName = String.valueOf(snapshot.child("lastname").getValue());
                edit_lastname.setText(LastrName);

                String MobileNo = String.valueOf(snapshot.child("mobileno").getValue());
                edit_mobileno.setText(MobileNo);

                String BirthDate = String.valueOf(snapshot.child("birthdate").getValue());
                edit_birthdate.setText(BirthDate);

                //set profile in circleImageview
                String url = snapshot.child("profileimg").getValue().toString();
                Picasso.get().load(url).into(ImageSetView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CODE && resultCode == RESULT_OK && data != null){
            uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                ImageSetView.setImageBitmap(bitmap);
            }catch (Exception ex){

            }
        }
    }

    private void UploadOnFire() {
        if (Validate()){
            final String Firstname = edit_firstname.getText().toString().trim();
            final String Lastname = edit_lastname.getText().toString().trim();
            final String Mobileno = edit_mobileno.getText().toString().trim();
            final String Birthdate = edit_birthdate.getText().toString().trim();

            ProgressDialog dialog = new ProgressDialog(ProfileEdit.this);
            dialog.setTitle("File Uploader");
            dialog.setCancelable(false);
            dialog.show();

            if (uri != null) {

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference Uploader = storage.getReference().child("Profile_Photo/" + uri.getLastPathSegment());

                Uploader.putFile(uri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        // Storedata in Realtime Database
                                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                                        DatabaseReference root = db.getReference("ProfileEditData");

                                        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                        EditProfileData newUserHelper = new EditProfileData(Firstname, Lastname, Mobileno, Birthdate, uri.toString(), user);
                                        root.child(user).setValue(newUserHelper);


                                        Toast.makeText(ProfileEdit.this, "Upload Data Successfully", Toast.LENGTH_LONG).show();

                                        Intent i = new Intent(ProfileEdit.this, UserProfile.class);
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
                Toast.makeText(ProfileEdit.this, "Please Select Image", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean Validate() {
        boolean result = false;

        String firstname = edit_firstname.getText().toString().trim();
        String lastname = edit_lastname.getText().toString().trim();
        String mobileno = edit_mobileno.getText().toString().trim();
        String birthdate = edit_birthdate.getText().toString().trim();

        if(firstname.isEmpty())
        {
            edit_firstname.setError("Enter First Name");
            edit_firstname.requestFocus();
            return false;
        }
        else if(lastname.isEmpty())
        {
            edit_lastname.setError("Enter Last Name");
            edit_lastname.requestFocus();
            return false;
        }
        else if(mobileno.isEmpty())
        {
            edit_mobileno.setError("Enter MobileNo");
            edit_mobileno.requestFocus();
            return false;
        }
        else if (mobileno.length() == 0 || mobileno.length() == 1 || mobileno.length() == 2 || mobileno.length() == 3 || mobileno.length() == 4 || mobileno.length() == 5 || mobileno.length() == 6 || mobileno.length() == 7 || mobileno.length() == 8 || mobileno.length() == 9)
        {
            edit_mobileno.setError("Enter Valid MobileNo");
            edit_mobileno.requestFocus();
            return false;
        }
        else if(birthdate.isEmpty())
        {
            edit_birthdate.setError("Enter Birthdate");
            edit_birthdate.requestFocus();
            return false;
        }
        else {
            result = true;
        }

        return result;
    }

    public void onBackPressed(){
        Intent i = new Intent(ProfileEdit.this,UserProfile.class);
        startActivity(i);
        finish();
    }
}