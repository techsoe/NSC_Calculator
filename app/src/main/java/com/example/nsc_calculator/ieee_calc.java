package com.example.nsc_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ieee_calc extends AppCompatActivity {

    private Button bit32Button, bit64Button, clearButton;
    private TextView resultText, resTxt;
    private TextInputEditText bitValueInputInputText;
    private float floatValue = 0.0f;
    private String binaryString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.new_primary_color)));
            SpannableString title = new SpannableString("NumSum");
            title.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            getSupportActionBar().setTitle(title);
        }


        setContentView(R.layout.activity_ieee_calc);

        resultText = findViewById(R.id.resultText);

        resTxt = findViewById(R.id.resTxt);
        resTxt.setVisibility(View.GONE);

        bitValueInputInputText = findViewById(R.id.bitValueInputInputText);

        bit32Button = findViewById(R.id.bit32Button);
        bit32Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = bitValueInputInputText.getText().toString().trim();
                resTxt.setVisibility(View.VISIBLE);
                clearButton.setVisibility(View.VISIBLE);
                try {
                    floatValue = Float.parseFloat(input);
                    int intBits = Float.floatToIntBits(floatValue);
                    binaryString = Integer.toBinaryString(intBits);
                    binaryString = String.format("%32s", binaryString).replace(' ', '0');
                    resTxt.setText("The 32-bit of " + Float.toString(floatValue) + " is");
                } catch (NumberFormatException e) {
                    floatValue = 0.0f;  // Set a default value or handle the error in an appropriate way
                }
                resultText.setText(binaryString);
            }
        });

        bit64Button = findViewById(R.id.bit64Button);
        bit64Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = bitValueInputInputText.getText().toString().trim();
                resTxt.setVisibility(View.VISIBLE);
                clearButton.setVisibility(View.VISIBLE);
                try {
                    floatValue = Float.parseFloat(input);

                    long longBits = Double.doubleToLongBits(floatValue);
                    binaryString = Long.toBinaryString(longBits);

                    binaryString = String.format("%64s", binaryString).replace(' ', '0');

                    resTxt.setText("The 64-bit of " + Float.toString(floatValue) + " is");
                } catch (NumberFormatException e) {
                    floatValue = 0.0f;  // Set a default value or handle the error in an appropriate way
                }
                resultText.setText(binaryString);
            }
        });

        clearButton = findViewById(R.id.clearButton);
        clearButton.setVisibility(View.GONE);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitValueInputInputText.setText("");
                resTxt.setText("");
                resultText.setText("");
            }
        });
    }
}