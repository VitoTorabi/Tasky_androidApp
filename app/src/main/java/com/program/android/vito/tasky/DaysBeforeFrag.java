package com.program.android.vito.tasky;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Calendar;

public class DaysBeforeFrag extends Fragment {


    ArrayList<MyTask> tasks = new ArrayList<>();
    RecyclerView taskRV;
    TaskAdapter adapter;
    LinearLayoutManager llm;
    ImageButton addTask ;
    public Onclick onclick;
    ArrayList<MyTask> doneTasks = new ArrayList<>();
    RecyclerView doneTaskRV;
    DoneTaskAdapter doneAdapter;
    LinearLayoutManager doneLlm;
    public Data data;
    View view;
    int index;
    Calendar calendar;
    MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.days_before_frag,container,false);


        mainActivity = (MainActivity) getActivity();
        calendar = Calendar.getInstance();

        index = mainActivity.day -Integer.valueOf((String) mainActivity.clicked.getTag());


        data = mainActivity.db.listTasks(
                String.valueOf(calendar.get(android.icu.util.Calendar.MONTH)),
                String.valueOf(calendar.get(android.icu.util.Calendar.DAY_OF_MONTH)-index));

        if(data!=null) {
            if (data.todo != null)
                tasks = new ArrayList<>();
            tasks.addAll(data.todo);
            if (data.done != null)
                doneTasks = new ArrayList<>();
            doneTasks.addAll(data.done);
        }


        taskRV = view.findViewById(R.id.task_rv_overdue);
        adapter = new TaskAdapter(tasks,mainActivity);
        llm = new LinearLayoutManager(mainActivity,LinearLayoutManager.VERTICAL,false);
        taskRV.setLayoutManager(llm);
        taskRV.setAdapter(adapter);

        doneTaskRV = view.findViewById(R.id.task_rv_done);
        doneAdapter = new DoneTaskAdapter(doneTasks,mainActivity);
        doneLlm = new LinearLayoutManager(mainActivity,LinearLayoutManager.VERTICAL,false);
        doneTaskRV.setLayoutManager(doneLlm);
        doneTaskRV.setAdapter(doneAdapter);


        ImageView noTask = view.findViewById(R.id.noDone);
        if(doneTasks.size() == 0)
            noTask.setVisibility(View.VISIBLE);
        else
            noTask.setVisibility(View.GONE);

        ImageView noOver = view.findViewById(R.id.noOverdue);
        if(tasks.size() == 0)
            noOver.setVisibility(View.VISIBLE);
        else
            noOver.setVisibility(View.GONE);



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        data = mainActivity.db.listTasks(
                String.valueOf(calendar.get(android.icu.util.Calendar.MONTH)),
                String.valueOf(calendar.get(android.icu.util.Calendar.DAY_OF_MONTH)-index));

        if(data!=null) {
            if (data.todo != null)
                tasks = new ArrayList<>();
            tasks.addAll(data.todo);
            if (data.done != null)
                doneTasks = new ArrayList<>();
            doneTasks.addAll(data.done);
        }
        taskRV = view.findViewById(R.id.task_rv_overdue);
        adapter = new TaskAdapter(tasks,mainActivity);
        llm = new LinearLayoutManager(mainActivity,LinearLayoutManager.VERTICAL,false);
        taskRV.setLayoutManager(llm);
        taskRV.setAdapter(adapter);

        doneTaskRV = view.findViewById(R.id.task_rv_done);
        doneAdapter = new DoneTaskAdapter(doneTasks,mainActivity);
        doneLlm = new LinearLayoutManager(mainActivity,LinearLayoutManager.VERTICAL,false);
        doneTaskRV.setLayoutManager(doneLlm);
        doneTaskRV.setAdapter(doneAdapter);

        ImageView noTask = view.findViewById(R.id.noDone);
        if(doneTasks.size() == 0)
            noTask.setVisibility(View.VISIBLE);
        else
            noTask.setVisibility(View.GONE);

        ImageView noOver = view.findViewById(R.id.noOverdue);
        if(tasks.size() == 0)
            noOver.setVisibility(View.VISIBLE);
        else
            noOver.setVisibility(View.GONE);
    }
}
