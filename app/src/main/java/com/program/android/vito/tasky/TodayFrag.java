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

public class TodayFrag extends Fragment {


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
    Calendar calendar;
    MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.today_frag,container,false);

        addTask = view.findViewById(R.id.addTask);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.onClickL();

            }
        });



        mainActivity = (MainActivity) getActivity();
        calendar = Calendar.getInstance();


        data = mainActivity.db.listTasks(
                String.valueOf(calendar.get(android.icu.util.Calendar.MONTH)),
                String.valueOf(calendar.get(android.icu.util.Calendar.DAY_OF_MONTH)));

        if(data!=null) {
            if (data.todo != null)
                tasks = new ArrayList<>();
                tasks.addAll(data.todo);
            if (data.done != null)
                doneTasks = new ArrayList<>();
                doneTasks.addAll(data.done);
        }


        taskRV = view.findViewById(R.id.task_rv_todo);
        adapter = new TaskAdapter(tasks,mainActivity);
        llm = new LinearLayoutManager(mainActivity,LinearLayoutManager.VERTICAL,false);
        taskRV.setLayoutManager(llm);
        taskRV.setAdapter(adapter);

        doneTaskRV = view.findViewById(R.id.task_rv_done);
        doneAdapter = new DoneTaskAdapter(doneTasks,mainActivity);
        doneLlm = new LinearLayoutManager(mainActivity,LinearLayoutManager.VERTICAL,false);
        doneTaskRV.setLayoutManager(doneLlm);
        doneTaskRV.setAdapter(doneAdapter);
        if(doneTasks.size() == 0){
            ImageView noTask = view.findViewById(R.id.noTaskDone);
            noTask.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        data = mainActivity.db.listTasks(
                String.valueOf(calendar.get(android.icu.util.Calendar.MONTH)),
                String.valueOf(calendar.get(android.icu.util.Calendar.DAY_OF_MONTH)));

        if(data!=null) {
            if (data.todo != null)
                tasks = new ArrayList<>();
            tasks.addAll(data.todo);
            if (data.done != null)
                doneTasks = new ArrayList<>();
            doneTasks.addAll(data.done);
        }
        taskRV = view.findViewById(R.id.task_rv_todo);
        adapter = new TaskAdapter(tasks,mainActivity);
        llm = new LinearLayoutManager(mainActivity,LinearLayoutManager.VERTICAL,false);
        taskRV.setLayoutManager(llm);
        taskRV.setAdapter(adapter);

        doneTaskRV = view.findViewById(R.id.task_rv_done);
        doneAdapter = new DoneTaskAdapter(doneTasks,mainActivity);
        doneLlm = new LinearLayoutManager(mainActivity,LinearLayoutManager.VERTICAL,false);
        doneTaskRV.setLayoutManager(doneLlm);
        doneTaskRV.setAdapter(doneAdapter);
    }
}
