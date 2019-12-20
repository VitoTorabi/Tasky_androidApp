package com.program.android.vito.tasky;


public class MyTask {
    public int id ;
    public String day;
    public String month;
    public String title;
    public String text;
    public String timeH;
    public String timeM;
    public String alarmM = null;
    public String alarmH = null;



    public MyTask(String dateM, String dateD, String title, String text, String h, String m){
        this.text = text;
        this.day = dateD;
        this.month = dateM;
        this.title = title;
        this.timeH = h;
        this.timeM = m;
    }
    public void setId(int id) {this.id = id; }
    public void setAlarmTime(String h, String m){
        this.alarmH = h;
        this.alarmM = m;
    }

    public boolean isEqual(MyTask t){
        if(this.text.equals(text) &&
                this.day.equals(t.day) &&
                this.month.equals(t.month) && this.title.equals(t.title) &&
                this.timeH.equals(t.timeH) &&
                this.timeM.equals(t.timeM))
            return true;
        else return false;

    }

}
