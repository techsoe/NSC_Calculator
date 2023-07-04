package com.example.nsc_calculator;

public class jacobi_Method {
    public static double[] jacobiMethod(String set1, String set2, String set3, double tol, int a0, int b0, int c0) {
        // Defining equations to be solved
        // in diagonally dominant form
        MyFunction f1 = (x, y, z) -> evaluateExpression(set1, x, y, z);
        MyFunction f2 = (x, y, z) -> evaluateExpression(set2, x, y, z);
        MyFunction f3 = (x, y, z) -> evaluateExpression(set3, x, y, z);

        // Initial setup
        double x0 = a0;
        double y0 = b0;
        double z0 = c0;
        int count = 0;

        // Implementation of Jacobi Iteration
        System.out.println("\nCount\tx\ty\tz\n");

        boolean condition = true;

        while (condition) {
            double x1 = f1.evaluate(x0, y0, z0);
            double y1 = f2.evaluate(x0, y0, z0);
            double z1 = f3.evaluate(x0, y0, z0);
            System.out.printf("%d\t%.4f\t%.4f\t%.4f\n", count, x0, y0, z0);
            double e1 = Math.abs(x0 - x1);
            double e2 = Math.abs(y0 - y1);
            double e3 = Math.abs(z0 - z1);

            count++;
            x0 = x1;
            y0 = y1;
            z0 = z1;

            condition = e1 > tol && e2 > tol && e3 > tol;
        }

        System.out.printf("\nSolution: x=%.3f, y=%.3f, z=%.3f\n", x0, y0, z0);

        // Create and return the solution array
        double[] solution = new double[] { x0, y0, z0 };
        return solution;
    }

    public static double evaluateExpression(String expression, double x, double y, double z) {
        expression = expression.replace("x", Double.toString(x))
                .replace("y", Double.toString(y))
                .replace("z", Double.toString(z));
        return eval(expression); // Perform expression evaluation using a library or custom implementation
    }

    // Interface for function evaluation
    interface MyFunction {
        double evaluate(double x, double y, double z);
    }

    // Custom expression evaluation method (replace with an appropriate implementation)
    public static double eval(String expression) {
        return 0.0; // Replace with your own expression evaluation logic
    }
}

