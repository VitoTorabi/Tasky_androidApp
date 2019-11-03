package com.program.android.vito.tasky;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mohd on 18/10/2017.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyTaskViewHolder> {

    List<MyTask> myTasks;
    MainActivity mainActivity;

    public TaskAdapter(List<MyTask> myTasks, MainActivity mainActivity) {
        this.myTasks = myTasks;
        this.mainActivity = mainActivity;
    }

    @Override
    public TaskAdapter.MyTaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_task, viewGroup, false);
        MyTaskViewHolder pvh = new MyTaskViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final MyTaskViewHolder viewHolder, final int i) {

            viewHolder.text.setText(myTasks.get(i).text);
            viewHolder.title.setText(myTasks.get(i).title);


    }

    @Override
    public int getItemCount() {
        return myTasks.size();
    }

    public static class MyTaskViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        TextView title;

        MyTaskViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.task);
            title = itemView.findViewById(R.id.task_title);


        }
    }

}

