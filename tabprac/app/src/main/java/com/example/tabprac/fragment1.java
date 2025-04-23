package com.example.tabprac;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class fragment1 extends Fragment {

    EditText et, et1;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment1, container, false);

        // Initialize views properly
        et = v.findViewById(R.id.et);
        et1 = v.findViewById(R.id.et1);
        button = v.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namee = et.getText().toString(); // Get input inside onClick
                String numm = et1.getText().toString();

                Intent intent = new Intent(getActivity(), MainActivity2.class);
                intent.putExtra("name", namee);
                intent.putExtra("num", numm);
                startActivity(intent);
            }
        });

        return v;
    }
}
