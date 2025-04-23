package com.example.investify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private Context context;
    private List<Expense> expenseList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public ExpenseAdapter(Context context, List<Expense> expenseList, OnItemClickListener listener) {
        this.context = context;
        this.expenseList = expenseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.expense_item, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        holder.textViewExpenseCategory.setText(expense.getCategoryName());
        holder.textViewExpenseAmount.setText(String.format("₹%.2f", expense.getAmount()));
        holder.textViewExpenseDate.setText(expense.getDate());
        holder.textViewExpenseDescription.setText(expense.getDescription());
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView textViewExpenseCategory, textViewExpenseAmount, textViewExpenseDate, textViewExpenseDescription;
        Button buttonDeleteExpense;

        ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewExpenseCategory = itemView.findViewById(R.id.textViewExpenseCategory);
            textViewExpenseAmount = itemView.findViewById(R.id.textViewExpenseAmount);
            textViewExpenseDate = itemView.findViewById(R.id.textViewExpenseDate);
            textViewExpenseDescription = itemView.findViewById(R.id.textViewExpenseDescription);
            buttonDeleteExpense = itemView.findViewById(R.id.buttonDeleteExpense);

            buttonDeleteExpense.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
}
