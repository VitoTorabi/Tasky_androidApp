package com.program.android.vito.tasky;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class SqliteDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "tasky";
    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_INDEX = "task_index", COLUMN_TITLE = "title",
            COLUMN_DONE = "is_done", COLUMN_TEXT = "text", COLUMN_TIME_H = "time_hours",
            COLUMN_ALARM = "alarm", COLUMN_DATE_DAY = "date_d",
            COLUMN_DATE_MONTH = "date_m",COLUMN_TIME_M = "time_minutes",
            COLUMN_IMAGE_PATH = "image_path";

    public SqliteDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE    TABLE " + TABLE_TASKS
                + "(" + COLUMN_INDEX + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DONE + " INTEGER,"
                + COLUMN_DATE_MONTH + " TEXT,"
                + COLUMN_DATE_DAY + " TEXT,"
                + COLUMN_TITLE+ " TEXT,"
                + COLUMN_TEXT + " TEXT,"
                + COLUMN_TIME_H + " TEXT,"
                + COLUMN_TIME_M + " TEXT,"
                + COLUMN_ALARM + " INTEGER,"
                + COLUMN_IMAGE_PATH + " TEXT"
                + ")";
        db.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    public Data listTasks(String dateM, String dateD){ //TODO
        String sql = "select * from " + TABLE_TASKS + " where " +
                COLUMN_DATE_DAY + " = ? " + " and " +
                COLUMN_DATE_MONTH + " = ? ";
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MyTask> todoTasks = new ArrayList<>();
        ArrayList<MyTask> doneTasks = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,new String[] { dateD , dateM});
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                int done = Integer.parseInt(cursor.getString(1));
                String month = cursor.getString(2);
                String day = cursor.getString(3);
                String title = cursor.getString(4);
                String text = cursor.getString(5);
                String timeH = cursor.getString(6);
                String timeM = cursor.getString(7);
                int alarm = Integer.parseInt(cursor.getString(8));
                String image = cursor.getString(9);

                Log.d(String.valueOf(id), String.valueOf(alarm));

                MyTask temp = new MyTask(month, day, title, text, timeH, timeM);
                temp.setId(id);
                if (alarm == 1)
                    temp.setAlarm();
                else temp.alarm = 0;

                if (image != null)
                    temp.setImagePath(image);

                if (done ==0)
                    todoTasks.add(temp);
                else if (done == 1)
                    doneTasks.add(temp);

            }while (cursor.moveToNext());
        }
        cursor.close();
        Data data = new Data(todoTasks, doneTasks);
        return data;
    }

    public void updateTask(MyTask task, int id){ //TODO doesn't work :|
        ContentValues values = new ContentValues();
        String month = task.month;
        String day = task.day;
        String alarm = String.valueOf(task.alarm);
        String text = task.text;
        String timeH = task.timeH;
        String timeM = task.timeM;
        String title = task.title;
        String image = task.imagePath;
        values.put(COLUMN_DATE_MONTH , month);
        values.put(COLUMN_DATE_DAY , day);
        values.put(COLUMN_ALARM , alarm);
        values.put(COLUMN_TEXT, text);
        values.put(COLUMN_TIME_H, timeH);
        values.put(COLUMN_TIME_M, timeM);
        values.put(COLUMN_TITLE , title);
        values.put(COLUMN_IMAGE_PATH , image);

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_TASKS,values,COLUMN_INDEX + "  = ? ",
                new String[] { String.valueOf(id)});
        Log.d(""+task.id,"updated");
    }

    public void setDone(int id, int done){
        ContentValues values = new ContentValues();
        values.put(COLUMN_DONE, done);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_TASKS,values, COLUMN_INDEX + "  = ? ",
                new String[] { String.valueOf(id)});
        Log.d(""+id,"done");
    }

    public void addTask(MyTask task){

        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE_MONTH , task.month);
        values.put(COLUMN_DATE_DAY , task.day);
        values.put(COLUMN_ALARM , String.valueOf(task.alarm));
        values.put(COLUMN_TEXT, task.text);
        values.put(COLUMN_TIME_H, task.timeH);
        values.put(COLUMN_TIME_M, task.timeM);
        values.put(COLUMN_TITLE , task.title);
        values.put(COLUMN_IMAGE_PATH , task.imagePath);
        values.put(COLUMN_DONE , 0);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_TASKS, null, values);
    }

//    public MyTask findTask(int i){
//        String query = "Select * FROM "    + TABLE_TASKS + " WHERE " + COLUMN_INDEX + " = " + i;
//        SQLiteDatabase db = this.getWritableDatabase();
//        MyTask task = null;
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor.moveToFirst()){
//            int id = Integer.parseInt(cursor.getString(0));
//            String levelName = cursor.getString(1);
//            int productQuantity = Integer.parseInt(cursor.getString(2));
//            task = new MyTask(0, "", "", "", "");
//        }
//        cursor.close();
//        return task;
//    }

    public void deleteTask(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, COLUMN_INDEX    + "    = ?", new String[] { String.valueOf(id)});
        Log.d(""+id,"delete");
    }
}

class Data{
    public ArrayList<MyTask> todo;
    public ArrayList<MyTask> done;
    public Data(ArrayList<MyTask> todo, ArrayList<MyTask> done){
        this.todo = todo;
        this.done = done;
    }
}


