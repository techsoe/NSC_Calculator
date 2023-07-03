package com.example.nsc_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.function.Function;

import com.google.android.material.textfield.TextInputEditText;

public class bisection_and_falsi extends AppCompatActivity {

    private Button bisectionButton, falsiButton, clearButton;
    private TextView rootFindTxt, rootTableTxt;
    private TextInputEditText xrInputText, xlInputText, toleranceInputText, formulaInputText;
    private Function<Double, Double> function;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bisection_and_falsi);

        rootFindTxt = findViewById(R.id.rootFindTxt);
        rootTableTxt = findViewById(R.id.rootTableTxt);

        formulaInputText = findViewById(R.id.formulaInputText);
        toleranceInputText = findViewById(R.id.toleranceInputText);
        xlInputText = findViewById(R.id.xlInputText);
        xrInputText = findViewById(R.id.xrInputText);

        bisectionButton = findViewById(R.id.bisectionButton);
        bisectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    public class BisectionMethod {
        private static final double EPSILON = 1e-6; // Error tolerance

        public static double findRoot(Function<Double, Double> function, double a, double b) {
            if (function.apply(a) * function.apply(b) >= 0) {
                throw new IllegalArgumentException("The function values at the endpoints must have opposite signs.");
            }

            double c = a;
            while ((b - a) >= EPSILON) {
                c = (a + b) / 2;

                if (function.apply(c) == 0.0) {
                    break;
                } else if (function.apply(c) * function.apply(a) < 0) {
                    b = c;
                } else {
                    a = c;
                }
            }

            return c;
        }
    }
}