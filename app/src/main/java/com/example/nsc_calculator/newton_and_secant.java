package com.example.nsc_calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.operator.Operator;

import java.util.function.Function;


public class newton_and_secant extends AppCompatActivity {

    private Button newtonButton, secantButton;
    private TextInputEditText formulaInputText, xaInputText, xbInputText, toleranceInputText;
    private TextView rootFindTxt, rootTableTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newton_and_secant);

        formulaInputText = findViewById(R.id.formulaInputText);
        xaInputText = findViewById(R.id.xaInputText);
        xbInputText = findViewById(R.id.xbInputText);
        toleranceInputText = findViewById(R.id.toleranceInputText);
        rootFindTxt = findViewById(R.id.rootFindTxt);
        rootTableTxt = findViewById(R.id.rootTableTxt);

        newtonButton = findViewById(R.id.newtonButton);
        newtonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double xa = Double.parseDouble(xaInputText.getText().toString());
                double tolerance = Double.parseDouble(toleranceInputText.getText().toString());
                String formula = formulaInputText.getText().toString();

                double result = newtonRaphson(formula, xa, tolerance);

                rootFindTxt.setText("Root: " + String.format("%.4f", result));
            }
        });

        secantButton = findViewById(R.id.secantButton);
        secantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String formula = formulaInputText.getText().toString();
                double xa = Double.parseDouble(xaInputText.getText().toString());
                double xb = Double.parseDouble(xbInputText.getText().toString());
                double tolerance = Double.parseDouble(toleranceInputText.getText().toString());

                double root = secant(formula, xa, xb, tolerance);
                String iterationTable = generateIterationTable(formula, xa, xb, tolerance);

                rootFindTxt.setText("Root: " + String.format("%.4f", root));
                rootTableTxt.setText(iterationTable);
            }
        });

    }

    private String generateIterationTable(String formula, double xa, double xb, double tolerance) {
        StringBuilder tableBuilder = new StringBuilder();

        tableBuilder.append("i\t  xa\t\t\t\t  xb\t\t\t\t  xn\t\t\t\t  ya\t\t\t\t  yb\n");

        double x0 = xa;
        double x1 = xb;
        double fx0 = evaluateFunction(formula, x0);
        double fx1 = evaluateFunction(formula, x1);

        int iteration = 1;
        int maxIterations = 100;

        while (Math.abs(fx1) > tolerance && iteration <= maxIterations) {
            double xn = x1 - ((fx1 * (x1 - x0)) / (fx1 - fx0));
            double fXn = evaluateFunction(formula, xn);

            tableBuilder.append(String.format("%d\t\t%.4f\t\t%.4f\t\t%.4f\t\t%.4f\t\t%.4f\n", iteration, x0, x1, xn, fx0, fx1));

            x0 = x1;
            x1 = xn;
            fx0 = fx1;
            fx1 = fXn;
            iteration++;
        }

        return tableBuilder.toString();
    }

    public double secant(String function, double x0, double x1, double epsilon) {
        int iteration = 0;
        int maxIterations = 100;
        double fx0 = evaluateFunction(function, x0);
        double fx1 = evaluateFunction(function, x1);

        while (Math.abs(fx1) > epsilon && iteration < maxIterations) {
            double x2 = x1 - ((fx1 * (x1 - x0)) / (fx1 - fx0));
            x0 = x1;
            fx0 = fx1;
            x1 = x2;
            fx1 = evaluateFunction(function, x1);
            iteration++;
        }

        if (Math.abs(fx1) <= epsilon) {
            System.out.println("Root found at x = " + x1);
            return x1;
        } else {
            System.out.println("Maximum iterations reached. Root approximation: x = " + x1);
            return x1;
        }
    }

    private double newtonRaphson(String functionString, double initialGuess, double epsilon) {
        double x0 = initialGuess;
        double x1;
        int iteration = 0;

        StringBuilder tableBuilder = new StringBuilder();
        tableBuilder.append("i\t\t\tXn\t\t\tXo\t\t\tYn\t\t\tYo\n");

        do {
            double fx = evaluateFunction(functionString, x0); // Calculate the value of the function at x0
            double fpx = evalDerivative(functionString, x0); // Calculate the derivative of the function at x0
            x1 = x0 - (fx / fpx); // Calculate the next approximation

            double yn = evaluateFunction(functionString, x1); // Calculate the value of the function at x1

            tableBuilder.append(String.format("%d\t\t%.4f\t\t\t%.4f\t\t\t%.4f\t\t\t%.4f\n", iteration, x1, x0, yn, fx));

            if (Math.abs(x1 - x0) < epsilon) {
                rootTableTxt.setText(tableBuilder.toString());
                rootFindTxt.setText("Root: " + x1 + ", Iterations: " + iteration);
                return x1; // Converged, return the root
            }

            x0 = x1;
            iteration++;

            // Add a maximum iteration limit to avoid infinite looping
            if (iteration >= 100) {
                rootTableTxt.setText(tableBuilder.toString());
                rootFindTxt.setText("Unable to converge");
                return Double.NaN; // Return NaN to indicate failure to converge
            }
        } while (true); // Continue the loop until convergence or maximum iterations are reached
    }

    private double evaluateFunction(String function, double x) {
        double result = 0;

        try {
            // Create a custom operator '^' for exponentiation
            Operator exponentiation = new Operator("^", 2, true, Operator.PRECEDENCE_POWER) {
                @Override
                public double apply(double... args) {
                    return Math.pow(args[0], args[1]);
                }
            };

            // Build the expression using the user input function
            Expression expression = new ExpressionBuilder(function)
                    .operator(exponentiation)
                    .variable("x")
                    .build();

            // Set the value of 'x' in the expression
            expression.setVariable("x", x);

            // Evaluate the expression
            result = expression.evaluate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private double evalDerivative(String function, double x) {
        double derivative = 0;

        try {
            // Create a custom operator '^' for exponentiation
            Operator exponentiation = new Operator("^", 2, true, Operator.PRECEDENCE_POWER) {
                @Override
                public double apply(double... args) {
                    return Math.pow(args[0], args[1]);
                }
            };

            // Build the expression using the user input function
            Expression expression = new ExpressionBuilder(function)
                    .operator(exponentiation)
                    .variable("x")
                    .build();

            // Set the value of 'x' in the expression
            expression.setVariable("x", x);

            // Calculate the derivative using numerical differentiation
            double h = 1e-8; // Step size for differentiation
            double fx = expression.evaluate(); // Evaluate the expression at x
            double fxPlusH = expression.setVariable("x", x + h).evaluate(); // Evaluate the expression at x + h
            double fxMinusH = expression.setVariable("x", x - h).evaluate(); // Evaluate the expression at x - h

            derivative = (fxPlusH - fxMinusH) / (2 * h);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return derivative;
    }
}