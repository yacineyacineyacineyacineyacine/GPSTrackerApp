package hdtch.com.gpstrackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEdirText;
    private EditText passwordEditText;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEdirText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.psswdEditText);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void login(View view){
        firebaseAuth.signInWithEmailAndPassword(emailEdirText.getText().toString().trim(), passwordEditText.getText().toString().trim()).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){

                           FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                           if(firebaseUser.isEmailVerified()){
                               Toast.makeText(LoginActivity.this, "User Logged In Successfully", Toast.LENGTH_LONG).show();
                               Intent intent = new Intent(LoginActivity.this, UserLocationMainActivity.class);
                               startActivity(intent);
                               finish();
                           }else{
                               Toast.makeText(LoginActivity.this, "Your Email is nit verified yet", Toast.LENGTH_SHORT).show();
                           }


                       }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Error: " +  e.getMessage().toString() , Toast.LENGTH_LONG).show();
            }
        });

    }
}
