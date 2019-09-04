package hdtch.com.gpstrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity {
    private String email = "";

    private EditText passwordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        Intent intent = getIntent();

        if(intent != null){
            email = intent.getStringExtra("email");
        }

        passwordEditText = findViewById(R.id.passwordEditText2);

    }

    public void goToNamePickActivity(View view){

        if(passwordEditText.getText().toString().length() > 6){
            Intent intent = new Intent(getApplicationContext(), NameActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("password", passwordEditText.getText().toString().trim());
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "Password length should be more than 6 character", Toast.LENGTH_SHORT).show();
        }

    }
}
