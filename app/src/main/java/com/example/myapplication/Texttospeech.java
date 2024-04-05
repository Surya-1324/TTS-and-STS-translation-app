package com.example.myapplication;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import rm.com.audiowave.AudioWaveView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Texttospeech extends AppCompatActivity {
    private ButtonHandler buttonHandler;
    private ProgressBar progressBar;
    private Intent intent;
    private Intent intent1;
    private AudioWaveView waveView;
    private ObjectAnimator progress;
  private  Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texttospeech);

        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            String selectedText = preferences.getString("selectedText", "");
            int selectedImage = preferences.getInt("selectedImage", 0);
            SharedPreferences preferences2 = getSharedPreferences("gender", MODE_PRIVATE);

            String selectedTextGender= preferences2.getString("selectedTextGender","");
            int selectedImageGender = preferences2.getInt("selectedImageGender", 0);
            // Display selected text and image in the layout
            TextView textView = findViewById(R.id.intenttext);
            ImageView imageView = findViewById(R.id.intentimage);
            textView.setText(selectedText);
            imageView.setImageResource(selectedImage);
            TextView textView2 = findViewById(R.id.gendtext);
            ImageView imageView2 = findViewById(R.id.genderimg);
            textView2.setText(selectedTextGender);
            imageView2.setImageResource(selectedImageGender);
        }
        buttonHandler = new ButtonHandler(this);
        EditText editText = findViewById(R.id.input);
        AppCompatButton button = findViewById(R.id.generatebutton);
        intent = new Intent(this, MainActivity.class);
        intent1 = new Intent(this, Gender.class);
        waveView = findViewById(R.id.wave);
        progress = ObjectAnimator.ofFloat(waveView, "progress", 0F, 100F);
        progress.setInterpolator(new LinearInterpolator());

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    button.setAlpha(1.0f);
                } else {
                    button.setAlpha(0.5f);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void generate(View view) {
        String[] originalStrings = {
                "http://localhost:8080/api/tamil/female",
                "http://localhost:8080/api/telugu/female",
                "http://localhost:8080/api/hindi/male"};
        String encryptedData = StringEncryptDecrypt.encrypt(originalStrings,context);
        String decryptedData = StringEncryptDecrypt.decrypt(encryptedData,context);
        progressBar = findViewById(R.id.progressbar);
        ConstraintLayout loadLayout = findViewById(R.id.load);
        ConstraintLayout none = findViewById(R.id.none);
        if (progressBar != null) {
            showProgressBar();
            loadLayout.setVisibility(View.GONE);
            none.setVisibility(View.GONE);
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(() -> {
                hideProgressBar();
                loadLayout.setVisibility(View.VISIBLE);
            }, 4000);
        }
        progress.start();
        byte[] rawAudioData = readRawAudioData(R.raw.sample);

        if (rawAudioData != null) {
            waveView.setScaledData(rawAudioData);
        } else {
            Toast.makeText(this, "Failed to read raw audio data", Toast.LENGTH_SHORT).show();
        }
        if (encryptedData != null && !encryptedData.isEmpty()) {
            Toast.makeText(this, encryptedData, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Failed to decrypt data", Toast.LENGTH_LONG).show();
        }
    }
    private void showProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }
    private void hideProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }
    public void language(View view) {
        String[] originalStrings = {
            "http://localhost:8080/api/tamil/female",
            "http://localhost:8080/api/telugu/female",
            "http://localhost:8080/api/hindi/male"};
        String encryptedData = StringEncryptDecrypt.encrypt(originalStrings,context);
        String decryptedData = StringEncryptDecrypt.decrypt(encryptedData,context);

        if (view.getId() == R.id.lang) {
            startActivity(intent);
            if (decryptedData != null && !decryptedData.isEmpty()) {
                Toast.makeText(this, decryptedData, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to decrypt data", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void gender(View view) {
        String[] originalStrings = {
            "http://localhost:8080/api/tamil/female",
            "http://localhost:8080/api/telugu/female",
            "http://localhost:8080/api/hindi/male"};
        String encryptedData = StringEncryptDecrypt.encrypt(originalStrings,context);
        String decryptedData = StringEncryptDecrypt.decrypt(encryptedData,context);

        if (view.getId() == R.id.gend) {
            startActivity(intent1);
            if (decryptedData != null && !decryptedData.isEmpty()) {
                Toast.makeText(this, decryptedData, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to decrypt data", Toast.LENGTH_LONG).show();
            }
        }
    }
    private byte[] readRawAudioData(int resourceId) {
        InputStream inputStream = getResources().openRawResource(resourceId);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int bytesRead;
        byte[] buffer = new byte[4096];

        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void download(View view) {
        Toast.makeText(this,"Downloading Your Audio...",Toast.LENGTH_SHORT).show();
    }
}
