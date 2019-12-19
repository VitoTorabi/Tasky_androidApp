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

public class TodayFrag extends Fragment {


    ArrayList<MyTask> tasks;
    RecyclerView taskRV;
    TaskAdapter adapter;
    LinearLayoutManager llm;
    ImageButton addTask ;
    public Onclick onclick;
    ArrayList<MyTask> doneTasks;
    RecyclerView doneTaskRV;
    DoneTaskAdapter doneAdapter;
    LinearLayoutManager doneLlm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.today_frag,container,false);

        addTask = view.findViewById(R.id.addTask);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.onClickL();
            }
        });


        tasks = new ArrayList<>();
        doneTasks = new ArrayList<>();
//        tasks.add(new MyTask("Vito","salam boro dars bekhon golam","08:00"));
//        tasks.add(new MyTask("Vito","salam boro dars bekhon golam","12:00"));
//        tasks.add(new MyTask("Vito","salam boro dars bekhon golam","7:30"));
//
//        doneTasks.add(new MyTask("Vito","salam boro dars bekhon golam","7:30"));
//        doneTasks.add(new MyTask("Vito","salam boro dars bekhon golam","7:30"));
//        doneTasks.add(new MyTask("Vito","salam boro dars bekhon golam","7:30"));
//        tasks.get(1).setAlarmTime("08:30");


        MainActivity mainActivity = (MainActivity) getActivity();

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
}
