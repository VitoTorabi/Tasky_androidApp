package com.program.android.vito.tasky;


public class MyTask {
    public String title;
    public String text;
    public String alarmTime = null;
    public String deadline;



    public MyTask(String title, String text, String deadline){
        this.text = text;
        this.title = title;
        this.deadline = deadline;
    }
    public void setAlarmTime(String alarmTime){
        this.alarmTime = alarmTime;
    }

}
