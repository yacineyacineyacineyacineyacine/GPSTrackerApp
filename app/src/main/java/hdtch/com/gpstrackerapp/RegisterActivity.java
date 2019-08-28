package hdtch.com.gpstrackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button newtButton;

    private ProgressDialog progressDialog;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = findViewById(R.id.emailEditText2);
        newtButton = findViewById(R.id.button3);
        progressDialog = new ProgressDialog(this);


        auth = FirebaseAuth.getInstance();
    }

    public void goToPasswordActivity(View view){

        progressDialog.setMessage("checking email adress");
        progressDialog.show();
        auth.fetchSignInMethodsForEmail(emailEditText.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                      boolean check = !task.getResult().toString().isEmpty();
                      if(!check){
                          progressDialog.dismiss();
                          // email does not exist so create the user
                          Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                          intent.putExtra("email", emailEditText.getText().toString().trim());
                          startActivity(intent);
                      }else{
                          progressDialog.dismiss();
                          Toast.makeText(RegisterActivity.this, "this email is already registred", Toast.LENGTH_SHORT).show();
                      }
                    }
                });

    }
}
