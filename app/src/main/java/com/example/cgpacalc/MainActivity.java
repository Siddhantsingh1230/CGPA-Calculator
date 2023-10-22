package com.example.cgpacalc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ListView list;
    TextView result;
    FrameLayout resultParent;
    Button calculateBtn;
    ArrayList<Double> listData;
    EditText sub1;
    EditText sub2;
    EditText sub3;
    EditText sub4;
    // for database operations
    ResultDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Status bar color to black
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        // Initialisation
        db = new ResultDatabaseHelper(this);
        list = findViewById(R.id.list);
        calculateBtn = findViewById(R.id.calculateBtn);
        resultParent = findViewById(R.id.resultParent);
        result = findViewById(R.id.result);
        sub1 = findViewById(R.id.sub1);
        sub2 = findViewById(R.id.sub2);
        sub3 = findViewById(R.id.sub3);
        sub4 = findViewById(R.id.sub4);

        //fetching values
        listData = db.getResults();
        // setting values
        ArrayAdapter<Double> data = new ArrayAdapter<>(this, R.layout.listcomponent, listData);
        list.setAdapter(data);
        // onclick
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sub1Value = sub1.getText().toString();
                String sub2Value = sub2.getText().toString();
                String sub3Value = sub3.getText().toString();
                String sub4Value = sub4.getText().toString();
                if (sub1Value.isEmpty() || sub2Value.isEmpty() || sub3Value.isEmpty() ||
                        sub4Value.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Empty Feilds!", Toast.LENGTH_SHORT).show();
                    return;
                }
                double res = (Integer.parseInt(sub1Value) + Integer.parseInt(sub2Value) +
                        Integer.parseInt(sub3Value) + Integer.parseInt(sub4Value)) / 4;
                resultParent.setVisibility(View.VISIBLE);
                result.setText("CGPA: " + res);
                db.addResult(res);
                //fetching values
                listData = db.getResults();
                ArrayAdapter<Double> data = new ArrayAdapter<>(MainActivity.this,
                        R.layout.listcomponent,
                        listData);
                list.setAdapter(data);
            }
        });
    }

}