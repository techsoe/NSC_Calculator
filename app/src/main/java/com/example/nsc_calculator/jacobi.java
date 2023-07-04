package com.example.nsc_calculator;

import static com.example.nsc_calculator.jacobi_Method.jacobiMethod;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;

public class jacobi extends AppCompatActivity {

    private TextInputEditText funcXinput, funcYinput, funcZinput, xoInput, yoInput, zoInput, toleranceInput;
    private Button jacobiButton;
    private TextView rootFindTxt, rootTableTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                String funcX = funcXinput.getText().toString();
                String funcY = funcYinput.getText().toString();
                String funcZ = funcZinput.getText().toString();
                double tolerance = Double.parseDouble(toleranceInput.getText().toString());
                int xoxo = Integer.parseInt(xoInput.getText().toString());
                int yoyo = Integer.parseInt(yoInput.getText().toString());
                int zozo = Integer.parseInt(zoInput.getText().toString());


                double[] solution = jacobiMethod(funcX, funcY, funcZ, tolerance, xoxo, yoyo, zozo);
                String solutionText = String.format("\nSolution: x=%.3f, y=%.3f, z=%.3f\n", solution[0], solution[1], solution[2]);
                rootTableTxt.setText(solutionText);

            }
        });

    }
}