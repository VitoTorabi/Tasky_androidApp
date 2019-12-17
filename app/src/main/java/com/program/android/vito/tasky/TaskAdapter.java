package com.program.android.vito.tasky;

import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

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
            viewHolder.time.setText(myTasks.get(i).deadline);

            viewHolder.layout.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View v, DragEvent event) {
                    return false;
                }
            });

            if(myTasks.get(i).alarmTime != null){
                viewHolder.alarmTime.setText(myTasks.get(i).alarmTime);
                viewHolder.alarmTime.setVisibility(View.VISIBLE);
                viewHolder.addAlarm.setVisibility(View.GONE);
            }else{
                viewHolder.alarmTime.setVisibility(View.GONE);
                viewHolder.addAlarm.setVisibility(View.VISIBLE);
            }


    }

    @Override
    public int getItemCount() {
        return myTasks.size();
    }

    public static class MyTaskViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        TextView title;
        TextView time;
        TextView alarmTime;
        ImageView addAlarm;
        LinearLayout layout;

        MyTaskViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.task_layout);
            text = itemView.findViewById(R.id.task);
            time = itemView.findViewById(R.id.task_deadLine);
            alarmTime = itemView.findViewById(R.id.task_alarm);
            title = itemView.findViewById(R.id.task_title);
            addAlarm = itemView.findViewById(R.id.add_alarm);
        }
    }

}

