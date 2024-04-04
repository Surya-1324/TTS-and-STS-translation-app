package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

public class Progressbar extends AppCompatActivity {
    private ProgressBar progressBar;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progressbar);
        progressBar = findViewById(R.id.progressbar1);
        progressBar.setVisibility(ProgressBar.VISIBLE);

        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            String selectedText = receivedIntent.getStringExtra("selectedText");
            int selectedImage = receivedIntent.getIntExtra("selectedImage", 0);
            String selectedTextGender = receivedIntent.getStringExtra("selectedTextGender");
            int selectedImageGender = receivedIntent.getIntExtra("selectedImageGender", 0);
            Intent intent = new Intent(Progressbar.this, Texttospeech.class);
            intent.putExtra("selectedText", selectedText);
            intent.putExtra("selectedImage", selectedImage);
            intent.putExtra("selectedTextGender", selectedTextGender);
            intent.putExtra("selectedImageGender", selectedImageGender);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(intent);
                    finish();
                }
            }, 3000);
        }
//
//        Intent intent = getIntent();
//        if(intent != null) {
//            String selectedText = intent.getStringExtra("selectedText");
//            int selectedImage = intent.getIntExtra("selectedImage", 0);

//        Intent genderintent = getIntent();
//        String selectedTextGender = genderintent.getStringExtra("selectedTextGender");
//        int selectedImageGender = genderintent.getIntExtra("selectedImageGender", 0);
//        intent.putExtra("selectedText", selectedTextGender);
//        intent.putExtra("selectedImage", selectedImageGender);
    }
}
