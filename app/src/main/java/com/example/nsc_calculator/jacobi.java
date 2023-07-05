package com.example.nsc_calculator;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;

public class jacobi extends AppCompatActivity {

    private TextInputEditText funcXinput, funcYinput, funcZinput, xoInput, yoInput, zoInput, toleranceInput;
    private Button jacobiButton, clearButton;
    private TextView rootFindTxt, rootTableTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.new_primary_color)));
            SpannableString title = new SpannableString("NumSum");
            title.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            getSupportActionBar().setTitle(title);
        }


        setContentView(R.layout.activity_jacobi);

        funcXinput = findViewById(R.id.funcXinput);
        funcYinput = findViewById(R.id.funcYinput);
        funcZinput = findViewById(R.id.funcZinput);
        xoInput = findViewById(R.id.xoInput);
        yoInput = findViewById(R.id.yoInput);
        zoInput = findViewById(R.id.zoInput);
        toleranceInput = findViewById(R.id.toleranceInput);

        rootFindTxt = findViewById(R.id.rootFindTxt);
        rootTableTxt = findViewById(R.id.rootTableTxt);


        jacobiButton = findViewById(R.id.jacobiButton);
        jacobiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearButton.setVisibility(View.VISIBLE);
                String funcX = funcXinput.getText().toString();
                String funcY = funcYinput.getText().toString();
                String funcZ = funcZinput.getText().toString();
                double tolerance = Double.parseDouble(toleranceInput.getText().toString());
                double xoxo = Integer.parseInt(xoInput.getText().toString());
                double yoyo = Integer.parseInt(yoInput.getText().toString());
                double zozo = Integer.parseInt(zoInput.getText().toString());

                jacobi_method.jacobiMethod(funcX, funcY, funcZ, tolerance, xoxo, yoyo, zozo, rootFindTxt, rootTableTxt);

            }
        });

        clearButton = findViewById(R.id.clearButton);
        clearButton.setVisibility(View.GONE);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcXinput.setText("");
                funcYinput.setText("");
                funcZinput.setText("");
                xoInput.setText("");
                yoInput.setText("");
                zoInput.setText("");
                toleranceInput.setText("");
                rootFindTxt.setText("");
                rootTableTxt.setText("");
            }
        });

    }

}