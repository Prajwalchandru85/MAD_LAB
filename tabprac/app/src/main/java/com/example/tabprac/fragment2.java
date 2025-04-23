package com.example.tabprac;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class fragment2 extends Fragment {

    Button b1,b2;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_fragment2, container, false);
        b1=v.findViewById(R.id.b1);
        b2=v.findViewById(R.id.b2);
        SeekBar sk=v.findViewById(R.id.sk);
        CheckBox ck=v.findViewById(R.id.ck);
        RadioButton rb = v.findViewById(R.id.rb);
        ToggleButton toggleButton = v.findViewById(R.id.toggleButton);
        Switch switch1 = v.findViewById(R.id.switch1);
       b1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int prog=sk.getProgress();
               Toast.makeText(getContext(), "toast"+prog, Toast.LENGTH_SHORT).show();
           }
       });
       b2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             int prog = sk.getProgress();
             boolean check=ck.isChecked();
             boolean radio=rb.isChecked();
             boolean toggle=toggleButton.isChecked();
             boolean sw=switch1.isChecked();


               Intent intent = new Intent(getActivity(), MainActivity2.class);
               intent.putExtra("prog",prog);
               intent.putExtra("check",check);
               intent.putExtra("radio",radio);
               intent.putExtra("toggle",toggle);
               intent.putExtra("switch",sw);
               startActivity(intent);
           }
       });


        return v;


    }
}