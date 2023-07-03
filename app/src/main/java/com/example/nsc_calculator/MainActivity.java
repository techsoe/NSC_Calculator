package com.example.nsc_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button ieeeButton, rootFindButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ieeeButton = findViewById(R.id.ieeeButton);
        ieeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIEEE();
            }
        });

        rootFindButton = findViewById(R.id.rootFindButton);
        rootFindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRootFinding();
            }
        });

    }

    public void openIEEE() {
        Intent intent = new Intent(this, ieee_calc.class);
        startActivity(intent);
    }

    public void openRootFinding() {
        Intent intent = new Intent(this, root_finding_calc.class);
        startActivity(intent);
    }

}