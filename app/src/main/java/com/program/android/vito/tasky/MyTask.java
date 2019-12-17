package com.program.android.vito.tasky;


public class MyTask {
    public int id ;
    public String date;
    public String title;
    public String text;
    public String deadline;
    public String alarmTime = null;



    public MyTask(int id, String date, String title, String text, String deadline){
        this.text = text;
        this.id = id;
        this.date = date;
        this.title = title;
        this.deadline = deadline;
    }
    public void setAlarmTime(String alarmTime){
        this.alarmTime = alarmTime;
    }

}
