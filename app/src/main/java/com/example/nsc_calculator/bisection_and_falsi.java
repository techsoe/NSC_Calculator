package com.example.nsc_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.function.Function;

import com.google.android.material.textfield.TextInputEditText;

public class bisection_and_falsi extends AppCompatActivity {

    private Button bisectionButton, falsiButton, clearButton;
    private TextView rootFindTxt, rootTableTxt;
    private TextInputEditText xrInputText, xlInputText, toleranceInputText, formulaInputText;

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
                double xl = Double.parseDouble(xlInputText.getText().toString());
                double xr = Double.parseDouble(xrInputText.getText().toString());
                double tolerance = Double.parseDouble(toleranceInputText.getText().toString());
                String formula = formulaInputText.getText().toString();
                StringBuilder iterationTable = new StringBuilder();

                double root = findRoot(xl, xr, tolerance, formula, iterationTable);

                String formattedRoot = String.format("%.4f", root);

                rootFindTxt.setText("Root: " + formattedRoot);
                rootTableTxt.setText(iterationTable.toString());
            }
        });

        falsiButton = findViewById(R.id.falsiButton);
        falsiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double xl = Double.parseDouble(xlInputText.getText().toString());
                double xr = Double.parseDouble(xrInputText.getText().toString());
                double tolerance = Double.parseDouble(toleranceInputText.getText().toString());
                String formula = formulaInputText.getText().toString();
                StringBuilder iterationTable = new StringBuilder();

                double root = falsePositionMethod(formula, xl, xr, tolerance, iterationTable);
                String formattedRoot = String.format("%.4f", root);

                rootFindTxt.setText("Root: " + formattedRoot);
                rootTableTxt.setText(iterationTable.toString());
            }
        });
    }

    private double findRoot(double a, double b, double epsilon, String function, StringBuilder iterationTable) {
        int maxIterations = 100; // Maximum number of iterations
        double root = 0;

        iterationTable.append("i\t\t\tXl\t\t\t\t\t\t\tXm\t\t\t\t\t\t\tXr\t\t\t\t\t\t\tf(Xl)\t\t\t\t\t\t\tf(Xm)\t\t\t\t\t\t\tf(Xr)\n");

        for (int i = 0; i < maxIterations; i++) {
            double c = (a + b) / 2; // Midpoint

            double fA = evaluateFunction(function, a);
            double fB = evaluateFunction(function, b);
            double fC = evaluateFunction(function, c);

            String formattedA = String.format("%.4f", a);
            String formattedB = String.format("%.4f", b);
            String formattedC = String.format("%.4f", c);
            String formattedFA = String.format("%.4f", fA);
            String formattedFC = String.format("%.4f", fC);
            String formattedFB = String.format("%.4f", fB);

            iterationTable.append(i).append("\t\t\t\t")
                    .append(formattedA).append("\t\t")
                    .append(formattedC).append("\t\t")
                    .append(formattedB).append("\t\t")
                    .append(formattedFA).append("\t\t")
                    .append(formattedFC).append("\t\t")
                    .append(formattedFB).append("\n");

            if (Math.abs(fC) < epsilon) { // Check for convergence
                root = c;
                break;
            }

            if (fA * fC < 0) { // Root lies between a and c
                b = c;
            } else { // Root lies between c and b
                a = c;
            }
        }

        return root;
    }

    private double evaluateFunction(String function, double x) {
        // Evaluate the user-defined function dynamically
        // You can use libraries like Apache Commons Math or write your own expression parser.
        // For simplicity, let's support basic arithmetic operators: +, -, *, /, and ^ for exponentiation.
        double result = 0;

        try {
            function = function.replaceAll("\\s+", ""); // Remove whitespace characters

            // Replace 'x' with the given value
            function = function.replaceAll("x", String.valueOf(x));

            String finalFunction = function;
            result = new Object() {
                int pos = -1, ch;

                void nextChar() {
                    ch = (++pos < finalFunction.length()) ? finalFunction.charAt(pos) : -1;
                }

                boolean eat(int charToEat) {
                    while (ch == ' ') nextChar();
                    if (ch == charToEat) {
                        nextChar();
                        return true;
                    }
                    return false;
                }

                double parse() {
                    nextChar();
                    double x = parseExpression();
                    if (pos < finalFunction.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                    return x;
                }

                double parseExpression() {
                    double x = parseTerm();
                    for (; ; ) {
                        if (eat('+')) x += parseTerm(); // Addition
                        else if (eat('-')) x -= parseTerm(); // Subtraction
                        else return x;
                    }
                }

                double parseTerm() {
                    double x = parseFactor();
                    for (; ; ) {
                        if (eat('*')) x *= parseFactor(); // Multiplication
                        else if (eat('/')) x /= parseFactor(); // Division
                        else return x;
                    }
                }

                double parseFactor() {
                    double x;
                    int startPos = this.pos;
                    if (eat('+')) return parseFactor(); // Unary plus
                    if (eat('-')) return -parseFactor(); // Unary minus

                    if (eat('(')) { // Parentheses
                        x = parseExpression();
                        eat(')');
                    } else if ((ch >= '0' && ch <= '9') || ch == '.') { // Numbers
                        while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                        x = Double.parseDouble(finalFunction.substring(startPos, this.pos));
                    } else {
                        throw new RuntimeException("Unexpected: " + (char) ch);
                    }

                    if (eat('^')) x = Math.pow(x, parseFactor()); // Exponentiation

                    return x;
                }
            }.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private double falsePositionMethod(String function, double a, double b, double epsilon, StringBuilder iterationTable) {

        iterationTable.append("i\t\t\tXl\t\t\t\t\t\t\tXm\t\t\t\t\t\t\tXr\t\t\t\t\t\t\tf(Xl)\t\t\t\t\t\t\tf(Xm)\t\t\t\t\t\t\tf(Xr)\n");

        double fa = evaluateFunction(function, a);
        double fb = evaluateFunction(function, b);
        double c = a;
        int iteration = 1;

        while ((b - a) >= epsilon) {
            // Find the value of function at point c
            c = (a * fb - b * fa) / (fb - fa);

            // Evaluate function at xl, xm, and xr
            double fc = evaluateFunction(function, c);
            double fA = evaluateFunction(function, a);
            double fB = evaluateFunction(function, b);

            // Append iteration values to the iterationBuilder
            iterationTable.append(String.format("%d\t\t %.4f\t\t %.4f\t\t %.4f\t\t %.4f\t\t %.4f\t\t %.4f\n", iteration, a, c, b, fA, fc, fB));

            // Check if the root is found or switch the values
            if (fc == 0.0)
                break;
            else if (fc * fa < 0)
                b = c;
            else
                a = c;

            iteration++;
        }
        return c;
    }

}

