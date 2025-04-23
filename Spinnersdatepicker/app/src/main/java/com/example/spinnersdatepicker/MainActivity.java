package com.example.spinnersdatepicker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Spinner sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        sp=findViewById(R.id.spinner);
        DatePicker dp = findViewById(R.id.datepicker);
        TimePicker tp = findViewById(R.id.timepicker);
        Button btn = findViewById(R.id.button);
        RadioButton rb1 = findViewById(R.id.rb1);
        RadioButton rb2 = findViewById(R.id.rb2);
        CheckBox cb1 = findViewById(R.id.checkBox);
        CheckBox cb2 = findViewById(R.id.checkBox2);
        EditText et=findViewById(R.id.edittext);
        Button btn2=findViewById(R.id.button2);
        List<String> category = new ArrayList<String>();
        category.add("1");
        category.add("2");
        category.add("3");
        category.add("4");
        category.add("5");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String datepicker = dp.getDayOfMonth() + "/" + (dp.getMonth() + 1) + "/" + dp.getYear();
                String time = tp.getHour() + ":" + tp.getMinute();
                String spinner = sp.getSelectedItem().toString();
                String rbb = "";
                if (rb1.isChecked()) {
                    rbb = rb1.getText().toString();
                } else if (rb2.isChecked()) {
                    rbb = rb2.getText().toString();
                }
                String cbb = "";
                if (cb1.isChecked()) {
                    cbb = cb1.getText().toString();
                } else if (cb2.isChecked()) {
                    cbb = cb2.getText().toString();
                }
                String ett = et.getText().toString();
                if (ett.isEmpty()) {
                    Toast.makeText(MainActivity.this, "entertext", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("date", datepicker);
                intent.putExtra("time",time);
                intent.putExtra("spinner",spinner);
                intent.putExtra("rbb",rbb);
                intent.putExtra("cbb",cbb);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear EditText
                et.setText("");

                // Uncheck CheckBoxes
                cb1.setChecked(false);
                cb2.setChecked(false);

                // Clear RadioButtons
                rb1.setChecked(false);
                rb2.setChecked(false);

                // Reset Spinner
                sp.setSelection(0);

                // Reset DatePicker to today
                Calendar calendar = Calendar.getInstance();
                dp.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                // Reset TimePicker to current time
                if (Build.VERSION.SDK_INT >= 23) {
                    tp.setHour(calendar.get(Calendar.HOUR_OF_DAY));
                    tp.setMinute(calendar.get(Calendar.MINUTE));
                } else {
                    tp.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
                    tp.setCurrentMinute(calendar.get(Calendar.MINUTE));
                }

                Toast.makeText(MainActivity.this, "Reset done", Toast.LENGTH_SHORT).show();
            }
        });

    }
    }
