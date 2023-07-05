package com.example.nsc_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;

public class root_finding_calc extends AppCompatActivity {

    private Button biAndFalButton, newAndSecButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.new_primary_color)));
            SpannableString title = new SpannableString("NumSum");
            title.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            getSupportActionBar().setTitle(title);
        }

        setContentView(R.layout.activity_root_finding_calc);

        biAndFalButton = findViewById(R.id.biAndFalButton);
        biAndFalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBiAndFal();
            }
        });

        newAndSecButton = findViewById(R.id.newAndSecButton);
        newAndSecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewAndSec();
            }
        });

    }

    public void openBiAndFal() {
        Intent intent = new Intent(this, bisection_and_falsi.class);
        startActivity(intent);
    }

    public void openNewAndSec() {
        Intent intent = new Intent(this, newton_and_secant.class);
        startActivity(intent);
    }

}