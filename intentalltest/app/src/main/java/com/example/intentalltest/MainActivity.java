package com.example.intentalltest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText ed1,ed2;
    RadioGroup rg;
    RadioButton rb1,rb2,rb3;
    Button b1;
    CheckBox c1,c2;
    Spinner sp;
    DatePicker dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ed1=findViewById(R.id.et);
        ed2=findViewById(R.id.et2);
        rg=findViewById(R.id.radioGroup);
        rb1=findViewById(R.id.radioButton1);
        rb2=findViewById(R.id.radioButton2);
        rb3=findViewById(R.id.radioButton3);
        b1=findViewById(R.id.button);
        c1=findViewById(R.id.checkBox1);
        c2=findViewById(R.id.checkBox2);
        sp=findViewById(R.id.spinner);
        dp=findViewById(R.id.datepicker);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Gpay");
        arrayList.add("cash");
        arrayList.add("card");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String name=ed1.getText().toString();
               String num=ed2.getText().toString();
               if(num.length()!=10)
               {
                   Toast.makeText(MainActivity.this, "invalid number", Toast.LENGTH_SHORT).show();
                   return;
               }
              String foodpref="";
              if(c1.isChecked()){
                  foodpref+="veg";
              }
              if(c2.isChecked()){
                  foodpref+="non veg";
              }
             // String mealType="";
                int selectedRadioId = rg.getCheckedRadioButtonId();

                String mealType = "";
                if (rb1.isChecked()) {
                    mealType = "starters";
                } else if (rb2.isChecked()) {
                    mealType = "maincourse";
                } else if (rb3.isChecked()) {
                    mealType = "dessert";
                }


                String payment=sp.getSelectedItem().toString();
                int day=dp.getDayOfMonth();
                int month=dp.getMonth()+1;
                int year=dp.getYear();



                String date =day+"/"+month+"/"+year;

                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("name",name);
                intent.putExtra("num",num);
                intent.putExtra("foodpref",foodpref);
                intent.putExtra("mealType",mealType);
                intent.putExtra("payment",payment);
                intent.putExtra("date",date);
                startActivity(intent);


            }



        });
    }
}