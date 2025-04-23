package com.example.intent_ordering;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText editTextName;
    RadioGroup radioGroupGender;
    CheckBox checkBoxYes, checkBoxNo;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        checkBoxYes = findViewById(R.id.checkBoxYes);
        checkBoxNo = findViewById(R.id.checkBoxNo);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
                RadioButton selectedGender = findViewById(selectedGenderId);
                String gender = selectedGender != null ? selectedGender.getText().toString() : "Not selected";

                String choice = "";
                if (checkBoxYes.isChecked()) choice = "Yes";
                if (checkBoxNo.isChecked()) choice = "No";
                if (checkBoxYes.isChecked() && checkBoxNo.isChecked()) choice = "Both checked (Invalid)";

                Intent intent = new Intent(MainActivity.this, second.class);
                intent.putExtra("name", name);
                intent.putExtra("gender", gender);
                intent.putExtra("choice", choice);
                startActivity(intent);
            }
        });
    }
}
