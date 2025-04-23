package com.example.calc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvDisplay;
    private String input = "";
    private double firstNumber = 0;
    private String operator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);

        // Number Buttons
        setNumberButtonListener(R.id.btn0, "0");
        setNumberButtonListener(R.id.btn1, "1");
        setNumberButtonListener(R.id.btn2, "2");
        setNumberButtonListener(R.id.btn3, "3");
        setNumberButtonListener(R.id.btn4, "4");
        setNumberButtonListener(R.id.btn5, "5");
        setNumberButtonListener(R.id.btn6, "6");
        setNumberButtonListener(R.id.btn7, "7");
        setNumberButtonListener(R.id.btn8, "8");
        setNumberButtonListener(R.id.btn9, "9");
        setNumberButtonListener(R.id.btnDot, ".");


        // Operator Buttons
        setOperatorButtonListener(R.id.btnPlus, "+");
        setOperatorButtonListener(R.id.btnMinus, "-");
        setOperatorButtonListener(R.id.btnMultiply, "×");
        setOperatorButtonListener(R.id.btnDivide, "÷");

        // Equals Button
        findViewById(R.id.btnEquals).setOnClickListener(view -> {
            if (!input.isEmpty() && !operator.isEmpty()) {
                double secondNumber = Double.parseDouble(input);
                double result = calculate(firstNumber, secondNumber, operator);
                tvDisplay.setText(String.valueOf(result));
                input = "";
                operator = "";
            }
        });

        // Clear Button
        findViewById(R.id.btnClear).setOnClickListener(view -> {
            input = "";
            firstNumber = 0;
            operator = "";
            tvDisplay.setText("0");
        });
    }

    // Method to handle number button clicks
    private void setNumberButtonListener(int buttonId, String value) {
        findViewById(buttonId).setOnClickListener(view -> {
            input += value;
            tvDisplay.setText(input);
        });
    }

    // Method to handle operator button clicks
    private void setOperatorButtonListener(int buttonId, String op) {
        findViewById(buttonId).setOnClickListener(view -> {
            if (!input.isEmpty()) {
                firstNumber = Double.parseDouble(input);
                operator = op;
                input = "";
            }
        });
    }

    // Calculation Method
    private double calculate(double num1, double num2, String op) {
        switch (op) {
            case "+": return num1 + num2;
            case "-": return num1 - num2;
            case "×": return num1 * num2;
            case "÷": return num2 != 0 ? num1 / num2 : 0;
            default: return 0;
        }
    }
}
