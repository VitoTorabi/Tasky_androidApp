package com.program.android.vito.tasky;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.AlarmClock;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyTaskViewHolder> {

    List<MyTask> myTasks;
    MainActivity mainActivity;
    TaskAdapter mAdapter = this;

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
            if(myTasks.get(i).timeH.length()<2)
                myTasks.get(i).timeH = "0" + myTasks.get(i).timeH;
            if(myTasks.get(i).timeM.length()<2)
                myTasks.get(i).timeM = "0" + myTasks.get(i).timeM;
            viewHolder.time.setText(myTasks.get(i).timeH+":"+ myTasks.get(i).timeM);

            viewHolder.layout.setOnTouchListener(new OnSwipeTouchListener(mainActivity){
                @Override
                public void onSwipeLeft() {
                    Log.d(""+myTasks.get(i).id,"left");
                    AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
                    builder.setTitle("Delete Confirm");
                    builder.setMessage("You are about to delete this task. Are you sure?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(mainActivity, "task deleted", Toast.LENGTH_SHORT).show();
                            mainActivity.db.deleteTask(myTasks.get(i).id);
                            mainActivity.refreshFrag();
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    builder.show();
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
            viewHolder.addAlarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                    intent.putExtra(AlarmClock.EXTRA_MESSAGE, myTasks.get(i).title);
                    intent.putExtra(AlarmClock.EXTRA_HOUR, Integer.parseInt(myTasks.get(i).timeH));
                    intent.putExtra(AlarmClock.EXTRA_MINUTES, Integer.parseInt(myTasks.get(i).timeM));
                    intent.putExtra(AlarmClock.EXTRA_SKIP_UI,true);
                    mainActivity.startActivity(intent);
                    myTasks.get(i).setAlarm();
                    mainActivity.db.updateTask(myTasks.get(i),myTasks.get(i).id);
                    mAdapter.notifyDataSetChanged();
                }
            });
            viewHolder.forward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer k = Integer.parseInt(myTasks.get(i).day)+1;
                    myTasks.get(i).day = String.valueOf(k);
                    mainActivity.db.updateTask(myTasks.get(i),myTasks.get(i).id);
                    mainActivity.refreshFrag();

                }
            });

            if(myTasks.get(i).alarm == 1 ){
                if(myTasks.get(i).timeH.length()<2)
                    myTasks.get(i).timeH = "0" + myTasks.get(i).timeH;
                if(myTasks.get(i).timeM.length()<2)
                    myTasks.get(i).timeM = "0" + myTasks.get(i).timeM;

                viewHolder.alarmTime.setText(myTasks.get(i).timeH+":"+myTasks.get(i).timeM);
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

