package com.example.investify;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.investify.model.Stock;

import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {

    private List<Stock> stocks;

    public StockAdapter(List<Stock> stocks) {
        this.stocks = stocks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stock, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Stock stock = stocks.get(position);
        holder.textViewSymbol.setText(stock.getSymbol());
        holder.textViewName.setText(stock.getName());
        holder.textViewPrice.setText("Price: " + stock.getPrice());
        holder.textViewChange.setText("Change: " + stock.getChange());
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSymbol, textViewName, textViewPrice, textViewChange;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSymbol = itemView.findViewById(R.id.textViewStockSymbol);
            textViewName = itemView.findViewById(R.id.textViewStockName);
            textViewPrice = itemView.findViewById(R.id.textViewStockPrice);
            textViewChange = itemView.findViewById(R.id.textViewStockChange);
        }
    }
}
