package com.example.nsc_calculator;

import android.widget.TextView;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import org.w3c.dom.Text;

public class jacobi_method {
    public static void jacobiMethod(String set1, String set2, String set3, double tol, double a0, double b0, double c0, TextView rootFind, TextView rootTable) {
        // Defining equations to be solved
        // in diagonally dominant form
        Equation f1 = (x, y, z) -> evaluateExpression(set1, x, y, z);
        Equation f2 = (x, y, z) -> evaluateExpression(set2, x, y, z);
        Equation f3 = (x, y, z) -> evaluateExpression(set3, x, y, z);

        // Initial setup
        double x0 = a0;
        double y0 = b0;
        double z0 = c0;
        int count = 0;

        // Implementation of Jacobi Iteration
        StringBuilder output = new StringBuilder("\nCount\tx\ty\tz\n");

        boolean condition = true;

        while (condition) {
            double x1 = f1.calculate(x0, y0, z0);
            double y1 = f2.calculate(x0, y0, z0);
            double z1 = f3.calculate(x0, y0, z0);
            output.append(String.format("%d\t%.4f\t%.4f\t%.4f\n", count, x0, y0, z0));
            double e1 = Math.abs(x0 - x1);
            double e2 = Math.abs(y0 - y1);
            double e3 = Math.abs(z0 - z1);

            count++;
            x0 = x1;
            y0 = y1;
            z0 = z1;

            condition = e1 > tol && e2 > tol && e3 > tol;
        }

        String solution = String.format("Solution: x=%.3f, y=%.3f and z=%.3f", x0, y0, z0);
        rootFind.setText(solution);
        rootTable.setText(output.toString());
    }

    private static double evaluateExpression(String expression, double x, double y, double z) {
        // Evaluate the given expression
        // Replace variable names with their corresponding values
        expression = expression.replace("x", Double.toString(x));
        expression = expression.replace("y", Double.toString(y));
        expression = expression.replace("z", Double.toString(z));

        // Evaluate the expression as a double
        return evaluate(expression);
    }


    private static double evaluate(String expression) {
        try {
            Expression e = new ExpressionBuilder(expression).build();
            return e.evaluate();
        } catch (Exception e) {
            e.printStackTrace();
            return Double.NaN;
        }
    }

    private interface Equation {
        double calculate(double x, double y, double z);
    }

}
