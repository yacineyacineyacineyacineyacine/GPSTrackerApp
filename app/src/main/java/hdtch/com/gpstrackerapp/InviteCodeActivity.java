package hdtch.com.gpstrackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

import hdtch.com.gpstrackerapp.tools.CreateUser;

public class InviteCodeActivity extends AppCompatActivity {

    private String email;
    private String password;
    private String name;
    private String date;
    private String isSharing;
    private String code;
    private Uri imageUri;
    private String userId;

    private TextView txtCode;
    private Button register;
    private ProgressDialog progressDialog;

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);

        txtCode = findViewById(R.id.textViewCode);
        register = findViewById(R.id.buttonRegister);
        progressDialog = new ProgressDialog(this);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        storageReference = FirebaseStorage.getInstance().getReference().child("User_images");

        Intent myIntent = getIntent();
        if(myIntent != null){
            email = myIntent.getStringExtra("email");
            password = myIntent.getStringExtra("password");
            name = myIntent.getStringExtra("name");
            date = myIntent.getStringExtra("date");
            isSharing = myIntent.getStringExtra("isSharing");
            code = myIntent.getStringExtra("code");
            imageUri = myIntent.getParcelableExtra("imageURI");
        }
       txtCode.setText(code);


    }

    public void registerUser(View view){

        progressDialog.setMessage("Please wait while we are creating an account for you");
        progressDialog.show();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // insert the new user in the database
                            CreateUser createUser = new CreateUser(name,email,password,code,date,"false","na","na","na");
                            firebaseUser = auth.getCurrentUser();
                            userId = firebaseUser.getUid();
                            databaseReference.child(userId).setValue(createUser)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                             if(task.isSuccessful()){

                                                 //save the image to FirebaseStorage

                                                 final StorageReference sr = storageReference.child(userId + ".jpg");
                                                    sr.putFile(imageUri)
                                                        .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                             @Override
                                                             public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                                                 if(task.isSuccessful()){

                                                                     sr.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                         @Override
                                                                         public void onSuccess(Uri uri) {
                                                                             String downlaodUrl = uri.toString();
                                                                             //save the image path to FirebaseStorage
                                                                             databaseReference.child(userId).child("imageUri").setValue(downlaodUrl)
                                                                                     .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                         @Override
                                                                                         public void onComplete(@NonNull Task<Void> task) {
                                                                                             if(task.isSuccessful()){
                                                                                                 //  Toast.makeText(InviteCodeActivity.this, "Email sent, Chec your emaul box", Toast.LENGTH_SHORT).show();
                                                                                                 sendVerfivationEmail();
                                                                                             }else{

                                                                                                 progressDialog.dismiss();
                                                                                                 Toast.makeText(InviteCodeActivity.this, "Failed to register the verification email", Toast.LENGTH_SHORT).show();
                                                                                             }
                                                                                         }
                                                                                     });
                                                                         }
                                                                     });





                                                                 }else {
                                                                     progressDialog.dismiss();
                                                                     Toast.makeText(InviteCodeActivity.this, "Could not register the user image", Toast.LENGTH_LONG).show();
                                                                 }

                                                             }
                                                         });
                                             }else{
                                                 progressDialog.dismiss();
                                                 Toast.makeText(InviteCodeActivity.this, "Could not register user", Toast.LENGTH_SHORT).show();
                                             }
                                        }
                                    });
                        }
                    }
                });
    }

    private void sendVerfivationEmail() {

        firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(InviteCodeActivity.this, "Email verification sent for you", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(myIntent);
                    finish();
                    auth.signOut();
                    }else{
                    Toast.makeText(InviteCodeActivity.this, "Could not send the email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
