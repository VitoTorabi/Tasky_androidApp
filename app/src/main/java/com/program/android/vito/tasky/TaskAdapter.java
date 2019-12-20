package com.program.android.vito.tasky;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
            viewHolder.time.setText(myTasks.get(i).timeH+":"+ myTasks.get(i).timeM);

            viewHolder.layout.setOnTouchListener(new OnSwipeTouchListener(mainActivity){
                @Override
                public void onSwipeLeft() {
                    Log.d(""+myTasks.get(i).id,"left");
                    mainActivity.db.deleteTask(myTasks.get(i).id);
                    mainActivity.refreshFrag();
                }
                @Override
                public void onSwipeRight() {
                    Log.d(""+myTasks.get(i).id,"right");
                    mainActivity.db.setDone(myTasks.get(i).id,1);
                    mainActivity.refreshFrag();
                }
            });

            viewHolder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(""+myTasks.get(i).id,"onclick");
                    Onclick onclick = mainActivity.taskOnClick(myTasks.get(i));
                    onclick.onClickL();
                    mainActivity.refreshFrag();
                }
            });

            if(myTasks.get(i).alarmM != "" && myTasks.get(i).alarmH != ""&&
                    myTasks.get(i).alarmM != null && myTasks.get(i).alarmH != null){
                viewHolder.alarmTime.setText(myTasks.get(i).alarmH+":"+myTasks.get(i).alarmM);
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
        ImageButton forward;
        ImageButton edit;

        MyTaskViewHolder(View itemView) {
            super(itemView);
            edit = itemView.findViewById(R.id.task_view);
            forward = itemView.findViewById(R.id.forward);
            layout = itemView.findViewById(R.id.task_layout);
            text = itemView.findViewById(R.id.task);
            time = itemView.findViewById(R.id.task_deadLine);
            alarmTime = itemView.findViewById(R.id.task_alarm);
            title = itemView.findViewById(R.id.task_title);
            addAlarm = itemView.findViewById(R.id.add_alarm);
        }
    }

}

