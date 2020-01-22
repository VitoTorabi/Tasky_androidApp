package com.program.android.vito.tasky;


public class MyTask {
    public int id ;
    public String day;
    public String month;
    public String title;
    public String text;
    public String timeH;
    public String timeM;
    public String imagePath = null;
    public int alarm = 0;



    public MyTask(String dateM, String dateD, String title, String text, String h, String m){
        this.text = text;
        this.day = dateD;
        this.month = dateM;
        this.title = title;
        this.timeH = h;
        this.timeM = m;
    }
    public void setId(int id) { this.id = id; }
    public void setAlarm(){ this.alarm = 1; }

    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

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
