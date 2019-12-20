package com.program.android.vito.tasky;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;


import java.util.List;

public class DoneTaskAdapter extends RecyclerView.Adapter<DoneTaskAdapter.DoneTaskViewHolder> {
    List<MyTask> dones;

    MainActivity mainActivity;

    public DoneTaskAdapter(List<MyTask> doneTasks, MainActivity mainActivity) {
        this.dones = doneTasks;
        this.mainActivity = mainActivity;
    }

    @Override
    public DoneTaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_done, viewGroup, false);
        DoneTaskAdapter.DoneTaskViewHolder pvh = new DoneTaskAdapter.DoneTaskViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final DoneTaskViewHolder doneTaskViewHolder, final int i) {
        doneTaskViewHolder.title.setText(dones.get(i).title);

        doneTaskViewHolder.frameLayout.setOnTouchListener(new OnSwipeTouchListener(mainActivity){
            @Override
            public void onSwipeLeft() {
                Log.d(""+dones.get(i).id,"left");
                mainActivity.db.deleteTask(dones.get(i).id);
                mainActivity.refreshFrag();
            }
            @Override
            public void onSwipeRight() {
                Log.d(""+dones.get(i).id,"right");
                mainActivity.db.setDone(dones.get(i).id,0);
                mainActivity.refreshFrag();
            }
        });

        doneTaskViewHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(""+dones.get(i).id,"onclick");
                Onclick onclick = mainActivity.taskOnClick(dones.get(i));
                onclick.onClickL();
            }
        });
    }



    @Override
    public int getItemCount() {
        return dones.size();
    }

    public static class DoneTaskViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        FrameLayout frameLayout;
        ImageButton check;

        DoneTaskViewHolder(View itemView) {
            super(itemView);
            check = itemView.findViewById(R.id.check_done);
            frameLayout = itemView.findViewById(R.id.done_task_frame);
            title = itemView.findViewById(R.id.done_task_id);


        }
    }
}
