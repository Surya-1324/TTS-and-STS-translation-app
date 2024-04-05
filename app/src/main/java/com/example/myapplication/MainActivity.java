package com.example.myapplication;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   private Intent intent;
   GridView gridView;
   SearchView searchView;
    String[] texts = {"Tamil", "Telugu", "Malayalam", "Hindi", "English", "Kaanada", "Urudu",
            "Bengali", "Punjabi", "Marathi", "Sanskrit", "Odia", "Gujarati",
            "Tamil", "Telugu", "Malayalam", "Hindi", "English", "Kaanada", "Urudu",
            "Bengali", "Punjabi", "Marathi", "Sanskrit", "Odia", "Gujarati"
    };
    int[] images = {R.drawable.tamil, R.drawable.telugu, R.drawable.malayalam,
            R.drawable.hindi, R.drawable.englis, R.drawable.kaanada, R.drawable.urudu,
            R.drawable.bengali, R.drawable.punjabi, R.drawable.marathi,
            R.drawable.sanskrit, R.drawable.odia, R.drawable.gujarati,
            R.drawable.tamil, R.drawable.telugu, R.drawable.malayalam,
            R.drawable.hindi, R.drawable.englis, R.drawable.kaanada, R.drawable.urudu,
            R.drawable.bengali, R.drawable.punjabi, R.drawable.marathi,
            R.drawable.sanskrit, R.drawable.odia, R.drawable.gujarati
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent=new Intent(this,Gender.class);
        gridView=(GridView) findViewById(R.id.gridView1);
        searchView =(SearchView) findViewById(R.id.searchView1);
        final Adapter adapter=new Adapter(this,this.getModels(),R.layout.model);
        if (gridView != null) {
            gridView.setAdapter(adapter);
        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Gender.class);
                SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("selectedText", texts[position]);
                editor.putInt("selectedImage", images[position]);
                editor.apply();
                startActivity(intent);
                Toast toast = Toast.makeText(MainActivity.this, texts[position], Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String query) {
                    adapter.getFilter().filter(query);
                    return false;
                }
            });
        }
    }
    private ArrayList<Model> getModels(){
        ArrayList<Model>models=new ArrayList<Model>();
        Model m;
        for (int i=0; i<texts.length;i++){
            m=new Model(texts[i],images[i]);
            models.add(m);
        }
        return models;
    }
}
