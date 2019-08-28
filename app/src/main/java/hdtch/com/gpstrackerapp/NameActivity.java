package hdtch.com.gpstrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class NameActivity extends AppCompatActivity {

    private String email;
    private String password;

    private EditText name;
    private Button next;
    private CircleImageView circleImageView;

    private URI resultURI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        name = findViewById(R.id.nameEditText);
        next = findViewById(R.id.button5);
        circleImageView = findViewById(R.id.ciecleImage);


        Intent myIntent = getIntent();
        if(myIntent != null){
            email = myIntent.getStringExtra("email");
            password = myIntent.getStringExtra("password");
        }
    }

    private void generateCode(View view){

        Date myDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:MM:ss a", Locale.getDefault());
        String currentDate = simpleDateFormat.format(myDate);

        Random random = new Random();
        int r = 10000 + random.nextInt(90000);
        String code = String.valueOf(r);

        if(resultURI != null){
             Intent myIntent = new Intent(getApplicationContext(), InviteCodeActivity.class);
             myIntent.putExtra("email", email);
             myIntent.putExtra("password", password);
             myIntent.putExtra("name", name.getText().toString().trim());
             myIntent.putExtra("date", currentDate);
             myIntent.putExtra("isSharing", "false");
             myIntent.putExtra("code", code);
             myIntent.putExtra("imageURI", resultURI);

             startActivity(myIntent);
        }else {
            Toast.makeText(this, "please choose an image", Toast.LENGTH_SHORT).show();
        }

    }
}
