package com.example.lab_8_1.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_8_1.R;
import com.example.lab_8_1.models.Task;
import com.google.android.material.chip.Chip;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private final List<Task> tasks;
    private final OnTaskClickListener listener;

    public interface OnTaskClickListener {
        void onTaskClick(Task task);
    }

    public TaskAdapter(List<Task> tasks, OnTaskClickListener listener) {
        this.tasks = tasks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.taskName.setText(task.getName());
        holder.dueDateChip.setText(task.getDueDate());
        holder.priorityChip.setText(task.getPriority());

        // Set priority chip color based on priority level
        int color = getPriorityColor(task.getPriority(), holder.itemView.getContext());
        holder.priorityChip.setChipBackgroundColor(ColorStateList.valueOf(color));

        holder.itemView.setOnClickListener(v -> listener.onTaskClick(task));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    private int getPriorityColor(String priority, Context context) {
        if (priority.equals("High")) {
            return context.getColor(android.R.color.holo_red_light);
        } else if (priority.equals("Medium")) {
            return context.getColor(android.R.color.holo_orange_light);
        } else {
            return context.getColor(android.R.color.holo_green_light);
        }
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskName;
        Chip dueDateChip;
        Chip priorityChip;

        TaskViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskName);
            dueDateChip = itemView.findViewById(R.id.dueDateChip);
            priorityChip = itemView.findViewById(R.id.priorityChip);
        }
    }
}

