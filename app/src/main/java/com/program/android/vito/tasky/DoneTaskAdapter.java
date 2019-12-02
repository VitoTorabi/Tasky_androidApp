package com.program.android.vito.tasky;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public void onBindViewHolder(@NonNull DoneTaskViewHolder doneTaskViewHolder, int i) {
        doneTaskViewHolder.title.setText(dones.get(i).title);
    }



    @Override
    public int getItemCount() {
        return dones.size();
    }

    public static class DoneTaskViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        DoneTaskViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.done_task_id);


        }
    }
}
