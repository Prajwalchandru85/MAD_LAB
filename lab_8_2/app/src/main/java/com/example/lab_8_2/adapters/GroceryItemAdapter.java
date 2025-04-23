package com.example.lab_8_2.adapters;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lab_8_2.R;
import com.example.lab_8_2.models.GroceryItem;

import java.util.List;
import java.util.Locale;

public class GroceryItemAdapter extends ArrayAdapter<Pair<GroceryItem, Boolean>> {
    public interface OnItemCheckedListener {
        void onItemChecked(int position, boolean isChecked);
    }

    private final List<Pair<GroceryItem, Boolean>> items;
    private OnItemCheckedListener onItemCheckedListener;

    public GroceryItemAdapter(@NonNull Context context, @NonNull List<Pair<GroceryItem, Boolean>> items) {
        super(context, 0, items);
        this.items = items;
    }

    public void setOnItemCheckedListener(OnItemCheckedListener onItemCheckedListener) {
        this.onItemCheckedListener = onItemCheckedListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_grocery, parent, false);
        }

        GroceryItem item = items.get(position).first;
        Boolean isSelected = items.get(position).second;

        TextView itemName = convertView.findViewById(R.id.itemName);
        TextView itemCost = convertView.findViewById(R.id.itemCost);
        CheckBox itemCheckBox = convertView.findViewById(R.id.itemCheckBox);

        itemName.setText(item.getName());
        itemCost.setText(String.format(Locale.getDefault(), "$%.2f", item.getCost()));
        itemCheckBox.setChecked(isSelected);

        itemCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (onItemCheckedListener != null) {
                onItemCheckedListener.onItemChecked(position, isChecked);
            }
        });

        return convertView;
    }
}