package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class Gender extends AppCompatActivity {
    GridView gridView;
    Adapter adapter;
    String[] texts = {"Sample1", "Sample2", "Sample3", "Sample4", "Sample5"};
    int[] images = {R.drawable.telugu, R.drawable.tamil, R.drawable.marathi, R.drawable.odia, R.drawable.gujarati};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);
        gridView = findViewById(R.id.gridView2);
        adapter = new Adapter(this, getModels(), R.layout.model_gender);

        if (gridView != null) {
            gridView.setAdapter(adapter);
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Gender.this, Progressbar.class);
                Model selectedItem = (Model) parent.getItemAtPosition(position);
                SharedPreferences preferences2 = getSharedPreferences("gender", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences2.edit();
                editor.putString("selectedTextGender", texts[position]);
                editor.putInt("selectedImageGender", images[position]);
                editor.apply();
                startActivity(intent);
                Toast.makeText(Gender.this, texts[position], Toast.LENGTH_SHORT).show();
            }
        });

        SearchView searchView = findViewById(R.id.searchView2);
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    return true;
                }
            });
        }
    }

    private ArrayList<Model> getModels() {
        ArrayList<Model> models = new ArrayList<>();
        for (int i = 0; i < texts.length; i++) {
            models.add(new Model(texts[i], images[i]));
        }
        return models;
    }
}
