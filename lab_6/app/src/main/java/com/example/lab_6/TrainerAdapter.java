package com.example.lab_6;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lab_6.Trainer;

import java.util.ArrayList;

public class TrainerAdapter extends ArrayAdapter<Trainer> {

    // invoke the suitable constructor of the ArrayAdapter class
    public TrainerAdapter(@NonNull Context context, ArrayList<Trainer> arrayList) {

        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.support_simple_list_item, parent, false);
        }

        Trainer currentNumberPosition = getItem(position);

        ImageView numbersImage = currentItemView.findViewById(R.id.trainer_image);
        assert currentNumberPosition != null;
        numbersImage.setImageResource(currentNumberPosition.getTrainer_image_id());

        TextView textView1 = currentItemView.findViewById(R.id.trainer_name);
        textView1.setText(currentNumberPosition.getTrainer_name());

        return currentItemView;
    }
}
