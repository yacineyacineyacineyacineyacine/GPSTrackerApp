package hdtch.com.gpstrackerapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

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

    private Uri resultUri;
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
    public void generateCode(View view){

        Date myDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:MM:ss a", Locale.getDefault());
        String currentDate = simpleDateFormat.format(myDate);

        Random random = new Random();
        int r = 10000 + random.nextInt(90000);
        String code = String.valueOf(r);

        if(resultUri != null){
             Intent myIntent = new Intent(getApplicationContext(), InviteCodeActivity.class);
             myIntent.putExtra("email", email);
             myIntent.putExtra("password", password);
             myIntent.putExtra("name", name.getText().toString().trim());
             myIntent.putExtra("date", currentDate);
             myIntent.putExtra("isSharing", "false");
             myIntent.putExtra("code", code);
             myIntent.putExtra("imageURI", resultUri);

             startActivity(myIntent);
        }else {
            Toast.makeText(this, "please choose an image", Toast.LENGTH_SHORT).show();
        }

    }
    public void selectImage(View view){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 12 && resultCode == RESULT_OK && data!= null){
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                circleImageView.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }
}
