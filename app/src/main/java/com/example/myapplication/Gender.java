package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Gender extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);
    }

    public void malegender(View view) {
        if (view.getId() == R.id.male) {
            startProgressbarActivity("Male", R.drawable.male);
        }
        Toast.makeText(this, "Male selected", Toast.LENGTH_SHORT).show();

    }

    public void femalegender(View view) {
        if (view.getId() == R.id.female) {
            startProgressbarActivity("Female", R.drawable.female);

        }
        Toast.makeText(this, "Female selected", Toast.LENGTH_SHORT).show();
    }
    private void startProgressbarActivity(String selectedTextGender, int selectedImageGender) {
        Intent receivedIntent = getIntent();
        String selectedText = receivedIntent.getStringExtra("selectedText");
        int selectedImage = receivedIntent.getIntExtra("selectedImage", 0);
        Intent intent = new Intent(Gender.this, Progressbar.class);
        intent.putExtra("selectedText", selectedText);
        intent.putExtra("selectedImage", selectedImage);
        intent.putExtra("selectedTextGender", selectedTextGender);
        intent.putExtra("selectedImageGender", selectedImageGender);
        startActivity(intent);
    }
}