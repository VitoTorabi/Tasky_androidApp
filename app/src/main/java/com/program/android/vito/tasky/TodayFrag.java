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

import java.util.ArrayList;

public class TodayFrag extends Fragment {


    ArrayList<MyTask> tasks;
    RecyclerView taskRV;
    TaskAdapter adapter;
    LinearLayoutManager llm;

    ArrayList<MyTask> doneTasks;
    RecyclerView doneTaskRV;
    DoneTaskAdapter doneAdapter;
    LinearLayoutManager doneLlm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.today_frag,container,false);
        tasks = new ArrayList<>();
        doneTasks = new ArrayList<>();
        tasks.add(new MyTask("Vito","salam boro dars bekhon golam"));
        tasks.add(new MyTask("reply","aleyk salam ghalat nakhor golam"));
        tasks.add(new MyTask("reply","aleyk salam ghalat nakhor golam"));
        doneTasks.add(new MyTask("You did it","aleyk salam ghalat nakhor golam"));
        doneTasks.add(new MyTask("Yeah I did","aleyk salam ghalat nakhor golam"));
        doneTasks.add(new MyTask("Nice work","aleyk salam ghalat nakhor golam"));
        doneTasks.add(new MyTask("Thanks bro","aleyk salam ghalat nakhor golam"));

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

        return view;
    }
}
